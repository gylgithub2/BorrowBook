package cn.dfrz.gyl.model;

/**
 * @Decription 操作员表ORM映射
 *
 */
public class Operator {
	private Integer id;
	private String name;
	private boolean sex;
	private Integer age;
	private String identityCard;
	private String workDate;
	private String tel;
	private boolean isAdmin;
	private String password;
	@Override
	public String toString() {
		return "Operator [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", identityCard="
				+ identityCard + ", workDate=" + workDate + ", tel=" + tel + ", isAdmin=" + isAdmin + ", password="
				+ password + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Operator(Integer id, String name, boolean sex, Integer age, String identityCard, String workDate, String tel,
			boolean isAdmin, String password) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.identityCard = identityCard;
		this.workDate = workDate;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.password = password;
	}
	public Operator() {
		super();
	}
	

}
