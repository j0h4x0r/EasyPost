package dbservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.FriendInfo;

public class FriendDAO implements BaseDAO{

	//��Ӻ���
	public int insert(Object object) {
		
		FriendInfo friend = (FriendInfo)object;
		String name = friend.getSname();
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet mark = dbc.connect("select * from user where " + TableConstant.user_name +
							 "=\'" + name + "\';" );
			try {
				if(mark.next())
				{
					ResultSet rs = dbc.connect("select * from" + " " + TableConstant.table_friend 
							+ " " + "where" + " " + TableConstant.friend_fname + "=\'" + friend.getFname() + "\'"
							+ " " + "and" + " " + TableConstant.friend_sname +  "=\'" + friend.getSname() + "\';");
					try {
						//���ѹ�ϵ�Ѵ���
						if(rs.next())
							return 1;
						else
						{
							int flag = dbc.operate("insert into" + " " + TableConstant.table_friend + " "
									+ "values" + "(\'" + friend.getFname() + "\',\'" + friend.getSname() + "\');");
							//���ѹ�ϵ��ӳɹ�
							if(flag > 0)
								return 0;
							//���ݿ��������
							else
								return 2;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return 2;
				}
				//�����ڸ��û�
				else
					return 3;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 2;
	}

	//û�ã���д��
	public boolean update(Object object) {
		
		return false;
	}

	//ɾ������
	public boolean delete(Object object) {
		
		FriendInfo friend = (FriendInfo)object;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		int flag = dbc.operate("delete from" + " " + TableConstant.table_friend + " " +
				"where" + " " + TableConstant.friend_fname + "=\'" + friend.getFname() + "\'" +
				" " + "and" + " " + TableConstant.friend_sname + "=\'" + friend.getSname() +"\';");
		if(flag > 0)
			return true;
		else
			return false;
	}

	//�õ������б�
	public ArrayList<Object> getList(Object object) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		FriendInfo friend = (FriendInfo)object;
		FriendInfo result ;
		
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs= dbc.connect("select * from" + " " +TableConstant.table_friend + " " 
					+ "where" + " " + TableConstant.friend_fname + "=\'" + friend.getFname()
					+ "\';");
		try {
			while(rs.next())
			{
				result = new FriendInfo();
				result.setFname(rs.getString(TableConstant.friend_fname));
				result.setSname(rs.getString(TableConstant.friend_sname));
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
		FriendInfo fr = new FriendInfo();
		fr.fname = "zhang";
		fr.sname = "yang";
		FriendDAO fd = new FriendDAO();
		boolean rs = fd.delete(fr);
		System.out.println(rs);
	}
	*/
}
