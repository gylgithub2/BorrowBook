
package cn.dfrz.gyl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @Decription UI的工具类,提供一些通用方法和获取配置文件信息;
 *
 */
public class JFrameUtils {

	/**
	 * @Decription 获取输入的字符串数组,是否有空字符串
	 */
	public static boolean StringArrAnyIsEmpty(String... args) {
		boolean sign = false;
		for (int i = 0; i < args.length; i++) {
			String string = args[i];
			if (string == null || string.isEmpty() || "".equals(string) || string.length() < 1) {
				sign = true;
				return sign;
			}
		}
		return sign;
	}

	/**
	 * @Decription 获取配置文件临时文件存放地址(目前仅新增图书订单时,新增临时图书信息)
	 */
	public static String getTemporaryFileLocation() {
		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("Jframe.properties");
		Properties pros = new Properties();
		try {
			pros.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pros.getProperty("temporaryFileLocation");
	}

	/**
	 * @Decription 获取配置文件背景地址
	 */
	public static String getBackground() {
		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("Jframe.properties");
		Properties pros = new Properties();
		try {
			pros.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = pros.getProperty("backgroundPictureLocation");
		return path;
	}

	/**
	 * 
	 * @Decription 判断日期类型是否合格,合格返回Date,不合格为null
	 */
	public static Date checkDate(String dateString) {
		Date date = null;
		boolean isDate = dateString.matches(
				"((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
		if (isDate) {
			try {
				return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
			} catch (ParseException e) {
			}
		}
		return date;
	}

	/**
	 * 
	 * @Decription 判断是不是数字(正整數),不是则返回-1
	 */
	public static Integer checkNum(String numString) {
		boolean isNum = numString.matches("[\\d]*");
		if (isNum) {
			return Integer.parseInt(numString);
		} else {
			return -1;
		}
	}

	/**
	 * 
	 * @Decription 判断是不是小数类型(包括正整數),不是则返回-1
	 */
	public static Double checkDoubleNum(String numString) {
		boolean isNum = numString.matches("([\\d]*)|([\\d]*\\.[\\d]*)");
		if (isNum) {
			return Double.parseDouble(numString);
		} else {
			return -1.0;
		}
	}

	/**
	 * 
	 * @Decription 判断是不是电话号码(包括正整数),不是则返回false
	 *    移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 *    联通号码段:130、131、132、136、185、186、145
	 *    电信号码段:133、153、180、189
	 */
	public static boolean checkPhoneNum(String numString) {
		return numString.matches("^1((3[0-9])|(4[5|7])|(5([0-3]|[5-9]))|(8[0,5-9]))[0-9]{8}$")
		 || numString.matches("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$");

	}
	/**
	 * 
	 * @Decription 判断密码是不是8-16位、包括数字和密码、不包含特殊字符)，不是则返回false
	 */
	public static boolean checkPasswordStandard(String numString) {
		return numString.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
	}
}