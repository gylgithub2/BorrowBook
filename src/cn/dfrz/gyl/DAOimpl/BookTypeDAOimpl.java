package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.DAO.BookTypeDAO;
import cn.dfrz.gyl.model.BookType;
import cn.dfrz.gyl.utils.JDBCUtils;

public class BookTypeDAOimpl extends BaseDAO<BookType> implements BookTypeDAO{

	@Override
	public int insert(BookType bookType) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "INSERT INTO `tb_booktype`(`id`,`type_name`,`days`,`FK`) VALUES(?,?,?,?)";
		return update(connect,sql,bookType.getId(),bookType.getTypeName(),bookType.getDays(),bookType.getFK());
	}

	@Override
	public int delete(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "DELETE FROM `tb_booktype` WHERE `id` = ?";
		return update(connect,sql,id);
	}

	@Override
	public int update(BookType bookType) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "UPDATE  `tb_booktype` SET `type_name` = ?,`days` = ?,`FK` = ? WHERE `id` = ?";
		return update(connect,sql,bookType.getTypeName(),bookType.getDays(),bookType.getFK(),bookType.getId());
	}

	@Override
	public List<BookType> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`type_name` typeName,`days`,`FK` FROM `tb_booktype`";
		return queryAll(connect,sql);
	}

	@Override
	public BookType queryById(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`type_name` typeName,`days`,`FK` FROM `tb_booktype` WHERE `id` = ?";
		return queryOne(connect,sql,id);
	}

	@Override
	public BookType queryByName(String name) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`type_name` typeName,`days`,`FK` FROM `tb_booktype` WHERE `type_name` = ?";
		return queryOne(connect,sql,name);
	}

	@Override
	public List<BookType> queryAllInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex-1)*19;
		String sql = "SELECT `id`,`type_name` typeName,`days`,`FK` FROM `tb_booktype` LIMIT ?,?";
		return queryAll(connect,sql,startIndex,19);
	}

	
}
