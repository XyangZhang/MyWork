package com.hoom.mytalk2.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoom.mytalk2.adapter.MsgListAdapter;
import com.hoom.mytalk2.service.Connect;
import com.hoom.mytalk2.util.Audio;
import com.hoom.mytalk2.util.ExpressionItemClicked;
import com.hoom.mytalk2.util.Photo;
import com.hoom.mytalk2.util.TextMessage;
import com.hoom.mytalk2.widget.ExpressionDislog;
import com.hoom.mytalk2.widget.SlideMenu;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChattingActivity extends Activity {

	ListView menuList,msgList;
	SlideMenu slideMenu;
	EditText et;
	Button speak,back,setting;//语音按钮
	GestureDetector detector; //收拾检测
	MsgListAdapter msgAdapter;//消息数据适配器
	broadcastReceiver broadcastRece;
	Dialog dialog; //表情选择框
	Connect connect;//后台服务，用于接收，发送消息
	Audio mAudio;   //录音、播放语音
	String mFileName;//录音文件名
	String photoName;
	int myPic;
	
	
	//用于绑定服务
	private ServiceConnection sConnection = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			connect = ((Connect.ServiceBinder)service).getService();
			
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			connect = null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatting);
		
		iniView();
		detector=new GestureDetector(this,new myListener());
		regBroadcastRece();
		
		mAudio=new Audio();
		if(Connect.MYNAME.equals("MX"))
			myPic=R.drawable.my_pic;
		else
			myPic=R.drawable.target_pic;
		//当前Activity与后台MainService进行绑定
        Intent mMainServiceIntent = new Intent(this,Connect.class);
        bindService(mMainServiceIntent, sConnection, BIND_AUTO_CREATE);
	}
	
	
	//界面初始化
	private void iniView(){
		slideMenu=(SlideMenu)findViewById(R.id.slide_menu);
		//menu view
		View menu=LayoutInflater.from(this).inflate(R.layout.chatting_menu, null);
		//chatting view
		View main=LayoutInflater.from(this).inflate(R.layout.chatting_main, null);
		slideMenu.addView(menu);
		slideMenu.addView(main);
		//------------------------菜单视图界面---------------------------
		menuList=(ListView)menu.findViewById(R.id.chatting_menu_list);
		menuList.setAdapter(new SimpleAdapter(this,getData(),R.layout.menu_list_content,
				new String[]{"img","info"},
				new int[]{R.id.menu_pic,R.id.menu_info}));
		menuList.setOnItemClickListener(new menuListener());
		
		//-------------------------聊天界面------------------------------
		msgList=(ListView)main.findViewById(R.id.chat_list);
		msgAdapter=Connect.msgListAdapter;
		msgList.setAdapter(msgAdapter);
		/*
		msgList.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				detector.onTouchEvent(event);
				return false;
			}
			
		});
		*/
		et=(EditText)main.findViewById(R.id.text_edit);
		speak=(Button)main.findViewById(R.id.audio_input);
		speak.setOnTouchListener(speakListener);
		Button send=(Button)main.findViewById(R.id.send_button);
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TextMessage tm=new TextMessage(myPic,2,et.getText().toString());
				msgAdapter.addData(tm);
				msgAdapter.notifyDataSetChanged(); 
				msgList.setSelection(msgList.getAdapter().getCount()-1);
				connect.sendMsg(et.getText().toString());
				et.setText("");
			}
			
		});
		
		back=(Button)main.findViewById(R.id.chatting_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(ChattingActivity.this,ChatListActivity.class);
				startActivity(it);
				ChattingActivity.this.finish();
			}
			
		});
		
		setting=(Button)main.findViewById(R.id.chatting_setting);
		setting.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(slideMenu.isVisible())
					slideMenu.hideMenu();
				else
					slideMenu.showMenu();
			}
			
		});
		
	}
	
	//注册广播接收
	private void regBroadcastRece(){
		broadcastRece=new broadcastReceiver();
		IntentFilter itFilter=new IntentFilter();
		itFilter.addAction(Connect.newMsgAction);
		itFilter.addAction(Connect.talkReqAction);
		registerReceiver(broadcastRece,itFilter);
	}
	
	//获取菜单数据内容
	private List<Map<String, Object>> getData() {            
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();             
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.menu_word);
		map.put("info", "Text");   
		list.add(map);    
		map = new HashMap<String, Object>();  
		map.put("img", R.drawable.menu_expression);
		map.put("info", "Expression");   
		list.add(map);     
		map = new HashMap<String, Object>();  
		map.put("img", R.drawable.menu_audio);
		map.put("info", "Voice");   
		list.add(map);       
		map = new HashMap<String, Object>();  
		map.put("img", R.drawable.menu_photo);
		map.put("info", "Photo");   
		list.add(map);       
		map = new HashMap<String, Object>();  
		map.put("img", R.drawable.menu_talking);
		map.put("info", "V-Chat");   
		list.add(map);       
		return list;        
		}
	
	public boolean onTouchEvent(MotionEvent event) {  
        // TODO Auto-generated method stub  
        detector.onTouchEvent(event);  
        return true;  
    }  
	
	//检测侧滑事件 在主界面与菜单之间切换
	private class myListener implements OnGestureListener{

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if (Math.abs(velocityX) > ViewConfiguration.get(  
                    ChattingActivity.this).getScaledMinimumFlingVelocity()) {  
                if (velocityX > 0 ) {//向右滑动  
                    slideMenu.hideMenu();  
                } else if (velocityX < 0) {//向左滑动  
                    slideMenu.showMenu();  
                }  
            }  
            return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	//语音发送按钮，按下开始录音，松开发送语音
	private OnTouchListener speakListener=new OnTouchListener(){

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			// TODO Auto-generated method stub
			int action=event.getAction();
			//mAudio=new Audio();
			switch(action){
			case MotionEvent.ACTION_DOWN:
				mFileName=mAudio.startRecorder();
				break;
			case MotionEvent.ACTION_UP:
				mAudio.stopRecorder();
				connect.sendAudio(mFileName);
				msgAdapter.addData(new TextMessage(myPic,
						MsgListAdapter.TYPE_SEND_AUDIO,mFileName));
				msgAdapter.notifyDataSetChanged(); 
				msgList.setSelection(msgList.getAdapter().getCount()-1);
		        break;
			}
			return true;
		}
		
	};
	
	//菜单点击事件
	private class menuListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			switch(position){
			case 0:
				et.setVisibility(View.VISIBLE);
				speak.setVisibility(View.INVISIBLE);
				slideMenu.hideMenu();
				break;
			case 1:
				ExpressionDislog ed=new ExpressionDislog();
				dialog=ed.onCreateDialog(ChattingActivity.this,
						new ExpressionItemClicked(){

							@Override
							public void onClicked(int ExpressionId) {
								// TODO Auto-generated method stub
								Log.v("click",""+ExpressionId);
								sendExpression(ExpressionId);
							}
					
				});
				//dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				slideMenu.hideMenu();
				dialog.show();
				break;
			case 2:
				speak.setVisibility(View.VISIBLE);
				et.setVisibility(View.INVISIBLE);
				slideMenu.hideMenu();
				break;
			case 3:
				slideMenu.hideMenu();
				photoName=Photo.getPhoto(ChattingActivity.this);
				break;
			case 4:
				slideMenu.hideMenu();
				Intent it=new Intent(ChattingActivity.this,TalkingActivity.class);
				it.putExtra("type", 0);
				startActivity(it);
				break;
			}
			
		}
		
	};
	//发送表情
	private void sendExpression(int ExpressionId){
		connect.sendExpression(ExpressionId);
		msgAdapter.addData(new TextMessage(myPic,
				MsgListAdapter.TYPE_SEND_EXP,ExpressionId+""));
		msgAdapter.notifyDataSetChanged(); 
		msgList.setSelection(msgList.getAdapter().getCount()-1);
		dialog.dismiss();
	}
	
	//广播接收器类
	private class broadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			//更新聊天内容
			if(intent.getAction().equals(Connect.newMsgAction)){
				msgAdapter.notifyDataSetChanged(); 
				msgList.setSelection(msgList.getAdapter().getCount()-1);
			}
			if(intent.getAction().equals(Connect.talkReqAction)){
				Intent it=new Intent(ChattingActivity.this,TalkingActivity.class);
				it.putExtra("type", 1);
				startActivity(it);
			}
		}
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode==12)
		{
			connect.sendPhoto(photoName);
			msgAdapter.addData(new TextMessage(myPic,
					MsgListAdapter.TYPE_SEND_PHOTO,photoName));
			msgAdapter.notifyDataSetChanged(); 
			msgList.setSelection(msgList.getAdapter().getCount()-1);
		}
		super.onActivityResult(requestCode, resultCode, data);  
	}
	
	protected void onDestroy(){
		super.onDestroy();
		unbindService(sConnection);
		unregisterReceiver(broadcastRece);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chatting, menu);
		return true;
	}

}
