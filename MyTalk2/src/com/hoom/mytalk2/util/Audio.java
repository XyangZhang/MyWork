package com.hoom.mytalk2.util;

import java.io.IOException;
import java.util.Calendar;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
/**用于录音，播放语音
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
			"/audioReceive"; //接受到的语音文件路径
	public static final String mFileSendPath=Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/audioSend";//发送的语音文件路径
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
	//停止录音
	public void stopRecorder(){
		mRecorder.stop();
		mRecorder.reset(); 
        mRecorder.release();
        mRecorder = null;
	}
	
	/**
	 * 播放录音
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
	
	//播放发送出去的语音
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
	
	//停止播放
	public void stopPlaying(){
		mPlayer.release();
		mPlayer=null;
	}
	
	public boolean isPlaying(){
		if(mPlayer==null)
			return false;
		return mPlayer.isPlaying();
	}
	
	//用当前时间创建文件名  201411_130311
	private String createFileName(){
		String mFileName=null;
		cal=Calendar.getInstance();//使用日历类
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
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
