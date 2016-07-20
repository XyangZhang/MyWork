package com.hoom.detectingforelder;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AppStart extends Activity {

	private ViewPager viewPager;
	private List<View> listViews;
	private ImageView tabImage;
	private TextView tv1,tv2,tv3;
	private ImageView ScrollImage1;
	private Button confirm;
	private LayoutInflater inflater ;
	
	private int offset=0;
	public static int currentIndex=0;
	private int imgW;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_start);
		
		inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		iniTextView();
		iniImageView();
		iniViewPager();
		//iniPage1();
	//	Log.v("appstart","oncreate");
	//	Log.v("appstart","currentIndex="+currentIndex);
	}
	
	private void iniTextView()
	{
		tv1=(TextView)findViewById(R.id.tab1);
		tv2=(TextView)findViewById(R.id.tab2);
		tv3=(TextView)findViewById(R.id.tab3);
		tv1.setOnClickListener(new mOnClickListener(0));
		tv2.setOnClickListener(new mOnClickListener(1));
		tv3.setOnClickListener(new mOnClickListener(2));
	}
	
	private void iniViewPager(){
		viewPager=(ViewPager)findViewById(R.id.pager);
		listViews=new ArrayList<View>();
		LayoutInflater mInflater=getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.viewpage_1,null));
		listViews.add(mInflater.inflate(R.layout.viewpage_2,null));
		listViews.add(mInflater.inflate(R.layout.viewpage_3,null));
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new mListener());
	}
	
	private void iniImageView(){

		tabImage=(ImageView)findViewById(R.id.tab_img);
		imgW=(int)BitmapFactory.decodeResource(getResources(), R.drawable.tab_img)
				                  .getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - imgW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.setTranslate(offset, 0);
		tabImage.setImageMatrix(matrix);// 设置动画初始位置
	//	Log.v("screenW",""+screenW);
	//	Log.v("imgW",""+imgW);
	//	Log.v("offset",""+offset);

	}

	//private void iniPage1(){
	//	ScrollImage1.setOnClickListener(new ImageListener(1));
	//}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private class mListener implements OnPageChangeListener{
		private int one=offset*2+imgW;
		private int two=one*2;
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
		//	Log.v("appstart","arg0="+arg0);
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currentIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					//Log.v("change","1-0");
					} else if (currentIndex == 2) {                
						animation = new TranslateAnimation(two, 0, 0, 0);
						//Log.v("change","2-0");
				}                
				break;       
			case 1:                 
				if (currentIndex == 0) {         
					animation = new TranslateAnimation(offset, one, 0, 0);
					//Log.v("change","0-1");
					
					} else if (currentIndex == 2) {
						animation = new TranslateAnimation(two, one, 0, 0);
						//Log.v("change","2-1");
				}                
				break;            
			case 2:              
				if (currentIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
					//Log.v("change","0-2");
					} else if (currentIndex == 1) {
						animation = new TranslateAnimation(one, two, 0, 0);
						//Log.v("change","1-2");
				      }else{
				    	  animation = new TranslateAnimation(offset, two, 0, 0);
				      }
				
				break;
             }
			//Log.v("offset",""+offset);
			//Log.v("inmW",""+imgW);
             currentIndex = arg0;
             if(animation!=null){
            	 animation.setFillAfter(true);// True:图片停在动画结束位置 
                 animation.setDuration(300);            
                 tabImage.startAnimation(animation);
             }
		}
		
	}
	
	public class MyPagerAdapter extends PagerAdapter{

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(listViews.get(arg1));
			//Log.v("pager","destroy"+arg1);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {             
			((ViewPager) arg0).addView(listViews.get(arg1), 0);    
		//	Log.v("pager","instantiate"+arg1);
			switch(arg1){
			case 0: 
				confirm=(Button)arg0.findViewById(R.id.page_1_confirm);
			//	Log.v("appstart","confirm="+confirm);
				confirm.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent it=new Intent(AppStart.this,Detecting.class);
						startActivity(it);
					}
					
				});
				break;
			case 2:
				ListView lv=(ListView)arg0.findViewById(R.id.page_3_list);
				SimpleAdapter adapter = new SimpleAdapter(AppStart.this,
						new AppUtil().getData(),
						R.layout.list_item,                    
						new String[]{"addr","info","go"},                    
						new int[]{R.id.addr_name,R.id.addr_descri,R.id.addr_go});            
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						// TODO Auto-generated method stub
						if(arg2==0){
							Intent it=new Intent(AppStart.this,MapMark.class);
							startActivity(it);
						}
					}
					
				});
				break;
			}
			return listViews.get(arg1);      
			
			}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
	}
	
	public class mOnClickListener implements OnClickListener{

		private int index=0;
		public mOnClickListener(int i){
			index=i;
			
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(index);
			//Log.v("tag",""+index);
		}
		
	}
	
}
