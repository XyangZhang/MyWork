package com.hoom.mytalk2.util;
/**
 * ��ʷ������Ϣ�б���󣬰���ͷ��������ʱ�䡢��Ϣ
 * @author hmd
 *
 */
public class ChatListInfo {

	public int userPicture;
	public String userName;
	public String userMsg;
	public String time;
	
	public ChatListInfo(int pic,String name,String msg,String time){
		this.userPicture=pic;
		this.userName=name;
		this.userMsg=msg;
		this.time=time;
	}
}
