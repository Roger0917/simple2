package com.zhgl.module.document.service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zhgl.module.bean.IdentityGenerator;
import com.zhgl.module.document.ebean.YunFile;
import com.zhgl.module.document.ebean.YunFileHistory;
import com.zhgl.util.dao.DAOSupport;

@Service
public class YunFileHistoryServiceBean extends DAOSupport<YunFileHistory> implements YunFileHistoryService {
	public YunFileHistory findByUK(String uk) {
		Query query = em.createQuery("select o from YunFileHistory o where o.visible=:visible and o.uk=:uk")
				.setParameter("visible", true).setParameter("uk", uk);
		try {
			YunFileHistory yunFileHistory = (YunFileHistory) query.getSingleResult();
			return yunFileHistory;
		} catch (NoResultException nre) {

		} catch (Exception e) {
		}
		return null;
	}

	public void createByYunFile(YunFile yunFile) {
		YunFileHistory history = new YunFileHistory();
		BeanUtils.copyProperties(yunFile, history, "id", "uk", "createDate");
		history.setUk(IdentityGenerator.uuid());
		history.setCreateDate(yunFile.getModifyDate());
		history.setYunFile(yunFile);
		save(history);
	}
}
