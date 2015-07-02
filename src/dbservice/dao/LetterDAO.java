package dbservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbservice.DBConnection;
import dbservice.ServerConstant;
import dbservice.TableConstant;
import dbservice.databean.LetterInfo;


public class LetterDAO implements BaseDAO{

	//send letter
	public int insert(Object object) {
		
		LetterInfo letter = (LetterInfo)object; 
		String name = letter.getReceiver();
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet mark = dbc.connect("select * from user where " + TableConstant.user_name
							+ "=\'" + name + "\';");
		try {
			if(mark.next())
			{
					int flag = dbc.operate("insert into" + " " + TableConstant.table_letter + " " + "(" +
									TableConstant.letter_content + "," + TableConstant.letter_time + "," + 
									TableConstant.letter_sender + "," +TableConstant.letter_receiver + ")" +
									"values" + "(\'"  + letter.getContent() + "\',\'" + letter.getLtime() +
									 "\',\'" + letter.getSender() + "\',\'" + letter.getReceiver() + "\');");
					if (flag > 0)
						return 0;
					else 
						return 1;
			}
			//找不到该用户
			else 
				return 2;
		} catch (NumberFormatException e) {
			e.printStackTrace();	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	//没用
	public boolean update(Object object) {
		
		return false;
	}

	//没用！
	public boolean delete(Object object) {
		return false;
	}

	//得到站内信列表
	public ArrayList<Object> getList(Object object) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		//list = null;
		LetterInfo letter = new LetterInfo();
		LetterInfo result;
		letter = (LetterInfo)object;
		String connect = null;
		DBConnection dbc = new DBConnection(ServerConstant.database_name);
		ResultSet rs = null;
		if(letter.getSender() == null)	
		{
			connect = letter.getReceiver();	
			 rs = dbc.connect("select * from" + " " + TableConstant.table_letter +
					" " + "where" + " " + TableConstant.letter_receiver + "=\'" +
					connect + "\';");
		}
		else if(letter.getReceiver() == null)
		{
			connect = letter.getSender();
			rs = dbc.connect("select * from" + " " + TableConstant.table_letter +
					" " + "where" + " " + TableConstant.letter_sender + "=\'" +
					connect + "\';");
		}		
		
		try {
			while(rs.next())
			{
				result = new LetterInfo();
				result.setLid(rs.getString(TableConstant.letter_id));
				result.setContent(rs.getString(TableConstant.letter_content));
				result.setLtime(rs.getString(TableConstant.letter_time));
				result.setReceiver(rs.getString(TableConstant.letter_receiver));
				result.setSender(rs.getString(TableConstant.letter_sender));
				list.add(result);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
/*
	public static void main(String[] args)
	{
		LetterInfo le = new LetterInfo();
		le.setReceiver("yang");

		LetterDAO ld = new LetterDAO();
		ArrayList<Object> ts = ld.getList(le);
		System.out.println(((LetterInfo)(ts.get(0))).getSender());
	}
*/
	public Object query(Object object) {
		return null;
	}
	

	
}
