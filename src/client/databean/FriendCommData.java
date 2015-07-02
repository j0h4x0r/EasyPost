package client.databean;

import comm.CommData;
import comm.DataType;

public class FriendCommData extends CommData {
	/**
	 * ���к��ѵ���ӣ�ɾ��
	 */
	private static final long serialVersionUID = 1L;
	private String friendName;								//���Ӻ���ʱ����������
	
	/*
	 * �������Ӻ���
	 */
	public FriendCommData(DataType dataType, String userName, String friendName) {
		this.type = dataType;
		this.username = userName;
		this.friendName = friendName;
	}	

	 /*
	  * ���ӣ�����ɾ������ʱ����ú�������
	  */
	 public String getFriendName() {
		 return this.friendName;
	 }
	 

}
