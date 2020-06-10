package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.Operator;

public interface OperatorService {

	public int insert(Operator admin);
	public int delete(int id);
	public int update(Operator admin);
	public List<Operator> queryAll();
	public Operator queryById(int id);
	int loginOperator(String name, String password);

	public Operator queryByName(String loginName);
	public List<Operator> queryAllInPage(int pageIndex);
}
