package server.processor;

import java.util.ArrayList;

import server.Respond;

import client.databean.UserCommData;

import dbservice.DBService;
import dbservice.databean.FriendInfo;
import dbservice.databean.GroupInfo;
import dbservice.databean.UserInfo;

public class UserProcessor {

	/*
	 * 登陆
	 */
	public void login(UserCommData userCommData, Respond respond) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userCommData.getUsername());
		userInfo.setPassword(userCommData.getPassword());
		ArrayList<Object> uArrayList = DBService.getList(userInfo);
		if (uArrayList.isEmpty()) {
			respond.setResult(false);
			respond.setErrorMessage("用户名或密码错误");
		} else {
			respond.setResult(true);
		}	
	}	
	
	/*
	 * 注册用户
	 */
	public void register(String userName, String password, Respond respond) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userName);
		userInfo.setPassword(password);
		
		int i = DBService.insert(userInfo);
		if (i == 0) {		//注册成功
			respond.setResult(true);
		} else if (i == 1) {
			respond.setResult(false);
			respond.setErrorMessage("已存在该用户名，请修改用户名重新注册");
		} else {
			respond.setResult(false);
			respond.setErrorMessage("注册失败，请稍后尝试");
		}
	}

	/*
	 * 添加好友
	 */
	public void addFriend(String userName, String friendName, Respond respond) {
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setSname(friendName);
		friendInfo.setFname(userName);
		 
		int i = DBService.insert(friendInfo);
		
		if (i == 0) {
			respond.setResult(true);
		} else if (i == 1) {
			respond.setResult(false);
			respond.setErrorMessage("已存在该该关系。");
		} else if (i == 3) {
			respond.setResult(false);
			respond.setErrorMessage("不存在该用户。");
		}else {
			respond.setResult(false);
			respond.setErrorMessage("添加好友失败，请稍后尝试");
		}
	}
	
	/*
	 * 删除好友
	 */
	public void delFriend(UserCommData userCommData, Respond respond) {
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setFname(userCommData.getUsername());
		friendInfo.setSname(userCommData.getFriendName());
		if (DBService.delete(friendInfo)) {
			respond.setResult(true);
		} else {
			respond.setResult(false);
			respond.setErrorMessage("删除好友失败。");
		}
	}
	
	/*
	 * 获得好友列表
	 */
	public void getFriendList(String userName, Respond respond) {
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setFname(userName);
		ArrayList<Object> fArrayList = DBService.getList(friendInfo);
		ArrayList<String> fStrings = new ArrayList<String>();
		for (Object o : fArrayList) {
			fStrings.add(((FriendInfo)o).getSname());
		}
		respond.setInfoArrayListArrayList(fStrings);
		respond.setResult(true);
	}
	
	/*
	 * 修改密码
	 */
	public void changePassword(UserCommData userCommData, Respond respond) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userCommData.getUsername());
		userInfo.setPassword(userCommData.getPassword());
		if (DBService.getList(userInfo).size() == 0) {
			respond.setResult(false);
			respond.setErrorMessage("原始密码名错误。");
		} else {
			userInfo.setPassword(userCommData.getNewPassword());
			if (DBService.update(userInfo)) {
				respond.setResult(true);
			} else {
				respond.setResult(false);
				respond.setErrorMessage("修改失败，请稍后再试。");
			}
		}
	}
	
	/*
	 * 获取组列表
	 */
	public void getGroup(Respond respond) {
		GroupInfo groupInfo = new GroupInfo();
		ArrayList<Object> arrayList = DBService.getList(groupInfo);
		for (Object object : arrayList) {
			if (((GroupInfo)object).getConfirm().equals("1")) {
				respond.addGroupArrayList(((GroupInfo)object).getGname());
			}
		}
		respond.setResult(true);
	}
	
}
