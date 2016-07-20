package com.example.mmset.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Util {
	public static void Inform(String s,Context context){
		Toast toast = Toast.makeText(context,
			     s, Toast.LENGTH_SHORT);//getApplicationContext()
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
