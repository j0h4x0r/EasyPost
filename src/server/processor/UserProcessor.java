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
	 * ��½
	 */
	public void login(UserCommData userCommData, Respond respond) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userCommData.getUsername());
		userInfo.setPassword(userCommData.getPassword());
		ArrayList<Object> uArrayList = DBService.getList(userInfo);
		if (uArrayList.isEmpty()) {
			respond.setResult(false);
			respond.setErrorMessage("�û������������");
		} else {
			respond.setResult(true);
		}	
	}	
	
	/*
	 * ע���û�
	 */
	public void register(String userName, String password, Respond respond) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userName);
		userInfo.setPassword(password);
		
		int i = DBService.insert(userInfo);
		if (i == 0) {		//ע��ɹ�
			respond.setResult(true);
		} else if (i == 1) {
			respond.setResult(false);
			respond.setErrorMessage("�Ѵ��ڸ��û��������޸��û�������ע��");
		} else {
			respond.setResult(false);
			respond.setErrorMessage("ע��ʧ�ܣ����Ժ���");
		}
	}

	/*
	 * ��Ӻ���
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
			respond.setErrorMessage("�Ѵ��ڸøù�ϵ��");
		} else if (i == 3) {
			respond.setResult(false);
			respond.setErrorMessage("�����ڸ��û���");
		}else {
			respond.setResult(false);
			respond.setErrorMessage("��Ӻ���ʧ�ܣ����Ժ���");
		}
	}
	
	/*
	 * ɾ������
	 */
	public void delFriend(UserCommData userCommData, Respond respond) {
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setFname(userCommData.getUsername());
		friendInfo.setSname(userCommData.getFriendName());
		if (DBService.delete(friendInfo)) {
			respond.setResult(true);
		} else {
			respond.setResult(false);
			respond.setErrorMessage("ɾ������ʧ�ܡ�");
		}
	}
	
	/*
	 * ��ú����б�
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
	 * �޸�����
	 */
	public void changePassword(UserCommData userCommData, Respond respond) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userCommData.getUsername());
		userInfo.setPassword(userCommData.getPassword());
		if (DBService.getList(userInfo).size() == 0) {
			respond.setResult(false);
			respond.setErrorMessage("ԭʼ����������");
		} else {
			userInfo.setPassword(userCommData.getNewPassword());
			if (DBService.update(userInfo)) {
				respond.setResult(true);
			} else {
				respond.setResult(false);
				respond.setErrorMessage("�޸�ʧ�ܣ����Ժ����ԡ�");
			}
		}
	}
	
	/*
	 * ��ȡ���б�
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
