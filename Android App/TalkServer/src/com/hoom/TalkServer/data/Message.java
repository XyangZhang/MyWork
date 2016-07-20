package com.hoom.TalkServer.data;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private List<SendPerson> sendPersonList;
	private String targetName;
	
	public Message(String targetName){
		sendPersonList=new ArrayList<SendPerson>();
		this.targetName=targetName;
	}
	
	public void addTextMsg(String sendName, String sendMsg){
		
	}
	/*
	private boolean isExist(String sendName){
		
	}*/
	
	public String getTargetName(){
		return targetName;
	}
	//������������ ������������������Ϣ�б������б�ͼƬ�б�
	private class SendPerson{
		public List<String> textMsg;
		public List<String> audioMsg;
		public List<String> imageMsg;
		public String sendName;
		
	}
}
