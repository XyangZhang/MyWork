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
 * ��̨�����Ƿ�����Ϣ
 * @author hmd
 * @version 1.0
 * 
 */
public class Connect extends Service{

	public static final int CONNECTFLAG=0x01;//����
	public static final int SENDFLAG=0x02;//������Ϣ
	public static final int AUDIOFLAG=0x03;//��������
	public static final int VIDEOFLAG=0x04;
	public static final int IMAGEFLAG=0x05;
	public static final int EXPRESSIONFLAG=0x06;//���ͱ���
	public static final int TALKREQUESTFLAG=0x07;//��������ͨ������
	public static final int STOPTALKFLAG=0x0b;//ֹͣ����ͨ��
	public static final int SENDAUDIOFLAG=0x08;
	public static final int RECEAUDIOFLAG=0x09;
	public static final int DISCONNECTFLAG=0x0a;
	public static String MYNAME;
	public static String TARGET;
	
	private static final String mFileReceivePath=Environment.getExternalStorageDirectory().
			getAbsolutePath()+"/audioReceive";//���յ������ļ�·��
	private static final String mFileSendPath=Environment.getExternalStorageDirectory().
			getAbsolutePath()+"/audioSend";//���͵������ļ�·��
	
	public static final String newMsgAction="com.hoom.action.NEW_MESSAGE";
	public static final String talkReqAction="com.hoom.action.TALK_REQUEST";
	public static final String talkStopAction="com.hoom.action.TALK_STOP";
	public static final String IPAddress="192.168.137.1";//������IP
	public static MsgListAdapter msgListAdapter;
	private boolean connectFlag=true;//�Ƿ��������������������
	
	private broadcastReceiver broadcastRece;
	private ServiceBinder sBinder = new ServiceBinder();//�������
	private boolean isTalking;//�Ƿ�����ͨ����
	private String targetIP=null;//Ŀ��IP
	private  InetAddress localInetAddress = null;
	private String localIp = null;//����IP
	private WifiManager wifiManager = null;

	//�����
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
		new CheckNetConnectivity().start();//�������״̬����ȡIP��ַ
	}
	
	//������磬��ȡ����IP
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
	
	//ÿһ��������һ�η�����������Ƿ�����Ϣ
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
	
	//�������̷߳�����Ϣ
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
	 * �������̷߳�������
	 * ����  audioFlag+myName+target+fileName+fileData
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
		
	//���ͱ���
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
	
	
	//��������ͨ������
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
	//����ʵʱ��������
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
              //���¼����������С
				int bufferSize = AudioRecord.getMinBufferSize(8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT);
				
				//���¼��������
				recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
						8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT,
						bufferSize*10);
				
				recorder.startRecording();//��ʼ¼��
				byte[] readBuffer = new byte[640];//¼��������
				
				int length = 0;
				while(isTalking){
					length = recorder.read(readBuffer,0,640);//��mic��ȡ��Ƶ����
					if(length>0 && length%2==0){
						dos.write(readBuffer,0,length);//д�뵽�����������Ƶ����ͨ�����緢�͸��Է�
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
	//����ʵʱ��������
	private class TalkingReceThread extends Thread{
		private ServerSocket sSocket = null;
		private int port;
		
		public TalkingReceThread(int port){
			this.port=port;
		}
		
		public void run(){
			
			try {
				Log.v("connect","ip is"+targetIP);
				sSocket = new ServerSocket(port);//������Ƶ�˿�
				Socket socket = sSocket.accept();
				DataInputStream dis=new DataInputStream(
						socket.getInputStream());
				Log.v("connect","talkingreceive");
				//Log.v("connect","ip is"+targetIP);
	          //�����Ƶ��������С
				int bufferSize = android.media.AudioTrack.getMinBufferSize(8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT);

				//����������
				AudioTrack player = new AudioTrack(AudioManager.STREAM_MUSIC, 
						8000,
						AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT,
						bufferSize,
						AudioTrack.MODE_STREAM);

				//������������
				player.setStereoVolume(1.0f, 1.0f);
				//��ʼ��������
				player.play();
				byte[] audio = new byte[160];//��Ƶ��ȡ����
				int length = 0;
				
				while(isTalking){
					length = dis.read(audio);//�������ȡ��Ƶ����
					if(length>0 && length%2==0){
					//	for(int i=0;i<length;i++)audio[i]=(byte)(audio[i]*2);//��Ƶ�Ŵ�1��
						player.write(audio, 0, length);//������Ƶ����
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
	
	
	//���͹㲥���յ�����Ϣ��֪ͨ����UI
	private void sendGetNewMsgBroadcast(){
		Intent it=new Intent();
		it.setAction(newMsgAction);
		sendBroadcast(it);
	}
	//���͹㲥 ����ͨ������
	private void sendTalkRequestBroadcast(){
		Intent it=new Intent();
		it.setAction(talkReqAction);
		sendBroadcast(it);
	}
	//���͹㲥 �ر�ͨ������
	private void sendTalkStopBroadcast(){
		Intent it=new Intent();
		it.setAction(talkStopAction);
		sendBroadcast(it);
	}
	
	//ע��㲥������
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
	//��Է�������Ϣ
	public void sendMsg(String msg){
		new SendMessageThread(msg).start();
	}
	
	//��Է���������
	public void sendAudio(String fileName){
		new SendAudioThread(fileName).start();
	}
	//���ͱ���
	public void sendExpression(int id){
		new SendExpressionThread(id).start();
	}
	//����ͼƬ
	public void sendPhoto(String path){
		new SendPhotoThread(path).start();
	}
	
	//����ͨ������
	public void sendTalkRequest(){
		new SendTalkRequestThread(false).start();
		
	}
	//����ͨ����������
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
