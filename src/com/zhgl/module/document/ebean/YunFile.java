package com.zhgl.module.document.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "td_yunfile")
@Getter
@Setter
public class YunFile extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String uk;
	private String name; // 文件名称
	private String number; // 文档编号
	private String extension;// 扩展名
	private Boolean isDir = false;
	private Boolean isFile = true;
	private Boolean init;// 系统固化，不可删除
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
	@JoinColumn(name = "parentId")
	private YunFile parentFile;

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "parentFile", fetch = FetchType.LAZY)
	private List<YunFile> fileList = new ArrayList<>();

	@Transient
	private String showSize;// 文件大小只在页面中显示
	@Transient
	private String icon;// 文件类型使用的图标

	public void setFileTypeIcon() {
		if ("mp4".equals(extension) || "avi".equals(extension) || "mp4".equals(extension)) {
			this.icon = "file-movie-o";
		} else if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension)) {
			this.icon = "file-image-o";
		} else if ("zip".equals(extension) || "rar".equals(extension) || "7z".equals(extension)) {
			this.icon = "file-archive-o";
		} else if ("mp3".equals(extension) || "ogg".equals(extension) || "wav".equals(extension)
				|| "wma".equals(extension) || "vqf".equals(extension) || "midi".equals(extension)) {
			this.icon = "file-audio-o";
		} else if ("java".equals(extension) || "php".equals(extension) || "jsp".equals(extension)
				|| "sql".equals(extension) || "html".equals(extension) || "xml".equals(extension)) {
			this.icon = "file-code-o";
		} else if ("doc".equals(extension)) {
			this.icon = "file-word-o";
		} else if ("xls".equals(extension)) {
			this.icon = "file-excel-o";
		} else if ("ppt".equals(extension)) {
			this.icon = "file-powerpoint-o";
		} else if ("pdf".equals(extension)) {
			this.icon = "file-pdf-o";
		} else if ("txt".equals(extension)) {
			this.icon = "file-text-o";
		} else {
			this.icon = "file-o";
		}
	}

}
