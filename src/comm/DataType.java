package comm;

public enum DataType {
	LOGIN,				//登录
	POST_GROUP,			//贴子组
	ANNOUNCE,			//公告
	REGI,				//注册
	ADD_GROUP,			//添加新组
	GET_GROUP_ADMIN,	//得到组管理员
	POST_POST, 			//发布帖子
	DEL_POST,			//删除帖子
	SEND_LETTER,		//发送站内信
	GET_LETTER,			//得到站内信
	GET_POST,			//帖子内容和评论
	GET_COMMENT, 		//得到帖子评论
	NEW_POST,			//新帖
	NEW_COMMENT,		//新评论
	CHANAGE_PASSWORD, 	//修改密码
	ADD_FRIEND,			//增加朋友
	DEL_FRIEND,			//删除好友
	REPLY_LETTER,		//回复站内信
	GET_POST_LIST,		//组内帖子
	GET_FRIEND_LIST,	//获得好友列表
	GET_GROUP_LIST,		//获得组列表
	GET_IN_LETTER_BOX_LIST,	//收件箱列表
	GET_OUT_LETTER_BOX_LIST,//发件箱列表
	GET_COMMENT_LIST,		//评论列表
	CHANGE_IS_TOP,		//改变是否置顶

	DISCONNECT
}
