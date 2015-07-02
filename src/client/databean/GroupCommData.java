package client.databean;

import comm.CommData;

/*
 * ����
 */
public class GroupCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String groupName;
	private String applyReason;

	//������
	public GroupCommData (String userName, String groupName, String applyReason) {
		this.username = userName;
		this.groupName = groupName;
		this.applyReason = applyReason;
	}
	
	/*
	 * �õ���������
	 */
	public String getGroupName () {
		return this.groupName;
	}
	
	/*
	 * �õ������������
	 */
	public String getApplyReason () {
		return this.applyReason;
	}
}
