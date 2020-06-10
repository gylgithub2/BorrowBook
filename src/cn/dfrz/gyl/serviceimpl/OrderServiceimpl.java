
package cn.dfrz.gyl.serviceimpl;

import java.util.List;

import cn.dfrz.gyl.DAO.OrderDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.service.OrderService;

public class OrderServiceimpl implements OrderService {

	private  OrderDAO orderDAO = FactoryDAO.getOrderDAO();

	@Override
	public int insert(Order order) {
		return orderDAO.insert(order);
	}

	@Override
	public int delete(int id) {
		return orderDAO.delete(id);
	}

	@Override
	public int update(Order order) {
		return orderDAO.update(order);
	}

	@Override
	public List<Order> queryAll() {
		return orderDAO.queryAll();
	}

	@Override
	public Order queryById(int id) {
		return orderDAO.queryById(id);
	}

	@Override
	public List<Order> queryOrderedInPage(int pageIndex){
		return orderDAO.queryOrderedInPage(pageIndex);
	};

	public List<Order> queryOrderInPage(int pageIndex){
		return orderDAO.queryOrderInPage(pageIndex);
	}

	@Override
	public int updateStatus(int id) {
		// TODO Auto-generated method stub
		return orderDAO.updateStatus(id);
	};
}
