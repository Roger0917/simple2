package com.zhgl.module.common.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 地区域
 * 
 */
@Entity
@Table(name = "t_area")
@Getter
@Setter
public class Area {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String fullName;
	private String pinyin;// 全拼
	private String py;// 简拼

	private String address;// 地址
	private String telephone;// 安监站电话

	/** 省市排序 :1=省 2=市 3=区 4=镇 */
	private int level = 1;// 排序用

	@Column(unique = true)
	private String theCode;// 地区码

	private int sort = 1;// 用于同级排序

	private String weatherPinyin; // 天气拼音

	private String airPinyin;// 空气拼音，不为空才进行该城市空气质量相关参数抓取

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	private Area parentArea; // 上级地区
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "parentArea", fetch = FetchType.LAZY)
	private Set<Area> areas = new HashSet<Area>();// 子地区

	/** 是否显示：主要解决授权时合并多个地区为一个逻辑地区(如成都平台的“青羊高新区”)时，这种逻辑地区在某些页面不显示 */
	private Boolean virtual = false;
	private Boolean visible = true;

	public Area() {
		
	}

	public Area(String name, int level, Area parentArea) {
		this.name = name;
		this.level = level;
		this.parentArea = parentArea;
	}

	public Area(String name, int level, Area parentArea, String theCode) {
		this.name = name;
		this.level = level;
		this.parentArea = parentArea;
		this.theCode = theCode;
	}

}
