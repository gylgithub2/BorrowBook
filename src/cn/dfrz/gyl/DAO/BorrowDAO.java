package cn.dfrz.gyl.DAO;

import java.util.List;

import cn.dfrz.gyl.model.Borrow;

/**
 * 
 * @Decription 借阅类数据库访问接口
 *
 */
public interface BorrowDAO {

	/**
	 * @Decription 传统CRUD
	 */
	public int insertBorrow(Borrow borrow);
	public int delete(int id);
	public int update(Borrow borrow);
	public List<Borrow> queryAll();
	public Borrow queryById(int id);
	/**
	 * @Decription 获取图书所以借阅信息
	 */
	public List<Borrow> queryByBookISBN(String backBookISBN_);
	/**  
	 * @Decription 借书时，查询读者的当前的借阅数
	 */
	public List<Borrow> queryReaderBorrowNum(String readerISBN);
	/**
	 * @Decription 分页查询
	 */
	public List<Borrow> queryAllInPage(int pageIndex);
	/**
	 * @Decription  查询该书有没有借阅未还记录
	 */
	public Borrow queryByBookIsBack(String backBookISBN_);
	/**
	 * @Decription 模糊查询
	 */
	public List<Borrow> dimQuery(String dimString);

}


