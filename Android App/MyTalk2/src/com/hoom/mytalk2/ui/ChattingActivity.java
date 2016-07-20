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
	Button speak,back,setting;//������ť
	GestureDetector detector; //��ʰ���
	MsgListAdapter msgAdapter;//��Ϣ����������
	broadcastReceiver broadcastRece;
	Dialog dialog; //����ѡ���
	Connect connect;//��̨�������ڽ��գ�������Ϣ
	Audio mAudio;   //¼������������
	String mFileName;//¼���ļ���
	String photoName;
	int myPic;
	
	
	//���ڰ󶨷���
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
		//��ǰActivity���̨MainService���а�
        Intent mMainServiceIntent = new Intent(this,Connect.class);
        bindService(mMainServiceIntent, sConnection, BIND_AUTO_CREATE);
	}
	
	
	//�����ʼ��
	private void iniView(){
		slideMenu=(SlideMenu)findViewById(R.id.slide_menu);
		//menu view
		View menu=LayoutInflater.from(this).inflate(R.layout.chatting_menu, null);
		//chatting view
		View main=LayoutInflater.from(this).inflate(R.layout.chatting_main, null);
		slideMenu.addView(menu);
		slideMenu.addView(main);
		//------------------------�˵���ͼ����---------------------------
		menuList=(ListView)menu.findViewById(R.id.chatting_menu_list);
		menuList.setAdapter(new SimpleAdapter(this,getData(),R.layout.menu_list_content,
				new String[]{"img","info"},
				new int[]{R.id.menu_pic,R.id.menu_info}));
		menuList.setOnItemClickListener(new menuListener());
		
		//-------------------------�������------------------------------
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
	
	//ע��㲥����
	private void regBroadcastRece(){
		broadcastRece=new broadcastReceiver();
		IntentFilter itFilter=new IntentFilter();
		itFilter.addAction(Connect.newMsgAction);
		itFilter.addAction(Connect.talkReqAction);
		registerReceiver(broadcastRece,itFilter);
	}
	
	//��ȡ�˵���������
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
	
	//���໬�¼� ����������˵�֮���л�
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
                if (velocityX > 0 ) {//���һ���  
                    slideMenu.hideMenu();  
                } else if (velocityX < 0) {//���󻬶�  
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
	//�������Ͱ�ť�����¿�ʼ¼�����ɿ���������
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
	
	//�˵�����¼�
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
	//���ͱ���
	private void sendExpression(int ExpressionId){
		connect.sendExpression(ExpressionId);
		msgAdapter.addData(new TextMessage(myPic,
				MsgListAdapter.TYPE_SEND_EXP,ExpressionId+""));
		msgAdapter.notifyDataSetChanged(); 
		msgList.setSelection(msgList.getAdapter().getCount()-1);
		dialog.dismiss();
	}
	
	//�㲥��������
	private class broadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			//������������
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
