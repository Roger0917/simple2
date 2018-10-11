package com.zhgl.module.common.ebean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.zhgl.module.bean.BaseEntity;

@Entity
@Table(name = "tc_member")
@Getter
@Setter
public class Member extends BaseEntity {

	/** 省平台帐号ID */
	private String puid;

	private static final long serialVersionUID = -1857279373000036061L;
	/** 身份证号 */
	private String idCard;
	/** 手机号 */
	@Column(unique = true)
	private String mobile;

	/** 登录密码 */
	private String password;

	/** 短信认证 */
	private boolean smsAuth = false;

	/** 姓名 */
	private String name;

	/** 昵称 */
	private String nickname;

	/** 出生日期 */
	private Date birthday;

	/** 性别 */
	private String sex;

	/** 头像 源图 */
	private String photo;

	/** 头像 压缩后的图像 */
	private String photoSmall;

	/** 极光推送id */
	private String rid;

	private String bg; // 后台背景

	private String currentProjectId;
}
