package com.hoom.mytalk2.util;

import java.io.IOException;
import java.util.Calendar;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
/**����¼������������
 * @author hoom
 * @version 1.0
 */
public class Audio {

	/**
	 * @param args
	 */
	private MediaRecorder mRecorder;
	private MediaPlayer mPlayer;
	private static final String mFileReceivePath=Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/audioReceive"; //���ܵ��������ļ�·��
	public static final String mFileSendPath=Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/audioSend";//���͵������ļ�·��
	private Calendar cal;
	
	/**
	 * @author hoom
	 * @param no param
	 * @return String the audio file name 
	 */
	public String startRecorder(){
		String mFileName=createFileName();
		mRecorder=new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileSendPath+"/"+mFileName+".3gp");
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRecorder.start();
		return mFileName;
	}
	//ֹͣ¼��
	public void stopRecorder(){
		mRecorder.stop();
		mRecorder.reset(); 
        mRecorder.release();
        mRecorder = null;
	}
	
	/**
	 * ����¼��
	 * @param audio file name
	 * @author hoom
	 * @return void
	 */
	public void startPlaying(String name){
		String mFileName=mFileReceivePath+"/"+name+".3gp";
		mPlayer=new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���ŷ��ͳ�ȥ������
	public void startNativePlaying(String name){
		String mFileName=mFileSendPath+"/"+name+".3gp";
		mPlayer=new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ֹͣ����
	public void stopPlaying(){
		mPlayer.release();
		mPlayer=null;
	}
	
	public boolean isPlaying(){
		if(mPlayer==null)
			return false;
		return mPlayer.isPlaying();
	}
	
	//�õ�ǰʱ�䴴���ļ���  201411_130311
	private String createFileName(){
		String mFileName=null;
		cal=Calendar.getInstance();//ʹ��������
		int year=cal.get(Calendar.YEAR);//�õ���
		int month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
		int day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		int minute=cal.get(Calendar.MINUTE);
		int second=cal.get(Calendar.SECOND);
		mFileName=year+""+month+""+day+"_"+hour+""+minute+""+second;
		return mFileName;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
