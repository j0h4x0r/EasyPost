package dbservice;

import java.util.ArrayList;

import dbservice.dao.BaseDAO;

public class DBService {
	
	public static int insert(Object object)
	{
		DAOFactory daoFactory = DAOFactory.getInstance(object);
		BaseDAO baseDAO = daoFactory.getBaseDAO();
		int result = baseDAO.insert(object);
		return result;
	}
	
	public static boolean update(Object object)
	{
		DAOFactory daoFactory = DAOFactory.getInstance(object);
		BaseDAO baseDAO = daoFactory.getBaseDAO();
		boolean result = baseDAO.update(object);
		return result;	
	}
	
	public static boolean delete(Object object)
	{
		DAOFactory daoFactory = DAOFactory.getInstance(object);
		BaseDAO baseDAO = daoFactory.getBaseDAO();
		boolean result = baseDAO.delete(object);
		return result;	
	}
	
	public static ArrayList<Object> getList(Object object)
	{
		DAOFactory daoFactory = DAOFactory.getInstance(object);
		BaseDAO baseDAO = daoFactory.getBaseDAO();
		ArrayList<Object> result = baseDAO.getList(object);
		return result;
	}
	public static Object query(Object object)
	{
		DAOFactory daoFactory = DAOFactory.getInstance(object);
		BaseDAO baseDAO = daoFactory.getBaseDAO();
		Object result = baseDAO.query(object);
		return result;
	}
}
