package dbservice;

import dbservice.dao.BaseDAO;
import dbservice.dao.CommentDAO;
import dbservice.dao.FriendDAO;
import dbservice.dao.GroupDAO;
import dbservice.dao.LetterDAO;
import dbservice.dao.PostDAO;
import dbservice.dao.UserDAO;
import dbservice.databean.CommentInfo;
import dbservice.databean.FriendInfo;
import dbservice.databean.GroupInfo;
import dbservice.databean.LetterInfo;
import dbservice.databean.PostInfo;
import dbservice.databean.UserInfo;

public class DAOFactory {
	
	private static int ch = 0 ;
	private static DAOFactory daoFactory;
	private String daoName;
	
	private DAOFactory(int choose)
	{
		ch = choose;
		if( choose == 1)
			daoName = "UserDAO";
		else if(choose == 2)
			daoName = "GroupDAO";
		else if(choose == 3)
			daoName = "PostDAO";
		else if(choose == 4)
			daoName = "CommentDAO";
		else if(choose == 5)
			daoName = "LetterDAO";
		else if(choose == 6)
			daoName = "FriendDAO";
	}
	
	public static DAOFactory getInstance(Object object)
	{
		if(object instanceof UserInfo)
			ch = 1;
		else if(object instanceof GroupInfo)
			ch = 2;
		else if(object instanceof PostInfo)
			ch = 3;
		else if(object instanceof CommentInfo)
			ch = 4;
		else if(object instanceof LetterInfo)
			ch = 5;
		else if(object instanceof FriendInfo)
			ch = 6;
		daoFactory =  new DAOFactory(ch);
		return daoFactory;
	}
	
	public BaseDAO getBaseDAO()
	{
		BaseDAO baseDAO = null;
		if(daoName.equals("UserDAO"))
			baseDAO = new UserDAO();
		else if(daoName.equals("GroupDAO"))
			baseDAO = new GroupDAO();
		else if(daoName.equals("PostDAO"))
			baseDAO = new PostDAO();
		else if(daoName.equals("CommentDAO"))
			baseDAO = new CommentDAO();
		else if(daoName.equals("LetterDAO"))
			baseDAO = new LetterDAO();
		else if(daoName.equals("FriendDAO"))
			baseDAO = new FriendDAO();
		return baseDAO;
	}
}
