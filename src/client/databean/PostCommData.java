package client.databean;

import java.util.Date;

import comm.CommData;
import comm.DataType;

public class PostCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String postName;
	private Date postTime;
	private Date editTime;
	private String postContent;
	private String group;
	private String isTop;
	private String inLetter;
	
	public PostCommData() {
		
	}
	
	/*
	 * ����վ����
	 */
	public PostCommData(String letterContent, String userName, Date sendTime, String inLetter, DataType dataType) {
		this.postContent = letterContent;
		this.username = userName;
		this.postTime = sendTime;
		this.inLetter = inLetter;
		this.type = dataType;
	}
	
	/*
	 * ��������
	 */
	public PostCommData (String postName, String userName, Date postTime, Date editTime, String postContent, String group, String isTop, DataType dataType) {
		this.username = userName;
		this.postName = postName;
		this.postTime = postTime;
		this.editTime = editTime;
		this.postContent = postContent;
		this.group = group;
		this.isTop = isTop;
		this.type = dataType;				//�����Ƿ������ӣ������޸����ӣ� ����ɾ������
	}
	
	public void setPostName(String postName) {
		this.postName = postName;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	/*
	 * �����������
	 */
	public String getPostName() {
		return this.postName;
	}
	
	/*
	 * ������ӷ���ʱ��
	 */
	public Date getPostTime() {
		return this.postTime;
	}
	
	/*
	 * �����������޸�ʱ��
	 */
	public Date getEditTime() {
		return this.editTime;
	}
	
	/*
	 * �����������
	 */
	public String getPostContent() {
		return this.postContent;
	}
	
	/*
	 * ����������
	 */
	public String getGroup() {
		return this.group;
	}
	
	/*
	 * ��������Ƿ��ö��� �ö�����1�����򷵻�0
	 */
	public String getIsTop() {
		return this.isTop;
	}
	
	/*
	 * ������Ӳ�������
	 */
	public DataType getDataType() {
		return this.type;
	}

	public String getInLetter() {
		return inLetter;
	}

	public void setInLetter(String inLetter) {
		this.inLetter = inLetter;
	}
	
	
}
