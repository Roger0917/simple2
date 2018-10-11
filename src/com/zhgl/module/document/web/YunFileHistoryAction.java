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
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.module.common.service.ProjectService;
import com.zhgl.module.document.ebean.YunFileHistory;
import com.zhgl.module.document.service.CategoryService;
import com.zhgl.module.document.service.YunFileHistoryService;
import com.zhgl.module.document.service.YunFileService;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.PageView;

/***
 * 文档历史记录
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/s/yunfile/history")
@Controller
public class YunFileHistoryAction {

	@Resource
	private YunFileService yunFileService;
	@Resource
	private YunFileHistoryService yunFileHistoryService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProjectService projectService;

	@Autowired
	private HttpServletRequest request;
	private int maxresult = 200;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = true, value = "uk") String uk) {
		ModelAndView mav = new ModelAndView("s/module/document/yunFileHistory_list");
		PageView<YunFileHistory> pageView = new PageView<YunFileHistory>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");

		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.yunFile.uk=?2 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(uk);
		pageView.setQueryResult(yunFileHistoryService.getScrollData(pageView.getFirstResult(), maxresult,
				jpql.toString(), params.toArray(), orderby));

		for (YunFileHistory yf : pageView.getRecords()) {
			yf.setShowSize(HelperUtil.showSize(yf.getSize()));
		}
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("menu", "doc");
		mav.addObject("child", "categoryList");
		return mav;
	}

	@RequestMapping(value = "/download/{uk}")
	public ResponseEntity<byte[]> downloadApp(@PathVariable String uk) throws IOException {
		YunFileHistory yunFile = yunFileHistoryService.findByUK(uk);
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
