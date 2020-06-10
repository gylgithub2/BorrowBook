package cn.dfrz.gyl.serviceimpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.dfrz.gyl.DAO.BookDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.utils.JDBCUtils;

public class BookServiceimpl implements BookService {

	private BookDAO bookDAO = FactoryDAO.getBookDAO();

	@Override
	public int insert(Book book) {
		Connection connect = JDBCUtils.getConnection();
		int i = bookDAO.insert(connect, book);
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;

	}

	@Override
	public int delete(String ISBN) {
		Connection connect = JDBCUtils.getConnection();
		int i = bookDAO.delete(connect, ISBN);
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int update(Book book) {
		return bookDAO.update(book);
	}

	@Override
	public List<Book> queryAll() {
		return bookDAO.queryAll();
	}

	@Override
	public Book queryByISBN(String ISBN) {
		return bookDAO.queryByISBN(ISBN);
	}

	@Override
	public List<Book> queryAllInPage(int pageIndex) {
		return bookDAO.queryAllInPage(pageIndex);
	}
	@Override
	public List<Book> dimQuery(String dimString) {
		return bookDAO.dimQuery(dimString);
		
	}

	// 图书订单的批量插入进数据库,但会-1,则出现异常,方法所有操作回滚,
	@Override
	public int batchInsert(Book book, int num, String date) {
		Connection connect = JDBCUtils.getConnection();
		try {
			// 想当于sql语句: set autocommit =0 ;
			connect.setAutoCommit(false);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		int insertNum = 0;
		try {
			String bookISBN = book.getISBN();
			for (int i = 0; i < num; i++) {
				book.setISBN(bookISBN + "_" + date.split(" ")[0].replaceAll("-", "") + "_" + (i + 1));
				insertNum += bookDAO.insert(connect, book);
			}
			if (insertNum != num) {
				throw new RuntimeException();
			}
			connect.commit();
			insertNum = 1;
		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (connect != null) {
				try {
					connect.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return insertNum;
	}



}
