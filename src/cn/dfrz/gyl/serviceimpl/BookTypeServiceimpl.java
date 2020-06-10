package cn.dfrz.gyl.serviceimpl;

import java.util.List;

import cn.dfrz.gyl.DAO.BookTypeDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.BookType;
import cn.dfrz.gyl.service.BookTypeService;

public class BookTypeServiceimpl implements  BookTypeService{
	private  BookTypeDAO bookTypeDAO = FactoryDAO.getBookTypeDAO();

	@Override
	public int insert(BookType bookType) {
		return bookTypeDAO.insert(bookType);
	}

	@Override
	public int delete(int id) {
		return bookTypeDAO.delete(id);
	}

	@Override
	public int update(BookType bookType) {
		return bookTypeDAO.update(bookType);
	}

	@Override
	public List<BookType> queryAll() {
		return bookTypeDAO.queryAll();
	}

	@Override
	public BookType queryById(int id) {
		return bookTypeDAO.queryById(id);
	}

	@Override
	public BookType queryByName(String typeName) {
		return bookTypeDAO.queryByName(typeName);
	}

	@Override
	public List<BookType> queryAllInPage(int pageIndex) {
		// TODO Auto-generated method stub
		return bookTypeDAO.queryAllInPage(pageIndex);
	}

}
