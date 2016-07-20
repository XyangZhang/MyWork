package com.hoom.detectingforelder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {


	ImageView anim;
	AnimationDrawable enterAnim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		anim=(ImageView)findViewById(R.id.enter_animation);
		enterAnim=(AnimationDrawable)anim.getBackground();
		Handler mHandler=new Handler();
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent it=new Intent(MainActivity.this,AppStart.class);
				startActivity(it);
				MainActivity.this.finish();
			}
			
		}, 5000);
	}
	
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		Log.v("main","onWindowFoucsChanged");
		enterAnim.start();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

