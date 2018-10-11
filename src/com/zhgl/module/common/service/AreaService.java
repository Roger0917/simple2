package com.zhgl.module.common.service;

import java.util.List;

import com.zhgl.module.common.ebean.Area;
import com.zhgl.util.dao.DAO;

public interface AreaService extends DAO<Area> {

	public Area findByTheCode(String theCode);

	public List<Long> getSubTypeid(Long[] aids);

	/**
	 * 列出所有地区
	 * 
	 * @return
	 */
	List<Area> listAll();

	/**
	 * 列出所有省级地区
	 * 
	 * @return
	 */
	public List<Area> listProvince();

	/**
	 * 列出所有子级地区
	 * 
	 * @return
	 */
	public List<Area> listChildArea(long areaId);

	/**
	 * 列出所有扬尘噪声相关的地区，以便生成右侧导航树
	 */
	public List<Area> listForYCZS();

	/**
	 * 列出需要进行空气质量抓取的城市
	 * 
	 * @return
	 */
	List<Area> listForAir();

	/**
	 * 列出需要进行天气状况抓取的城市
	 * 
	 * @return
	 */
	List<Area> listForWeather();
}
