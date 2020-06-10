package cn.dfrz.gyl.DAOimpl;

import java.sql.Connection;
import java.util.List;

import cn.dfrz.gyl.DAO.ReaderDAO;
import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.utils.JDBCUtils;

public class ReaderDAOimpl extends BaseDAO<Reader> implements ReaderDAO{

	@Override
	public int insert(Reader reader) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "INSERT INTO `tb_reader`(`name`,`ISBN`,`sex`,`age`,`identity_card`,`date`,`max_num`,`tel`,`keep_money`,`zj`,`zy`,`bztime`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		return update(connect,sql,reader.getName(),reader.getISBN(),reader.getSex(),reader.getAge(),reader.getIdentityCard(),reader.getDate(),reader.getMaxNum(),reader.getTel(),reader.getKeepMoney(),reader.getZj(),reader.getZy(),reader.getBztime());
	}

	@Override
	public int delete(String ISBN) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "DELETE FROM `tb_reader` WHERE ISBN=?";
		return update(connect,sql,ISBN);
	}

	@Override
	public int update(Reader reader) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "UPDATE `tb_reader` SET `name`=?,`sex`=?,`age`=?,`identity_card`=?,`date`=?,`max_num`=?,`tel`=?,`keep_money`=?,`zj`=?,`zy`=?,`bztime`=?  WHERE `ISBN`=?" ;
		return update(connect,sql,reader.getName(),reader.getSex(),reader.getAge(),reader.getIdentityCard(),reader.getDate(),reader.getMaxNum(),reader.getTel(),reader.getKeepMoney(),reader.getZj(),reader.getZy(),reader.getBztime(),reader.getISBN());
	}

	@Override
	public List<Reader> queryAll() {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `name`,`ISBN`,`sex`,`age`,`identity_card` identityCard,`date`,`max_num` maxNum,`tel`,`keep_money` keepMoney,`zj`,`zy`,`bztime` FROM `tb_reader`";
		return queryAll(connect,sql);
	}

	@Override
	public Reader queryByISBN(String ISBN) {
		Connection connect = JDBCUtils.getConnection();
		String sql = "SELECT `name`,`ISBN`,`sex`,`age`,`identity_card` identityCard,`date`,`max_num` maxNum,`tel`,`keep_money` keepMoney,`zj`,`zy`,`bztime` FROM `tb_reader` WHERE `ISBN` = ?";
		return queryOne(connect,sql,ISBN);
	}

	@Override
	public List<Reader> queryAllInPage(int pageIndex) {
		Connection connect = JDBCUtils.getConnection();
		int startIndex = (pageIndex-1)*19;
		String sql = "SELECT `name`,`ISBN`,`sex`,`age`,`identity_card` identityCard,`date`,`max_num` maxNum,`tel`,`keep_money` keepMoney,`zj`,`zy`,`bztime` FROM `tb_reader` LIMIT ?,?";
		return queryAll(connect,sql,startIndex,19);
	}

	@Override
	public List<Reader> dimQuery(String dimString) {
		Connection connect = JDBCUtils.getConnection();
		String sql =  "SELECT `name`,`ISBN`,`sex`,`age`,`identity_card` identityCard,`date`,`max_num` maxNum,`tel`,`keep_money` keepMoney,`zj`,`zy`,`bztime` FROM `tb_reader` WHERE `name` LIKE ? OR `ISBN` LIKE ? OR `tel` LIKE ? OR `identity_card` LIKE ?";
		return super.queryAll(connect, sql,"%"+dimString+"%","%"+dimString+"%","%"+dimString+"%","%"+dimString+"%");
	}

}
