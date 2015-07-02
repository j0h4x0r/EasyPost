package client.databean;

import comm.CommData;

/**
 * 
 * @author zhukangfeng
 *  �û���Ϣ
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
	 * �޸��û���
	 */
	public boolean setUserName(String userName) {
		this.username = userName;
		return true;
	}
	
	/*
	 * ��ȡ����
	 */
	public String getPassword() {
		return this.password;
	}
	
	/*
	 * ��������
	 */
	public Boolean setPassword(String password) {
		this.password = password;
		return true;
	}
}
