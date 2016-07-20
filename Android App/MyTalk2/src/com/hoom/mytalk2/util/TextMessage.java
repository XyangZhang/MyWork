package com.hoom.mytalk2.util;
/**
 * 当前聊天消息对象，包括头像，类型(区分我发的消息与对方发来的消息)、消息
 * @author hmd
 * 
 */
public class TextMessage {

	public int picture;
	public int Type;
	public String msg;
	
	public TextMessage(int pic ,int type,String msg){
		this.picture=pic;
		this.Type=type;
		this.msg=msg;
	}
}
