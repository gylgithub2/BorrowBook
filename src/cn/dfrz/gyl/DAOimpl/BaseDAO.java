package cn.dfrz.gyl.DAOimpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.utils.JDBCUtils;

/**
 * @Decription 为所有的DAOimpl提供带泛型的父类
 */
@SuppressWarnings("unchecked")
public  class BaseDAO<T> {
	private Class<T> clazz = null;
	{
		// 获取当前对象 泛型父类类型
		Type tp = this.getClass().getGenericSuperclass();
		// 强转为 带参数类型
		ParameterizedType p = (ParameterizedType) tp;
		// 获取父类的泛型类型
		Type[] t2 = p.getActualTypeArguments();
		// 获取第一个参数,强转
		clazz = (Class<T>) t2[0];
	}
	public int delete(Connection connect,String sql,Object...args) {
		return JDBCUtils.update(connect, sql, args);
	}
	public int update(Connection connect,String sql,Object...args) {
		return JDBCUtils.update(connect, sql, args);
	}
	//考虑事务的增删改
	public int transactionUpdate(Connection connect,String sql,Object...args) {
		return JDBCUtils.transactionUpdate(connect, sql, args);
	}
	public int insert(Connection connect,String sql,Object...args) {
		return JDBCUtils.update(connect, sql, args);
	}
	public T queryOne(Connection connect,String sql,Object...args) {
		List<T> list = JDBCUtils.query(connect, sql, clazz, args); 
		if(list.size()<=0) {
			return null;
		}
		return list.get(0);
	}
	public List<T> queryAll(Connection connect,String sql,Object...args) {
		return JDBCUtils.query(connect, sql, clazz, args);
	}
}
