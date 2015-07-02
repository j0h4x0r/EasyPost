package server.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import server.Respond;

import client.databean.CommentCommData;
import client.databean.GroupCommData;
import client.databean.PostCommData;
import dbservice.DBService;
import dbservice.databean.CommentInfo;
import dbservice.databean.GroupInfo;
import dbservice.databean.PostInfo;

public class PostProcessor {
	
	/*
	 * 发布新帖
	 */
	public void postPost(PostCommData postCommData, Respond respond) {
		PostInfo postInfo = new PostInfo();
		postInfo.setAuthor(postCommData.getUsername());
		postInfo.setContent(postCommData.getPostContent());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getEditTime());
		postInfo.setEtime(dateStr);
		postInfo.setGroupname(postCommData.getGroup());
		postInfo.setPname(postCommData.getPostName());
		dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getPostTime());
		postInfo.setPtime(dateStr);
		postInfo.setTop(postCommData.getIsTop());
		
		int i = DBService.insert(postInfo);
		if (i == 0) {
			respond.setResult(true);
		} else if (i == 1) {
			respond.setResult(false);
			respond.setErrorMessage("帖子已存在，插入失败。");
		} else {
			respond.setResult(false);
			respond.setErrorMessage("发帖失败，请稍后再试。");
		}
	}
	
	
	/*
	 * 获得帖子列表
	 */
	public void getPostList(PostCommData postCommData, Respond respond) {
		PostInfo postInfo = new PostInfo();
		postInfo.setGroupname(postCommData.getGroup());
		ArrayList<Object> objects = DBService.getList(postInfo);
		
		for (Object object : objects) {
			postCommData = new PostCommData();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//df1 = null;

			try {
				postCommData.setEditTime(df1.parse(((PostInfo)object).getEtime()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			postCommData.setGroup(((PostInfo)object).getGroupname());
			postCommData.setIsTop(((PostInfo)object).getTop());
			postCommData.setPostContent(((PostInfo)object).getContent());
			postCommData.setPostName(((PostInfo)object).getPname());
			//df2 = null;

			try {
				postCommData.setPostTime(df2.parse(((PostInfo)object).getPtime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				respond.setResult(false);
				respond.setErrorMessage("获取帖子失败，请稍后再试。");
				e.printStackTrace();
			}
			postCommData.setUsername(((PostInfo)object).getAuthor());			
			respond.addPostArrayList(postCommData);
		}
		respond.setResult(true);
	}
	
	/*
	 * 得到帖子具体内容  ???????????????????????????????????????
	 */
	public void getPostDetail(PostCommData postCommData, Respond respond) {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//df1 = null;
		PostInfo postInfo = new PostInfo();
		postInfo.setPname(postCommData.getPostName());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getPostTime());
		postInfo.setPtime(dateStr);
		Object object = DBService.query(postInfo);
		try {
			postCommData.setEditTime(df1.parse(((PostInfo)object).getEtime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			respond.setResult(false);
			respond.setErrorMessage("获取帖子失败，请稍后再试。");
			e1.printStackTrace();
		}
		postCommData.setGroup(((PostInfo)object).getGroupname());
		postCommData.setIsTop(((PostInfo)object).getTop());
		postCommData.setPostContent(((PostInfo)object).getContent());
		postCommData.setPostName(((PostInfo)object).getPname());
		//df2 = null;

		try {
			postCommData.setPostTime(df2.parse(((PostInfo)object).getPtime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			respond.setResult(false);
			respond.setErrorMessage("获取帖子失败，请稍后再试。");
			e.printStackTrace();
		}
		postCommData.setUsername(((PostInfo)object).getAuthor());
		
		respond.setResult(true);
		respond.setPostCommData(postCommData);
		respond.addPostArrayList(postCommData);
	}

	/*
	 * 获得帖子评论列表
	 */
	public void getCommentList(PostCommData postCommData, Respond respond) {
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setPname(postCommData.getPostName());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getPostTime());
		commentInfo.setPtime(dateStr);				//待会添加帖子发布时间
		
		ArrayList<Object> objects = DBService.getList(commentInfo);
		CommentCommData commData;
		
		for (Object object : objects) {
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				commData = new CommentCommData(((CommentInfo)object).getAuthor(), 
					((CommentInfo)object).getContent(), ((CommentInfo)object).getPname(), 
					((CommentInfo)object).getPtime(), dt.parse(((CommentInfo)object).getCtime()));
				respond.addCommentArrayList(commData);	
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				respond.setResult(false);
				respond.setErrorMessage("获取好友列表错误");
				e.printStackTrace();
			}						
		}
		respond.setResult(true);
	}
	
	/*
	 * 申请组
	 */
	public void applyGroup(GroupCommData groupCommData, Respond respond) {
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setAdmin(groupCommData.getUsername());
		groupInfo.setConfirm("0");
		groupInfo.setGname(groupCommData.getGroupName());
		
		int i = DBService.insert(groupInfo);
		if (i == 0) {
			respond.setResult(true);
		} else {
			 respond.setResult(false);
			 if (i == 1) {
				respond.setErrorMessage("已经存在该组名，请改组名");
			} else {
				respond.setErrorMessage("发生请求失败，请稍后再试");
			}
		}
	}

	/*
	 * 删除帖子
	 */
	public void deletePost(PostCommData postCommData, Respond respond) {
		PostInfo postInfo = new PostInfo();
		postInfo.setPname(postCommData.getPostName());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getPostTime());
		postInfo.setPtime(dateStr);
		if (DBService.delete(postInfo)) {
			respond.setResult(true);
		} else {
			respond.setResult(false);
			respond.setErrorMessage("删除失败，请稍后再试");
		}		
	}
	
	/*
	 * 修改帖子是否置顶
	 */
	public void changePostIsTop(PostCommData postCommData, Respond respond) {
		PostInfo postInfo = new PostInfo();
		postInfo.setPname(postCommData.getPostName());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(postCommData.getPostTime());
		postInfo.setPtime(dateStr);
		if (DBService.update(postInfo)) {
			respond.setResult(true);
		} else {
			respond.setResult(false);
			respond.setErrorMessage("操作失败，请稍后尝试");
		}
	}

	/*
	 * 添加评论
	 */
	public void addComment(CommentCommData commData, Respond respond) {
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setAuthor(commData.getUsername());
		commentInfo.setCid(null);
		commentInfo.setContent(commData.getCommentContent());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commData.getCommentTime());
		commentInfo.setCtime(dateStr);
		commentInfo.setPname(commData.getCommentPostName());
		commentInfo.setPtime(commData.getPostPostTime());
		int i = DBService.insert(commentInfo);
		if (i == 0) {
			respond.setResult(true);
		} else {
			respond.setResult(false);
			respond.setErrorMessage("评论失败，请稍后再试。");
		}
	}
}
