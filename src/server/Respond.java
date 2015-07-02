package server;

import java.util.ArrayList;

import client.databean.CommentCommData;
import client.databean.LetterCommData;
import client.databean.PostCommData;

import comm.CommData;

public class Respond extends CommData {
	private static final long serialVersionUID = 1L;
	private Boolean result;
	private String errorMessage;
	private String infoString;									//存放获取的组名等字符串回复
	private LetterCommData letterCommData;
	private PostCommData postCommData;
	private CommentCommData commentCommData;					//存放评论内容
	private ArrayList<String> infoArrayListArrayList;			//存放好友列表或者组列表
	private ArrayList<LetterCommData> letterCommDatasList;
	private ArrayList<PostCommData> postCommDatasList;
	private ArrayList<CommentCommData> commentCommDataArrayList; //评论列表
	
	
	public Respond(Boolean result, String errorMessage) {
		this.result = result;
		this.errorMessage = errorMessage;
		this.infoArrayListArrayList = null;
		this.letterCommDatasList = null;
		this.postCommDatasList = null;
		this.commentCommDataArrayList = null;
	}
	
	public Respond(Boolean result, String errorMessage, ArrayList<String> userArrayList) {
		this.result = result;
		this.errorMessage = errorMessage;
		this.infoArrayListArrayList = userArrayList;
		this.letterCommDatasList = null;
		this.postCommDatasList = null;
		this.commentCommDataArrayList = null;
	}
	
	

	public ArrayList<LetterCommData> getLetterCommDatasList() {
		return letterCommDatasList;
	}

	public void setLetterCommDatasList(ArrayList<LetterCommData> letterCommDatasList) {
		this.letterCommDatasList = letterCommDatasList;
	}

	public ArrayList<PostCommData> getPostCommDatasList() {
		return postCommDatasList;
	}

	public void setPostCommDatasList(ArrayList<PostCommData> postCommDatasList) {
		this.postCommDatasList = postCommDatasList;
	}

	public ArrayList<String> getInfoArrayListArrayList() {
		return infoArrayListArrayList;
	}

	public void setInfoArrayListArrayList(ArrayList<String> infoArrayListArrayList) {
		this.infoArrayListArrayList = infoArrayListArrayList;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	
	public String getInfoString() {
		return infoString;
	}

	public void setInfoString(String infoString) {
		this.infoString = infoString;
	}

	public LetterCommData getLetterCommData() {
		return letterCommData;
	}

	public void setLetterCommData(LetterCommData letterCommData) {
		this.letterCommData = letterCommData;
	}

	public PostCommData getPostCommData() {
		return postCommData;
	}

	public void setPostCommData(PostCommData postCommData) {
		this.postCommData = postCommData;
	}

	public Boolean getResult() {
		return result;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public CommentCommData getCommentCommData() {
		return commentCommData;
	}

	public void setCommentCommData(CommentCommData commentCommData) {
		this.commentCommData = commentCommData;
	}

	public ArrayList<CommentCommData> getCommentCommDataArrayList() {
		return commentCommDataArrayList;
	}

	public void setCommentCommDataArrayList(
			ArrayList<CommentCommData> commentCommDataArrayList) {
		this.commentCommDataArrayList = commentCommDataArrayList;
	}
	
	public void addLetterCommDataList(LetterCommData letterCommData) {
		if (this.letterCommDatasList == null) {
			this.letterCommDatasList = new ArrayList<LetterCommData>();
		}
		this.letterCommDatasList.add(letterCommData);
	}
	
	public void addGroupArrayList(String s) {
		if (this.infoArrayListArrayList == null) {
			this.infoArrayListArrayList = new ArrayList<String>();
		}
		this.infoArrayListArrayList.add(s);
	}
	
	public void addCommentArrayList(CommentCommData commData) {
		if (this.commentCommDataArrayList == null) {
			this.commentCommDataArrayList = new ArrayList<CommentCommData>();
		}
		this.commentCommDataArrayList.add(commData);
	}
	
	public void addPostArrayList(PostCommData postCommData) {
		if (this.postCommDatasList == null) {
			this.postCommDatasList = new ArrayList<PostCommData>();
		}
		this.postCommDatasList.add(postCommData);
	}
}
