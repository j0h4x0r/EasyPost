package dbservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.PostInfo;

public class PostDAO implements BaseDAO{

	//发表新帖子
	public int insert(Object object) {
		PostInfo post = (PostInfo)object;
	
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet check = dbc.connect("select * from" + " " +TableConstant.table_post + " "
				+ "where" +  " " + TableConstant.post_name + "=\'" + post.getPname() + "\' " + "and" +
				" " +TableConstant.post_ptime + "=\'" + post.getPtime() +"\';");
		try {
			//此帖子已经存在，插入失败
			if(check.next())
				return 1;
			else{
				int flag = dbc.operate("insert into" + " " + TableConstant.table_post + " " + "values" 
						+"(\'" + post.getPname() + "\',\'" + post.getPtime() +"\',\'" + post.getEtime() + "\',\'" + post.getTop() 
						+ "\',\'" + post.getContent() + "\',\'" + post.getAuthor() + "\',\'" + post.getGroupname() + "\');");
				//插入成功
				if(flag > 0)
					return 0;
				//数据库操作失败，插入失败
				else
					return 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 2;
	}

	//置顶帖子！
	public boolean update(Object object) {
		//boject分为pname && ptime
		PostInfo post = (PostInfo)object;
	
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag;
		ResultSet rs = dbc.connect("select"+ " " + TableConstant.post_top+ " " + "from" + " "
						+ TableConstant.table_post + " " + "where" + " " + TableConstant.post_name 
						+ "=\'" + post.getPname() +"\' and " +TableConstant.post_ptime + "=\'" 
						+ post.getPtime() + "\';");
		String top = new String();
		try {
			if(rs.next())
				top = rs.getString(TableConstant.post_top);
			} catch (SQLException e) {
			e.printStackTrace();
		}
			
			if(top.equals("1"))
			{
				flag = dbc.operate("update" + " " + TableConstant.table_post + " " + "set" + " " +
						TableConstant.post_top + "=\'0\'" + " " + "where" + " " +
						TableConstant.post_name + "=\'" + post.getPname() +"\' and " +
						TableConstant.post_ptime + "=\'" + post.getPtime() + "\';");
			}
			else
			{
				flag = dbc.operate("update" + " " + TableConstant.table_post + " " + "set" + " " +
						TableConstant.post_top + "=\'1\'" + " " + "where" + " " +
						TableConstant.post_name + "=\'" + post.getPname() +"\' and " +
						TableConstant.post_ptime + "=\'" + post.getPtime() + "\';");
			}
		
		if(flag > 0)
			return true;
		else
			return false;
	}

	//删除帖子！
	public boolean delete(Object object) {
		
		PostInfo post = (PostInfo)object;		
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("delete from" + " " + TableConstant.table_post + " " 
				+ "where" + " " + TableConstant.post_name + "=\'" + post.getPname() + "\'" + " "
				+ "and" + " " + TableConstant.post_ptime + "=\'" + post.getPtime() +"\';");
		if(flag > 0) 
			return true;
		else
			return false;
	}

	//得到帖子列表
	public ArrayList<Object> getList(Object object) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		PostInfo post = (PostInfo)object;
		PostInfo result;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_post + " "
					+ "where" + " " + TableConstant.post_groupname + "=\'" + post.getGroupname()
					+ "\'" + " " + "ORDER BY" + " " + TableConstant.post_top + " DESC," 
					+ TableConstant.post_ptime +" "+ "DESC;");
		try {
			while(rs.next())
			{
				result = new PostInfo();
				result.setPname(rs.getString(TableConstant.post_name));
				result.setPtime(rs.getString(TableConstant.post_ptime));
				result.setEtime(rs.getString(TableConstant.post_etime));
				result.setTop(rs.getString(TableConstant.post_top));
				result.setAuthor(rs.getString(TableConstant.post_author));
				result.setGroupname(rs.getString(TableConstant.post_groupname));
				result.setContent(rs.getString(TableConstant.post_content));
				list.add(result);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Object query(Object object) {
		//ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		PostInfo post =(PostInfo)object;
		PostInfo result= new PostInfo();
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_post + " "
					+ "where" + " " + TableConstant.post_name + "=\'" + post.getPname()
					+ "\'" + " "  + "and" + " " + TableConstant.post_ptime + "=\'" + post.getPtime()
					+"\';");
		try { 
			if(rs.next())
			{ 
				
				result.setPname(rs.getString(TableConstant.post_name));
				result.setPtime(rs.getString(TableConstant.post_ptime));
				result.setEtime(rs.getString(TableConstant.post_etime));
				result.setTop(rs.getString(TableConstant.post_top));
				result.setAuthor(rs.getString(TableConstant.post_author));
				result.setGroupname(rs.getString(TableConstant.post_groupname));
				result.setContent(rs.getString(TableConstant.post_content));
				//System.out.println(rs.getString(TableConstant.post_content));
				//list.add(result);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args)
	{
		PostInfo group = new PostInfo();
		group.setPname("11111");
		group.setPtime("2012-12-07 05:49:40");
		//group.setEtime("2012-12-3 20:49:50");
		//group.setTop("0") ;
		//group.setContent("aaaaaaaaaaaaaaa");
		//group.setAuthor("zhang");
		//group.setGroupname("testgroup2");
		
		//int ts = 7;
		PostDAO pd = new PostDAO();
		//ArrayList<Object> re =  pd.getList(group);
		boolean rs = pd.update(group);
		//for(int i = 0 ; i < re.size();i++)
		//	System.out.println(((PostInfo)re.get(i)).getPname());
		System.out.println(rs);
	}
	
}
