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
	 * 发送站内信
	 */
	public PostCommData(String letterContent, String userName, Date sendTime, String inLetter, DataType dataType) {
		this.postContent = letterContent;
		this.username = userName;
		this.postTime = sendTime;
		this.inLetter = inLetter;
		this.type = dataType;
	}
	
	/*
	 * 发布帖子
	 */
	public PostCommData (String postName, String userName, Date postTime, Date editTime, String postContent, String group, String isTop, DataType dataType) {
		this.username = userName;
		this.postName = postName;
		this.postTime = postTime;
		this.editTime = editTime;
		this.postContent = postContent;
		this.group = group;
		this.isTop = isTop;
		this.type = dataType;				//定义是发布帖子，还是修改帖子， 还是删除帖子
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
	 * 获得帖子名称
	 */
	public String getPostName() {
		return this.postName;
	}
	
	/*
	 * 获得帖子发布时间
	 */
	public Date getPostTime() {
		return this.postTime;
	}
	
	/*
	 * 获得帖子最后修改时间
	 */
	public Date getEditTime() {
		return this.editTime;
	}
	
	/*
	 * 获得帖子内容
	 */
	public String getPostContent() {
		return this.postContent;
	}
	
	/*
	 * 获得帖子组别
	 */
	public String getGroup() {
		return this.group;
	}
	
	/*
	 * 获得帖子是否置顶， 置顶返回1，否则返回0
	 */
	public String getIsTop() {
		return this.isTop;
	}
	
	/*
	 * 获得帖子操作类型
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
