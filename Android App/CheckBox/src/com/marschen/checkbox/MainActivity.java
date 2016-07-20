package com.marschen.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {
	
	private CheckBox eatBox;
	private CheckBox sleepBox;
	private CheckBox dotaBox;
	private CheckBox allCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		eatBox = (CheckBox)findViewById(R.id.eatBox);
		sleepBox = (CheckBox)findViewById(R.id.sleepBox);
		dotaBox = (CheckBox)findViewById(R.id.dotaBox);
		allCheckBox = (CheckBox)findViewById(R.id.allCheckBox);
		
		CheckListener allCheckListener = new CheckListener();
		allCheckBox.setOnCheckedChangeListener(allCheckListener);
		
		EatCheckListener eatCheckListener = new EatCheckListener();
		eatBox.setOnCheckedChangeListener(eatCheckListener);
		
		SleepCheckListener sleepCheckListener = new SleepCheckListener();
		sleepBox.setOnCheckedChangeListener(sleepCheckListener);
		
		DotaCheckListener dotaCheckListener = new DotaCheckListener();
		dotaBox.setOnCheckedChangeListener(dotaCheckListener);
	}
	
	boolean a,b,c = false;
	class EatCheckListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			if(isChecked)
				a = true;
			else
				a = false;
			if(a & b & c)
				allCheckBox.setChecked(true);
			else
				allCheckBox.setChecked(false);
		}
		
	}
	
	class SleepCheckListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			if(isChecked)
				b = true;
			else
				b = false;
			if(a & b & c)
				allCheckBox.setChecked(true);
			else
				allCheckBox.setChecked(false);
		
		}
		
	}
	
	class DotaCheckListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			if(isChecked)
				c = true;
			else
				c = false;
			if(a & b & c)
				allCheckBox.setChecked(true);
			else
				allCheckBox.setChecked(false);
			
			
		}
		
	}
	
	class CheckListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			if(isChecked){
				eatBox.setChecked(true);
				sleepBox.setChecked(true);
				dotaBox.setChecked(true);
			}
			else{
				eatBox.setChecked(false);
				sleepBox.setChecked(false);
				dotaBox.setChecked(false);
			}
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
