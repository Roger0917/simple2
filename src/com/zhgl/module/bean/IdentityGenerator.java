package com.zhgl.module.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import com.zhgl.util.RandomStringUtil;

public class IdentityGenerator implements IdentifierGenerator, Configurable {

	/**
	 * 产生ID <br>
	 * 格式：8-6-8(年月日-时分秒-8位随机数)
	 * 
	 * @return
	 */
	public static String generator() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String id = dateStr + RandomStringUtil.generatedNumber(5);
		return id;
	}

	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		return generator();
	}

	@Override
	public void configure(Type arg0, Properties arg1, Dialect arg2) throws MappingException {

	}

}
