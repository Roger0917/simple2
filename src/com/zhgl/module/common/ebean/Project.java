package com.zhgl.module.common.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import com.zhgl.module.bean.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tc_project")
@Getter
@Setter
public class Project extends BaseEntity {
	private static final long serialVersionUID = 3433108395714476516L;

	/** 外部交互使用 */
	@Column(unique = true)
	private String uk;

	/** 项目名称 */
	private String name;

	/** 项目地址 */
	private String address;

	/** 项目经理 */
	private String manager;

	/** 项目经理电话 */
	private String managerPhone;

	/** 开工时间 */
	@Temporal(TemporalType.DATE)
	private Date startDate;
	/** 完工时间 */
	@Temporal(TemporalType.DATE)
	private Date completeDate;

	/** 施工单位 */
	private String buildUnit;
	/** 建设单位 */
	private String constructUnit;
	/** 设计单位 */
	private String designUnit;

	/** 经度 */
	private Double longitude;

	/** 纬度 */
	private Double latitude;

	/** 考勤有效范围半径：以米为单位 */
	private Float attendanceRadius = 50.0f;

	/** 员工信息更新添加会 同步更新此时间 */
	private Date lastEmployeeUpdateTime = new Date();

	/** 平台是否进行认证，认证才可显示给安监站级帐号 */
	private Boolean auth = false;

	/** 创建者 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	/** 所属地区 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "area_id")
	private Area area;

	/** 终端用户名 */
	@Column(unique = true)
	private String username;
	/** 终端密码 */
	private String password;
	/** 终端最后一次交互通讯信息 */
	private String lastAccessInfo;



	/** 关联到省平台的工程ID */
	@Column(unique = true)
	private String pid;

	/** 当前帐号在该项目下的角色 */
	@Transient
	private int role;

	@Override
	public String toString() {
		return "Project [name=" + name + ", address=" + address + ", manager=" + manager + ", managerPhone="
				+ managerPhone + ", startDate=" + startDate + ", completeDate=" + completeDate + ", buildUnit="
				+ buildUnit + ", constructUnit=" + constructUnit + ", designUnit=" + designUnit + ", longitude="
				+ longitude + ", latitude=" + latitude + ", attendanceRadius=" + attendanceRadius
				+ ", lastEmployeeUpdateTime=" + lastEmployeeUpdateTime + ", auth=" + auth + ", member=" + member
				+ ", area=" + area + ", username=" + username + ", password=" + password + ", lastAccessInfo="
				+ lastAccessInfo + ", role=" + role + "]";
	}

}
