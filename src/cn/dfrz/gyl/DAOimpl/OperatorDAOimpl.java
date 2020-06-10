package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import cn.dfrz.gyl.DAO.OperatorDAO;
import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.utils.JDBCUtils;

public class OperatorDAOimpl extends BaseDAO<Operator> implements OperatorDAO {
	@Override
	public int insert(Operator operator) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " INSERT INTO `tb_operator`(`name`,`sex`,`age`,`identity_card`,`work_date`,`tel`,`admin`,`password`) VALUES(?,?,?,?,?,?,?,?)";
		return update(connect, sql, operator.getName(), operator.getSex(), operator.getAge(),
				operator.getIdentityCard(), operator.getWorkDate(), operator.getTel(), operator.getIsAdmin(),
				operator.getPassword());
	}

	@Override
	public int delete(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " DELETE FROM `tb_operator` WHERE `id` = ?";
		return update(connect, sql, id);
	}
	@Override
	public int update(Operator operator) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " UPDATE  `tb_operator` SET `name` = ?,`sex` = ?,`age` = ?,`identity_card` = ?,`work_date` = ?,`tel` = ?,`admin` = ?,`password` = ? WHERE `id` = ?";
		return update(connect, sql, operator.getName(), operator.getSex(), operator.getAge(),
				operator.getIdentityCard(), operator.getWorkDate(), operator.getTel(), operator.getIsAdmin(),
				operator.getPassword(), operator.getId());
	}

	@Override
	public List<Operator> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `id`,`name`,`sex`,`age`,`identity_card` identityCard,`work_date` workDate,`tel`,`admin` isAdmin,`password` FROM `tb_operator`";
		return super.queryAll(connect, sql);
	}

	@Override
	public Operator queryById(int id) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `id`,`name`,`sex`,`age`,`identity_card` identityCard,`work_date` workDate,`tel`,`admin` as isAdmin,`password` FROM `tb_operator` WHERE `id` = ?";
		return queryOne(connect, sql, id);
	}

	@Override
	public int loginOperator(String name, String password) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `name`,`password`,`admin` isAdmin FROM `tb_operator`";
		List<Operator> list = new ArrayList<Operator>();
		list = queryAll(connect, sql);
		int login = -1;
		if(list.size()>0) {
		for (int i = 0; i < list.size(); i++) {
			Operator operator = list.get(i);
			if (name.equals(operator.getName())) {
				if (password.equals(operator.getPassword())) {
					if (operator.getIsAdmin()) {
						login = 1;
					}else {
						login = 0;
						
					}
					break;
				}
			}
		}
		}

		return login;
	}

	@Override
	public Operator queryByName(String loginName) {
		Connection connect = JDBCUtils.getConnection();
		String sql = " SELECT `id`,`name`,`sex`,`age`,`identity_card` identityCard,`work_date` workDate,`tel`,`admin` as isAdmin,`password` FROM `tb_operator` WHERE `name` = ?";
		return queryOne(connect, sql, loginName);

	}

	@Override
	public List<Operator> queryAllInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex - 1) * 19;
		String sql = " SELECT `id`,`name`,`sex`,`age`,`identity_card` identityCard,`work_date` workDate,`tel`,`admin` as isAdmin,`password` FROM `tb_operator` LIMIT ?,?";
		return queryAll(connect, sql, startIndex, 19);
	}

}
