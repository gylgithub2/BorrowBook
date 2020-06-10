package cn.dfrz.gyl.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
/**
 * @Decription 图书表ORM映射，需要序列化保存在本地,实现序列化接口
 *
 */
public class Book implements Serializable{

	private static final long serialVersionUID = 123451452L;
	private String ISBN;
	private Integer typeId;
	private String bookName;
	private String writer;
	private String translator;
	private Date date;
	private String publisher;
	private BigDecimal price;
	public Book() {
		super();
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTranslator() {
		return translator;
	}
	public void setTranslator(String translator) {
		this.translator = translator;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Book(String iSBN, Integer typeId, String bookName, String writer, String translator, Date date,
			String publisher, BigDecimal price) {
		super();
		ISBN = iSBN;
		this.typeId = typeId;
		this.bookName = bookName;
		this.writer = writer;
		this.translator = translator;
		this.date = date;
		this.publisher = publisher;
		this.price = price;
	}


}