package com.zhgl.module.common.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.module.bean.IdentityGenerator;
import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.module.common.service.AreaService;
import com.zhgl.module.common.service.MemberService;
import com.zhgl.module.common.service.ProjectService;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.WebUtil;
import com.zhgl.util.dao.PageView;

/***
 * 工程
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/s/project")
@Controller
public class ProjectManageAction {

	@Resource
	private MemberService memberService;
	@Resource
	private ProjectService projectService;
	@Resource
	private AreaService areaService;
	@Autowired
	private HttpServletRequest request;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String keyword) {
		PageView<Project> pageView = new PageView<>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);

		Member member = WebUtil.getloginUser(request);
		jpql.append(" and o.member.id=?2");
		params.add(member.getId());

		if (keyword != null && !"".equals(keyword)) {
			jpql.append(" and o.name like?" + (params.size() + 1));
			params.add("%" + keyword + "%");
		}
		pageView.setQueryResult(projectService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		for (Project p : pageView.getRecords()) {
			if (p.getUk() == null) {
				p.setUk(IdentityGenerator.uuid());
				projectService.update(p);
			}
		}
		ModelAndView mav = new ModelAndView("s/module/common/project_list");
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("keyword", keyword);
		mav.addObject("menu", "project");
		return mav;
	}

	@RequestMapping("/default/{projectId}")
	public ModelAndView defaultProject(@PathVariable String projectId) {
		Project project = projectService.find(projectId);
		HttpSession session = request.getSession();
		session.setAttribute("currentProject", project);
		// 切换工程
		memberService.switchProject(projectId, WebUtil.getloginUser(request));

		ModelAndView mav = new ModelAndView("redirect:/s/category/list");
		return mav;
	}

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("s/module/common/project_create");
		return mav;
	}

	@RequestMapping("/save")
	public @ResponseBody
	String save(@ModelAttribute Project project, Long areaId, String beginDate, String endDate) {
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		project.setMember(member);
		project.setArea(areaService.find(areaId));
		project.setStartDate(HelperUtil.parseDate("yyyy-MM-dd", beginDate));
		project.setCompleteDate(HelperUtil.parseDate("yyyy-MM-dd", endDate));
		if (project.getUsername() == null || "".equals(project.getUsername())) {
			project.setUsername(HelperUtil.converPinYin(project.getName(), 1));
		}
		project.setUk(IdentityGenerator.uuid()); // 生成UUID
		projectService.save(project);
		return "success";
	}

	@RequestMapping("/edit")
	public ModelAndView edit(String projectId) {
		ModelAndView mav = new ModelAndView("s/module/common/project_edit");
		Project project = projectService.find(projectId);
		mav.addObject("project", project);
		return mav;
	}

	@RequestMapping("/update")
	public @ResponseBody
	String update(@ModelAttribute Project project, Long areaId, String beginDate, String endDate) {
		Project entity = projectService.find(project.getId());
		BeanUtils.copyProperties(project, entity, "id", "createDate", "visible", "area", "member");
		entity.setArea(areaService.find(areaId));
		entity.setStartDate(HelperUtil.parseDate("yyyy-MM-dd", beginDate));
		entity.setCompleteDate(HelperUtil.parseDate("yyyy-MM-dd", endDate));
		projectService.update(entity);
		return "success";
	}

	@RequestMapping("/switch")
	public ModelAndView switchProject() {
		ModelAndView mav = new ModelAndView("s/module/common/project_switch");
		return mav;
	}

	@RequestMapping(value = "/switch/update", produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String switchProjectUpdate() {
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		String currentPid = member.getCurrentProjectId();
		if (currentPid != null && !"".equals(currentPid)) {
			Project project = projectService.find(currentPid);
			if (project != null) {
				HttpSession session = request.getSession();
				session.setAttribute("currentProject", project);
			}
			String result = project.getName();
			return result;
		}
		return "fail";
	}

}
