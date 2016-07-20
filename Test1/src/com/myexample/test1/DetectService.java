package com.myexample.test1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
public class DetectService extends Service{
	
    static final double pi=3.1415926;
	private SensorManager AccelerationSM,GyroscopeSM;
	private float [] AValues=new float[3];
	private float GX;
	boolean detecting=false;
	Timer timer1=new Timer();
	float a;
	double angle,sum,radian;
	int count=0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private MediaPlayer mp3;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate()
	{
		super.onCreate();
		AccelerationSM=(SensorManager)getSystemService(Context.SENSOR_SERVICE);  //加速度传感器
		GyroscopeSM=(SensorManager)getSystemService(Context.SENSOR_SERVICE);     //陀螺仪传感器
		AccelerationSM.registerListener(AccelerationEvent
				, AccelerationSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
				, SensorManager.SENSOR_DELAY_GAME);                              // 注册传感器  确定采样频率
		
		//定时器1--每200ms检测一次合加速度是否小于3；
		timer1.schedule(tt1, 0,100);
		mp3=new MediaPlayer();
		mp3=MediaPlayer.create(this, R.raw.alarm);
	}
	
	//加速度传感器监听
	public SensorEventListener AccelerationEvent=new SensorEventListener()
	{

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			AValues=event.values;
			a=(float)Math.sqrt(AValues[0]*AValues[0]+AValues[1]*AValues[1]+AValues[2]*AValues[2]);
		}
		
	};
	
	public SensorEventListener GyroscopeEvent=new SensorEventListener()
	{

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			GX=event.values[0];
		}
		
	};
	
	public TimerTask tt1=new TimerTask() 
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
                
				Log.v("acceleration",""+a);
				if(a!=0.0&&a<3)
				{
					detecting=true;
				}
				if(detecting){
					GyroscopeSM.registerListener(GyroscopeEvent
							, GyroscopeSM.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
							,SensorManager.SENSOR_DELAY_GAME);
					Record w=new Record();
					w.write(df.format(new Date()).toString()+": ");
					w.write("a:"+a+"  radian:"+sum+"\n");
					count++;
					sum+=GX*0.1;
					if(a>20)
					{
						angle=(sum/pi)*180;
						if(angle>45||angle<-45)
						{
							tt1.cancel();
							MusicStart();
							
						}
					}
				}
			
			if(count>20)
			{
				count=0;
				detecting=false;
				sum=0;
				
			}
		}
		
	};
	
	
	
	public void MusicStart()
	{
		try
		{
			if(mp3!=null) mp3.stop();
			mp3.prepare();
			mp3.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void onDestroy()
	{
		super.onDestroy();
		tt1.cancel();
		mp3.stop();
		mp3.release();
		
	}
	
}