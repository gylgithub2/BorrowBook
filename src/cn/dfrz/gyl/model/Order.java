
package cn.dfrz.gyl.model;

/**
 * @Decription 订购图书表ORM映射
 */
public class Order {

	private Integer id;
	private boolean newBookFlag;
	public boolean getNewBookFlag() {
		return newBookFlag;
	}

	public void setNewBookFlag(boolean newBookFlag) {
		this.newBookFlag = newBookFlag;
	}

	private String bookISBN;
	private String date;
	private Integer number;
	private String operator;
	private Boolean cheakAndAccept;
	private Double zk;
	private Double pay;

	public Order(Integer id, boolean newBookFlag, String bookISBN, String date, Integer number, String operator,
			Boolean cheakAndAccept, Double zk, Double pay) {
		super();
		this.id = id;
		this.newBookFlag = newBookFlag;
		this.bookISBN = bookISBN;
		this.date = date;
		this.number = number;
		this.operator = operator;
		this.cheakAndAccept = cheakAndAccept;
		this.zk = zk;
		this.pay = pay;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Order() {
		super();
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookISBN() {
		return bookISBN;
	}


	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getCheakAndAccept() {
		return cheakAndAccept;
	}

	public void setCheakAndAccept(Boolean cheakAndAccept) {
		this.cheakAndAccept = cheakAndAccept;
	}

	public Double getZk() {
		return zk;
	}

	public void setZk(Double zk) {
		this.zk = zk;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", newBookFlag=" + newBookFlag + ", bookISBN=" + bookISBN + ", date=" + date
				+ ", number=" + number + ", operator=" + operator + ", cheakAndAccept=" + cheakAndAccept + ", zk=" + zk
				+ ", pay=" + pay + "]";
	}
}
