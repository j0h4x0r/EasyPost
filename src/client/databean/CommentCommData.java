package client.databean;

import java.util.Date;

import comm.CommData;

public class CommentCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String commentContent;
	private String commentPostName;
	private Date commentTime;
	private String postPostTime;
	
	public CommentCommData(String userName, String commentContent, String commentPostName, String postPostTime, 
			Date commentTime) {
		// TODO Auto-generated constructor stub
		this.username = userName;
		this.commentContent = commentContent;
		this.commentPostName = commentPostName;
		this.postPostTime = postPostTime;
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentPostName() {
		return commentPostName;
	}

	public void setCommentPostName(String commentPostName) {
		this.commentPostName = commentPostName;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getPostPostTime() {
		return postPostTime;
	}

	public void setPostPostTime(String postPostTime) {
		this.postPostTime = postPostTime;
	}
	
	
	
}
