package com.hoom.mytalk2.ui;

import java.util.ArrayList;
import java.util.List;

import com.hoom.mytalk2.adapter.ChatListAdapter;
import com.hoom.mytalk2.service.Connect;
import com.hoom.mytalk2.util.ChatListInfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatListActivity extends Activity {

	ListView chatListView;
	ChatListAdapter adapter;
	private List<ChatListInfo> chatList;
	int pic;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_list);
		
		chatList=new ArrayList<ChatListInfo>();
		chatListView=(ListView)findViewById(R.id.chat_list);
		adapter=new ChatListAdapter(this);
		chatListView.setAdapter(adapter);
		chatListView.setOnItemClickListener(chatListListener);
		if(Connect.MYNAME.equals("TQ")){
			pic=R.drawable.my_pic;
			name="hoom";
		}else{
			pic=R.drawable.target_pic;
			name="dou7";
		}
		chatList.add(new ChatListInfo(pic,name,"hello!","2-23"));
		adapter.setList(chatList);
	}
	
	private OnItemClickListener chatListListener=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			ChatListInfo info=(ChatListInfo) arg0.getItemAtPosition(position);
			//Log.v("chatlist",info.userName);
			Intent it=new Intent(ChatListActivity.this,ChattingActivity.class);
			startActivity(it);
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_list, menu);
		return true;
	}

}
