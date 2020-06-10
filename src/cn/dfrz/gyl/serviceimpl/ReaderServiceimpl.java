
package cn.dfrz.gyl.serviceimpl;

import java.util.List;

import cn.dfrz.gyl.DAO.ReaderDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.service.ReaderService;


public class ReaderServiceimpl implements ReaderService{
	private  ReaderDAO readerDAO = FactoryDAO.getReaderDAO();

	@Override
	public int insert(Reader reader) {
		return readerDAO.insert(reader);
	}

	@Override
	public int delete(String ISBN) {
		return readerDAO.delete(ISBN);
	}

	@Override
	public int update(Reader reader) {
		return readerDAO.update(reader);
	}

	@Override
	public List<Reader> queryAll() {
		return readerDAO.queryAll();
	}

	@Override
	public Reader queryByISBN(String ISBN) {
		return readerDAO.queryByISBN(ISBN);
	}

	@Override
	public List<Reader> queryAllInPage(int pageIndex) {
		return readerDAO.queryAllInPage(pageIndex);
	}

	@Override
	public List<Reader> dimQuery(String dimString) {
		return readerDAO.dimQuery(dimString);
	}

}
