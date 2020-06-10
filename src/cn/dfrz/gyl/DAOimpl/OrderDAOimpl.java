package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.DAO.OrderDAO;
import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.utils.JDBCUtils;

public class OrderDAOimpl extends BaseDAO<Order> implements OrderDAO{

	@Override
	public int insert(Order order) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "INSERT INTO `tb_order`(`new_book_flag`,`book_ISBN`,`date`,`number`,`operator`,`cheak_and_accept`,`zk`,`pay`) VALUES(?,?,?,?,?,?,?,?)";
		return update(connect,sql,order.getNewBookFlag(),order.getBookISBN(),order.getDate(),order.getNumber(),order.getOperator(),order.getCheakAndAccept(),order.getZk(),order.getPay());
	}

	@Override
	public int delete(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "DELETE FROM `tb_order` WHERE `id`=?";
		return update(connect,sql,id);
	}

	@Override
	public int update(Order order) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "UPDATE `tb_order` SET `new_book_flag`=?,`book_ISBN`=?,`date`=?,`number`=?,`operator`=?,`cheak_and_accept`=?,`zk`=?,`pay`=? WHERE `id`=?";
		return  update(connect,sql, order.getNewBookFlag(),order.getBookISBN(),order.getDate(),order.getNumber(),order.getOperator(),order.getCheakAndAccept(),order.getZk(),order.getPay(),order.getId());
	}

	@Override
	public List<Order> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `new_book_flag` newBookFlag,`id`,`book_ISBN`,`date`,`number`,`operator`,`cheak_and_accept` cheakAndAccept,`zk`,`pay` FROM `tb_order`";
		return queryAll(connect,sql);
	}

	@Override
	public Order queryById(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `new_book_flag` newBookFlag,`id`,`book_ISBN`bookISBN,`date`,`number`,`operator`,`cheak_and_accept` cheakAndAccept,`zk`,`pay` FROM `tb_order` WHERE `id`=?";
		return queryOne(connect,sql,id);
	}



	@Override
	public List<Order> queryOrderedInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex-1)*19;
		String sql = "SELECT `new_book_flag` newBookFlag,`id`,`book_ISBN` bookISBN,`date`,`number`,`operator`,`cheak_and_accept` cheakAndAccept,`zk`,`pay` FROM `tb_order` where `cheak_and_accept`=1 LIMIT ?,?";
		return queryAll(connect,sql,startIndex,19);
	}

	@Override
	public List<Order> queryOrderInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex-1)*19;
		String sql = "SELECT `new_book_flag` newBookFlag,`id`,`book_ISBN` bookISBN,`date`,`number`,`operator`,`cheak_and_accept` cheakAndAccept,`zk`,`pay`  FROM `tb_order` where `cheak_and_accept`=0 LIMIT ?,?";
		return queryAll(connect,sql,startIndex,19);
	}

	@Override
	public int updateStatus(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "UPDATE `tb_order` SET `cheak_and_accept`= 1 WHERE `id`=?";
		return  update(connect,sql,id);
	}

}
