package com.hoom.mytalk2.ui;

import com.hoom.mytalk2.service.Connect;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TalkingActivity extends Activity {

	Connect connect;
	Button stop;
	int type;
	broadcastReceiver broadcastRece;
	
	//用于绑定服务
	private ServiceConnection sConnection = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			connect = ((Connect.ServiceBinder)service).getService();
			Intent it=getIntent();
	        type=it.getIntExtra("type", 2);
	        if(type==0)
	        	connect.sendTalkRequest();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			connect = null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talking);
		stop=(Button)findViewById(R.id.button1);
		stop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				TalkingActivity.this.finish();
			}
			
		});
		
		//当前Activity与后台MainService进行绑定
        Intent mMainServiceIntent = new Intent(this,Connect.class);
        bindService(mMainServiceIntent, sConnection, BIND_AUTO_CREATE);
		
        regBroadcast();
        
	}
	
	private void regBroadcast(){
		broadcastRece=new broadcastReceiver();
		IntentFilter itFilter=new IntentFilter();
		itFilter.addAction(Connect.talkStopAction);
		registerReceiver(broadcastRece,itFilter);
	}
	
	private class broadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(Connect.talkStopAction)){
				TalkingActivity.this.finish();
				type=2;
			}
		}
		
	}

	protected void onDestroy(){
		super.onDestroy();
		if(type!=2)
			connect.stopTalk();
		unbindService(sConnection);
		unregisterReceiver(broadcastRece);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.talking, menu);
		return true;
	}

}
