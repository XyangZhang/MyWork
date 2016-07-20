package com.hoom.mytalk2.util;
/**
 * 历史聊天消息列表对象，包括头像、姓名、时间、消息
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
