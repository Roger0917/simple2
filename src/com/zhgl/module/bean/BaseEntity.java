package com.zhgl.module.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.zhgl.util.security.MD5;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "myIdentityGenerator", strategy = "com.zhgl.module.bean.IdentityGenerator")
	@GeneratedValue(generator = "myIdentityGenerator")
	protected String id;
	protected Date createDate;
	protected Date modifyDate;
	protected Boolean visible = true;

	public BaseEntity(String id) {
		this.id = id;
	}

	public BaseEntity() {

	}
	
	public static void main(String[] args) {
		System.out.println(MD5.MD5Encode("123456"));
	}

}
