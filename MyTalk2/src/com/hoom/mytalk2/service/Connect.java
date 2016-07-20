package com.hoom.mytalk2.service;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.hoom.mytalk2.adapter.MsgListAdapter;
import com.hoom.mytalk2.ui.ChattingActivity;
import com.hoom.mytalk2.ui.R;
import com.hoom.mytalk2.util.TextMessage;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

/**
 * 后台监听是否新消息
 * @author hmd
 * @version 1.0
 * 
 */
public class Connect extends Service{

	public static final int CONNECTFLAG=0x01;//连接
	public static final int SENDFLAG=0x02;//发送信息
	public static final int AUDIOFLAG=0x03;//发送语音
	public static final int VIDEOFLAG=0x04;
	public static final int IMAGEFLAG=0x05;
	public static final int EXPRESSIONFLAG=0x06;//发送表情
	public static final int TALKREQUESTFLAG=0x07;//发送语音通话请求
	public static final int STOPTALKFLAG=0x0b;//停止语音通话
	public static final int SENDAUDIOFLAG=0x08;
	public static final int RECEAUDIOFLAG=0x09;
	public static final int DISCONNECTFLAG=0x0a;
	public static String MYNAME;
	public static String TARGET;
	
	private static final String mFileReceivePath=Environment.getExternalStorageDirectory().
			getAbsolutePath()+"/audioReceive";//接收的语音文件路径
	private static final String mFileSendPath=Environment.getExternalStorageDirectory().
			getAbsolutePath()+"/audioSend";//发送的语音文件路径
	
	public static final String newMsgAction="com.hoom.action.NEW_MESSAGE";
	public static final String talkReqAction="com.hoom.action.TALK_REQUEST";
	public static final String talkStopAction="com.hoom.action.TALK_STOP";
	public static final String IPAddress="192.168.137.1";//服务器IP
	public static MsgListAdapter msgListAdapter;
	private boolean connectFlag=true;//是否像服务器发送链接请求
	
	private broadcastReceiver broadcastRece;
	private ServiceBinder sBinder = new ServiceBinder();//服务绑定器
	private boolean isTalking;//是否正在通话中
	private String targetIP=null;//目标IP
	private  InetAddress localInetAddress = null;
	private String localIp = null;//本机IP
	private WifiManager wifiManager = null;

	//服务绑定
	public class ServiceBinder extends Binder{
		public Connect getService(){
			return Connect.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return sBinder;
	}
	
	
	public void onCreate(){
		super.onCreate();
		
		msgListAdapter=new MsgListAdapter(this);
		regBroadcastRece();
		new ConnectThread().start();
		Log.v("service","create");
		System.out.println("service create");
		
		wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		new CheckNetConnectivity().start();//侦测网络状态，获取IP地址
	}
	
	//检测网络，获取本地IP
	private class CheckNetConnectivity extends Thread {
		public void run() {
			try {
				if (!wifiManager.isWifiEnabled()) {
					wifiManager.setWifiEnabled(true);
				}
				
				for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							if(inetAddress.isReachable(1000)){
								localInetAddress = inetAddress;
								localIp = inetAddress.getHostAddress().toString();
								//localIpBytes = inetAddress.getAddress();
								//System.arraycopy(localIpBytes,0,regBuffer,44,4);
							}
						}
					}
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		};
	}
	
	//每一秒钟连接一次服务器，检测是否有消息
	class ConnectThread extends Thread {
			public void run(){
				int pic=0;
	        	if(MYNAME.equals("TQ"))
	        		pic=R.drawable.my_pic;
	        	else
	        		pic=R.drawable.target_pic;
				while(connectFlag){
					//Log.v("running",""+connectFlag);
					//System.out.println("connecting");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Socket socket = new Socket(IPAddress, 7777);
						//Log.v("MX","connected");
						DataOutputStream dos = new DataOutputStream(
		                        socket.getOutputStream());
						dos.writeInt(CONNECTFLAG);
						dos.writeUTF(MYNAME);
						dos.writeUTF(localIp);
						DataInputStream dis = new DataInputStream(
		                        socket.getInputStream());
						boolean Talking=dis.readBoolean();
						boolean stop=dis.readBoolean();
						if(stop){
							isTalking=false;
							sendTalkStopBroadcast();
						}
						targetIP=dis.readUTF();
						if(Talking){
							isTalking=true;
							//new TalkingSendThread().start();
							Log.v("talking","request");
							new TalkingReceThread(8888).start();
							new TalkingSendThread(8889).start();
							sendTalkRequestBroadcast();
						}
						int msgAmount=dis.readInt();
				        for(int i=0;i<msgAmount;i++){
				        	
				        	msgListAdapter.addData(new TextMessage(pic,MsgListAdapter.TYPE_RECE,
				        			dis.readUTF()));
				        	sendGetNewMsgBroadcast();
				        }
				        
				       // System.out.println(msgList);
				        
				        int audioAmount=dis.readInt(); 
				        if(audioAmount!=0){
				        	long fileName=System.currentTimeMillis();
				        	//audioList.add(""+fileName);
				        	BufferedOutputStream bos=new BufferedOutputStream(
									new FileOutputStream(new File(
											mFileReceivePath+"/"+fileName+".3gp")));
							int bytesRead = 0;  
				            byte[] buffer = new byte[1024];  
				            while ((bytesRead = dis.read(buffer, 0, buffer.length)) != -1) {  
				                bos.write(buffer, 0, bytesRead);  
				            }  
				            //bos.flush();  
				            bos.close();
				            msgListAdapter.addData(new TextMessage(pic,MsgListAdapter.TYPE_RECE_AUDIO,
				        			""+fileName));
				        	sendGetNewMsgBroadcast();
				        }
				        
				        int photoAmount=dis.readInt(); 
				        if(photoAmount!=0){
				        	long fileName=System.currentTimeMillis();
				        	//audioList.add(""+fileName);
				        	String filePath=mFileReceivePath+"/"+fileName+".png";
				        	BufferedOutputStream bos=new BufferedOutputStream(
									new FileOutputStream(new File(filePath)));
							int bytesRead = 0;  
				            byte[] buffer = new byte[1024];  
				            while ((bytesRead = dis.read(buffer, 0, buffer.length)) != -1) {  
				                bos.write(buffer, 0, bytesRead);  
				            }  
				            //bos.flush();  
				            bos.close();
				            msgListAdapter.addData(new TextMessage(pic,MsgListAdapter.TYPE_RECE_PHOTO,
				        			""+filePath));
				        	sendGetNewMsgBroadcast();
				        }
				        
				        int expsAmount=dis.readInt();
				        for(int i=0;i<expsAmount;i++){
				        	msgListAdapter.addData(new TextMessage(pic,MsgListAdapter.TYPE_RECE_EXP,
				        			""+dis.readInt()));
				        	sendGetNewMsgBroadcast();
				        }
				        
				        socket.close();
				        dos.close();
				        dis.close();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	//开启新线程发送消息
	private	class SendMessageThread extends Thread {

			String msg;
			public SendMessageThread(String msg){
				this.msg=msg;
			}
			
			public void run() {
	            try {
	                Socket socket = new Socket(IPAddress, 7777);
	                DataOutputStream dos = new DataOutputStream(
	                        socket.getOutputStream());
	                dos.writeInt(SENDFLAG);Log.v("send",""+"message");
	                dos.writeUTF(MYNAME);Log.v("send",""+MYNAME);
	                dos.writeUTF(TARGET);Log.v("send",""+TARGET);
	                dos.writeUTF(msg);
	                Log.v("send",msg);

	                //DataInputStream dis = new DataInputStream(
	                //        socket.getInputStream());
	                socket.close();
	                dos.close();
	                //dis.close();
	                
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	}
	
	/*
	 * 开启新线程发送语音
	 * 包括  audioFlag+myName+target+fileName+fileData
	 */
		class SendAudioThread extends Thread {

			private String mFileName=null;
			public SendAudioThread(String fileName){
				this.mFileName=fileName;
			}
	        public void run() {
	        	
	            try {
	                Socket socket = new Socket(IPAddress, 7777);
	                //Log.v("MX","sended");
	                DataOutputStream dos = new DataOutputStream(
	                        socket.getOutputStream());
	                dos.writeInt(AUDIOFLAG);Log.v("send",""+"audio");
	                dos.writeUTF(MYNAME);Log.v("send",""+MYNAME);
	                dos.writeUTF(TARGET);Log.v("send",""+TARGET);
	                dos.writeUTF(MYNAME+mFileName);
	                
	                FileInputStream reader=new FileInputStream(
	                		new File(mFileSendPath+"/"+mFileName+".3gp"));
	                int bufferSize=10240;
	                byte[] buf=new byte[bufferSize];
	                int read=0;
	                while((read=reader.read(buf,0,buf.length))!=-1){
	                	dos.write(buf,0,read);
	                }
	                
	                //DataInputStream dis = new DataInputStream(
	                //        socket.getInputStream());
	                socket.close();
	                dos.close();
	                reader.close();
	                //dis.close();
	                
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	    }
	private class SendPhotoThread extends Thread{
		
		public String path;
		public SendPhotoThread(String path){
			this.path=path;
		}
		public void run(){
			try {
                Socket socket = new Socket(IPAddress, 7777);
                //Log.v("MX","sended");
                DataOutputStream dos = new DataOutputStream(
                        socket.getOutputStream());
                dos.writeInt(IMAGEFLAG);Log.v("send",""+"image");
                dos.writeUTF(MYNAME);Log.v("send",""+MYNAME);
                dos.writeUTF(TARGET);Log.v("send",""+TARGET);
                dos.writeUTF(MYNAME+path);
                
                FileInputStream reader=new FileInputStream(
                		new File(path));
                int bufferSize=10240;
                byte[] buf=new byte[bufferSize];
                int read=0;
                while((read=reader.read(buf,0,buf.length))!=-1){
                	dos.write(buf,0,read);
                }
                
                //DataInputStream dis = new DataInputStream(
                //        socket.getInputStream());
                socket.close();
                dos.close();
                reader.close();
                //dis.close();
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
	}
		
	//发送表情
	private class SendExpressionThread extends Thread{
		
		int id;
		public SendExpressionThread(int id){
			this.id=id;
		}
		
		public void run(){
			try {
				Socket socket=new Socket(IPAddress,7777);
				DataOutputStream dos=new DataOutputStream(
						socket.getOutputStream());
				dos.writeInt(EXPRESSIONFLAG);
				dos.writeUTF(MYNAME);
                dos.writeUTF(TARGET);
                dos.writeInt(id);
                Log.v("expr",""+id);
                socket.close();
                dos.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	} 
	
	
	//发送语音通话请求
	private class SendTalkRequestThread extends Thread{
		
		boolean stop=false;
		public SendTalkRequestThread(boolean stop){
			this.stop=stop;
		}
		
		public void run(){
			try {
				Socket socket=new Socket(IPAddress,7777);
				DataOutputStream dos=new DataOutputStream(
						socket.getOutputStream());
				if(!stop)
					dos.writeInt(TALKREQUESTFLAG);
				else 	
					dos.writeInt(STOPTALKFLAG);
				dos.writeUTF(MYNAME);
                dos.writeUTF(TARGET);
                
                if(!stop){
                	Log.v("send","talkRequest");
                	isTalking=true;
                	new TalkingSendThread(8888).start();
                	new TalkingReceThread(8889).start();
					//new TalkingReceThread().start();
                }
                socket.close();
                dos.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//发送实时语音数据
	private class TalkingSendThread extends Thread{
		
		private int port;
		public TalkingSendThread(int port){
			this.port=port;
		}
		
		public void run(){
			Socket socket=null;
			DataOutputStream dos=null;
			AudioRecord recorder = null;
			try {
				if(targetIP==null){
					Log.v("connect","target ip is null");
				}
				Log.v("connect","target ip is "+targetIP);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket = new Socket(targetIP,port);
				socket.setSoTimeout(5000);
				dos=new DataOutputStream(
						socket.getOutputStream());
				Log.v("connect","talkingsend");
              //获得录音缓冲区大小
				int bufferSize = AudioRecord.getMinBufferSize(8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT);
				
				//获得录音机对象
				recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
						8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT,
						bufferSize*10);
				
				recorder.startRecording();//开始录音
				byte[] readBuffer = new byte[640];//录音缓冲区
				
				int length = 0;
				while(isTalking){
					length = recorder.read(readBuffer,0,640);//从mic读取音频数据
					if(length>0 && length%2==0){
						dos.write(readBuffer,0,length);//写入到输出流，把音频数据通过网络发送给对方
					}
				}
				Log.v("talk","send stop");
				recorder.stop();
				recorder.release();
				dos.close();
				socket.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
			
		}
		
	}
	//接收实时语音数据
	private class TalkingReceThread extends Thread{
		private ServerSocket sSocket = null;
		private int port;
		
		public TalkingReceThread(int port){
			this.port=port;
		}
		
		public void run(){
			
			try {
				Log.v("connect","ip is"+targetIP);
				sSocket = new ServerSocket(port);//监听音频端口
				Socket socket = sSocket.accept();
				DataInputStream dis=new DataInputStream(
						socket.getInputStream());
				Log.v("connect","talkingreceive");
				//Log.v("connect","ip is"+targetIP);
	          //获得音频缓冲区大小
				int bufferSize = android.media.AudioTrack.getMinBufferSize(8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT);

				//获得音轨对象
				AudioTrack player = new AudioTrack(AudioManager.STREAM_MUSIC, 
						8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT,
						bufferSize,
						AudioTrack.MODE_STREAM);

				//设置喇叭音量
				player.setStereoVolume(1.0f, 1.0f);
				//开始播放声音
				player.play();
				byte[] audio = new byte[160];//音频读取缓存
				int length = 0;
				
				while(isTalking){
					length = dis.read(audio);//从网络读取音频数据
					if(length>0 && length%2==0){
					//	for(int i=0;i<length;i++)audio[i]=(byte)(audio[i]*2);//音频放大1倍
						player.write(audio, 0, length);//播放音频数据
					}
				}
				Log.v("talk","rece stop");
				player.stop();
				dis.close();
				socket.close();
				sSocket.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	//发送广播，收到新消息，通知更新UI
	private void sendGetNewMsgBroadcast(){
		Intent it=new Intent();
		it.setAction(newMsgAction);
		sendBroadcast(it);
	}
	//发送广播 进入通话界面
	private void sendTalkRequestBroadcast(){
		Intent it=new Intent();
		it.setAction(talkReqAction);
		sendBroadcast(it);
	}
	//发送广播 关闭通话界面
	private void sendTalkStopBroadcast(){
		Intent it=new Intent();
		it.setAction(talkStopAction);
		sendBroadcast(it);
	}
	
	//注册广播接收器
	private void regBroadcastRece(){
		broadcastRece=new broadcastReceiver();
		IntentFilter itFilter=new IntentFilter();
		
		registerReceiver(broadcastRece,itFilter);
	}
	
	private class broadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	//向对方发送消息
	public void sendMsg(String msg){
		new SendMessageThread(msg).start();
	}
	
	//向对方发送语音
	public void sendAudio(String fileName){
		new SendAudioThread(fileName).start();
	}
	//发送表情
	public void sendExpression(int id){
		new SendExpressionThread(id).start();
	}
	//发送图片
	public void sendPhoto(String path){
		new SendPhotoThread(path).start();
	}
	
	//语音通话请求
	public void sendTalkRequest(){
		new SendTalkRequestThread(false).start();
		
	}
	//语音通话结束请求
	public void stopTalk(){
		new SendTalkRequestThread(true).start();
		isTalking=false;
	}
	
	public void onDestroy(){
		super.onDestroy();
		Log.v("service","destroy");
		connectFlag=false;
		unregisterReceiver(broadcastRece);
	}

}
