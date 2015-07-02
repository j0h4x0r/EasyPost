package dbservice.dao;

import java.sql.*;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.GroupInfo;

public class GroupDAO implements BaseDAO{
	
	//申请一个新组
	public int insert(Object object) {
		GroupInfo group = new GroupInfo();
		group = (GroupInfo)object;
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_group + " " +
					"where" + " " + TableConstant.group_name + "=\"" + group.getGname() +"\";");
		try {
			//已存在这样的组
			if(rs.next())
				return 1;
			else
			{
				int flag = dbc.operate("insert into" + " " + TableConstant.table_group + " " +
						"values(" + "\"" + group.getGname() + "\",\"" + group.getAdmin() + "\",\"" + group.getConfirm() +"\");");
				//操作成功
				if(flag > 0)
					return 0;
				//数据库操作失败
				else
					return 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 2;
	}
	//同意分组申请
	public boolean update(Object object) {
		GroupInfo group = (GroupInfo)object;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("update" + " " +TableConstant.table_group + " " +
				"set" + " " +TableConstant.group_confirm + "=\"1\"" +" " + "where" + " " + 
				TableConstant.group_name + "=\"" + group.getGname() +"\";");
		if(flag > 0)
			return true;
		else
			return false;
	}
	//拒绝分组申请
	public boolean delete(Object object) {
		GroupInfo group = (GroupInfo)object;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("delete from" + " " + TableConstant.table_group + " " +
				"where" + " " + TableConstant.group_name + "=\"" + group.getGname() +"\";");
		if(flag > 0)
			return true;
		else
			return false;
	}

	//得到组列表
	public ArrayList<Object> getList(Object object) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		GroupInfo result;		
	
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_group +";");
		//	+ " " +
		//		"where" + " " + TableConstant.group_admin + "=\"" + group.getAdmin() + 
		//		"\"" + " " + "and" + " " + TableConstant.group_confirm + "=\"1\";");
		try {
			while(rs.next())
			{
				result = new GroupInfo();
				result.setGname(rs.getString(TableConstant.group_name));
				result.setAdmin(rs.getString(TableConstant.group_admin));
				result.setConfirm(rs.getString(TableConstant.group_confirm));
				list.add(result);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object query(Object object) {
		GroupInfo group = (GroupInfo)object;
		GroupInfo result = new GroupInfo();

		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs= dbc.connect("select * from " + TableConstant.table_group + " " +
						"where" + " " + TableConstant.group_name + "=\"" + group.getGname()
						+ "\";");
		try {
			if(rs.next())
			{				
				result.setAdmin(rs.getString(TableConstant.group_admin));
				result.setConfirm(rs.getString(TableConstant.group_confirm));
				result.setGname(rs.getString(TableConstant.group_name));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	

	/*
	public static void main(String[] args)
	{
		GroupInfo group = new GroupInfo();
		//group.setGname("testgroup2") ;
	//	group.setAdmin("yang") ;
	//	group.confirm = "0";
		GroupDAO gd = new GroupDAO();
		ArrayList<Object>  rs = gd.getList(group);
		System.out.println(((GroupInfo)(rs.get(0))).getConfirm());
	}
	*/
	 
}
