package com.hoom.mytalk2.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hoom.mytalk2.ui.R;
import com.hoom.mytalk2.util.Audio;
import com.hoom.mytalk2.util.Photo;
import com.hoom.mytalk2.util.TextMessage;
import com.hoom.mytalk2.widget.ExpressionDislog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 当前聊天消息列表
 * @author hmd
 * @version 1.0
 * 
 */
public class MsgListAdapter extends BaseAdapter{

	private List<TextMessage> msgList;
	private LayoutInflater mInflater;
	private Audio mAudio;
	public static final int TYPE_RECE=1;
	public static final int TYPE_SEND=2;
	public static final int TYPE_RECE_AUDIO=3;
	public static final int TYPE_SEND_AUDIO=4;
	public static final int TYPE_RECE_EXP=5;
	public static final int TYPE_SEND_EXP=6;
	public static final int TYPE_SEND_PHOTO=7;
	public static final int TYPE_RECE_PHOTO=8;
	
	public MsgListAdapter(Context context){
		mInflater=LayoutInflater.from(context);
		msgList=new ArrayList<TextMessage>();
		mAudio=new Audio();
	}
	
	public void addData(TextMessage msg){
		msgList.add(msg);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return msgList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView text=null;
		ImageView pic=null;
		ImageView audioPic=null;
		ImageView expression=null;
		int id=0;
		String photoPath=null;
		File file=null;
		switch(msgList.get(position).Type){
		case TYPE_RECE:
			view=mInflater.inflate(R.layout.chat_text, null);
			text=(TextView)view.findViewById(R.id.left_text);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			text.setText(msgList.get(position).msg);
			pic.setImageResource(msgList.get(position).picture);
			break;
		case TYPE_SEND:
			view=mInflater.inflate(R.layout.chat_text_right, null);
			text=(TextView)view.findViewById(R.id.right_text);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			text.setText(msgList.get(position).msg);
			pic.setImageResource(msgList.get(position).picture);
			break;
		case TYPE_RECE_AUDIO:
			view=mInflater.inflate(R.layout.chat_audio_left, null);
			audioPic=(ImageView)view.findViewById(R.id.audio_left_pic);
			audioPic.setOnClickListener(new playAudioListener(TYPE_RECE_AUDIO,msgList.get(position).msg));
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			break;
		case TYPE_SEND_AUDIO:
			view=mInflater.inflate(R.layout.chat_audio_right, null);
			audioPic=(ImageView)view.findViewById(R.id.audio_right_pic);
			audioPic.setOnClickListener(new playAudioListener(TYPE_SEND_AUDIO,msgList.get(position).msg));
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			break;
		case TYPE_RECE_EXP:
			view=mInflater.inflate(R.layout.chat_exp_left, null);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			expression=(ImageView)view.findViewById(R.id.exp_left_pic);
			id=Integer.parseInt(msgList.get(position).msg);
			expression.setImageResource(ExpressionDislog.EXPRESSION_LIST[id]);
			break;
		case TYPE_SEND_EXP:
			view=mInflater.inflate(R.layout.chat_exp_right, null);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			expression=(ImageView)view.findViewById(R.id.exp_right_pic);
			id=Integer.parseInt(msgList.get(position).msg);
			expression.setImageResource(ExpressionDislog.EXPRESSION_LIST[id]);
			break;
		case TYPE_RECE_PHOTO:
			view=mInflater.inflate(R.layout.chat_photo_left, null);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			expression=(ImageView)view.findViewById(R.id.exp_left_pic);
			photoPath=msgList.get(position).msg;
			file=new File(photoPath);
			if (file.exists()) {  
		        Bitmap bmp = Photo.getimage(photoPath);  
		        expression.setImageBitmap(bmp);  
			} 
			break;
		case TYPE_SEND_PHOTO:
			view=mInflater.inflate(R.layout.chat_photo_right, null);
			pic=(ImageView)view.findViewById(R.id.chatting_pic);
			pic.setImageResource(msgList.get(position).picture);
			expression=(ImageView)view.findViewById(R.id.exp_right_pic);
			photoPath=msgList.get(position).msg;
			Log.v("photoPath",photoPath);
			file=new File(photoPath);
			if (file.exists()) {  
				Bitmap bmp = Photo.getimage(photoPath);   
		        expression.setImageBitmap(bmp);  
			} 
			break;
		}
		
		return view;
	}
	
	private class playAudioListener implements View.OnClickListener{

		String fileName;
		int type;
		public playAudioListener(int type,String fileName){
			this.type=type;
			this.fileName=fileName;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(mAudio.isPlaying()){
				Log.v("audio",fileName+"play");
				mAudio.stopPlaying();
			}
			if(type==TYPE_RECE_AUDIO)
				mAudio.startPlaying(fileName);
			else
				mAudio.startNativePlaying(fileName);
		}
		
	}

}
