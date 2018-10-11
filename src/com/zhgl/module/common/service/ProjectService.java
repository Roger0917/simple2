package com.zhgl.module.common.service;

import java.util.List;

import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.util.dao.DAO;

public interface ProjectService extends DAO<Project> {

	/***
	 * 切换工程，检查能否进行切换，能切换则返回Project对象，不能则返回null
	 * 
	 * @param projectId
	 * @param member
	 * @return
	 */
	public Project switchProject(String projectId, String uid, String token);

	/***
	 * 根据帐号获取所有相关的关程<br>
	 * 相关：当工程创建了Employee信息，Employee中的手机号或者身份证号 与 传递的Member中的手机号或身份证号对应认为是相关工程，即可以显示给当前用户
	 * 
	 * @param member
	 * @return
	 */
	public List<String> listProjectIdByMember(Member member);

	
}
