package com.example.mmset;

import java.util.Calendar;

import com.example.mmset.data.PersonData;
import com.example.mmset.util.Util;
import com.example.mmset.widget.SingleChoice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	ImageView man,woman;
	ImageView degree1,degree2,degree3;
	EditText nameET,ageET;
	Button loginButton;
	PersonData pd=new PersonData();
	SingleChoice degreeChoice,sexChoice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		man=(ImageView)findViewById(R.id.sex_man);
		woman=(ImageView)findViewById(R.id.sex_woman);
		degree1=(ImageView)findViewById(R.id.degree_1);
		degree2=(ImageView)findViewById(R.id.degree_2);
		degree3=(ImageView)findViewById(R.id.degree_3);
		nameET=(EditText)findViewById(R.id.name);
		ageET=(EditText)findViewById(R.id.age);
		loginButton=(Button)findViewById(R.id.login_confirm);
		
		ImageView[] sex={man,woman};
		sexChoice=new SingleChoice(sex);
		
		ImageView[] degree={degree1,degree2,degree3};
		degreeChoice=new SingleChoice(degree);
		//Log.v("degree",""+degreeChoice);
		
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//��ȡ����
				String name=nameET.getText().toString();
				if(name.equals("")){
					Util.Inform("��������������",getApplicationContext());
					return;
				}
				//��ȡ����
				int age;
				try{
					age = Integer.parseInt(ageET.getText().toString());
				}catch(Exception e){
					Util.Inform("����������������",getApplicationContext());
					return;
				}
				//��ȡ�Ա�
				String sex;
				if(sexChoice.getChoice()==0){
					Util.Inform("��ѡ����������",getApplicationContext());
					return;
				}
				if(sexChoice.getChoice()==1)
					sex="��";
				else
					sex="Ů";
				//��ȡ�Ļ��̶�
				String degree;
				if(degreeChoice.getChoice()==0){
					Util.Inform("��ѡ�������Ļ��̶�",getApplicationContext());
					return;
				}
				if(degreeChoice.getChoice()==1)
					degree="δ�Ϲ�ѧ";
				else if(degreeChoice.getChoice()==2)
					degree="Сѧ�Ļ�";
				else
					degree="���м�����";
				//��ȡʱ��
				Calendar cal=Calendar.getInstance();//ʹ��������
				int year=cal.get(Calendar.YEAR);//�õ���
				int month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
				int day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
				int hour=cal.get(Calendar.HOUR);//�õ�Сʱ
				int minute=cal.get(Calendar.MINUTE);//�õ�����
				String time=year+"-"+month+"-"+day+" "+hour+":"+minute;
				
				pd.save(name);
				pd.save(sex);
				pd.save(degree);
				pd.save(age);
				pd.save(time);
				
				Intent it=new Intent(LoginActivity.this,QuestionActivity.class);
				startActivity(it);
				LoginActivity.this.finish();
			}
			
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
