package com.project.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activation.CommandMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.word.service.LoginService_Impl;
import com.project.word.service.StudentService_Impl;
import com.project.word.vo.Admin;
import com.project.word.vo.Student;


/**
 * Handles requests for the application home page.
 */

@Controller
public class LoginController {
	

	@Autowired
	public LoginService_Impl loginService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
		/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
		
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model,HttpSession session) throws IOException {
		if(session.getAttribute("SID")!=null){
			return   "redirect:/makeTest";
		}else if(session.getAttribute("AID")!=null){
			return "redirect:/admin";
		}else{
			return "index";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws IOException{
		session.invalidate();
		return "redirect:/";
	}
	/*
	 * 학생로그인 및 관리자 로그인 분기제어
	 * 
	 * 관리자인 경우 
	 * cnt=0 로그인정보가 일치하지않음
	 * cnt=1 정상로그인
	 * cnt=2 최초로그인
	 * 
	*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value="name") String name, 
			@RequestParam(value="number") String number,
			@RequestParam(value="password",required=false) String password,
			Student svo, Admin avo, RedirectAttributes redirectAttributes,
			HttpSession session)  {
		
		
		if(password.equals("")){
			svo.setStName(name);
			svo.setStNum(number);
			if(loginService.loginWithStudent(svo)){
				session.setAttribute("SID", number);
				return "redirect:/makeTest";
			}else{
				redirectAttributes.addFlashAttribute("err","wrong_student_login" );
				return "redirect:/";
			}
		}else{
			avo.setAdPw(password);
			avo.setAdName(name);
			avo.setAdNum(number);
			int cnt=loginService.loginWithAdmin(avo);
		
			switch (cnt) {
			case 0:
				
				redirectAttributes.addFlashAttribute("err","wrong_admin_login");
				return "redirect:/";
			case 1:	
				
				session.setAttribute("AID", number);
				return "redirect:/admin";
			case 2:	
				
				redirectAttributes.addFlashAttribute("number",number);
				redirectAttributes.addFlashAttribute("msg","changepassword");
				return "redirect:/";
			default:
				
				redirectAttributes.addFlashAttribute("err","wrong_admin_login");
				return "redirect:/";
			}
		}
	}
	
	
	@RequestMapping(value = "/chg_passwd", method = RequestMethod.POST)
	public String changeAdminPassProc(
			@RequestParam(value="adNum") String adNum,
			@RequestParam(value="passwd1") String password,
			Admin avo, RedirectAttributes redirectAttributes,
			Model model,HttpSession session) throws IOException {
		
		
		avo.setAdNum(adNum);
		avo.setAdPw(password);
		loginService.changeAdminPassword(avo);
		redirectAttributes.addFlashAttribute("msg","relogin");
		return "redirect:/";
	}

}
