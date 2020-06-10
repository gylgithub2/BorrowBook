
package cn.dfrz.gyl.serviceimpl;

import java.util.List;

import cn.dfrz.gyl.DAO.BorrowDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.Borrow;
import cn.dfrz.gyl.service.BorrowService;

public class BorrowServiceimpl implements BorrowService {

	private   BorrowDAO borrowDAO = FactoryDAO.getBorrowDAO();

	@Override
	public int insertBorrow(Borrow borrow) {
		return borrowDAO.insertBorrow(borrow);
	}

	@Override
	public int delete(int id) {
		return borrowDAO.delete(id);
	}

	@Override
	public int update(Borrow borrow) {
		return borrowDAO.update(borrow);
	}

	@Override
	public List<Borrow> queryAll() {
		return borrowDAO.queryAll();
	}

	@Override
	public Borrow queryById(int id) {
		return borrowDAO.queryById(id);
	}

	@Override
	public List<Borrow> queryReaderBorrowNum(String ReaderISBN) {
		return borrowDAO.queryReaderBorrowNum(ReaderISBN);
	}

	@Override
	public List<Borrow>  queryByBookISBN(String backBookISBN_) {
		return borrowDAO.queryByBookISBN(backBookISBN_);
	}

	@Override
	public List<Borrow> queryAllInPage(int pageIndex) {
		return borrowDAO.queryAllInPage(pageIndex);
	}

	@Override
	public Borrow queryByBookIsBack(String backBookISBN_) {
		return borrowDAO.queryByBookIsBack(backBookISBN_);
	}

	@Override
	public List<Borrow> dimQuery(String dimString) {
		// TODO Auto-generated method stub
		return borrowDAO.dimQuery(dimString);
	}

}
