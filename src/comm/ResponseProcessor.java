package comm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.databean.CommentCommData;
import client.databean.GroupCommData;
import client.databean.PostCommData;
import client.databean.UserCommData;

import server.Respond;
import server.processor.OtherProcessor;
import server.processor.PostProcessor;
import server.processor.UserProcessor;

public class ResponseProcessor extends Thread {
	private Socket socket;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private CommData receivedData = null;
//	private CommData responseData = null;

	public ResponseProcessor(Socket socket) {
		this.socket = socket;
	}

	// 启动线程，响应请求消息
	public void run() {

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		processRequest();
	}

	private void processRequest() {
		while (true) {
			try {
				receivedData = (CommData) in.readObject();
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "反序列化错误！", "错误",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				break;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				break;
			}

			if (receivedData != null) {
				Respond respond = new Respond(true, "");
				if (receivedData.getType() == DataType.ADD_FRIEND) { // 增加好友
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.addFriend(
							((UserCommData) receivedData).getUsername(),
							((UserCommData) receivedData).getFriendName(),
							respond);
				} else if (receivedData.getType().equals(DataType.LOGIN)) { // 登陆
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.login((UserCommData) receivedData, respond);
				} else if (receivedData.getType().equals(
						DataType.GET_FRIEND_LIST)) { // 获得好友列表
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.getFriendList(receivedData.getUsername(),
							respond);
				} else if (receivedData.getType().equals(
						DataType.GET_GROUP_LIST)) { // 获得组列表
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.getGroup(respond);
				} else if (receivedData.getType().equals(
						DataType.GET_IN_LETTER_BOX_LIST)
						|| receivedData.getType().equals(
								DataType.GET_OUT_LETTER_BOX_LIST)) { // 获得收件箱或者发件箱列表
					OtherProcessor otherProcessor = new OtherProcessor();
					otherProcessor.getLetterList((UserCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.GET_LETTER)) { // 获得站内信
					OtherProcessor otherProcessor = new OtherProcessor();
					otherProcessor.getLetterList((UserCommData) receivedData,
							respond);
				} else if (receivedData.getType()
						.equals(DataType.GET_POST_LIST)) { // 获得帖子列表
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.getPostList((PostCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.GET_POST)) { // 获取帖子详情
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.getPostDetail((PostCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(
						DataType.GET_COMMENT_LIST)) { // 评论列表
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.getCommentList(((PostCommData) receivedData),
							respond);
				} else if (receivedData.getType().equals(DataType.REGI)) { // 注册用户
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.register(
							((UserCommData) receivedData).getUsername(),
							((UserCommData) receivedData).getPassword(),
							respond);
				} else if (receivedData.getType().equals(DataType.ADD_FRIEND)) { // 添加好友
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.addFriend(
							((UserCommData) receivedData).getUsername(),
							((UserCommData) receivedData).getFriendName(),
							respond);
				} else if (receivedData.getType().equals(DataType.DEL_FRIEND)) { // 删除好友
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.delFriend((UserCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.POST_POST)) { // 发布新帖
					PostProcessor postProcessor = new PostProcessor();
					postProcessor
							.postPost((PostCommData) receivedData, respond);
				} else if (receivedData.getType().equals(DataType.ADD_GROUP)) { // 申请分组
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.applyGroup((GroupCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.SEND_LETTER)
						|| receivedData.getType().equals(DataType.REPLY_LETTER)) { // 发布或回复站内信
					OtherProcessor otherProcessor = new OtherProcessor();
					otherProcessor.sendLeter((PostCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.DEL_POST)) { // 删除帖子
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.deletePost((PostCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(
						DataType.CHANAGE_PASSWORD)) { // 修改密码
					UserProcessor userProcessor = new UserProcessor();
					userProcessor.changePassword((UserCommData) receivedData,
							respond);
				} else if (receivedData.getType()
						.equals(DataType.CHANGE_IS_TOP)) { // 修改帖子置顶情况
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.changePostIsTop((PostCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.NEW_COMMENT)) { // 添加评论
					PostProcessor postProcessor = new PostProcessor();
					postProcessor.addComment((CommentCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(
						DataType.GET_GROUP_ADMIN)) {
					OtherProcessor otherProcessor = new OtherProcessor();
					otherProcessor.getGroupAdmin((GroupCommData) receivedData,
							respond);
				} else if (receivedData.getType().equals(DataType.DISCONNECT)) {
					try {
						sleep(1000);
						socket.close();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					break;
				} else if (receivedData.getType().equals(DataType.ANNOUNCE)) {
					OtherProcessor otherProcessor = new OtherProcessor();
					otherProcessor.getAnnounce(respond);
				} else {
					respond.setResult(false);
					respond.setErrorMessage("出现未知错误，请稍后再试");
				}
				try {
					out.writeObject(respond);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
		}
	}
}
