package com.zhgl.module.common.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Student;
import com.zhgl.module.document.ebean.Category;
import com.zhgl.util.dao.DAOSupport;

@Service
public class StudentServiceBean extends DAOSupport<Student> implements StudentService {

	@Override
	public List<Student> listAll() {
		// TODO Auto-generated method stub
		return em.createQuery("select o from Student o")
				.getResultList();
	}

	@Override
	public List<Student> listByNum(String number) {
		// TODO Auto-generated method stub
		return em.createQuery("select o from Student o where o.number=?1")
				.setParameter(1, number).getResultList();
	}

	@Override
	public List<Student> listByName(String name) {
		// TODO Auto-generated method stub
		return em.createQuery("select o from Student o where o.name=?1")
				.setParameter(1, name).getResultList();
	}

	@Override
	public void insert(Student student) {
		// TODO Auto-generated method stub
		this.save(student);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		 this.delete(id);
	}

	

	@Override
	public List<Student> listByNumOrName(String arg) {
		// TODO Auto-generated method stub
		return em.createQuery("select o from Student o where o.number like?1 or o.name like?2")
				.setParameter(1, arg).setParameter(2, arg).getResultList();
	}

	@Override
	public Student findById(String id) {
		Query query = em.createQuery("select o from Student o where o.visible=:visible and o.id=:id")
				.setParameter("visible", true).setParameter("id", id);
		try {
			Student student = (Student) query.getSingleResult();
			return student;
		} catch (NoResultException nre) {

		} catch (Exception e) {
		}
		return null;
	}

}
