package com.zhgl.module.document.service;

import com.zhgl.module.document.ebean.YunFile;
import com.zhgl.module.document.ebean.YunFileHistory;
import com.zhgl.util.dao.DAO;

public interface YunFileHistoryService extends DAO<YunFileHistory> {
	public YunFileHistory findByUK(String uk);

	/**
	 * 保存YunFile为历史版本记录
	 * 
	 * @param yunFile
	 */
	public void createByYunFile(YunFile yunFile);
}
