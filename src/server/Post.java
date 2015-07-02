package server;

public class Post {
	private String postName;
	private String postAuthor;
	private String postTime;
	private String editTime;
	public boolean isTop;
	
	public Post(String name,String author,String posttime,String edittime,boolean top){
		this.postName = name;
		this.postAuthor = author;
		this.postTime = posttime;
		this.editTime = edittime;
		this.isTop = top;
	}
	
	/**
	 * @return the postName
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getPostName() {
		return postName;
	}
	/**
	 * @return the postAuthor
	 */
    public void setPostAuthor(String postAuthor) {
	    this.postAuthor = postAuthor;
    }
	public String getPostAuthor() {
		return postAuthor;
	}
	/**
	 * @return the postTime
	 */
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getPostTime() {
		return postTime;
	}
	/**
	 * @return the editTime
	 */
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}
	

}
