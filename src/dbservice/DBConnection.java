package dbservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import dbservice.ServerConstant;

public class DBConnection {
	private String username = ServerConstant.database_username;
	private String password = ServerConstant.database_password;
	private String databaseServer = ServerConstant.database_server;
	private String databaseName;
	//JDBC����
	public DBConnection(String databaseName)
	{
		this.databaseName = databaseName;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException cnfe) {
			System.out.println("�Ҳ������ݿ�������");
		cnfe.printStackTrace();
		}
		try {
			Properties config = new Properties();
			config.load(new FileInputStream("server.properties"));
			String value = null;
			value = config.getProperty("DB_SERVER");
			if (value != null)
				this.databaseServer = value;
			value = null;
			value = config.getProperty("DB_USERNAME");
			if (value != null)
				this.username = value;
			value = null;
			value = config.getProperty("DB_PASSWORD");
			if (value != null)
				this.password = value;
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "��ȡ�����ļ�����", "����",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
	}
	//�������ݿ�
	public ResultSet connect(String queryString)
	{
		try {
			String url = databaseServer + databaseName + "?" + ServerConstant.databaseArgus;
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(queryString);
			return rs;
		} catch (SQLException sqle) {
			System.out.println("�������ݿ��쳣��");
			System.out.println("�쳣��Ϣ��" + sqle.getMessage());
			sqle.printStackTrace();
		}
		return null;		
	}
		public int operate(String operateString)
		{
			try
			{
				String url = databaseServer + databaseName + "?" + ServerConstant.databaseArgus;
				Connection conn = DriverManager.getConnection(url, username, password);
				Statement state = conn.createStatement();
				int result = state.executeUpdate(operateString);
				return result;
			}catch(SQLException sqle)
			{
				System.out.println("�������ݿ��쳣��");
				System.out.println("�쳣��Ϣ��" + sqle.getMessage());
				sqle.printStackTrace();	
			}	
			return 0;
		}
	/*
	public static void main(String[] args)
	{
		DBConnection db = new DBConnection(ServerConstant.database_name);
		ResultSet rs = db.connect("select * from user;");
		try {
			while(rs.next())
			{
				System.out.println(rs.getString("id")+" "+rs.getString("name") + 
						" " + rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	 */
}
