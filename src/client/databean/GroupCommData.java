package client.databean;

import comm.CommData;

/*
 * 申请
 */
public class GroupCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String groupName;
	private String applyReason;

	//申请组
	public GroupCommData (String userName, String groupName, String applyReason) {
		this.username = userName;
		this.groupName = groupName;
		this.applyReason = applyReason;
	}
	
	/*
	 * 得到申请组名
	 */
	public String getGroupName () {
		return this.groupName;
	}
	
	/*
	 * 得到申请组的理由
	 */
	public String getApplyReason () {
		return this.applyReason;
	}
}
