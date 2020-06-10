/**
 * 
 */
package cn.dfrz.gyl.DAO;

import java.util.List;

import cn.dfrz.gyl.model.Order;

/**
 * @Decription 图书订购类数据库访问接口
 */
public interface OrderDAO {

	public int insert(Order order);
	public int delete(int id);
	public int update(Order order);
	public List<Order> queryAll();
	public Order queryById(int id);
	/**
	 * @Decription 已购分页查询
	 */
	public List<Order> queryOrderedInPage(int pageIndex);
	/**
	 * @Decription 未购分页查询
	 */
	public List<Order> queryOrderInPage(int pageIndex);


	/**
	 * @Decription 在订购页面被订购时，修改被订购标志信息为是
	 */
	public int updateStatus(int id);
}
