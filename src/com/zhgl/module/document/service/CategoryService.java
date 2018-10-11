package com.zhgl.module.document.service;

import com.zhgl.module.document.ebean.Category;
import com.zhgl.util.dao.DAO;

public interface CategoryService extends DAO<Category> {

	public Category findByUK(String uk);
}
