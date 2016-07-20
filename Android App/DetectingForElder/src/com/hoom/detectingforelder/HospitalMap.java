package com.hoom.detectingforelder;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HospitalMap extends Activity {

	private ListView lv;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_map);
		lv=(ListView)findViewById(R.id.adr_list);
		back=(ImageView)findViewById(R.id.map_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(HospitalMap.this,AppStart.class);
				startActivity(it);
				HospitalMap.this.finish();
			}
			
		});
		SimpleAdapter adapter = new SimpleAdapter(this,
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
					Intent it=new Intent(HospitalMap.this,MapMark.class);
					startActivity(it);
				}
			}
			
		});
		
	//	Toast toast = Toast.makeText(getApplicationContext(),
	//		     "    ��λ������Ҫ����ϸ�ļ�飬����ǰ�����ҽԺ����ר��ҽ��������ϣ����緢�֡��������ƣ����ӻ���֪�����˻����ٶȣ�ά����ѵ�����Ʒ��!", 
	//		     Toast.LENGTH_LONG);
	//	toast.setGravity(Gravity.CENTER, 0, 0);
	//	toast.show();
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("��λ������Ҫ����ϸ�ļ�飬����ǰ�����ҽԺ����ר��ҽ��������ϣ����緢�֡��������ƣ����ӻ���֪�����˻����ٶȣ�ά����ѵ�����Ʒ��!");
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hospital_map, menu);
		return true;
	}

}
