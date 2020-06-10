package cn.dfrz.gyl.DAOimpl;

import cn.dfrz.gyl.DAO.BookDAO;
import cn.dfrz.gyl.DAO.BookTypeDAO;
import cn.dfrz.gyl.DAO.BorrowDAO;
import cn.dfrz.gyl.DAO.OperatorDAO;
import cn.dfrz.gyl.DAO.OrderDAO;
import cn.dfrz.gyl.DAO.ReaderDAO;

/**
 * @Decription 工厂类,进一步实现Service与Dao层的解耦,获得对应的DAO实现类
 */
public class FactoryDAO {
	public static BookDAO getBookDAO() {
		return new BookDAOimpl() ;
	}
	public static BookTypeDAO getBookTypeDAO() {
		return new BookTypeDAOimpl() ;
	}
	public static BorrowDAO getBorrowDAO() {
		return new BorrowDAOimpl() ;
	}
	public static OperatorDAO getOperatorDAO() {
		return new OperatorDAOimpl() ;
	}
	public static OrderDAO getOrderDAO() {
		return new OrderDAOimpl() ;
	}
	public static ReaderDAO getReaderDAO() {
		return new ReaderDAOimpl() ;
	}

}
