package client.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import client.AddFriend;
import client.FriendList;
import client.Main;
import client.MessageDetail;
import client.PostPost;
import client.Reg;
import client.WriteMessage;
import client.databean.CommentCommData;
import client.databean.GroupCommData;
import client.databean.PostCommData;
import client.databean.UserCommData;


import server.Respond;
import comm.DataType;
import comm.MsgClient;

public class UpdateProcessor {
	private MsgClient msgClient;
	private String userName;
	public UpdateProcessor(String userName, MsgClient msgClient) {
		this.msgClient = msgClient;
		this.userName = userName;
	}
	
	/*
	 * 注册新用户
	 */
	public Boolean regist(Reg reg) {
		UserCommData userCommData = new UserCommData(reg.getUserName().getText());
		userCommData.setPassword(String.valueOf(reg.getPassword().getPassword()));
		userCommData.setType(DataType.REGI);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "注册成功，请登录！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 添加好友
	 */
	public Boolean addFriend(String friendName, AddFriend addFriend) {
		UserCommData userCommData = new UserCommData(this.userName);
		userCommData.setFriendName(friendName);
		userCommData.setType(DataType.ADD_FRIEND);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "添加好友成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			addFriend.closeWindow();
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 删除好友
	 */
	public Boolean delFriend(FriendList friendList) {
		UserCommData userCommData = new UserCommData(friendList.getUserName());
		userCommData.setFriendName(friendList.getList().getSelectedItem());
		userCommData.setType(DataType.DEL_FRIEND);
		Respond respond = (Respond) msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "删除好友成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 发表新帖
	 */	
	public Boolean postPost(String postName, String userName, Date postTime, Date editTime, 
			String postContent, String group, String isTop, DataType dataType, Main main) {
		PostCommData postCommData = new PostCommData(postName, userName, postTime, editTime, 
				postContent, group, isTop, dataType);
		postCommData.setType(DataType.POST_POST);
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "发表成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			main.addPost(postCommData);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 申请组操作
	 */	
	public Boolean applyGroup(String groupName, String applyReason, String groupLead, Main main) {
		GroupCommData groupCommData = new GroupCommData(main.getUserName(), groupName, applyReason);
		groupCommData.setUsername(groupLead);
		groupCommData.setType(DataType.ADD_GROUP);
		Respond respond = (Respond)msgClient.sendRequest(groupCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "申请请求已经发送，请等待管理员审核！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}

	/*
	 * 发送站内信
	 */
	public Boolean sendLetter(WriteMessage letter) {
		PostCommData l = new PostCommData(letter.getLetterContent(), letter.getUserName(), 
				new Date(), letter.getLetterIn(), DataType.SEND_LETTER);
		Respond respond = (Respond)msgClient.sendRequest(l);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "站内信发送成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);		
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 回复站内信
	 */
	public Boolean replyLetter(MessageDetail messageDetail) {
		PostCommData l = new PostCommData(messageDetail.getReply(), messageDetail.getUserName(), 
				new Date(), messageDetail.getSendPerson(), DataType.REPLY_LETTER);
		Respond respond = (Respond)msgClient.sendRequest(l);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "站内信发送成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
/*
 * 删除帖子	
 */
	public Boolean delPost(Main main, DataType dataType) {
/*		if (!main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 1).toString().equals(main.getUserName())) {
			JOptionPane.showConfirmDialog(null, "错误，你不是发帖人，无法删除该贴");
			return false;
		} else {*/
			PostCommData postCommData = new PostCommData();
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				postCommData.setPostTime(dt.parse(main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 2).toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postCommData.setPostName(main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 0).toString());
			
			postCommData.setType(dataType);
			
			Respond respond = (Respond)msgClient.sendRequest(postCommData);
			
			if (respond.getResult() == true) {
				JOptionPane.showMessageDialog(null, "删除成功！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else {
				JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
//		}
		
	}
	
	
	/*
	 * 发布帖子
	 */
	public Boolean postPost(Main main, PostPost postPost, Date date, DataType dataType) {
		PostCommData postCommData = new PostCommData();			
		postCommData.setType(DataType.POST_POST);
		postCommData.setEditTime(date);
		postCommData.setGroup(main.getGroupList().getSelectedItem());
		postCommData.setIsTop("0");
		postCommData.setPostContent(postPost.getPostContent().getText());
		postCommData.setPostName(postPost.getPostTitle().getText());
		postCommData.setPostTime(date);
		postCommData.setUsername(postPost.getUserName());
		
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "发帖成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		
		
	}

	/*
	 * 用户修改密码
	 */
	public Boolean changePssword(String userName, String password, String newPassword) {
		UserCommData userCommData = new UserCommData(userName);
		userCommData.setPassword(password);
		userCommData.setNewPassword(newPassword);
		userCommData.setType(DataType.CHANAGE_PASSWORD);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "密码修改成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}

	/*
	 * 修改置顶
	 */
	public Boolean changeIsTop(String postName, String postTime) {
		PostCommData postCommData = new PostCommData();
		postCommData.setType(DataType.CHANGE_IS_TOP);
		postCommData.setPostName(postName);
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			postCommData.setPostTime(d.parse(postTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * 添加评论
	 */
	public Boolean addComment(String userName, String commentContent, String postName, String postPostTime, 
			Date commentTime,DataType dataType) {
		CommentCommData commentCommData = new CommentCommData(userName, commentContent, postName, postPostTime, commentTime);
		commentCommData.setType(dataType);
		
		Respond respond = (Respond)msgClient.sendRequest(commentCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "评论成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}	
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
