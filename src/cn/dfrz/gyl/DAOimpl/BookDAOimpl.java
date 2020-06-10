package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.DAO.BookDAO;
import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.utils.JDBCUtils;

public class BookDAOimpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public int insert(Connection connect,Book book) {
		String sql = " INSERT INTO `tb_bookinfo`(`ISBN`,`type_id`,`book_name`,`writer`,`translator`,`date`,`publisher`,`price`) VALUES(?,?,?,?,?,?,?,?)";
		return transactionUpdate(connect, sql, book.getISBN(), book.getTypeId(), book.getBookName(), book.getWriter(),
				book.getTranslator(),book.getDate(), book.getPublisher(), book.getPrice());
	}

	@Override
	public int delete(Connection connect,String ISBN) {
		String sql = " DELETE FROM `tb_bookinfo` WHERE `ISBN` = ?";
		return transactionUpdate(connect, sql, ISBN);
	}

	@Override
	public int update(Book book) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " UPDATE  `tb_bookinfo` SET `type_id`=?,`book_name` = ?,`writer`=?,`translator`=?,`date`=?,`publisher`=?,`price` = ? WHERE `ISBN` = ?";
		return update(connect, sql,book.getTypeId(),book.getBookName(),book.getWriter(),book.getTranslator(),book.getDate(),book.getPublisher(),book.getPrice(),book.getISBN());
	}

	@Override
	public List<Book> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `ISBN`,`type_id` typeId,`book_name` bookName,`writer`,`translator`,`date`,`publisher`,`price` FROM `tb_bookinfo`";
		return super.queryAll(connect, sql);
	}

	@Override
	public Book queryByISBN(String ISBN) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `ISBN`,`type_id` typeId,`book_name` bookName,`writer`,`translator`,`date`,`publisher`,`price` FROM `tb_bookinfo` WHERE `ISBN` = ?";
		return queryOne(connect, sql, ISBN);
	}
	

	public List<Book> queryAllInPage(int pageIndex,int rows) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex-1)*rows;
		String sql = "SELECT `ISBN`,`type_id` typeId,`book_name` bookName,`writer`,`translator`,`date`,`publisher`,`price` FROM `tb_bookinfo` LIMIT ?,?";
		return queryAll(connect,sql,startIndex,rows);
	}

	@Override
	public List<Book> dimQuery(String dimString) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `ISBN`,`type_id` typeId,`book_name`"
				+ " bookName,`writer`,`translator`,`date`,`publisher`,"
				+ "`price` FROM `tb_bookinfo` WHERE `ISBN` like ? OR `book_name` like ?";
		return super.queryAll(connect, sql,"%"+dimString+"%","%"+dimString+"%");
	}

	@Override
	public List<Book> queryAllInPage(int pageIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
