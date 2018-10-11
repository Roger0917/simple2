package com.zhgl.module.document.service;

import com.zhgl.module.document.ebean.YunFile;
import com.zhgl.util.dao.DAO;

public interface YunFileService extends DAO<YunFile> {
	public YunFile findByUK(String uk);
}
