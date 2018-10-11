package com.zhgl.module.common.ebean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zhgl.module.bean.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_student")
public class Student extends BaseEntity {	
	private String name;
	private Integer age;
	
	public static void main(String[] args) {
		String str="";
		System.out.println(str.charAt(0));
	}
	
	public Student() {
		
	}

	public Student(String number, String name, char sex, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
	
	
}
