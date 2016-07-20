package com.hoom.detectingforelder;

import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Detecting extends Activity {

	private ImageView story1;
	private ImageView storyBack;
	private TextView head;
	private Button skip;
	private MediaPlayer mp3;
	private int storyNum=1;
	private boolean sicked;
	
	int[] ImageInt={R.drawable.story1_1,R.drawable.story1_2,R.drawable.story1_3,
			R.drawable.story1_4,R.drawable.story1_5,R.drawable.story1_6,
			R.drawable.story1_7};
	int[] MusicInt={R.raw.mus1_1,R.raw.mus1_2,R.raw.mus1_3,R.raw.mus1_4,
			R.raw.mus1_5,R.raw.mus1_6,R.raw.mus1_7};
	int index=0;
	ArrayList<Integer> imgList=new ArrayList<Integer>();
	ArrayList<Integer> musList=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detecting);
		story1=(ImageView)findViewById(R.id.story_1);
		head=(TextView)findViewById(R.id.story_head);
		storyBack=(ImageView)findViewById(R.id.story_back);
		storyBack.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(Detecting.this,AppStart.class);
				startActivity(it);
				Log.v("detectActivity","back");
				Detecting.this.finish();
			}
			
		});
		skip=(Button)findViewById(R.id.story_skip);
		skip.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(index!=ImageInt.length)
				index=ImageInt.length;
			}
			
		});
		imgList=AppUtil.getImg1();
		story1.setImageResource(imgList.get(index));
		mp3=MediaPlayer.create(this, MusicInt[index++]);
		mp3.start();
		mp3.setOnCompletionListener(new myMusicListener());
		
	}
	protected void onStop(){
	//	Log.v("detectActivity","stop");
		super.onStop();
		if(mp3!=null){
			mp3.release();
		}
	}
	protected void onDestroy(){
		ImageInt=null;
		MusicInt=null;
	//	Log.v("detectActivity","destroy");
		if(mp3!=null){
			mp3.release();
		}
		super.onDestroy();
		this.finish();
	}

	private class myMusicListener implements OnCompletionListener{

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mp3.release();
			if(index<ImageInt.length){
				story1.setImageResource(ImageInt[index]);
				mp3=MediaPlayer.create(Detecting.this, MusicInt[index++]);
				mp3.setOnCompletionListener(new myMusicListener());
				mp3.start();
				
			}
			else{
				QusDialog();
			}
		}
		
	}
	
	
	private void nextStory(){
		index=0;
		switch(storyNum){
		case 2:
			
			head.setText("谁能带我回家");
			int[] ImageInt={R.drawable.story2_1,R.drawable.story2_2,R.drawable.story2_3,
					R.drawable.story2_4,R.drawable.story2_5,R.drawable.story2_6,
					R.drawable.story2_7,R.drawable.story2_8};
			int[] MusicInt={R.raw.mus2_1,R.raw.mus2_2,R.raw.mus2_3,R.raw.mus2_4,
					R.raw.mus2_5,R.raw.mus2_6,R.raw.mus2_7,R.raw.mus2_8};
			story1.setImageResource(ImageInt[index]);
			mp3=MediaPlayer.create(this, MusicInt[index++]);
			mp3.start();
			mp3.setOnCompletionListener(new myMusicListener());
		}
	}
	
	private void QusDialog(){
		AlertDialog.Builder builder=new Builder(Detecting.this);
		builder.setTitle("提问");
		switch(storyNum){
		case 1:
			builder.setMessage("这位长辈有没有出现过混淆时间记不清日期和星期的情况？");
			break;
		case 2:
			builder.setMessage("这位长辈有没有在熟悉的地方迷过路？");
			break;
		}
		builder.setCancelable(false);
		builder.setPositiveButton("是", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				sicked=true;
				if(storyNum==2){
					Intent it=new Intent(Detecting.this,HospitalMap.class);
					startActivity(it);
					Detecting.this.finish();
				}
				else NextDialog();
			}
			
		});
		builder.setNegativeButton("否",new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				sicked=false;
				if(storyNum==2){
					Intent it=new Intent(Detecting.this,Suggest.class);
					startActivity(it);
					Detecting.this.finish();
				}
				else NextDialog();
			}
			
		});
		builder.create().show();
	}
	
	private void NextDialog(){
		AlertDialog.Builder builder2=new Builder(Detecting.this);
		builder2.setTitle("提示");
		builder2.setMessage("是否阅读下一个故事？");
		builder2.setCancelable(false);
		builder2.setPositiveButton("是", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				storyNum++;
				nextStory();
				
			}
			
		});
		builder2.setNegativeButton("否",new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent it;
				if(sicked){
					 it=new Intent(Detecting.this,HospitalMap.class);
				}
				else{
					it=new Intent(Detecting.this,Suggest.class);
				}
				startActivity(it);
				//Log.v("detectActivity","exit");
				Detecting.this.finish();
			}
			
		});
		builder2.create().show();
	}

}
