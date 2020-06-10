
package cn.dfrz.gyl.DAO;

import java.util.List;

import cn.dfrz.gyl.model.BookType;

/**
 * 
 * @Decription 图书类型数据库访问接口
 * 
 */
public interface BookTypeDAO {

	/**
	 * @Decription 传统CRUD
	 */
	public int insert(BookType bookType);
	public int delete(int id);
	public int update(BookType bookType);
	public List<BookType> queryAll();
	public BookType queryById(int id);
	public BookType queryByName(String typeName);
	/**
	 * @Decription 分页查询
	 */
	public List<BookType> queryAllInPage(int pageIndex);
}
