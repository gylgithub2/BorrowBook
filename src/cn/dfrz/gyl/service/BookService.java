package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.Book;

public interface BookService {
	public int insert(Book book);
	public int delete(String ISBN);
	public int update(Book book);
	public List<Book> queryAll();
	public Book queryByISBN(String ISNM);
	public List<Book> queryAllInPage(int pageIndex);
	/**
	 * 
	 * @Decription 新增图书批量插入,返回0失败插入,1则成功插入
	 */
	public int batchInsert(Book book, int num, String date);

	public List<Book> dimQuery(String dimString);

}
