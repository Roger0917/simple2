package com.zhgl.module.document.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zhgl.module.bean.BaseEntity;
import com.zhgl.module.common.ebean.Member;

import lombok.Getter;
import lombok.Setter;

/**
 * 可以是文件、可以是目录
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "td_yunfile_history")
@Getter
@Setter
public class YunFileHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String uk;
	private String name; // 文件名称
	private String number; // 文档编号
	private String extension;// 扩展名
	private Boolean isDir = false;
	private Boolean isFile = true;
	private Long size;// 文件大小
	private String path;// 文件路径
	private String md5; // 文件MD5值

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "fileId")
	private YunFile yunFile;

	@Transient
	private String showSize;// 文件大小只在页面中显示
}
