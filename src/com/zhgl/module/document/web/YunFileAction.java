package com.zhgl.module.document.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.module.bean.IdentityGenerator;
import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.module.common.service.MemberService;
import com.zhgl.module.common.service.ProjectService;
import com.zhgl.module.document.ebean.Category;
import com.zhgl.module.document.ebean.YunFile;
import com.zhgl.module.document.service.CategoryService;
import com.zhgl.module.document.service.YunFileHistoryService;
import com.zhgl.module.document.service.YunFileService;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.WebUtil;
import com.zhgl.util.dao.PageView;
import com.zhgl.util.security.MD5FileUtil;

/***
 * 文档管理
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/s/yunfile")
@Controller
public class YunFileAction {

	@Resource
	private YunFileService yunFileService;
	@Resource
	private YunFileHistoryService yunFileHistoryService;
	@Resource
	private MemberService memberService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProjectService projectService;

	@Autowired
	private HttpServletRequest request;
	private int maxresult = 120;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false) String projectId,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = true, value = "uk") String categoryUk,
			@RequestParam(required = false, value = "fk") String fileUK, String order, String i) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_list");
		PageView<YunFile> pageView = new PageView<YunFile>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("isDir", "desc");
		if (order != null && !"".equals(order)) {
			orderby.put(order, i);
		} else {
			orderby.put("createDate", "desc");
		}
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.category.uk=?2 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(categoryUk);
		if (fileUK != null && !"".equals(fileUK)) {
			jpql.append("and o.parentFile.uk=?3 ");
			params.add(fileUK);
		} else {
			jpql.append("and o.parentFile is null ");
		}

		pageView.setQueryResult(yunFileService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		YunFile yunFile = yunFileService.findByUK(fileUK);
		YunFile parentFile = yunFile;
		ArrayList<YunFile> navFileList = new ArrayList<>();
		while (parentFile != null) {
			navFileList.add(0, parentFile);
			parentFile = parentFile.getParentFile();
		}

		for (YunFile yf : pageView.getRecords()) {
			if (yf.getIsFile()) {
				yf.setShowSize(HelperUtil.showSize(yf.getSize()));
				yf.setFileTypeIcon();
			}
		}

		if (projectId != null && !"".equals(projectId)) {
			Project project = projectService.find(projectId);
			if (project != null && project.getVisible()) {
				mav.addObject("project", project);
			}
		}

		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("uk", categoryUk);
		mav.addObject("fk", fileUK);
		mav.addObject("category", categoryService.findByUK(categoryUk));
		mav.addObject("yunFile", yunFile);
		mav.addObject("navFileList", navFileList);

		mav.addObject("menu", "doc");
		mav.addObject("child", "categoryList");
		return mav;
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(required = false) String projectId,
			@RequestParam(required = false, defaultValue = "1") int page, String query) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_search");
		PageView<YunFile> pageView = new PageView<YunFile>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("modifyDate", "desc");

		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.isFile=?2 and o.category.project.id=?3 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
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
		if (query != null && !"".equals(query)) {
			jpql.append("and o.name like?4");
			params.add("%" + query + "%");
		}

		pageView.setQueryResult(yunFileService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		for (YunFile yf : pageView.getRecords()) {
			yf.setShowSize(HelperUtil.showSize(yf.getSize()));
			yf.setFileTypeIcon();
		}

		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("query", query);

		mav.addObject("menu", "doc");
		mav.addObject("child", "yunFileSearch");
		return mav;
	}

	@RequestMapping("/create/folder")
	public ModelAndView createFolder(String uk, String fk) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_create_folder");
		mav.addObject("uk", uk);
		mav.addObject("fk", fk);
		Category category = categoryService.findByUK(uk);
		mav.addObject("category", category);
		YunFile parentFile = yunFileService.findByUK(fk);
		mav.addObject("parentFile", parentFile);
		return mav;
	}

	@RequestMapping("/save/folder")
	public @ResponseBody
	String saveFolder(String uk, String fk, String name) {
		YunFile yunFile = new YunFile();
		yunFile.setIsDir(true);
		yunFile.setIsFile(false);
		yunFile.setName(name);
		yunFile.setUk(IdentityGenerator.uuid());
		yunFile.setCategory(categoryService.findByUK(uk));
		yunFile.setParentFile(yunFileService.findByUK(fk));
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		yunFile.setMember(member);
		yunFileService.save(yunFile);
		return "success";
	}

	@RequestMapping("/edit/folder")
	public ModelAndView editFolder(String uk) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_edit_folder");
		YunFile yunFile = yunFileService.findByUK(uk);
		mav.addObject("yunFile", yunFile);
		return mav;
	}

	@RequestMapping("/update/folder")
	public @ResponseBody
	String updateFolder(String uk, String name) {
		YunFile yunFile = yunFileService.findByUK(uk);
		yunFile.setName(name);
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		yunFile.setMember(member);
		yunFileService.update(yunFile);
		return "success";
	}

	@RequestMapping("/create")
	public ModelAndView create(String uk, String fk) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_create");
		mav.addObject("uk", uk);
		mav.addObject("fk", fk);
		Category category = categoryService.findByUK(uk);
		mav.addObject("category", category);
		YunFile parentFile = yunFileService.findByUK(fk);
		mav.addObject("parentFile", parentFile);
		return mav;
	}

	@RequestMapping("/save")
	public @ResponseBody
	String save(String uk, String fk, String number,
			@RequestParam(value = "yunFile", required = false) MultipartFile document) {
		YunFile yunFile = new YunFile();
		yunFile.setUk(IdentityGenerator.uuid());
		yunFile.setNumber(number);

		Category category = categoryService.findByUK(uk);
		if (document != null) {
			Project project = category.getProject();

			String oriname = document.getOriginalFilename();
			String suffix = oriname.substring(oriname.lastIndexOf(".") + 1);
			String fileName = IdentityGenerator.uuid() + "." + suffix;
			if (suffix.equals(fileName)) {// 解决无后缀名的情况
				suffix = "";
			}
			String filePath = "resource/upload/yunFile/" + project.getId() + "/" + uk + "/" + fileName;
			File saveFile = new File(request.getSession().getServletContext().getRealPath(filePath));
			try {
				FileUtils.copyInputStreamToFile(document.getInputStream(), saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String md5 = MD5FileUtil.getFileMD5String(saveFile);
			yunFile.setMd5(md5);
			yunFile.setName(oriname);
			yunFile.setExtension(suffix.toLowerCase());
			yunFile.setPath(filePath);
			yunFile.setSize(document.getSize());
		}
		yunFile.setCategory(category);
		yunFile.setParentFile(yunFileService.findByUK(fk));
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		yunFile.setMember(member);
		yunFileService.save(yunFile);
		return "success";
	}

	@RequestMapping("/edit")
	public ModelAndView edit(String uk) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFile_edit");
		YunFile yunFile = yunFileService.findByUK(uk);
		mav.addObject("yunFile", yunFile);
		return mav;
	}

	@RequestMapping("/update")
	public @ResponseBody
	String update(String uk, String number, @RequestParam(value = "yunFile", required = false) MultipartFile document) {
		YunFile entity = yunFileService.findByUK(uk);
		// 创建历史版本
		yunFileHistoryService.createByYunFile(entity);

		entity.setNumber(number);
		if (document != null) {
			Project project = entity.getCategory().getProject();

			String oriname = document.getOriginalFilename();
			String suffix = oriname.substring(oriname.lastIndexOf(".") + 1);
			if (suffix.equals(oriname)) {// 解决无后缀名的情况
				suffix = "";
			}
			String fileName = IdentityGenerator.uuid() + "." + suffix;
			if ("".equals(suffix)) {
				fileName = IdentityGenerator.uuid();
			}
			String filePath = "resource/upload/yunFile/" + project.getId() + "/" + uk + "/" + fileName;
			File saveFile = new File(request.getSession().getServletContext().getRealPath(filePath));
			try {
				FileUtils.copyInputStreamToFile(document.getInputStream(), saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String md5 = MD5FileUtil.getFileMD5String(saveFile);
			entity.setMd5(md5);
			entity.setName(oriname);
			entity.setExtension(suffix.toLowerCase());
			entity.setPath(filePath);
			entity.setSize(document.getSize());
		}
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		entity.setMember(member);
		yunFileService.update(entity);
		return "success";
	}

	@RequestMapping("/delete")
	public @ResponseBody
	String delete(String uk) {
		YunFile entity = yunFileService.findByUK(uk);
		entity.setVisible(false);
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		entity.setMember(member);
		yunFileService.update(entity);
		return "success";
	}

	/**
	 * 下载文件
	 */
	@RequestMapping(value = "/download/{uk}")
	public ResponseEntity<byte[]> downloadApp(@PathVariable String uk) throws IOException {
		YunFile yunFile = yunFileService.findByUK(uk);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		String attachName = yunFile.getName();
		headers.set("Content-Disposition", "attachment;fileName=" + new String(attachName.getBytes(), "iso-8859-1"));
		String filePath = request.getServletContext().getRealPath(yunFile.getPath());
		File file = new File(filePath);
		byte[] bytes = FileUtils.readFileToByteArray(file);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK); // 一定要是OK；否则多数手机浏览器不支持
		return responseEntity;
	}
}
