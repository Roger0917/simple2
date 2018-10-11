package com.zhgl.module.common.sfl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhgl.module.common.ebean.Member;
import com.zhgl.util.WebUtil;

/**
 * 新版本后台登录进行过滤检查
 */
public class MemberFilter implements Filter {
	private Log log = LogFactory.getLog("access");

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		Member member = WebUtil.getloginUser(request);
		//
		log.info("head.referer=" + request.getHeader("referer"));
		log.info("RemoteHost=" + request.getRemoteHost());
		log.info("getRemoteAddr=" + request.getRemoteAddr() + ":" + request.getRemotePort());

		log.info("X-Real-IP =" + request.getHeader("X-Real-IP"));
		log.info("X-Forwarded-For =" + request.getHeader("X-Forwarded-For"));
		//

		if (member == null) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(request.getContextPath() + "/");
			String wantUrl = request.getRequestURI();
			wantUrl = wantUrl.substring(wantUrl.indexOf("/s/"));
			request.getSession().setAttribute("redirectUrl", wantUrl);
			return;
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
