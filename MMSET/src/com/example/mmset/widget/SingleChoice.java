package com.example.mmset.widget;

import com.example.mmset.R;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SingleChoice {

	/**
	 * @param args
	 */
	int choice=-1;
	ImageView[] ButtonGroup;
	public SingleChoice(ImageView[] ButtonGroup){
		this.ButtonGroup=ButtonGroup;
		for(int i=0;i<ButtonGroup.length;i++){
			ButtonGroup[i].setOnClickListener(new mListener(i));
		}
	}
	
	public class mListener implements OnClickListener{

		int j;
		mListener(int i){
			this.j=i;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			for(int i=0;i<ButtonGroup.length;i++){
					ButtonGroup[i].setImageResource(R.drawable.option_no);
			}
			//Log.v("degree",""+j);
			ButtonGroup[j].setImageResource(R.drawable.option_yes);
			choice=j;
		}
		
	} 
	
	public int getChoice(){
		//Log.v("getchoice",""+(choice+1));
		return choice+1;
	} 
	

}
