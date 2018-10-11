package com.zhgl.module.document.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.module.bean.IdentityGenerator;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.module.common.service.ProjectService;
import com.zhgl.module.document.ebean.Category;
import com.zhgl.module.document.service.CategoryService;
import com.zhgl.util.WebUtil;
import com.zhgl.util.dao.PageView;

/***
 * 分类
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/s/category")
@Controller
public class CategoryAction {

	@Resource
	private ProjectService projectService;
	@Resource
	private CategoryService categoryService;

	@Autowired
	private HttpServletRequest request;
	private int maxresult = 3;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false) String projectId,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		ModelAndView mav = new ModelAndView("s/module/document/category_list");
		PageView<Category> pageView = new PageView<Category>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("lv", "desc");
		orderby.put("createDate", "asc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.project.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);

		Project currentProject = WebUtil.getCurrentProject(request);
		if (projectId != null && !"".equals(projectId)) {
			Project project = projectService.find(projectId);
			if (project != null && project.getVisible()) {
				mav.addObject("project", project);
				currentProject = project;
			}
		}

		params.add(currentProject.getId());
		if (queryContent != null && !"".equals(queryContent)) {
			jpql.append(" and o.name like?3 ");
			params.add("%" + queryContent + "%");
		}
		pageView.setQueryResult(categoryService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby)); 
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("queryContent", queryContent);
		mav.addObject("menu", "doc");
		mav.addObject("child", "categoryList");
		return mav;
	}

	@RequestMapping("/init")
	public ModelAndView init(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		ModelAndView mav = new ModelAndView("s/module/index");
		return mav;
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam(required = false) String projectId) {
		ModelAndView mav = new ModelAndView("s/module/document/category_create");
		mav.addObject("projectId", projectId);
		return mav;
	}

	@RequestMapping("/save")
	public ModelAndView save(@RequestParam(required = false) String projectId, @ModelAttribute Category category) {
		ModelAndView mav = new ModelAndView("s/module/document/category_create");
		Project currentProject = WebUtil.getCurrentProject(request);
		Project dbProject = projectService.find(currentProject.getId());
		if (projectId != null && !"".equals(projectId)) {
			Project project = projectService.find(projectId);
			if (project != null && project.getVisible()) {
				dbProject = project;
			}
		}
		category.setProject(dbProject);
		category.setUk(IdentityGenerator.uuid());
		categoryService.save(category);
		return mav;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(String uk) {
		ModelAndView mav = new ModelAndView("s/module/document/category_edit");
		Category category = categoryService.findByUK(uk);
		mav.addObject("category", category);
		return mav;
	}

	@RequestMapping("/update")
	public ModelAndView update(@ModelAttribute Category category) {
		ModelAndView mav = new ModelAndView("s/module/document/category_edit");
		Category entity = categoryService.findByUK(category.getUk());
		entity.setName(category.getName());
		entity.setLv(category.getLv());
		categoryService.update(entity);
		return mav;
	}

	@RequestMapping("/delete")
	public @ResponseBody
	String delete(String uk) {
		Category entity = categoryService.findByUK(uk);
		entity.setVisible(false);
		categoryService.update(entity);
		return "success";
	}

}
