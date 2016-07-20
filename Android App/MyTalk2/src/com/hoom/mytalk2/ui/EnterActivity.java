package com.hoom.mytalk2.ui;

import com.hoom.mytalk2.service.Connect;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class EnterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter);
		Animation animation=AnimationUtils.loadAnimation(this,R.anim.myanim);
		findViewById(R.id.enter_icon).startAnimation(animation);
		new Thread(){
			public void run(){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent it=new Intent(EnterActivity.this,LoginActivity.class);
				startActivity(it);
				EnterActivity.this.finish();
			}
		}.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter, menu);
		return true;
	}

}
