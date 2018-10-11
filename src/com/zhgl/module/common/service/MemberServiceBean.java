package com.zhgl.module.common.service;

import javax.persistence.NoResultException;
import org.springframework.stereotype.Service;
import com.zhgl.module.common.ebean.Member;
import com.zhgl.util.dao.DAOSupport;

@Service
public class MemberServiceBean extends DAOSupport<Member> implements MemberService {

	@Override
	public void switchProject(String projectId, Member member) {
		Member entity = this.find(member.getId());
		entity.setCurrentProjectId(projectId);
		this.update(entity);
	}

	@Override
	public Member login(String mobile, String password) {
		try {
			Member member = (Member) em
					.createQuery(
							"select o from Member o where o.visible=:visible and o.mobile=:mobile and o.password=:password")
					.setParameter("visible", true).setParameter("mobile", mobile).setParameter("password", password)
					.getSingleResult();
			return member;
		} catch (NoResultException nre) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
