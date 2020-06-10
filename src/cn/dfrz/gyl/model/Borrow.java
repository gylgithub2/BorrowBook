
package cn.dfrz.gyl.model;

/**
 * @Decription 图书借阅表ORM映射
 */
public class Borrow {
	private Integer id;
	private String bookISBN;
	private Integer operatorId;
	private String readerISBN;
	private boolean isBack;
	private String borrowDate;
	private String backDate;
	private String realBackDate;
	public Borrow(Integer id, String bookISBN, Integer operatorId, String readerISBN, boolean isBack, String borrowDate,
			String backDate,String realBackDate) {
		super();
		this.id = id;
		this.bookISBN = bookISBN;
		this.operatorId = operatorId;
		this.readerISBN = readerISBN;
		this.isBack = isBack;
		this.borrowDate = borrowDate;
		this.backDate = backDate;
		this.realBackDate = realBackDate;
	}
	public Borrow() {
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
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getReaderISBN() {
		return readerISBN;
	}
	public void setReaderISBN(String readerISBN) {
		this.readerISBN = readerISBN;
	}
	public boolean getIsBack() {
		return isBack;
	}
	public void setIsBack(boolean isBack) {
		this.isBack = isBack;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getRealBackDate() {
		return realBackDate;
	}
	public void setRealBackDate(String realBackDate) {
		this.realBackDate = realBackDate;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	@Override
	public String toString() {
		return "Borrow [id=" + id + ", bookISBN=" + bookISBN + ", operatorId=" + operatorId + ", readerISBN="
				+ readerISBN + ", isBack=" + isBack + ", borrowDate=" + borrowDate + ", backDate=" + backDate + ",realBackDate "+realBackDate+"]";
	}
}
