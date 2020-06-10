package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.Order;


public interface OrderService {
	public int insert(Order order);
	public int delete(int id);
	public int update(Order order);
	public List<Order> queryAll();
	public Order queryById(int id);

	public List<Order> queryOrderedInPage(int pageIndex);

	public List<Order> queryOrderInPage(int pageIndex);

	public int updateStatus(int id);

}
