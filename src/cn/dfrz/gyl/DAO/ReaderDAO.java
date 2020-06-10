/**
 * 
 */
package cn.dfrz.gyl.DAO;

import java.util.List;

import cn.dfrz.gyl.model.Reader;

/**
 * @Decription
 * @author gyl  Email:1076030424@qq.com
 * @version 1.0
 * @date 2020年1月18日下午9:04:47
 *
 */
public interface ReaderDAO {
	public int insert(Reader reader);
	public int delete(String ISBN);
	public int update(Reader reader);
	public List<Reader> queryAll();
	public Reader queryByISBN(String ISBN);
	/**
	 * @Decription 分页查询
	 */
	public List<Reader> queryAllInPage(int pageIndex);
	/**
	 * @Decription 模糊查询
	 */
	public List<Reader> dimQuery(String dimString);

}
