package com.hoom.mytalk2.widget;

import java.util.ArrayList;
import java.util.HashMap;

import com.hoom.mytalk2.ui.R;
import com.hoom.mytalk2.util.ExpressionItemClicked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;


public class ExpressionDislog{

	public final static int[] EXPRESSION_LIST ={R.drawable.f001,R.drawable.f002,R.drawable.f003,R.drawable.f004
		,R.drawable.f005,R.drawable.f006,R.drawable.f007,R.drawable.f008,R.drawable.f009,R.drawable.f010
		,R.drawable.f011,R.drawable.f012,R.drawable.f013,R.drawable.f014,R.drawable.f015,R.drawable.f016
		,R.drawable.f017,R.drawable.f018,R.drawable.f019,R.drawable.f020,R.drawable.f021,R.drawable.f022
		,R.drawable.f023,R.drawable.f024};
	
	public Dialog createDialog(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater=LayoutInflater.from(context);
		View gridView=inflater.inflate(R.layout.expression_grid, null);
		GridView gv=(GridView)gridView.findViewById(R.id.grid_view);
		ArrayList<HashMap<String,Integer>> imgList=new ArrayList<HashMap<String,Integer>>();
		for(int i=0;i<24;i++){
			HashMap<String, Integer> hs = new HashMap<String, Integer>();
			hs.put("img", EXPRESSION_LIST[i]);
			imgList.add(hs);
		}
		gv.setAdapter(new SimpleAdapter(context,imgList,R.layout.grid_content,
				new String[]{"img"},new int[]{R.id.expression} ));
		
		//builder.setView(gridView);
		AlertDialog dialog=builder.create();
		dialog.setView(gridView,0,0,0,0);
		return dialog;
	}
	
	
	
	public Dialog onCreateDialog(Context context,ExpressionItemClicked e){
		LayoutInflater inflater=LayoutInflater.from(context);
		View gridView=inflater.inflate(R.layout.expression_grid, null);
		GridView gv=(GridView)gridView.findViewById(R.id.grid_view);
		ArrayList<HashMap<String,Integer>> imgList=new ArrayList<HashMap<String,Integer>>();
		for(int i=0;i<24;i++){
			HashMap<String, Integer> hs = new HashMap<String, Integer>();
			hs.put("img", EXPRESSION_LIST[i]);
			imgList.add(hs);
		}
		gv.setAdapter(new SimpleAdapter(context,imgList,R.layout.grid_content,
				new String[]{"img"},new int[]{R.id.expression} ));
		gv.setOnItemClickListener(new listener(e));
		
		
		Dialog dialog=new Dialog(context,R.style.Translucent_NoTitle);
		dialog.setContentView(gridView);
		
		return dialog;
		
	}
	
	private class listener implements OnItemClickListener{

		ExpressionItemClicked e;
		public listener(ExpressionItemClicked e){
			this.e=e;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			e.onClicked(position);
		}
		
	}

}
