package dbservice;

public class TableConstant {
	
	public static final String table_user = "user";
	public static final String user_id = "id";
	public static final String user_name = "name";
	public static final String user_password = "password";
	
	public static final String table_post = "post";
	public static final String post_name = "pname";
	public static final String post_ptime = "ptime";
	public static final String post_etime = "etime";
	public static final String post_top = "top";
	public static final String post_author = "author";
	public static final String post_groupname = "groupname";
	public static final String post_content = "content";
	
	public static final String table_comment = "comment";
	public static final String comment_id = "cid";
	public static final String comment_time = "ctime";
	public static final String comment_author = "author";
	public static final String comment_content = "content";
	public static final String comment_pname = "pname";
	public static final String comment_ptime = "ptime";
	
	public static final String table_letter = "letter";
	public static final String letter_id = "lid";
	public static final String letter_content = "content";
	public static final String letter_time = "ltime";
	public static final String letter_sender = "sender";
	public static final String letter_receiver = "receiver";
	
	public static final String table_group = "groupinfo";
	public static final String group_name = "gname";
	public static final String group_admin = "admin";
	public static final String group_confirm = "confirm";
	
	public static final String table_friend = "friend";
	public static final String friend_fname = "fname";
	public static final String friend_sname = "sname";
	
}
