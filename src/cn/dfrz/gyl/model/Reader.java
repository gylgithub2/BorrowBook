/**
 * 
 */
package cn.dfrz.gyl.model;

import java.math.BigDecimal;

/**
 * @Decription
 * @author gyl  Email:1076030424@qq.com
 * @version 1.0
 * @date 2020年1月18日下午9:00:52
 *
 */
public class Reader {

	private String name;
	private String ISBN;
	private boolean sex;
	private Integer age;
	private String identityCard;
	private String date;
	private Integer maxNum;
	private String tel;
	private BigDecimal keepMoney;
	//证件类型
	private Integer zj;
	//职业
	private String zy;
	//办证时间
	private String bztime;
	@Override
	public String toString() {
		return "Reader [name=" + name + ", ISBN=" + ISBN + ", sex=" + sex + ", age=" + age + ", identityCard="
				+ identityCard + ", date=" + date + ", maxNum=" + maxNum + ", tel=" + tel + ", keepMoney=" + keepMoney
				+ ", zj=" + zj + ", zy=" + zy + ", bztime=" + bztime + "]";
	}
	public Reader(String name, String iSBN, boolean sex, Integer age, String identityCard, String date, Integer maxNum,
			String tel, BigDecimal keepMoney, Integer zj, String zy, String bztime) {
		super();
		this.name = name;
		ISBN = iSBN;
		this.sex = sex;
		this.age = age;
		this.identityCard = identityCard;
		this.date = date;
		this.maxNum = maxNum;
		this.tel = tel;
		this.keepMoney = keepMoney;
		this.zj = zj;
		this.zy = zy;
		this.bztime = bztime;
	}
	public Reader() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public BigDecimal getKeepMoney() {
		return keepMoney;
	}
	public void setKeepMoney(BigDecimal keepMoney) {
		this.keepMoney = keepMoney;
	}
	public Integer getZj() {
		return zj;
	}
	public void setZj(Integer zj) {
		this.zj = zj;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getBztime() {
		return bztime;
	}
	public void setBztime(String bztime) {
		this.bztime = bztime;
	}
	
}
