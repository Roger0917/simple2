package com.zhgl.module.common.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.util.dao.DAOSupport;

@Service
public class ProjectServiceBean extends DAOSupport<Project> implements ProjectService {
	@Resource
	private MemberService memberService;

	public Project switchProject(String projectId, String uid, String token) {
		Project project = this.find(projectId);
		return project;
	}

	public List<String> listProjectIdByMember(Member member) {
		@SuppressWarnings("unchecked")
		List<String> list = em
				.createQuery(
						"select o.project.id from ProjectMember o where o.visible=:visible and o.member.id=:memberId ")
				.setParameter("visible", true).setParameter("memberId", member.getId()).getResultList();
		return list;
	}

}
