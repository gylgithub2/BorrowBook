package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.Borrow;

public interface BorrowService {

	public int insertBorrow(Borrow borrow);
	public int delete(int id);
	public int update(Borrow borrow);
	public List<Borrow> queryAll();
	public Borrow queryById(int id);
	public List<Borrow> queryReaderBorrowNum(String ReaderISBN);

	public List<Borrow>  queryByBookISBN(String backBookISBN_);


	public List<Borrow> queryAllInPage(int pageIndex);

	public Borrow queryByBookIsBack(String backBookISBN_);

	public List<Borrow> dimQuery(String dimString);

	

}
