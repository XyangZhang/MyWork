package com.hoom.detectingforelder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;


public class MapMark extends Activity {

	BMapManager mBMapMan=null;
	MapView bMapView;//��ͼ  
	
	ImageView back;
//	MyLocationOverlay mLocationOverlay;//��ͼ������  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());//�����ٶȵ�ͼ������  
	    mBMapMan.init("35DBD4B5FD1DAB237D8F8DB4526E6B8143C230E3",null);//��һ�����������������key�� 
	    setContentView(R.layout.activity_map_mark);
	    
	    back=(ImageView)findViewById(R.id.map_mark_back);
	    back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MapMark.this,AppStart.class);
				startActivity(it);
				MapMark.this.finish();
			}
	    	
	    });
	    
	    iniMap();
	} 
	
	private void iniMap(){
		bMapView=(MapView)findViewById(R.id.bmapsView);//�ҵ��ؼ���ͼ  
		bMapView.setBuiltInZoomControls(true);
	    MapController mMapController=bMapView.getController();   
	    GeoPoint point1 =new GeoPoint((int)(31.316266* 1E6),(int)(121.392678* 1E6));  
	    //�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)   
	    mMapController.setCenter(point1);//���õ�ͼ���ĵ�   
	    mMapController.setZoom(15);//���õ�ͼzoom����
	    
	    //��ȡ��ǰλ�õĸ�����  
	 //   mLocationOverlay=new MyLocationOverlay(bMapView);  
	    //��Ӷ�λ������  
	 //   bMapView.getOverlays().add(mLocationOverlay); 
	 //   mLocationOverlay.enableCompass(); 
	    Drawable mark=getResources().getDrawable(R.drawable.map_mark);
	    OverlayItem item1=new OverlayItem(point1,"item1","item1");
	    
	    OverlayTest itemOverlay=new OverlayTest(mark,bMapView);
	    bMapView.getOverlays().clear();   
	    bMapView.getOverlays().add(itemOverlay); 
        itemOverlay.addItem(item1);
        bMapView.refresh();
	}
	
	@Override   
	protected void onDestroy(){   
	        bMapView.destroy();   
	        if(mBMapMan!=null){   
	                mBMapMan.destroy();   
	                mBMapMan=null;   
	        }   
	        this.finish();
	        super.onDestroy();   
	}   
	@Override   
	protected void onPause(){   
	        bMapView.onPause();   
	        if(mBMapMan!=null){   
	               mBMapMan.stop();   
	        }   
	        super.onPause();   
	}   
	@Override   
	protected void onResume(){   
	        bMapView.onResume();   
	        if(mBMapMan!=null){   
	                mBMapMan.start();   
	        }   
	       super.onResume();   
	}  



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class OverlayTest extends ItemizedOverlay<OverlayItem>{

		public OverlayTest(Drawable mark,MapView mapView) {
			super(mark,mapView);
			// TODO Auto-generated constructor stub
		}
		
		protected boolean onTap(int index){
			System.out.println("item onTap: "+index);   
			Toast toast = Toast.makeText(getApplicationContext(),
							     "�Ϻ���ѧ��ɽУ��", 
							     Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
	        return true;
		}

		}
		
	}
