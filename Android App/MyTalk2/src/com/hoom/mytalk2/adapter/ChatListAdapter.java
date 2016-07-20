package com.hoom.mytalk2.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hoom.mytalk2.ui.R;
import com.hoom.mytalk2.util.ChatListInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 历史聊天人物列表
 * @author hmd
 * @version 1.0
 */
public class ChatListAdapter extends BaseAdapter{
	
	private List<ChatListInfo> chatList;
	private LayoutInflater mInflater;
	
	public ChatListAdapter(Context context){
		chatList=new ArrayList<ChatListInfo>();
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void setList(List<ChatListInfo> list){
		this.chatList=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chatList.size();
	}

	@Override
	public Object getItem(int positon) {
		// TODO Auto-generated method stub
		return chatList.get(positon);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(view==null){
			view=mInflater.inflate(R.layout.chat_list_content, null);
		}
		ImageView pic=(ImageView)view.findViewById(R.id.chat_list_picture);
		TextView name=(TextView)view.findViewById(R.id.chat_list_name);
		TextView msg=(TextView)view.findViewById(R.id.chat_list_msg);
		TextView time=(TextView)view.findViewById(R.id.chat_list_time);
		pic.setImageResource(chatList.get(position).userPicture);
		name.setText(chatList.get(position).userName);
		msg.setText(chatList.get(position).userMsg);
		time.setText(chatList.get(position).time);
		return view;
	} 
	
}
