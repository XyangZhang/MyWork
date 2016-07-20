package com.hoom.mytalk2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideMenu extends ViewGroup{
	
	 	private int distance;// ��ȫ��ʾ�˵���Ҫ�ƶ��ľ���  
	    private View menu;  
	    private View main;  
	    private Scroller scroller;  
	    private boolean menuVisible = false;  
	  
	    public SlideMenu(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        // TODO Auto-generated constructor stub  
	        scroller = new Scroller(context);  
	    }  
	  
	    @Override  
	    protected void onLayout(boolean changed, int l, int t, int r, int b) {  
	        // TODO Auto-generated method stub  
	  
	        distance = getWidth() * 3 / 5;// ���ƽ���ƶ��ľ��룬Ҳ�ǲ˵��Ŀ��  
	  
	        // ���ֲ˵�����ҳ����ͼ  
	        menu = getChildAt(0);// ��ò˵���ͼ  
	        menu.setVisibility(VISIBLE);  
	        menu.measure(  
	                MeasureSpec.makeMeasureSpec(distance, MeasureSpec.EXACTLY),  
	                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY));  
	        //menu.layout(-distance, 0, 0, getHeight());  
	        menu.layout(getWidth(), 0, getWidth()+distance, getHeight()); 
	        main = getChildAt(1);// �����ҳ����ͼ  
	        main.setVisibility(VISIBLE);  
	        // �൱��fill_parent  
	        main.measure(  
	                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),  
	                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));  
	        main.layout(0, 0, getWidth(), getHeight());  
	  
	    }  
	  
	    public void showMenu() {  
	        if (!menuVisible) {  
	            scroller.startScroll(getScrollX(), 0, distance, 0, 300);  
	            invalidate();  
	            menuVisible = true;  
	        }  
	  
	    }  
	  
	    public void hideMenu() {  
	        if (menuVisible) {  
	            scroller.startScroll(getScrollX(), 0, -distance, 0, 300);  
	            invalidate();  
	            menuVisible = false;  
	        }  
	  
	    }  
	    
	    public boolean isVisible(){
	    	if(menuVisible)
	    		return true;
	    	return false;
	    }
	  
	    @Override  
	    public void computeScroll() {  
	        // TODO Auto-generated method stub  
	        // ������û�����  
	        if (scroller.computeScrollOffset()) {  
	            scrollTo(scroller.getCurrX(), 0);  
	            postInvalidate();  
	        }  
	    }  
	  
	} 
