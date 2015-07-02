package dbservice.dao;

import java.sql.*;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.UserInfo;


public class UserDAO implements BaseDAO {
	//注册新用户
	public int insert(Object object) {
		
		UserInfo user = (UserInfo) object;
		
		DBConnection dbc= new DBConnection(ServerConstant.database_name);
		ResultSet check = dbc.connect("select * from" + " "+TableConstant.table_user +
				" " + "where" + " "+TableConstant.user_name + "=\'" + user.getName() +"\';");
			try {
				//重名错误
				if(check.next())
					return 1;
				else
				{
					int flag = dbc.operate("insert into" +" " +TableConstant.table_user + " "
							+ "values(\'"+ user.getName() +"\'," + "\'" + user.getPassword() + "\');");
					//注册成功
					if(flag > 0)
						return 0;
					//数据库操作失败，注册失败
					else
						return 2;	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 2;
	}
	//修改密码
	public boolean update(Object object) {
		
		UserInfo user = (UserInfo)object;
		
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("update" + " " + TableConstant.table_user + " " +
		"set" +" " +TableConstant.user_password + "=\'" + user.getPassword() + "\' "+ "where"+ 
				" " + TableConstant.user_name + "=\'" + user.getName() +"\';");
			if(flag > 0)
				return true;
			else
				return false;
	}
	
	//删除用户
	public boolean delete(Object object) {
		
		UserInfo user = (UserInfo) object;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("delete from" + " " + TableConstant.table_user + " " + "where"
			+ " " + TableConstant.user_name+"=" + "\'" + user.getName() + "\'" + ";");
		if(flag > 0)
			return true;
		else
			return false;
	}
	/*
	public boolean queryByName(Object object) {
		
		UserInfo user = new UserInfo();
		user = (UserInfo) object;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " "+ TableConstant.table_user + " "
				+ "where" + " " +TableConstant.user_name + "=" + "\'" + user.getName() + "\'"
				+ " " + "and" + " " + TableConstant.user_password + "=" +"\'"+ user.getPassword() 
				+ "\';");
		try {                                                                  
			if(rs.next())
				return true; 		
			else 
				return false;
		} catch (SQLException e) {                                                                      
			e.printStackTrace();
		}
		return false;
		
	}
	 */
	//得到用户列表
	public ArrayList<Object> getList(Object object) {
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		UserInfo user = (UserInfo) object;
		UserInfo result;
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs;
		if(user.getName() == null && user.getPassword() == null)		
			 rs = dbc.connect("select * from" + " " +TableConstant.table_user);
		else	
			 rs = dbc.connect("select * from" + " "+ TableConstant.table_user + " "
					+ "where" + " " + TableConstant.user_name + "=" + "\'" + user.getName() + "\'"
					+ " " + "and" + " " + TableConstant.user_password + "=" + "\'"+ user.getPassword() 
					+ "\';");
		
		try {
			while(rs.next())
			{
				result = new UserInfo();
				result.setName(rs.getString(TableConstant.user_name));
				result.setPassword(rs.getString(TableConstant.user_password));
				list.add(result);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	/*
	public static void main(String []args)
	{
		UserInfo user = new UserInfo();
		user.setName("zhang");
		user.setPassword("zheng");
		ArrayList<Object> list = DBservice.getList(user);
		boolean i = true;
		if(list.isEmpty())
			i= false;
		System.out.println(i);
	}
	*/

	public Object query(Object object) {
		
		return null;
	}
}
