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
	//创建发送者类 ，包括发送者名、信息列表、语音列表、图片列表
	private class SendPerson{
		public List<String> textMsg;
		public List<String> audioMsg;
		public List<String> imageMsg;
		public String sendName;
		
	}
}
