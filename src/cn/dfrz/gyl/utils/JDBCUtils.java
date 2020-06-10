package cn.dfrz.gyl.utils;

/**
 * @Decription 工具类,jdbc数据库连接以及基础处理sal语句增删改查
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {
	
	/**
	 * 
	 * @Decription 连接获取
	 */
	public static Connection getConnection() {
		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		Connection conet = null;
		try {
			pros.load(is);
		} catch (IOException e1) {
		}
		String driver = pros.getProperty("driver");
		String url = pros.getProperty("url");
		String userName = pros.getProperty("userName");
		String password = pros.getProperty("password");
		try {
			is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName(driver);
			conet = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conet;
	}

	/**
	 * 
	 * @Decription 资源关闭
	 */
	public static void closeSource(Connection connect, Statement s, ResultSet rs) {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (s != null) {
			try {
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 增删改,传入sql语句以及顺序填入占位符的内容
	public static int update(Connection connect, String sql, Object... args) {
		int row = 0;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connect.prepareStatement(sql);
			// 遍历args形参内容,填入占位符
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			row = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			closeSource(connect, preparedStatement, null);
		}
		return row;

	}
	//相对于传统update 不关闭connection
	public static int transactionUpdate(Connection connect, String sql, Object... args) {
		int row = 0;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connect.prepareStatement(sql);
			// 遍历args形参内容,填入占位符
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			row = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			closeSource(null, preparedStatement, null);
		}
		return row;
		
	}

	// 查询,使用泛型方法返回T类型集合,需要额外传入类的运行时对象
	public static <T> List<T> query(Connection connect, String sql, Class<T> clazz, Object... args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// 泛型集合
		List<T> list = new ArrayList<T>();
		try {
			ps = connect.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();// 获取结果集元数据
			int len = metaData.getColumnCount();// 获取结果集中表的列数
			while (rs.next()) {
				T t = clazz.newInstance();// T类型对象
				for (int i = 0; i < len; i++) { // 遍历
					Object obj = (rs.getObject(i + 1));
					String fieldName = metaData.getColumnLabel(i + 1);// 获取指定列的列名
					Field field = clazz.getDeclaredField(fieldName);// 通过列名(与属性名不同,需在sql语句中加别名)反射获取传入类的对应属性,并赋值
					field.setAccessible(true);
					if (obj instanceof Timestamp) {
						field.set(t, obj.toString());
					} else {
						field.set(t, obj);
					}
				}
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSource(connect, ps, rs);
		}
		return null;
	}
}
