package com.zhgl.module.common.service;

import java.util.List;

import com.zhgl.module.common.ebean.Student;
import com.zhgl.module.document.ebean.Category;
import com.zhgl.util.dao.DAO;

public interface StudentService extends DAO<Student> {
	
	List<Student> listAll();
	
	List<Student> listByNum(String number);
	
	List<Student> listByName(String name);
	
	List<Student> listByNumOrName(String arg);
	
	void insert(Student student);
	
	void delete(int id);
	
	//void update1(Student student);
	
	Student findById(String id);
	
	
}
