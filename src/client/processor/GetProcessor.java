package client.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.FriendList;
import client.Main;
import client.Message;
import client.MessageDetail;
import client.databean.CommentCommData;
import client.databean.GroupCommData;
import client.databean.LetterCommData;
import client.databean.PostCommData;
import client.databean.UserCommData;

import server.Respond;
import comm.DataType;
import comm.MsgClient;

public class GetProcessor {
	private String userName;
	private MsgClient msgClient;
	
	public  GetProcessor(String userName, MsgClient msgClient) {
		this.userName = userName;
		this.msgClient = msgClient;
	}
	
	/*
	 * 登陆
	 */
	public Respond Login(String userName, String password) {
		UserCommData userCommData = new UserCommData(userName);
		userCommData.setPassword(password);
		userCommData.setType(DataType.LOGIN);
		return (Respond) msgClient.sendRequest(userCommData); //判断结果	
		
	}
	
	/*
	 * 获取好友列表
	 */
	public Boolean getFriendList(FriendList friendList) {
		UserCommData userCommData = new UserCommData(this.userName);
		userCommData.setType(DataType.GET_FRIEND_LIST);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			if (respond.getInfoArrayListArrayList() != null) {
				FriendList.friendList.addFriendList(respond.getInfoArrayListArrayList());	
			}		
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}		
	}
	
	/*
	 * 获取好友组
	 */
	public ArrayList<String> getFrientArrayList() {
		UserCommData userCommData = new UserCommData(this.userName);
		userCommData.setType(DataType.GET_FRIEND_LIST);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			return respond.getInfoArrayListArrayList();			
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}	
	}
	
	/*
	 * 获取组别
	 */
	public Boolean getGroupList(String userName, Main main) {
		UserCommData userCommData = new UserCommData(userName);
		userCommData.setType(DataType.GET_GROUP_LIST);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			for (String s : respond.getInfoArrayListArrayList()) {
				main.addGrouplistNember(s);
			}			
			return true;
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}		
	}
	
	public String getGroupAdmin(String groupName) {
		GroupCommData groupCommData = new GroupCommData(null, groupName, null);
		groupCommData.setType(DataType.GET_GROUP_ADMIN);
		return ((Respond)msgClient.sendRequest(groupCommData)).getInfoString();
	}
	
	/*
	 * 获得组列表
	 */
	public ArrayList<String> getGroupList(String userName) {
		UserCommData userCommData = new UserCommData(userName);
		userCommData.setType(DataType.GET_GROUP_LIST);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == false) {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return respond.getInfoArrayListArrayList();
	}

	
	/*
	 * 获取收件箱或者发件箱
	 */
	public ArrayList<LetterCommData> getLetterList(Message message) {
		UserCommData userCommData = new UserCommData(this.userName);
		if (message.getBoxTypeText().equals("收件箱")) {			
			userCommData.setType(DataType.GET_IN_LETTER_BOX_LIST);			
		} else {
			userCommData.setType(DataType.GET_OUT_LETTER_BOX_LIST);			
		}
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			message.setLetterList(respond.getLetterCommDatasList());
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return respond.getLetterCommDatasList();		
	}
	
	/*
	 * 获得站内信具体信息
	 */
	public Boolean getLetterDetail(MessageDetail messageDetail) {
		UserCommData userCommData = new UserCommData(this.userName);
		userCommData.setType(DataType.GET_LETTER);
		Respond respond = (Respond)msgClient.sendRequest(userCommData);
		if (respond.getResult() == true) {
			String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(respond.getLetterCommDatasList()
					.get(messageDetail.getSelectItemNum()).getTime());;
			messageDetail.setContentText(respond.getLetterCommDatasList().get(messageDetail.getSelectItemNum())
					.getLetterOut() + ":\n" + s + "\n" + respond.getLetterCommDatasList()
					.get(messageDetail.getSelectItemNum()).getLetterContent());
		
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;	
		
	}


	/*
	 * 获得帖子列表
	 */
	public Object[][] getRowData(String userName, String groupName) {
		PostCommData postCommData = new PostCommData();
		postCommData.setType(DataType.GET_POST_LIST);
		postCommData.setUsername(userName);
		postCommData.setGroup(groupName);
		
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getPostCommDatasList() != null) {
		
			Object[][] rowData = new Object[respond.getPostCommDatasList().size()][];
			if (respond.getResult()) {	
				Boolean b;
				for (int i = 0; i < respond.getPostCommDatasList().size(); i++) {
					if (respond.getPostCommDatasList().get(i).getIsTop().equals("1")) {
						b = true;
					} else {
						b = false;
					}		
					String dateStr1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(respond.getPostCommDatasList().get(i).getPostTime());
					String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(respond.getPostCommDatasList().get(i).getEditTime());
					rowData[i] = new Object[]{respond.getPostCommDatasList().get(i).getPostName(), 
							respond.getPostCommDatasList().get(i).getUsername(), dateStr1, dateStr2, b};
				}				
			} else {
				JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			return rowData;		
		} else {
			return null;
		}
	}
	

	/*
	 * 获得组内帖子ArrayList
	 */
	public ArrayList<PostCommData> getPostCommDataArrayList(String userName, String groupName) {
		PostCommData postCommData = new PostCommData();
		postCommData.setType(DataType.GET_POST_LIST);
		postCommData.setUsername(userName);
		postCommData.setGroup(groupName);
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult() == false) {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return respond.getPostCommDatasList();
	}
	
	/*
	 * 获得帖子内容
	 */
	public PostCommData getPostCommData(String postName, String postTime) {
		PostCommData postCommData = new PostCommData();
		postCommData.setPostName(postName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			postCommData.setPostTime(df.parse(postTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postCommData.setType(DataType.GET_POST);
		
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult() == false) {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return respond.getPostCommData();
	}

	/*
	 * 获得评论列表
	 */
	public ArrayList<CommentCommData> getCommentCommDataArrayList(String userName, String postName, String postPostTime) {
		PostCommData postCommData = new PostCommData();
		postCommData.setType(DataType.GET_COMMENT_LIST);
		postCommData.setPostName(postName);
		postCommData.setUsername(userName);
		
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		try {
			postCommData.setPostTime(d.parse(postPostTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		if (respond.getResult() == false) {
			JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return respond.getCommentCommDataArrayList();		
	}
	
	public String getAnnounce() {
		PostCommData postCommData = new PostCommData();
		postCommData.setType(DataType.ANNOUNCE);
		Respond respond = (Respond)msgClient.sendRequest(postCommData);
		return respond.getInfoString();
	}
}
