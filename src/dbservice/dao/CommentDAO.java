package dbservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.CommentInfo;

public class CommentDAO implements BaseDAO {

	// 新增评论
	public int insert(Object object) {
		CommentInfo comment = (CommentInfo) object;
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		dbc.operate("update" + " " + TableConstant.table_post +  " " + "set" 
				+ " " + TableConstant.post_etime + "=\'" + comment.getCtime() + "\'" + " "
				+ "where" + " " + TableConstant.post_name + "=\'" + comment.getPname() + "\' and "
				+ TableConstant.post_ptime + "=\'" + comment.getPtime() + "\';");
		int flag = dbc.operate("insert into" + " " + TableConstant.table_comment + " (" 
				+ TableConstant.comment_time + "," + TableConstant.comment_author + ","
				+ TableConstant.comment_content + "," + TableConstant.comment_pname + ","
				+ TableConstant.comment_ptime + ")" + "values(\'"+ comment.getCtime() 
				+ "\',\'"	+ comment.getAuthor() + "\',\'" + comment.getContent()
				+ "\',\'" + comment.getPname() + "\',\'" + comment.getPtime() + "\');");
			// 插入成功
			if (flag > 0)
				return 0;
			// 数据库操作错误！！
			else
				return 1;
	}

	// 没用，不写了。。
	public boolean update(Object object) {

		return false;
	}

	// 没用，不写了。。
	public boolean delete(Object object) {

		return false;
	}

	//得到评论列表
	public ArrayList<Object> getList(Object object) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		CommentInfo comment = (CommentInfo) object;
		CommentInfo result;
	
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_comment + " "
					+ "where" + " " + TableConstant.comment_pname + "=\'" + comment.getPname()
					+ "\'" + " " + "and" + " " + TableConstant.comment_ptime + "=\'" + comment.getPtime()
					+ "\';");
		try {
			while(rs.next())
			{
				result = new CommentInfo();
				result.setCid(rs.getString(TableConstant.comment_id));
				result.setCtime(rs.getString(TableConstant.comment_time));
				result.setAuthor(rs.getString(TableConstant.comment_author));
				result.setContent(rs.getString(TableConstant.comment_content));
				result.setPname(rs.getString(TableConstant.comment_pname));
				list.add(result);
			}
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	public Object query(Object object) {
		
		return null;
	}
	/*
	public static void main(String[] args)
	{
		CommentInfo comm = new CommentInfo();
		CommentDAO comment = new CommentDAO();
		comm.setAuthor("dfd");
		comm.setCid("dd");
		comm.setContent("ffff");
		comm.setCtime("2012-12-10 19:47:23");
		comm.setPname("testpost");
		comm.setPtime("2012-12-03 20:49:51");
		int i = comment.insert(comm);
		System.out.println(i);
	}
	*/
	
}
