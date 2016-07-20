package com.example.mmset;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.widget.ImageView;

public class EnterActivity extends Activity {

	ImageView anim;
	AnimationDrawable enterAnim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter);
        
		anim=(ImageView)findViewById(R.id.enter_animation);
		enterAnim=(AnimationDrawable)anim.getBackground();
		Handler mHandler=new Handler();
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent it=new Intent(EnterActivity.this,MainActivity.class);
				startActivity(it);
				EnterActivity.this.finish();
			}
			
		}, 5000);
	}
	
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		//Log.v("main","onWindowFoucsChanged");
		enterAnim.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter, menu);
		return true;
	}

}
