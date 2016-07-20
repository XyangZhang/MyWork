package com.marschen.s01_e09_checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	
	private CheckBox eatBox;
	private CheckBox sleepBox;
	private CheckBox dotaBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        

        
        eatBox = (CheckBox)findViewById(R.id.eatId);
        sleepBox = (CheckBox)findViewById(R.id.sleepId);
        dotaBox = (CheckBox)findViewById(R.id.dotaId);
        
        OnBoxClickListener listener = new OnBoxClickListener();
        eatBox.setOnClickListener(listener);
        sleepBox.setOnClickListener(listener);
        dotaBox.setOnClickListener(listener);
    }
    
    class OnBoxClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			System.out.println("CheckBox is clicked");
			
		}
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
