package cn.dfrz.gyl.service;

import java.util.List;

import cn.dfrz.gyl.model.Reader;

public interface ReaderService {
	public int insert(Reader reader);
	public int delete(String ISBN);
	public int update(Reader reader);
	public List<Reader> queryAll();
	public Reader queryByISBN(String ISBN);
	public List<Reader> queryAllInPage(int pageIndex);
	public List<Reader> dimQuery(String dimString);

}
