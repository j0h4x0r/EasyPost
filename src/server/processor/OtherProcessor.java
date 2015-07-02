package server.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import server.Respond;
import server.Service;

import client.databean.GroupCommData;
import client.databean.LetterCommData;
import client.databean.PostCommData;
import client.databean.UserCommData;
import comm.DataType;
import dbservice.*;
import dbservice.databean.GroupInfo;
import dbservice.databean.LetterInfo;

public class OtherProcessor {

	/*
	 * 获取站内信列表
	 */
	public void getLetterList(UserCommData userCommData, Respond respond) {
		LetterInfo letterInfo = new LetterInfo();
		if (userCommData.getType().equals(DataType.GET_IN_LETTER_BOX_LIST)) {
			letterInfo.setReceiver(userCommData.getUsername());
			letterInfo.setSender(null);
		} else {
			letterInfo.setReceiver(null);
			letterInfo.setSender(userCommData.getUsername());
		}
		ArrayList<Object> arrayList = DBService.getList(letterInfo);
		LetterCommData letterCommData;
		if (arrayList != null) {
			for (Object o : arrayList) {
				letterCommData = new LetterCommData();
				letterCommData.setId(((LetterInfo)o).getLid());
				letterCommData.setLetterContent(((LetterInfo)o).getContent());
				if (userCommData.getType().equals(DataType.GET_IN_LETTER_BOX_LIST)) {
					letterCommData.setLetterOut(((LetterInfo)o).getSender());
					letterCommData.setUsername(((LetterInfo)o).getReceiver());
				} else {
					letterCommData.setLetterOut(((LetterInfo)o).getReceiver());
					letterCommData.setUsername(((LetterInfo)o).getSender());
				}			
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					letterCommData.setTime(df.parse(((LetterInfo)o).getLtime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				letterCommData.setUsername(((LetterInfo)o).getReceiver());
				respond.addLetterCommDataList(letterCommData);			
			}		
		}
		respond.setResult(true);
	}
	
	/*
	 * 发送站内信
	 */
	public void sendLeter(PostCommData letterCommData, Respond respond) {
		LetterInfo letterInfo = new LetterInfo();
		letterInfo.setContent(letterCommData.getPostContent());
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		letterInfo.setLtime(d.format(letterCommData.getPostTime()));
		letterInfo.setReceiver(letterCommData.getInLetter());
		letterInfo.setSender(letterCommData.getUsername());
		
		int i = DBService.insert(letterInfo);
		if (i == 0) {
			respond.setResult(true);
		} else if (i == 2) {
			respond.setResult(false);
			respond.setErrorMessage("不存在该用户。");
		} else {
			respond.setResult(false);
			respond.setErrorMessage("发送失败。");
		}		
	}
	
	/*
	 * 得到组长
	 */
	public void getGroupAdmin(GroupCommData groupCommData, Respond respond) {
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setGname(groupCommData.getGroupName());
		Object o = DBService.query(groupInfo);
		respond.setInfoString(((GroupInfo)o).getAdmin());
		respond.setResult(true);
	}
	
	public void getAnnounce(Respond respond) {
		Service service = new Service();
		respond.setResult(true);
		respond.setInfoString(service.getAnnounce());
	}
}
