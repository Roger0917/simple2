package com.zhgl.module.common.service;

import com.zhgl.module.common.ebean.Member;
import com.zhgl.util.dao.DAO;

public interface MemberService extends DAO<Member> {

	Member login(String mobile, String password);

	void switchProject(String projectId, Member member);

}
