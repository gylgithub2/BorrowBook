
package cn.dfrz.gyl.serviceimpl;

import java.util.List;

import cn.dfrz.gyl.DAO.OperatorDAO;
import cn.dfrz.gyl.DAOimpl.FactoryDAO;
import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.service.OperatorService;

public class OperatorServiceimpl implements OperatorService {

	private   OperatorDAO operatorDAO = FactoryDAO.getOperatorDAO();
	@Override
	public int insert(Operator admin) {
		return operatorDAO.insert(admin);
	}

	@Override
	public int delete(int id) {
		return operatorDAO.delete(id);
	}

	@Override
	public int update(Operator admin) {
		return operatorDAO.update(admin);
	}

	@Override
	public List<Operator> queryAll() {
		return operatorDAO.queryAll();
	}

	@Override
	public Operator queryById(int id) {
		return operatorDAO.queryById(id);
	}

	@Override
	public int loginOperator(String name, String password) {
		return operatorDAO.loginOperator(name, password);
	}

	@Override
	public Operator queryByName(String loginName) {

		return operatorDAO.queryByName(loginName);
	}

	@Override
	public List<Operator> queryAllInPage(int pageIndex) {

		return operatorDAO.queryAllInPage(pageIndex);
	}



	
}
