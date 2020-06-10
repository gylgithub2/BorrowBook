package cn.dfrz.gyl.DAO;

import java.util.List;

import cn.dfrz.gyl.model.Operator;

/**
 * 
 * @Decription 操作员类数据库访问接口
 *
 */
public interface OperatorDAO {
	public int insert(Operator admin);
	public int delete(int id);
	public int update(Operator admin);
	public List<Operator> queryAll();
	public Operator queryById(int id);
	public Operator queryByName(String loginName);
	
	/**
	 * @Decription 登录方法,返回有三种情况,-1:账号密码错误,0:普通操作员,1:超级管理员
	 */
	public int loginOperator(String name, String password);
	/**
	 * @Decription 分页查询
	 */
	public List<Operator> queryAllInPage(int pageIndex);
}
