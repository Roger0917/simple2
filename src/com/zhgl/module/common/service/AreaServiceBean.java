package com.zhgl.module.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.zhgl.module.common.ebean.Area;
import com.zhgl.util.dao.DAOSupport;

@Service
public class AreaServiceBean extends DAOSupport<Area> implements AreaService {

	@Override
	@SuppressWarnings("unchecked")
	public List<Area> listForAir() {
		return em.createQuery("select o from Area o where o.visible=?1 and o.airPinyin is not null")
				.setParameter(1, true).getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Area> listForWeather() {
		return em.createQuery("select o from Area o where o.visible=?1 and o.weatherPinyin is not null")
				.setParameter(1, true).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Area> listProvince() {
		return em.createQuery("select o from Area o where o.visible=?1 and o.level=?2 order by sort asc ")
				.setParameter(1, true).setParameter(2, 1).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Area> listChildArea(long areaId) {
		return em.createQuery("select o from Area o where o.visible=?1 and o.parentArea.id=?2").setParameter(1, true)
				.setParameter(2, areaId).getResultList();
	}

	@Override
	public Area findByTheCode(String theCode) {
		try {
			return (Area) em.createQuery("select o from Area o where o.visible=?1 and o.theCode=?2")
					.setParameter(1, true).setParameter(2, theCode).getSingleResult();
		} catch (NoResultException ne) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getSubTypeid(Long[] aids) {
		List<Long> list = new ArrayList<>();
		for (Long aid : aids) {
			List<Long> aidList = em.createQuery("select o.id from Area o where o.parentArea.id=?1 and o.level<4")
					.setParameter(1, aid).getResultList();
			list.addAll(aidList);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> listAll() {
		return em.createQuery("select o from Area o where o.visible=?1 order by level asc").setParameter(1, true)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> listForYCZS() {
		List<Area> list = em
				.createQuery(
						"select o from Area o where o.visible=?1 and o.level<=?2 and (o.parentArea.theCode='5101')")
				.setParameter(1, true).setParameter(2, 3).getResultList();
		list.add(this.findByTheCode("51"));
		list.add(this.findByTheCode("5101"));
		list.add(this.findByTheCode("5106"));
		return list;
	}

}
