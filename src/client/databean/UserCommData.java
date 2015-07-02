package client.databean;

import comm.CommData;

/**
 * 
 * @author zhukangfeng
 *  用户信息
 */
public class UserCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String password;
	private String newPassword;
	private String friendName;
	
	public UserCommData(String userName) {
		this.username = userName;
	}
	
	public boolean setFriendName(String friendName) {
		this.friendName = friendName;
		return true;
	}
	
	

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getFriendName() {
		return friendName;
	}

	/*
	 * 修改用户名
	 */
	public boolean setUserName(String userName) {
		this.username = userName;
		return true;
	}
	
	/*
	 * 获取密码
	 */
	public String getPassword() {
		return this.password;
	}
	
	/*
	 * 设置密码
	 */
	public Boolean setPassword(String password) {
		this.password = password;
		return true;
	}
}
