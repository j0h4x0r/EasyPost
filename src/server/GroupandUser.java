package server;

public class GroupandUser {
	private String groupName;
	private String groupLeader;
	private String userName;
	private String userPwd;
	private boolean type;

	public GroupandUser(String Name,String pwdOrgroupleader,boolean type){
		if(type){
			this.groupName = Name;
			this.groupLeader = pwdOrgroupleader;
		}
		else{
			this.userName = Name;
			this.userPwd = pwdOrgroupleader;
		}
		
	}
	

	/**
	 * @return the userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}


	/**
	 * @return the userPwd
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserPwd() {
		return userPwd;
	}


	/**
	 * @return the type
	 */
	public void setType(boolean type) {
		this.type = type;
	}
	public boolean isType() {
		return type;
	}


	/**
	 * @return the groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return the groupLeader
	 */
	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}
	public String getGroupLeader() {
		return groupLeader;
	}
	
	
}
