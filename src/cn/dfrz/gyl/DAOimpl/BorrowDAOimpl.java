package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.DAO.BorrowDAO;
import cn.dfrz.gyl.model.Borrow;
import cn.dfrz.gyl.utils.JDBCUtils;

public class BorrowDAOimpl extends BaseDAO<Borrow> implements BorrowDAO {

	@Override
	public int insertBorrow(Borrow borrow) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "INSERT INTO `tb_borrow`(`id`,`book_ISBN`,`operator_id`,`reader_ISBN`,`isback`,`borrow_date`,`back_date`,`real_back_date`) VALUES (?,?,?,?,?,?,?,?)";
		return update(connect, sql, borrow.getId(), borrow.getBookISBN(), borrow.getOperatorId(),
				borrow.getReaderISBN(), borrow.getIsBack(), borrow.getBorrowDate(), borrow.getBackDate(),
				borrow.getRealBackDate());
	}

	@Override
	public int delete(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "DELETE FROM `tb_borrow` WHERE id = ?";
		return update(connect, sql, id);
	}

	@Override
	public int update(Borrow borrow) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "UPDATE `tb_borrow` SET `book_ISBN`=?,`operator_id`=?,`reader_ISBN`=?,`isback`=?,`borrow_date`=?,`back_date`=?,`real_back_date`=? WHERE `id`=?";
		return update(connect, sql, borrow.getBookISBN(), borrow.getOperatorId(), borrow.getReaderISBN(),
				borrow.getIsBack(), borrow.getBorrowDate(), borrow.getBackDate(), borrow.getRealBackDate(),
				borrow.getId());
	}

	@Override
	public List<Borrow> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate,`real_back_date` realBackDate  FROM `tb_borrow`";
		return queryAll(connect, sql);
	}

	@Override
	public Borrow queryById(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` WHERE `id`=?";
		return queryOne(connect, sql, id);
	}

	@Override
	public List<Borrow> queryReaderBorrowNum(String readerISBN) {

		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` WHERE `reader_ISBN`=? And `isback`=0";
		return queryAll(connect, sql, readerISBN);
	}

	@Override
	public List<Borrow> queryByBookISBN(String backBookISBN_) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` WHERE `book_ISBN`=?";
		List<Borrow> list = queryAll(connect, sql, backBookISBN_);

		return list;

	}

	@Override
	public List<Borrow> queryAllInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex - 1) * 19;
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` LIMIT ?,?";
		return queryAll(connect, sql, startIndex, 19);
	}

	@Override
	public Borrow queryByBookIsBack(String backBookISBN_) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` WHERE `book_ISBN`=? AND `isBack`=0";
		List<Borrow> list = queryAll(connect, sql, backBookISBN_);
		if (list.size() > 0) {
			return list.get(list.size() - 1);
		}else {
			return null;
		}
	}

	@Override
	public List<Borrow> dimQuery(String dimString) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `id`,`book_ISBN` bookISBN,`operator_id` operatorId,`reader_ISBN` readerISBN,`isback` isBack,`borrow_date` borrowDate,`back_date` backDate ,`real_back_date` realBackDate FROM `tb_borrow` WHERE `book_ISBN` like ? OR `reader_ISBN` like ?";
		return super.queryAll(connect, sql,"%"+dimString+"%","%"+dimString+"%");
	}

}
