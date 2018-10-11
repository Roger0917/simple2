package com.zhgl.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class HelperUtil {

	private HelperUtil() {

	}

	/**
	 * 将字节转换为要显示的 xM xKB等
	 * 
	 * @param size
	 * @return
	 */
	public static String showSize(Long size) {
		if (size == null) {
			return "";
		}
		if (size > 1024 * 1024) {
			double mb = size / 1024d / 1024d;
			return decimalTwo(mb) + " M";
		} else if (size > 1024) {
			double kb = size / 1024d;
			return decimalTwo(kb) + " KB";
		} else {
			return size + " Byte";
		}
	}

	/**
	 * 保留两位小数
	 * 
	 * @param d
	 * @return
	 */
	public static String decimalTwo(double d) {
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(d);
	}

	/**
	 * 得到字串的编码方式
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GB2312
				String s = encode;
				return s; // 是的话，返回“GB2312“，以下代码同理
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
	}

	public static Date parseDate(String dateFormat, String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param date
	 *            为null是返回空串
	 * @return
	 */
	public static String parseDate(String dateFormat, Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 判断两个日期是否是同一天 两个日期相差在1天内则认为是同一天
	 * 
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		long s = Math.abs(date1.getTime() - date2.getTime());
		if (s < 24 * 3600 * 1000) {
			return true;
		}
		return false;
	}

	/**
	 * 正式环境返回1
	 * 
	 * @return
	 */
	public static int getRunEvn() {
		try {
			Properties pro = findByName("config.properties");
			return Integer.parseInt(pro.getProperty("run.env"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	public int currentRunEvn() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties props = new Properties();
		try {
			props.load(stream);
			return Integer.parseInt(props.getProperty("run.env"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	public static HelperUtil getInstance() {
		return new HelperUtil();
	}

	public static Properties findByName(String name) {
		InputStream stream = HelperUtil.class.getClassLoader().getResourceAsStream("config.properties");
		Properties props = new Properties();
		try {
			props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String rmbConversion(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零")
						.replaceAll("^整$", "零元整");
	}

	/**
	 * 拼音转换：字母或数字进行原样转换
	 * 
	 * @param str
	 * @param mode
	 *            1=首字母，2=全拼
	 * @return
	 */
	public static String converPinYin(String str, int mode) {
		str = delSESpaceStr(str);
		char py[] = str.trim().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (py[i] >= 'A' && py[i] <= 'Z' || py[i] >= 'a' && py[i] <= 'z' || py[i] >= '0' && py[i] <= '9') {
				sb.append(py[i]);
			} else {
				try {
					String[] pinyin = PinyinHelper.convertToPinyinArray(py[i]);
					if (mode == 1) {
						sb.append(pinyin[0].substring(0, 1));
					} else if (mode == 2) {
						sb.append(pinyin[0]);
					}
				} catch (Exception e) {
					sb.append(py[i]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取传递的字串的长度：一个英文字符计数1，其它字符计数2
	 * 
	 */
	public static long getLength(String str) {
		long count = 0;
		for (char c : str.toCharArray()) {
			if (c > 0 && c < 128) {
				count = count + 1;
			} else {
				count = count + 2;
			}
		}
		return count;
	}

	/**
	 * 载取字符：一个汉字计两个字符，一个英文计一个字符 index:截取的字符个数
	 * 
	 * @param str
	 *            :要操作的字符串
	 * @param count
	 *            ：要截取的字符个数
	 */
	public static String interceptStr(String str, int count) {
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			sb.append(c);
			if (isChineseChar(c + "")) {
				counter = counter + 2;
			} else {
				counter = counter + 1;
			}
			if (counter >= count) {
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 判断是否是汉字
	 * 
	 * @return
	 */
	public static boolean isChineseChar(String str) {
		return str.matches("[\u4e00-\u9fa5]");
	}

	/**
	 * 把汉字替换成空字符
	 */
	public static String replaceEmpty(String str) {
		return str.replaceAll("[\u4e00-\u9fa5]", "");
	}

	/**
	 * 根据出生日期字串（如1990-02-13）得到年龄
	 */
	public static int getAge(String birthday) {
		int age = 0;
		int month = 0;
		int day = 0;
		int year = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowYear = sdf.format(new Date()).substring(0, 4);
		String nowMonth = sdf.format(new Date()).substring(5, 7);
		String nowDay = sdf.format(new Date()).substring(8, 10);
		if (birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
			String bYear = birthday.substring(0, 4);
			String bMonth = birthday.substring(5, 7);
			String bDay = birthday.substring(8, 10);
			month = Integer.parseInt(bMonth);
			day = Integer.parseInt(bDay);
			year = Integer.parseInt(nowYear) - Integer.parseInt(bYear);
			if (month < Integer.parseInt(nowMonth)) {
				age = year;
			} else if (month == Integer.parseInt(nowMonth)) {
				if (day <= Integer.parseInt(nowDay)) {
					age = year;
				} else {
					age = year - 1;
				}
			} else {
				age = year - 1;
			}
		}
		if (age < 0) {
			age = 0;
		}
		return age;
	}

	/**
	 * 删除前后空白字符串
	 */
	public static String delSESpaceStr(String s) {
		String ss = s;
		if (s.startsWith(" ")) {
			ss = s.replaceFirst("( )+", "");
		}
		StringBuffer sb = new StringBuffer(ss);
		while (sb.toString().endsWith(" ")) {
			sb.deleteCharAt(sb.toString().lastIndexOf(" "));
		}
		return sb.toString();
	}

	/**
	 * 对原始日期增加天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要增加的天数
	 * @return
	 */
	public static Date addDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) + day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 对原始日期减少天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要减少的天数
	 * @return
	 */
	public static Date reduceDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) - day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 对原始日期减少小时数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要减少的小时
	 * @return
	 */
	public static Date reduceHours(Date srcDate, int hour) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.HOUR_OF_DAY, cale.get(Calendar.HOUR_OF_DAY) - hour);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 对日期进行加减操作
	 * 
	 * @param srcDate
	 * @param type
	 *            ：1=操作天、2=操作小时、3=操作分钟、4=操作秒
	 * @param unit
	 *            操作的单位，正表示加，负表示减
	 * @return
	 */
	public static Date arithmeticDate(Date srcDate, int type, int unit) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		switch (type) {
		case 1: // 天
			cale.set(Calendar.DATE, cale.get(Calendar.DATE) + unit);
			break;
		case 2: // 小时
			cale.set(Calendar.HOUR_OF_DAY, cale.get(Calendar.HOUR_OF_DAY) + unit);
			break;
		case 3: // 分钟
			cale.set(Calendar.MINUTE, cale.get(Calendar.MINUTE) + unit);
			break;
		case 4: // 秒
			cale.set(Calendar.SECOND, cale.get(Calendar.SECOND) + unit);
			break;
		default:
			break;
		}

		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算两个日期之间的所有日期并返回字串格式：比如2012-01-02，2012-01-06：返回的是2号到5号，但是如果起始日期和结束日期相同， 则不返回任何日期
	 * 
	 * @param startDate
	 *            ：格式为yyyy-MM-dd
	 * @param endDate
	 *            ：格式为yyyy-MM-dd
	 * @return
	 */
	public static List<String> calcDate(String startDate, String endDate) {
		List<String> ret = new ArrayList<String>();
		GregorianCalendar sgc = new GregorianCalendar();
		sgc.setTime(stringToDate(startDate, "yyyy-MM-dd"));
		GregorianCalendar egc = new GregorianCalendar();
		egc.setTime(stringToDate(endDate, "yyyy-MM-dd"));
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
		while (sgc.before(egc)) {
			Date d = sgc.getTime();
			String tempstr = smdf.format(d);
			ret.add(tempstr);
			sgc.add(GregorianCalendar.DATE, 1);
		}
		return ret;
	}

	/**
	 * 计算两个日期之间的天数 (LJX提示:此方法有bug，最好别用 )
	 * 
	 * @param startDate
	 *            ：格式为yyyy-MM-dd
	 * @param endDate
	 *            ：格式为yyyy-MM-dd
	 * @return
	 */
	public static long calcDays(Date startDate, Date endDate) {
		long ret = 0;
		GregorianCalendar sgc = new GregorianCalendar();
		sgc.setTime(startDate);
		GregorianCalendar egc = new GregorianCalendar();
		egc.setTime(endDate);
		while (sgc.before(egc)) {
			ret++;
			sgc.add(GregorianCalendar.DATE, 1);
		}
		return ret;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 把字串转成日期对象
	 * 
	 * @param str
	 *            ：字串对象
	 * @param format
	 *            ：格式化样式
	 */
	public static Date stringToDate(String str, String format) {
		if (str == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date = sdf.parse(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	/***
	 * 根据传入的日期计算 计算当月天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysThisMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDate;
	}

	/**
	 * 根据传入的日期 得到当月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayThisMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 根据传入的日期 得到下月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 根据查询日期类型获取对应的日期列表
	 * 
	 * @param queryDateType
	 * @param queryBeginDate
	 * @param queryEndDate
	 * @return List<0>起始日期，List<1>结束日期，当queryDateType获取List<1>和<2>均为null
	 */
	public static List<Date> listQueryDate(int queryDateType, String queryBeginDate, String queryEndDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		Date beginDate = null;
		Date endDate = null;
		switch (queryDateType) {
		case 0:// 今天
			cal.set(year, month, day, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 1:// 昨天
			cal.set(year, month, day - 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 2:// 前天
			cal.set(year, month, day - 2, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day - 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 3:// 一周内
			cal.set(year, month, day - 6, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 4:// 一月内
			cal.set(year, month - 1, day + 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 5:// 一年内
			cal.set(year - 1, month, day + 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 6: // 全部
			break;
		case 7: // 自定义
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				beginDate = sdf.parse(queryBeginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				endDate = sdf.parse(queryEndDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		List<Date> list = new ArrayList<Date>();
		list.add(beginDate);
		list.add(endDate);
		return list;
	}

	/**
	 * 把日期对象转成【yyyy年MM月dd日】格式的字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}

	/**
	 * 把时间对象转成【yyyy年MM月dd日 hh时mm分ss秒】格式的字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @return
	 */
	public static String dateTimeToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
		return sdf.format(date);
	}

	/**
	 * 根据日期来获取是星期几
	 * 
	 * @param date
	 *            ：日期
	 * @return
	 */
	public static String getWeekDayByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int mydate = calendar.get(Calendar.DAY_OF_WEEK); // 获取指定日期转换成星期几
		String showDate = "";
		switch (mydate) {
		case 1:
			showDate = "日";
			break;
		case 2:
			showDate = "一";
			break;
		case 3:
			showDate = "二";
			break;
		case 4:
			showDate = "三";
			break;
		case 5:
			showDate = "四";
			break;
		case 6:
			showDate = "五";
			break;
		default:
			showDate = "六";
			break;
		}
		return showDate;
	}

	/**
	 * 精确的加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确的乘法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static int rand(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	/**
	 * 获取某年第一天日期
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 */
	public static Date getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 * 获取当前日期几个月后的日期
	 * 
	 * @param mons
	 */
	public static Date getManyMonthLater(int mons) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, mons);
		return calendar.getTime();
	}

}
