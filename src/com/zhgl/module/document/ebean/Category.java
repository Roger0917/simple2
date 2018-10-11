package com.zhgl.module.document.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zhgl.module.bean.BaseEntity;
import com.zhgl.module.common.ebean.Project;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "td_category")
@Getter
@Setter
public class Category extends BaseEntity {

	private static final long serialVersionUID = 4536947303323410597L;

	@Column(unique = true)
	private String uk; // 唯一标识
	private String name;
	private Integer lv; // 排序标识
	private Boolean init = false;// 系统固化，不可删除

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId")
	private Project project;
}
