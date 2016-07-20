package com.example.mmset;

import com.example.mmset.data.PersonData;
import com.example.mmset.data.QuestionData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

	TextView gradeText;
	Button backButton;
	QuestionData qd;
	PersonData pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		pd=new PersonData();
		qd=new QuestionData();
		gradeText=(TextView)findViewById(R.id.result_grade);
		backButton=(Button)findViewById(R.id.result_back);
		
		gradeText.setText(""+qd.getGrade());
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(ResultActivity.this,MainActivity.class);
				startActivity(it);
				ResultActivity.this.finish();
			}
			
		});
		
		pd.save(qd.getGrade());
		pd.save("very good!!");
		pd.insert();
		//pd.getData();
		qd.setGrade();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
