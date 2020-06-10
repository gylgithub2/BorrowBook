package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.BookType;

public interface BookTypeService {
	public int insert(BookType bookType);
	public int delete(int id);
	public int update(BookType bookType);
	public List<BookType> queryAll();
	public BookType queryById(int id);
	public BookType queryByName(String typeName);

	public List<BookType> queryAllInPage(int pageIndex);

}
