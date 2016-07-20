package com.example.mmset.data;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class PersonData {

	/**
	 * @param args
	 */
	private SQLiteDatabase db;
	private static List<String> infoList=new ArrayList<String>();
	private final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/mmse";
	private String DATABASE_FILENAME = "test.db";
	private String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;

	public PersonData(){
		//db=SQLiteDatabase.openOrCreateDatabase("/sdcard/mmse/test.db3", null);
		//String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
		File dir = new File(DATABASE_PATH);
		if (!dir.exists())
			dir.mkdirs();
		db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		//infoList=new ArrayList<String>();
		db.execSQL("create table if not exists person_info( " +
				"person_id integer primary key," +
				"name varchar(20)," +
				"sex varchar(20)," +
				"edu_degree varchar(20)," +
				"age integer ," +
				"time varchar(40)," +
				"ques_1 integer," +
				"ques_2 integer," +
				"ques_3 integer," +
				"ques_4 integer," +
				"ques_5 integer," +
				"ques_6 integer," +
				"ques_7 integer," +
				"ques_8 integer," +
				"ques_9 integer," +
				"ques_10 integer," +
				"ques_11 integer," +
				"ques_12 integer," +
				"ques_13 integer," +
				"ques_14 integer," +
				"ques_15 integer," +
				"ques_16 integer," +
				"ques_17 integer," +
				"ques_18 integer," +
				"ques_19 integer," +
				"ques_20 integer," +
				"grade integer," +
				"situation varchar(20))");
		db.close();
	}
	
	public void save(String s){
		infoList.add(s);
		Log.v("save",infoList.toString());
	}
	public void save(int i){
		String s=""+i;
		infoList.add(s);
		Log.v("save",infoList.toString());
	}
	public void getData(){
		db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		String sql = "select * from person_info";
		Cursor cs=db.rawQuery(sql, null);
		while(cs.moveToNext()){
			for(int i=0;i<28;i++)
			Log.v(cs.getColumnName(i), cs.getString(i));
		}
		cs.close();
		db.close();
	}
	
	public void insert(){
		db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		String sql = "INSERT INTO person_info VALUES (" +
				"null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
		SQLiteStatement stmt = db.compileStatement(sql);  
		for (int i = 0; i < infoList.size(); i++) {  
		    stmt.bindString(i+1, infoList.get(i));  
		}
		stmt.execute();  
	    stmt.clearBindings();
	    db.close();
	    infoList.clear();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
