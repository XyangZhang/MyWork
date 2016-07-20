package com.example.sql;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class QueryActivity extends ListActivity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setTitle("浏览音乐列信息");
		final DBHelper helper = new DBHelper(this);
		Cursor c = helper.query();
		
		String[] from = { "_id", "name", "singer" };
		int[] to = { R.id.text0, R.id.text1, R.id.text2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, c, from, to);
		ListView listView = getListView();
		listView.setAdapter(adapter);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final long temp = arg3;
				builder.setMessage("真的要删除该记录吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.del((int) temp);
						Cursor c = helper.query();
						String[] from = { "_id", "name", "singer" };
						int[] to = { R.id.text0, R.id.text1, R.id.text2 };
						
						SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.row, c, from, to);
						ListView listView = getListView();
						listView.setAdapter(adapter);
					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}
					
					
				});		
			AlertDialog ad = builder.create();
			ad.show();	
			}
		});
		helper.close();
	}
}
