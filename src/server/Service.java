package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import comm.MsgServer;
import dbservice.DBService;
import dbservice.databean.CommentInfo;
import dbservice.databean.GroupInfo;
import dbservice.databean.LetterInfo;
import dbservice.databean.PostInfo;
import dbservice.databean.UserInfo;

public class Service {

	private MsgServer msgServer;

	/**
	 * ������̳����
	 */
	public boolean startService() {
		msgServer = new MsgServer();
		msgServer.start();

		return true;
	}

	/**
	 * �ر���̳
	 */
	public boolean stopService() {
		msgServer.stopService();
		return true;
	}

	/**
	 * ��ȡ��̳����
	 */
	public String getAnnounce() {
		String announce="";
		String temp ;
		FileReader fr = null;
		BufferedReader br = null;
		
		
		try {
			fr = new FileReader("announce.ann");
			br = new BufferedReader(fr);
			
			temp = br.readLine();
			while(temp != null){
				announce = announce +temp +"\n";
				temp = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//	e.printStackTrace();
			new File("announce.ann");
			announce = "û���ҵ�����  д�����~";
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
		if(fr!=null && br!=null){
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return announce;
		
	}

	/**
	 * ������̳����
	 */
	public boolean postAnnounce(String announce) {
		FileWriter fw = null;
		BufferedWriter br = null;

		try {
			fw = new FileWriter("announce.ann");
			br = new BufferedWriter(fw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			try {
				new File("announce.ann");
				fw = new FileWriter("announce.ann");
				br = new BufferedWriter(fw);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		if(br!=null){
			try {
				br.write(announce);
				br.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				try {
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return false;
			}
		}
		
		else{
			return false;
		}
			
	}


	/**
	 * ��������������� ��������Ѱ�� o
	 */
	public ArrayList<Post> getPostByGroup(String groupName) {
		ArrayList<Post> posts = new ArrayList<Post>();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PostInfo flag1 = new PostInfo();
		Post flag2 = new Post(null, null, null, null, true);
		PostInfo group = new PostInfo();
		group.setGroupname(groupName);
		ArrayList<Object> post = new ArrayList<Object>();
		post = DBService.getList(group);
		for (int i = 0; i < post.size(); i++) {
			flag1 = (PostInfo) post.get(i);
			flag2 = new Post(null, null, null, null, true);
			flag2.setPostName(flag1.getPname());
			try {
				flag2.setEditTime( df1.format(df1.parse(flag1.getEtime())) );
				flag2.setPostTime( df1.format(df1.parse(flag1.getEtime()))  );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			flag2.setPostAuthor(flag1.getAuthor());
			if (flag1.getTop().equals("1"))
				flag2.setTop(true);
			else
				flag2.setTop(false);
			posts.add(i, flag2);
		}
		return posts;
	}

	/**
	 * �������ͨ���������Ϣ o
	 */
	public ArrayList<GroupandUser> getAllGroup() {
		ArrayList<GroupandUser> allGroup = new ArrayList<GroupandUser>();
		GroupInfo group = new GroupInfo();
		GroupInfo flag1 = new GroupInfo();
		GroupandUser flag2 = new GroupandUser(null, null, true);
		ArrayList<Object> groups = new ArrayList<Object>();
		groups = DBService.getList(group);
		for (int i = 0; i < groups.size(); i++) {
			flag1 = (GroupInfo) groups.get(i);
			flag2 = new GroupandUser(null, null, true);
			flag2.setGroupName(flag1.getGname());
			flag2.setGroupLeader(flag1.getAdmin());
			flag2.setType(true);
			if(flag1.getConfirm().equals("1"))
				allGroup.add(flag2);
		}

		/*
		 * for debug for(int i =0 ;i<10;i++){ allGroup.add(new
		 * GroupandUser("allGroup"+i,"allLeader"+i,true)); }
		 */
		return allGroup;
	}

	/**
	 * ��ô���׼�ķ��� ���������������� o
	 */
	public ArrayList<GroupandUser> getGroupInfo() {
		ArrayList<GroupandUser> groupInfo = new ArrayList<GroupandUser>();
		GroupInfo group = new GroupInfo();
		GroupInfo flag1 = new GroupInfo();
		GroupandUser flag2 = new GroupandUser(null, null, true);
		ArrayList<Object> groups = new ArrayList<Object>();
		group.setGname(null);
		group.setAdmin(null);
		group.setConfirm(null);
		groups = DBService.getList(group);
		for (int i = 0; i < groups.size(); i++) {
			flag1 = (GroupInfo) groups.get(i);
			flag2 = new GroupandUser(null, null, true);
			flag2.setGroupName(flag1.getGname());
			flag2.setGroupLeader(flag1.getAdmin());
			if(flag1.getConfirm().equals("0"))
				groupInfo.add(flag2);
		}

		return groupInfo;
	}

	/**
	 * ����û��б� o
	 */
	public ArrayList<GroupandUser> getUserInfo() {
		ArrayList<GroupandUser> userInfo = new ArrayList<GroupandUser>();
		UserInfo user = new UserInfo();
		UserInfo flag1 = new UserInfo();
		GroupandUser flag2 = new GroupandUser(null, null, false);
		ArrayList<Object> users = new ArrayList<Object>();
		user.setName(null);
		user.setPassword(null);
		users = DBService.getList(user);
		for (int i = 0; i < users.size(); i++) {
			flag1 = (UserInfo) users.get(i);
			flag2 = new GroupandUser(null, null, false);
			flag2.setUserName(flag1.getName());
			flag2.setUserPwd(flag1.getPassword());
			if(flag2.getUserName().equals("admin") == false)
				userInfo.add(flag2);
		}

		return userInfo;
	}

	/**
	 * ������ӻظ�����  
	 */
	public Object[][] getPostReply(String postTime, String postName) {
		ArrayList<Object> comments = new ArrayList<Object>();
		CommentInfo getcomment = new CommentInfo();
		getcomment.setPname(postName);
		getcomment.setPtime(postTime);
		comments = DBService.getList(getcomment);
		Object[][] reply = new Object[comments.size()][];
		
		int i = 0;
		for (Object object : comments) {
			CommentInfo comment = (CommentInfo)object;
			
			reply[i] = new Object[] { i,comment.getContent(), comment.getAuthor(), 
					 comment.getCtime() };
			i++;
		}
		return reply;
	}

	/**
	 * ����������� ��Ϊ�����߷���������
	 */
	public String getPostDetail(String postTime, String postName) {
	//	String detail = "my new post ";
		PostInfo post = new PostInfo();
		post.setPname(postName);
		post.setPtime(postTime);
		Object object = DBService.query(post);
		PostInfo getPost = (PostInfo)object;
		
		return getPost.getContent();
	}

	/**
	 * Ϊ������ӻظ� o
	 */
	
	public boolean addReply(String postTime, String postName, String reply) {
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CommentInfo addcomment = new CommentInfo();
		addcomment.setPname(postName);
		addcomment.setPtime(postTime);
		addcomment.setCtime(df.format(now));
		addcomment.setAuthor("admin");
		addcomment.setContent(reply);
		if( DBService.insert(addcomment) == 0)
			return true;
		else
			return false;
	}

	/**
	 * ����鳤
	 */
	// ûд
	public String getGroupLeader(String groupName) {
		String leader = "";
		ArrayList<GroupandUser> groupInfo = getAllGroup();
		for(GroupandUser group : groupInfo ){
			if(group.getGroupName().equals(groupName))
				leader = group.getGroupLeader();
			//System.out.println(group.getGroupName()+" "+group.getGroupLeader());
		}
		return leader;
	}

	/**
	 * ͬ��������� ����GroupInfo o
	 */
	public boolean agreeGroup(GroupandUser group) {
		GroupInfo aGroup = new GroupInfo();
		aGroup.setGname(group.getGroupName());
		if( DBService.update(aGroup) )
			return true;
		else
			return false;
	}

	/**
	 * �ܾ��������� ����GroupInfo o
	 */
	public boolean denyGroup(GroupandUser group) {
		GroupInfo dGroup = new GroupInfo();
		dGroup.setGname(group.getGroupName());
		if( DBService.delete(dGroup))
			return true;
		else
			return false;
	}

	/**
	 * ɾ���û� ����UserInfo o
	 */
	public boolean delUser(GroupandUser user) {
		UserInfo dUser = new UserInfo();
		dUser.setName(user.getUserName());
		if( DBService.delete(dUser) )
			return true;
		else
			return false;
	}

	/**
	 * ɾ���� o
	 */
	public boolean delGroup(String groupName) {
		GroupInfo group = new GroupInfo();
		group.setGname(groupName);
		group.setAdmin(null);
		group.setConfirm(null);
		if( DBService.delete(group))
			return true;
		else
			return false;
	}

	/**
	 * ���������ö� ���ԭ����Ϊ�ö� ��ȡ���ö� ���ԭ���Ӳ����ö� ���ö�
	 */
	public boolean setTop(String postTime, String postName) {
		PostInfo post = new PostInfo();
		//post.setGroupname(groupName);
		post.setPname(postName);
		post.setPtime(postTime);
		PostInfo newpost = (PostInfo) DBService.query(post);
		if( DBService.update(newpost) )
			return true;
		else 
			return false;
	}
	

	/**
	 * ɾ������ o
	 */
	public boolean delPost(String postTime, String postName) {
		PostInfo post = new PostInfo();
		//post.setGroupname(groupName);
		post.setPname(postName);
		post.setPtime(postTime);
		if( DBService.delete(post) )
			return true;
		else
			return false;
	}

	/**
	 * ϵͳ����Ա ���û����� o
	 */
	public boolean sendMessage(String userName, String message) {
		Date now = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LetterInfo letter = new LetterInfo();
		letter.setReceiver(userName);
		letter.setSender("admin");
		letter.setContent(message);
		letter.setLid(null);
		letter.setLtime(df2.format(now));
		if( DBService.insert(letter) == 0)
			return true;
		else
			return false;
	}

}
