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
	 * ע�����û�
	 */
	public Boolean regist(Reg reg) {
		UserCommData userCommData = new UserCommData(reg.getUserName().getText());
		userCommData.setPassword(String.valueOf(reg.getPassword().getPassword()));
		userCommData.setType(DataType.REGI);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "ע��ɹ������¼��", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * ��Ӻ���
	 */
	public Boolean addFriend(String friendName, AddFriend addFriend) {
		UserCommData userCommData = new UserCommData(this.userName);
		userCommData.setFriendName(friendName);
		userCommData.setType(DataType.ADD_FRIEND);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "��Ӻ��ѳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			addFriend.closeWindow();
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * ɾ������
	 */
	public Boolean delFriend(FriendList friendList) {
		UserCommData userCommData = new UserCommData(friendList.getUserName());
		userCommData.setFriendName(friendList.getList().getSelectedItem());
		userCommData.setType(DataType.DEL_FRIEND);
		Respond respond = (Respond) msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "ɾ�����ѳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * ��������
	 */	
	public Boolean postPost(String postName, String userName, Date postTime, Date editTime, 
			String postContent, String group, String isTop, DataType dataType, Main main) {
		PostCommData postCommData = new PostCommData(postName, userName, postTime, editTime, 
				postContent, group, isTop, dataType);
		postCommData.setType(DataType.POST_POST);
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "����ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			main.addPost(postCommData);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * ���������
	 */	
	public Boolean applyGroup(String groupName, String applyReason, String groupLead, Main main) {
		GroupCommData groupCommData = new GroupCommData(main.getUserName(), groupName, applyReason);
		groupCommData.setUsername(groupLead);
		groupCommData.setType(DataType.ADD_GROUP);
		Respond respond = (Respond)msgClient.sendRequest(groupCommData);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "���������Ѿ����ͣ���ȴ�����Ա��ˣ�", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}

	/*
	 * ����վ����
	 */
	public Boolean sendLetter(WriteMessage letter) {
		PostCommData l = new PostCommData(letter.getLetterContent(), letter.getUserName(), 
				new Date(), letter.getLetterIn(), DataType.SEND_LETTER);
		Respond respond = (Respond)msgClient.sendRequest(l);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "վ���ŷ��ͳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);		
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * �ظ�վ����
	 */
	public Boolean replyLetter(MessageDetail messageDetail) {
		PostCommData l = new PostCommData(messageDetail.getReply(), messageDetail.getUserName(), 
				new Date(), messageDetail.getSendPerson(), DataType.REPLY_LETTER);
		Respond respond = (Respond)msgClient.sendRequest(l);
		if (respond.getResult() == true) {
			JOptionPane.showMessageDialog(null, "վ���ŷ��ͳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
/*
 * ɾ������	
 */
	public Boolean delPost(Main main, DataType dataType) {
/*		if (!main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 1).toString().equals(main.getUserName())) {
			JOptionPane.showConfirmDialog(null, "�����㲻�Ƿ����ˣ��޷�ɾ������");
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
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else {
				JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
//		}
		
	}
	
	
	/*
	 * ��������
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
			JOptionPane.showMessageDialog(null, "�����ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		
		
	}

	/*
	 * �û��޸�����
	 */
	public Boolean changePssword(String userName, String password, String newPassword) {
		UserCommData userCommData = new UserCommData(userName);
		userCommData.setPassword(password);
		userCommData.setNewPassword(newPassword);
		userCommData.setType(DataType.CHANAGE_PASSWORD);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "�����޸ĳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}

	/*
	 * �޸��ö�
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
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/*
	 * �������
	 */
	public Boolean addComment(String userName, String commentContent, String postName, String postPostTime, 
			Date commentTime,DataType dataType) {
		CommentCommData commentCommData = new CommentCommData(userName, commentContent, postName, postPostTime, commentTime);
		commentCommData.setType(dataType);
		
		Respond respond = (Respond)msgClient.sendRequest(commentCommData);
		if (respond.getResult()) {
			JOptionPane.showMessageDialog(null, "���۳ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "��ʾ",
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
