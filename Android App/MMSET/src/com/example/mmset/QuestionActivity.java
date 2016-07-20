package com.example.mmset;

import java.util.ArrayList;
import java.util.List;

import com.example.mmset.data.PersonData;
import com.example.mmset.data.QuestionData;
import com.example.mmset.util.Util;
import com.example.mmset.widget.SingleChoice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class QuestionActivity extends Activity implements OnGestureListener{

	ViewFlipper ViewF;
	Button nextButton;
	private List<View> listViews;
	LayoutInflater inflater;
	ImageView image1,image2,image3,image4;
	EditText ques12_1,ques12_2,ques12_3,ques12_4,ques12_5;
	int currentIndex;
	public static final int quesNum=20;
	private GestureDetector gestureDetector;
	QuestionData quesData;
	PersonData pd;
	List<SingleChoice> listChoice;               //存放前11个问题的选择按钮
	SingleChoice choice14,choice15,choice16,choice17,choice18,choice19,choice20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		quesData=new QuestionData();
		pd=new PersonData();
		gestureDetector = new GestureDetector(getBaseContext(),this);
		//ViewF.setOnTouchListener(this);
		ViewF=(ViewFlipper)findViewById(R.id.mViewFlipper);
		inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		nextButton=(Button)findViewById(R.id.ques_next);
		nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showNext();
			}
			
		});
		iniView();
		
		
	}
	
	private void iniView(){
		listViews=new ArrayList<View>();
		LayoutInflater mInflater=getLayoutInflater();
		listChoice=new ArrayList<SingleChoice>();
		
		
		View mView=null;
		//初始化前11与13个问题界面
		for(int i=0;i<13;i++){
			if(i!=11){
				mView=mInflater.inflate(R.layout.ques_1,null);
				TextView quesNum=(TextView)mView.findViewById(R.id.ques_num);
				TextView quesDetail=(TextView)mView.findViewById(R.id.ques_detail);
				TextView choice1=(TextView)mView.findViewById(R.id.choice_1);
				TextView choice2=(TextView)mView.findViewById(R.id.choice_2);
				TextView choice3=(TextView)mView.findViewById(R.id.choice_3);
				TextView choice4=(TextView)mView.findViewById(R.id.choice_4);
				
				quesNum.setText((i+1)+"/20");
				quesDetail.setText(quesData.getQuesData(i));
				choice1.setText(quesData.getChoiceData1(i));
				choice2.setText(quesData.getChoiceData2(i));
				choice3.setText(quesData.getChoiceData3(i));
				choice4.setText(quesData.getChoiceData4(i));
				
				image1=(ImageView)mView.findViewById(R.id.image_1_1);
				image2=(ImageView)mView.findViewById(R.id.image_1_2);
				image3=(ImageView)mView.findViewById(R.id.image_1_3);
				image4=(ImageView)mView.findViewById(R.id.image_1_4);
				ImageView[] image={image1,image2,image3,image4};
				listChoice.add(new SingleChoice(image));
				
				listViews.add(mView);
				ViewF.addView(mView);
			}else{
				//初始化第12个问题界面
				mView=mInflater.inflate(R.layout.ques_12,null);
				ques12_1=(EditText)mView.findViewById(R.id.ques_12_1);
				ques12_2=(EditText)mView.findViewById(R.id.ques_12_2);
				ques12_3=(EditText)mView.findViewById(R.id.ques_12_3);
				ques12_4=(EditText)mView.findViewById(R.id.ques_12_4);
				ques12_5=(EditText)mView.findViewById(R.id.ques_12_5);
				ViewF.addView(mView);
			}
			
		}
		mView=mInflater.inflate(R.layout.ques_14,null);
		ImageView ques14_1=(ImageView)mView.findViewById(R.id.image_14_1);
		ImageView ques14_2=(ImageView)mView.findViewById(R.id.image_14_2);
		ImageView ques14_3=(ImageView)mView.findViewById(R.id.image_14_3);
		ImageView ques14_4=(ImageView)mView.findViewById(R.id.image_14_4);
		ImageView[] image14={ques14_1,ques14_2,ques14_3,ques14_4};
		choice14=new SingleChoice(image14);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_15,null);
		ImageView ques15_1=(ImageView)mView.findViewById(R.id.image_15_1);
		ImageView ques15_2=(ImageView)mView.findViewById(R.id.image_15_2);
		ImageView ques15_3=(ImageView)mView.findViewById(R.id.image_15_3);
		ImageView ques15_4=(ImageView)mView.findViewById(R.id.image_15_4);
		ImageView[] image15={ques15_1,ques15_2,ques15_3,ques15_4};
		choice15=new SingleChoice(image15);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_16,null);
		ImageView ques16_1=(ImageView)mView.findViewById(R.id.image_16_1);
		ImageView ques16_2=(ImageView)mView.findViewById(R.id.image_16_2);
		ImageView[] image16={ques16_1,ques16_2};
		choice16=new SingleChoice(image16);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_17,null);
		ImageView ques17_1=(ImageView)mView.findViewById(R.id.image_17_1);
		ImageView ques17_2=(ImageView)mView.findViewById(R.id.image_17_2);
		ImageView ques17_3=(ImageView)mView.findViewById(R.id.image_17_3);
		ImageView ques17_4=(ImageView)mView.findViewById(R.id.image_17_4);
		ImageView[] image17={ques17_1,ques17_2,ques17_3,ques17_4};
		choice17=new SingleChoice(image17);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_18,null);
		ImageView ques18_1=(ImageView)mView.findViewById(R.id.image_18_1);
		ImageView ques18_2=(ImageView)mView.findViewById(R.id.image_18_2);
		ImageView[] image18={ques18_1,ques18_2};
		choice18=new SingleChoice(image18);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_19,null);
		ImageView ques19_1=(ImageView)mView.findViewById(R.id.image_19_1);
		ImageView ques19_2=(ImageView)mView.findViewById(R.id.image_19_2);
		ImageView[] image19={ques19_1,ques19_2};
		choice19=new SingleChoice(image19);
		ViewF.addView(mView);
		
		mView=mInflater.inflate(R.layout.ques_20,null);
		ImageView ques20_1=(ImageView)mView.findViewById(R.id.image_20_1);
		ImageView ques20_2=(ImageView)mView.findViewById(R.id.image_20_2);
		ImageView[] image20={ques20_1,ques20_2};
		choice20=new SingleChoice(image20);
		ViewF.addView(mView);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}
	
	private void showNext(){
		if(currentIndex<11){
			if(listChoice.get(currentIndex).getChoice()==0){
				Util.Inform("请先回答当前问题再进入下一题",getApplicationContext());
				return;
			}
			quesData.answerMatch(currentIndex,listChoice.get(currentIndex).getChoice());
		}
		
		if(currentIndex==11){
			String[] ans12 ={ques12_1.getText().toString(),ques12_2.getText().toString(),
					ques12_3.getText().toString(),ques12_4.getText().toString(),
					ques12_5.getText().toString()};
			for(int i=0;i<5;i++){
				if(ans12[i].equals("")){
					Util.Inform("请输入答案",getApplicationContext());
					return;
				}
			}
			quesData.answerMatch(currentIndex,ans12[0],ans12[1],ans12[2],ans12[3],ans12[4]);
		}
		if(currentIndex==12){
			if(listChoice.get(currentIndex-1).getChoice()==0){
				Util.Inform("请先回答当前问题再进入下一题",getApplicationContext());
				return;
			}
			quesData.answerMatch(currentIndex,listChoice.get(currentIndex-1).getChoice());
		}
		
		SingleChoice[] sc={choice14,choice15,choice16,choice17,choice18,choice19,choice20};
		if(currentIndex>=13&&currentIndex<20){
			if(sc[currentIndex-13].getChoice()==0){
				Util.Inform("请先回答当前问题再进入下一题",getApplicationContext());
				return;
			}
			quesData.answerMatch(currentIndex,sc[currentIndex-13].getChoice());
		}
		
		if(currentIndex<quesNum-1){
			ViewF.showNext();
			currentIndex++;
		}else{
			Intent it=new Intent(QuestionActivity.this,ResultActivity.class);
			startActivity(it);
			QuestionActivity.this.finish();
		}
		
		
	}
	
	 public boolean onTouchEvent(MotionEvent event) {    
		 gestureDetector.onTouchEvent(event); 
		 InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {  
	            if (QuestionActivity.this.getCurrentFocus() != null) {  
	                if (this.getCurrentFocus().getWindowToken() != null) {  
	                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),  
	                            InputMethodManager.HIDE_NOT_ALWAYS);  
	                }  
	            }  
	        }  
	     return super.onTouchEvent(event);
	 }

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		if (e2.getX() - e1.getX() < -120){
			showNext();
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
