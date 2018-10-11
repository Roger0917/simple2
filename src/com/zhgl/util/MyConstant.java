package com.zhgl.util;

public interface MyConstant {
	
	public static final String MYLOG_NETTY_LOG = "nettyLog";
	
	/** 小写字母 */
	public static final int LOWERCASE = 1;
	/** 大写字母 */
	public static final int CAPITAL = 2;

	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";

	/** 错误的AccessKey */
	public static final String ERROR_ACCESSKEY = "ERROR_ACCESSKEY";

	/** 错误的TOKEN */
	public static final String ERROR_TOKEN = "ERROR_TOKEN";

	/** 内容为空 */
	public static final String ERROR_EMPTY = "ERROR_EMPTY";

	/** 内容为NULL */
	public static final String ERROR_NULL = "ERROR_NULL";

	/** 未知异常 */
	public static final String ERROR_EXCEPTION = "ERROR_EXCEPTION";
	/** 登录错误 */
	public static final String ERROR_LOGIN = "ERROR_LOGIN";

	/** -----工程信息---- */
	/** 工程ID已存在，不能创建 */
	public static final String ERROR_PROJECT_IDEXIST = "ERROR_PROJECT_IDEXIST";
	/** 工程ID不存在，不能修改 */
	public static final String ERROR_PROJECT_IDNOTEXIST = "ERROR_PROJECT_IDNOTEXIST";

	/** -----帐号信息---- */
	/** 帐号ID已存在，不能创建 */
	public static final String ERROR_ACCOUNT_IDEXIST = "ERROR_ACCOUNT_IDEXIST";
	/** 帐号ID不存在，不能修改 */
	public static final String ERROR_ACCOUNT_IDNOTEXIST = "ERROR_ACCOUNT_IDNOTEXIST";

	/** 工程信息来源：省平台 */
	public static final String PROJECT_SOURCE_PROVINCEPLAT = "1";
	/** 工程信息来源：数据平台 */
	public static final String PROJECT_SOURCE_DATAPLAT = "2";

}
