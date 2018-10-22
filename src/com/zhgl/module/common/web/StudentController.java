package com.zhgl.module.common.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import sun.util.logging.resources.logging;

import com.mysql.jdbc.log.Log;
import com.sun.org.apache.regexp.internal.recompile;
import com.zhgl.module.bean.IdentityGenerator;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.module.common.ebean.Student;
import com.zhgl.module.common.service.StudentService;
import com.zhgl.module.common.service.StudentServiceBean;
import com.zhgl.module.document.ebean.Category;
import com.zhgl.util.WebUtil;
import com.zhgl.util.dao.PageView;
import com.zhgl.util.dao.QueryResult;

@Controller
@RequestMapping("/stu/")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	private ModelAndView mav;
	
	private int maxresult = 3;
	
	@RequestMapping("create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("student/student_create");
		return mav;
	}
	
	@RequestMapping("save")
	public ModelAndView save(@ModelAttribute Student student){
		ModelAndView mav = new ModelAndView("student/student_create");
		studentService.save(student);
		return mav;
	}
	
	/*@RequestMapping("listAll")
	@ResponseBody
	public List listAll(@RequestParam(required = false, defaultValue = "1") int page){
		PageView<Student> pageView = new PageView<>(maxresult, page);
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("number", "asc");
		StringBuffer jpql = new StringBuffer("");
		List<Object> params = new ArrayList<Object>();
		
		pageView.setQueryResult(studentService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		
		return pageView.getRecords();
	}*/
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestParam String id){
		Student entity = studentService.findById(id);
		entity.setVisible(false);
		studentService.update(entity);
		return "success";
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(String id) {
		ModelAndView mav = new ModelAndView("student/student_edit");
		Student student = studentService.findById(id);
		mav.addObject("student", student);
		return mav;
	}
	
	@RequestMapping("update")
	public ModelAndView update(@ModelAttribute Student student){
		ModelAndView mav = new ModelAndView("student/student_edit");
		Student entity = studentService.findById(student.getId());
		entity.setName(student.getName());
		entity.setAge(student.getAge());
		studentService.update(entity);
		return mav;
	}
	
	
	
	/**
	 * 不传关键字时,查询所有数据按照升序排列并分页,传关键字时,查询结果按照升序排列并分页
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping("listAllPage")
	public ModelAndView listAllPage(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false,defaultValue="") String name){
		StringBuffer jpql = null;
		ModelAndView mav = new ModelAndView("student/student_list");
		PageView<Student> pageView = new PageView<>(maxresult, page);
		List<Object> params = new ArrayList<Object>();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("age", "asc");
		
		jpql = new StringBuffer("o.visible=?1 and o.name like?2");
		params.add(true);
		System.out.println("name"+name);
		params.add("%"+name+"%");
		/*System.out.println(params);
		System.out.println(pageView.getFirstResult());
		System.out.println(jpql);
		System.out.println(orderby);*/
		QueryResult<Student> qResult= studentService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby);
		pageView.setQueryResult(qResult);
		List<Student> list = pageView.getRecords();
		for (Student student : list) {
			System.out.print(student.getName()+student.getAge());
		}
		mav.addObject("pageView",pageView);
		mav.addObject("page",page);
		mav.addObject("keyword",name);
		
		return mav;
	}
	
	
	@RequestMapping("list2")
	@ResponseBody
	public List<Student> listAllPage2(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false,defaultValue="") String name){
		StringBuffer jpql = null;
		ModelAndView mav = new ModelAndView("student/student_list");
		PageView<Student> pageView = new PageView<>(maxresult, page);
		List<Object> params = new ArrayList<Object>();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("age", "asc");
		
		jpql = new StringBuffer("o.visible=?1 and o.name like?2");
		params.add(true);
		System.out.println("name"+name);
		params.add("%"+name+"%");
		/*System.out.println(params);
		System.out.println(pageView.getFirstResult());
		System.out.println(jpql);
		System.out.println(orderby);*/
		QueryResult<Student> qResult= studentService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby);
		pageView.setQueryResult(qResult);
		List<Student> list = pageView.getRecords();
		for (Student student : list) {
			System.out.print(student.getName()+student.getAge());
		}
		
		return list;
	}
	
	
	@RequestMapping("list")
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("student/kingtable");
		return mav;
	}
	public StudentController() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {

        Date d = new Date();  
        System.out.println(d);  
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");  
        String dateNowStr = sdf.format(d);  
        System.out.println("格式化后的日期：" + dateNowStr);
        System.out.println("格式化后的日期：" + dateNowStr.replaceAll("[[\\s-:punct:]]","")); 
	}
}
