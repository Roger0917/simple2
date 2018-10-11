package com.zhgl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.module.common.ebean.Member;
import com.zhgl.module.common.ebean.Project;
import com.zhgl.module.common.service.AreaService;
import com.zhgl.module.common.service.MemberService;
import com.zhgl.module.common.service.ProjectService;
import com.zhgl.util.WebUtil;
import com.zhgl.util.security.MD5;

/** 新版登录 */
@Controller
public class LoginAndExitAction {
	@Resource
	private MemberService memberService;
	@Resource
	private ProjectService projectService;
	@Resource
	private AreaService areaService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	@RequestMapping("/account/login")
	public ModelAndView login(@RequestParam String username, @RequestParam String password) {
		ModelAndView mav = new ModelAndView("login");
		Member member = memberService.login(username, MD5.MD5Encode(password));
		HttpSession session = request.getSession();
		// member为空 或者 当前帐号是省平台帐号 登录省平台验证 :

		if (member == null) {
			request.setAttribute("error", "用户名或密码错误");
			return mav;
		}
		
		/** 此段代码，数据库数据升级完成后，可以去掉 */

		session.setAttribute("loginUser", member);
	

		// 设置背景
		if (member.getBg() != null && !"".equals(member)) {
			session.setAttribute("bg", member.getBg());
		} else {
			session.setAttribute("bg", "bg-3");
		}
		String currentPid = member.getCurrentProjectId();
		if (currentPid != null && !"".equals(currentPid)) {
			Project project = projectService.find(currentPid);
			if (project != null) {
				session.setAttribute("currentProject", project);
			}
		}

		mav = new ModelAndView("redirect:/s/category/list");
		return mav;
		// String wantUrl = (String) session.getAttribute("redirectUrl");
		// if (wantUrl != null && wantUrl.startsWith("/s/")) {
		// mav = new ModelAndView("redirect:" + wantUrl);
		// session.removeAttribute("redirectUrl");
		// } else {
		// mav = new ModelAndView("redirect:/s/index");
		// }
	}

	@RequestMapping("/account/logout")
	public ModelAndView logout() {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		session.removeAttribute("currentProject");
		ModelAndView mav = new ModelAndView("redirect:/login");
		return mav;
	}

	@RequestMapping(value = "/s/set/bg", produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String setBG(String bg) {
		Member member = memberService.find(WebUtil.getloginUser(request).getId());
		member.setBg(bg);
		memberService.update(member);
		HttpSession session = request.getSession();
		session.setAttribute("bg", bg);
		return "success";
	}

}
