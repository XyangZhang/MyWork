package com.hoom.TalkServer.main;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainServer {

	/**
	 * @param args
	 */
	List<String> msgToTQ; //��ŷ���TQ����Ϣ
	List<String> audioToTQ;//��ŷ���TQ������
	List<String> msgToMX; //��ŷ���MX����Ϣ
	List<String> audioToMX;//��ŷ���MX������
	List<Integer> expressionToTQ;
	List<Integer> expressionToMX;
	List<String> photoToMX;
	List<String> photoToTQ;
	ServerSocket ss;
	int isTalk;
	String MXLocalIP="0.0.0.0",TQLocalIP="0.0.0.0";
	boolean talkToMX=false;
	boolean talkToTQ=false;
	boolean stopToMX=false;
	boolean stopToTQ=false;
	
	private static final String AUDIOPATH="D:\\audio";
	private static final String PHOTOPATH="D:\\photo";
	
	public MainServer(){
		msgToTQ=new ArrayList<String>();
		msgToMX=new ArrayList<String>();
		audioToTQ=new ArrayList<String>();
		audioToMX=new ArrayList<String>();
		expressionToTQ=new ArrayList<Integer>();
		expressionToMX=new ArrayList<Integer>();
		photoToMX=new ArrayList<String>();
		photoToTQ=new ArrayList<String>();
	}
	
	public void StartServer(){
		while (true) {
			try {
				//System.out.println("�ȴ��û�...");
				ss = new ServerSocket(7777);   //����7777�˿�
				Socket socket = ss.accept();   //�������ȴ��û�����
				//System.out.println("���û�����");
				ss.close();
	            new NewClient(socket).start(); //�������̴߳�����Ϣ
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
	}
	
	public class NewClient extends Thread{
		
		public Socket client;
		public NewClient(Socket s){
			client=s;
		}
		public void run(){
			try {
				DataInputStream dis = new DataInputStream(client.getInputStream());
				DataOutputStream dos = new DataOutputStream(client.getOutputStream());
				int type=dis.readInt();
				if(type!=1)
					System.out.println("type="+type);
				switch(type){
				//ͷ�ַ��������ַ��Ͷ��Ż��ǽ������ӻ�ȡ��Ϣ
				case 0x01:
					sendMessage(dis,dos);
					//System.out.println("�û���ȡ����Ϣ");
					break;
				case 0x02:
					getMessage(dis);
					System.out.println("�û�������Ϣ");
					break;
				case 0x03:
					getAudio(dis,dos);
					System.out.println("�û���������");
					break;
				case 0x05:
					getPhoto(dis,dos);
					System.out.println("�û�����ͼƬ");
					break;
				case 0x06:
					getExpression(dis);
					System.out.println("�û����ͱ���");
					break;
				case 0x07:
					String name=dis.readUTF();
					String target=dis.readUTF();
					System.out.println("�û�����ͨ��");
					if(target.equals("MX")){
						talkToMX=true;
						dos.writeUTF(MXLocalIP);
					}
					else{
						talkToTQ=true;
						dos.writeUTF(TQLocalIP);
					}
					break;
				case 0x08:
					//getTalkingData(dis);
					break;
				case 0x0b:
					String name2=dis.readUTF();
					String target2=dis.readUTF();
					System.out.println("�û�ֹͣͨ��");
					if(target2.equals("MX"))
						stopToMX=true;
					else
						stopToTQ=true;
				}
				if(client!=null)
					client.close();
				if(dos!=null)
					dos.close();
				if(dis!=null)
					dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
		}
		//�������Ӻ����û����ʹ洢����Ϣ
		private void sendMessage(DataInputStream dis,DataOutputStream dos) throws IOException{
			FileInputStream reader=null;
			int bufferSize=10240;
            byte[] buf=new byte[bufferSize];
            int read=0;
            
            String name=dis.readUTF();
			if(name.equals("TQ")){
				TQLocalIP=dis.readUTF();
				dos.writeBoolean(talkToTQ);
				talkToTQ=false;
				dos.writeBoolean(stopToTQ);
				stopToTQ=false;
				dos.writeUTF(MXLocalIP);
				//--------------�����ı���Ϣ---------------
				int msgAmount=msgToTQ.size();
				dos.writeInt(msgAmount);
				if(msgAmount!=0){
					for(int i=0;i<msgAmount;i++){
			        	dos.writeUTF(msgToTQ.get(i));
			        }
					 msgToTQ.clear();
				}
				//--------------����������Ϣ---------------
				int audioAmount=audioToTQ.size();
				dos.writeInt(audioAmount);
				if(audioAmount!=0){
					reader=new FileInputStream(
	                		new File(AUDIOPATH+"\\"+audioToTQ.get(0)+".3gp"));
	                while((read=reader.read(buf,0,buf.length))!=-1){
	                	dos.write(buf,0,read);
	                }
					
					reader.close();
					audioToTQ.remove(0);
				}
				//----------------����ͼƬ--------------
				int photoAmount=photoToTQ.size();
				dos.writeInt(photoAmount);
				if(photoAmount!=0){
					reader=new FileInputStream(
	                		new File(PHOTOPATH+"\\"+photoToTQ.get(0)+".png"));
	                while((read=reader.read(buf,0,buf.length))!=-1){
	                	dos.write(buf,0,read);
	                }
					
					reader.close();
					photoToTQ.remove(0);
				}
				//-------------���ͱ���-------------------
				int ExpsAmount=expressionToTQ.size();
				dos.writeInt(ExpsAmount);
				if(ExpsAmount!=0){
					for(int i=0;i<ExpsAmount;i++){
			        	dos.writeInt(expressionToTQ.get(i));
			        }
					expressionToTQ.clear();
				}
			}
			
			if(name.equals("MX")){
				MXLocalIP=dis.readUTF();
				dos.writeBoolean(talkToMX);
				talkToMX=false;
				dos.writeBoolean(stopToMX);
				stopToMX=false;
				dos.writeUTF(TQLocalIP);
				int msgAmount=msgToMX.size();
				dos.writeInt(msgAmount);
				if(msgAmount!=0){
					for(int i=0;i<msgAmount;i++){
			        	dos.writeUTF(msgToMX.get(i));
			        }
					msgToMX.clear();
				}
		        //System.out.println(msgToMX);
				
				int audioAmount=audioToMX.size();
				dos.writeInt(audioAmount);
				if(audioAmount!=0){
					for(int i=0;i<audioAmount;i++){
						reader=new FileInputStream(
		                		new File(AUDIOPATH+"\\"+audioToMX.get(i)+".3gp"));
		                while((read=reader.read(buf,0,buf.length))!=-1){
		                	dos.write(buf,0,read);
		                }
					}
					reader.close();
					audioToMX.clear();
				}
				
				int photoAmount=photoToMX.size();
				dos.writeInt(photoAmount);
				if(photoAmount!=0){
					reader=new FileInputStream(
	                		new File(PHOTOPATH+"\\"+photoToMX.get(0)+".png"));
	                while((read=reader.read(buf,0,buf.length))!=-1){
	                	dos.write(buf,0,read);
	                }
					
					reader.close();
					photoToMX.remove(0);
				}
				
				int ExpsAmount=expressionToMX.size();
				dos.writeInt(ExpsAmount);
				if(ExpsAmount!=0){
					for(int i=0;i<ExpsAmount;i++){
			        	dos.writeInt(expressionToMX.get(i));
			        }
					expressionToMX.clear();
				}
				
			}
			
		}
		//���û�����������Ϣ�洢
		private void getMessage(DataInputStream dis) throws IOException{
			String name=dis.readUTF();
			String target=dis.readUTF();
			String msg=dis.readUTF();
			System.out.println(name+" send to "+target+" :"+msg);
			if(target.equals("TQ")){
				msgToTQ.add(msg);
			}
			if(target.equals("MX")){
				msgToMX.add(msg);
			}
		}
		
		//��ȡ�û����͵�����
		private void getAudio(DataInputStream dis,DataOutputStream dos) throws IOException{
			String name=dis.readUTF();
			String target=dis.readUTF();
			String mFileName=dis.readUTF();
			BufferedOutputStream bos=new BufferedOutputStream(
					new FileOutputStream(new File(
							AUDIOPATH+"\\"+mFileName+".3gp")));
			int bytesRead = 0;  
            byte[] buffer = new byte[1024];  
            while ((bytesRead = dis.read(buffer, 0, buffer.length)) != -1) {  
                bos.write(buffer, 0, bytesRead);  
            }  
            //bos.flush();  
            bos.close();
            if(target.equals("TQ")){
				audioToTQ.add(mFileName);
			}
			if(target.equals("MX")){
				audioToMX.add(mFileName);
			}
		}
		//�����û�������ͼƬ
		private void getPhoto(DataInputStream dis,DataOutputStream dos) throws IOException{
			String name=dis.readUTF();
			String target=dis.readUTF();
			String mFileName=dis.readUTF();
			String photoName=System.currentTimeMillis()+"";
			BufferedOutputStream bos=new BufferedOutputStream(
					new FileOutputStream(new File(
							PHOTOPATH+"\\"+photoName+".png")));
			int bytesRead = 0;  
            byte[] buffer = new byte[1024];  
            while ((bytesRead = dis.read(buffer, 0, buffer.length)) != -1) {  
                bos.write(buffer, 0, bytesRead);  
            }  
            //bos.flush();  
            bos.close();
            if(target.equals("TQ")){
				photoToTQ.add(photoName);
			}
			if(target.equals("MX")){
				photoToMX.add(photoName);
			}
		}
		
		//���û��������ı���洢
		private void getExpression(DataInputStream dis) throws IOException{
			String name=dis.readUTF();
			String target=dis.readUTF();
			int ExpressionID=dis.readInt();
			System.out.println(name+" send to "+target+" :"+ExpressionID);
			if(target.equals("TQ")){
				expressionToTQ.add(ExpressionID);
			}
			if(target.equals("MX")){
				expressionToMX.add(ExpressionID);
			}
		}
		
		private void getTalkingData(DataInputStream dis)throws IOException{
			String name=dis.readUTF();
			String target=dis.readUTF();
			
		}
	
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MainServer mainServer=new MainServer();
		mainServer.StartServer();
		
	}

}
