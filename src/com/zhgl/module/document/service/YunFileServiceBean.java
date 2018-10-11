package com.zhgl.module.document.service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.module.document.ebean.YunFile;
import com.zhgl.util.dao.DAOSupport;

@Service
public class YunFileServiceBean extends DAOSupport<YunFile> implements YunFileService {
	public YunFile findByUK(String uk) {
		Query query = em.createQuery("select o from YunFile o where o.visible=:visible and o.uk=:uk")
				.setParameter("visible", true).setParameter("uk", uk);
		try {
			YunFile yunFile = (YunFile) query.getSingleResult();
			return yunFile;
		} catch (NoResultException nre) {

		} catch (Exception e) {
		}
		return null;
	}
}
