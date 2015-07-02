package dbservice;

import java.util.ArrayList;

import dbservice.databean.UserInfo;

public class test {
	public static void main(String[] args)
	{
		//UserInfo comment = new UserInfo();
		//comment.setName("Õý");
		//comment.setPassword("Õý");
		/*PostInfo post= new PostInfo();
		post.setPname("11");
		post.setPtime("2012-12-07 05:43:30");
		PostInfo list= (PostInfo) DBservice.query(post);
		System.out.println(list.getContent());
		System.out.println(list.getGroupname());*/
		UserInfo user =new UserInfo();
		user.setName("zhang");
		user.setPassword("zheng");
		ArrayList<Object> flag = DBService.getList(user);
		//int i = 0;
		//if(flag == null)
		//	 i =1;
		System.out.println(flag.size());
	}
}
