package dbservice.dao;

import java.util.ArrayList;

public interface BaseDAO {
	public int insert(Object object);
	public boolean update(Object object);
	public boolean delete(Object object);
	public ArrayList<Object> getList(Object object);
	public Object query(Object object);
}
