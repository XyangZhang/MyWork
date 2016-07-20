package com.hoom.mytalk2.ui;

import com.hoom.mytalk2.service.Connect;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button login;
	TextView account,password;
	String mAccount,mPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login=(Button)findViewById(R.id.login_button);
		account=(TextView)findViewById(R.id.login_user_name);
		password=(TextView)findViewById(R.id.login_password);
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mAccount=account.getText().toString();
				mPassword=password.getText().toString();
				Log.v("LOGIN",mAccount+" "+mPassword);
				if(mAccount.equals("10000")){
					Connect.MYNAME="MX";
					Connect.TARGET="TQ";
				}
				else if(mAccount.equals("10001")){
					Connect.MYNAME="TQ";
					Connect.TARGET="MX";
				}else{
					Toast toast = Toast.makeText(LoginActivity.this,
						     "’Àªß ‰»Î”–ŒÛ", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				
				Intent it=new Intent(LoginActivity.this,ChatListActivity.class);
				startActivity(it);
				LoginActivity.this.finish();
				
				Intent its=new Intent(LoginActivity.this,Connect.class);
				startService(its);
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
