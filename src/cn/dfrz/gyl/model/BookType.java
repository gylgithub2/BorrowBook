
package cn.dfrz.gyl.model;

/**
 * @Decription 书类别表ORM映射
 *
 */
public class BookType {
	private Integer id;
	private String typeName;
	private Integer days;
	private double FK;
	public BookType() {
		super();
	}
	public BookType(Integer id, String typeName, Integer days, double FK) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.days = days;
		this.FK = FK;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public double getFK() {
		return FK;
	}
	public void setFK(double FK) {
		this.FK = FK;
	}
	@Override
	public String toString() {
		return "BookType [id=" + id + ", typeName=" + typeName + ", days=" + days + ", FK=" + FK + "]";
	}

}
