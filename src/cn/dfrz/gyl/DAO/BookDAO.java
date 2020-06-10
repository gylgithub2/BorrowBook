package cn.dfrz.gyl.DAO;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.model.Book;

/**
 * 
 * @Decription 图书数据库访问接口
 * 
 */
public interface BookDAO {

	/**
	 * @Decription 传统CRUD
	 */
	public int insert(Connection connect,Book book);
	public int delete(Connection connect, String ISBN);
	public int update(Book book);
	public List<Book> queryAll();
	public Book queryByISBN(String ISBN);
	/**
	 * 
	 * @Decription 分页查询
	 * 
	 */
	public List<Book> queryAllInPage(int pageIndex);
	/**
	 * @Decription 模糊查询
	 */
	public List<Book> dimQuery(String dimString);

}
