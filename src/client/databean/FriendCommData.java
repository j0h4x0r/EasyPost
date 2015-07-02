package client.databean;

import comm.CommData;
import comm.DataType;

public class FriendCommData extends CommData {
	/**
	 * 进行好友的添加，删除
	 */
	private static final long serialVersionUID = 1L;
	private String friendName;								//增加好友时，好友姓名
	
	/*
	 * 进行增加好友
	 */
	public FriendCommData(DataType dataType, String userName, String friendName) {
		this.type = dataType;
		this.username = userName;
		this.friendName = friendName;
	}	

	 /*
	  * 增加，或者删除好友时，获得好友姓名
	  */
	 public String getFriendName() {
		 return this.friendName;
	 }
	 

}
