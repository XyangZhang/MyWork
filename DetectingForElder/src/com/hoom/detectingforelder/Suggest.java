package com.hoom.detectingforelder;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Suggest extends Activity {

	ImageView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		back=(ImageView)findViewById(R.id.suggest_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(Suggest.this,AppStart.class);
				startActivity(it);
				Suggest.this.finish();
			}
			
		});
		
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("这位长辈的认知情况良好，更多优质生活资讯，请参考“记忆物语”");
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest, menu);
		return true;
	}

}
