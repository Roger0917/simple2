package com.zhgl.module.document.service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.module.document.ebean.Category;
import com.zhgl.util.dao.DAOSupport;

@Service
public class CategoryServiceBean extends DAOSupport<Category> implements CategoryService {

	public Category findByUK(String uk) {
		Query query = em.createQuery("select o from Category o where o.visible=:visible and o.uk=:uk")
				.setParameter("visible", true).setParameter("uk", uk);
		try {
			Category category = (Category) query.getSingleResult();
			return category;
		} catch (NoResultException nre) {

		} catch (Exception e) {
		}
		return null;
	}
}
