package com.project.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import com.project.word.dao.TimerDao;
import com.project.word.data.Data;
import com.project.word.data.StudentTestResult;
import com.project.word.service.ExcelUploadService_impl;
import com.project.word.service.StudentGroupService_Impl;
import com.project.word.service.StudentService_Impl;
import com.project.word.service.TestHistoryService_Impl;
import com.project.word.vo.Admin;
import com.project.word.vo.ResultView;
import com.project.word.vo.Student;
import com.project.word.vo.StudentGroup;
import com.project.word.vo.Word;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Handles requests for the application home page.
 */

@Controller
public class AdminController {
	@Autowired
	public TestHistoryService_Impl testhistoryService;
	
	@Autowired
	public ExcelUploadService_impl excelUploadService;
	
	@Autowired
	public StudentGroupService_Impl studentgroupService;
	
	@Autowired
	public StudentService_Impl studentService;
	
	@Autowired
	public TimerDao timerdao;	

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
		/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String chart(Model model,HttpSession session
			) throws IOException {
		return "vertical";
	}
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model,HttpSession session
			) throws IOException {
		
		
		if(session.getAttribute("AID")!=null){
			
			
			List<Student> slist= new ArrayList<Student>();
			List<StudentGroup> sglist =new ArrayList<StudentGroup>();
			
			slist=studentService.getStudentList();
			sglist=studentgroupService.getStudentGroupList();
			
			model.addAttribute("slist", slist);
			model.addAttribute("sglist", sglist);
			/*
			 * 모든 학생시험 이력 
			 */
			model.addAttribute("reslist", testhistoryService.getresultViewList());
				
			
			
			ArrayList<ResultView> resList=(ArrayList<ResultView>) testhistoryService.getresultViewList();
			
			ArrayList<StudentTestResult> stu_resList=new ArrayList<StudentTestResult>();
			
			HashSet<String> set=new HashSet();
			
			SimpleDateFormat format=new SimpleDateFormat("yy.MM.dd");
			
			for(ResultView res :resList){
				
				if(set.add(res.getStNum())){
				
					StudentTestResult str=new StudentTestResult();
					str.setStNum(res.getStNum());
					ArrayList labelList= new ArrayList<String>();
					
					labelList.add(format.format(res.getThTestDate())+"-"+ res.getThTestNum()+"회");
					str.setLabels(labelList);
					
					Data data=new Data();
					ArrayList<String> scorelList= new ArrayList<String>();
					ArrayList<String> backgroundlList= new ArrayList<String>();
					ArrayList<String> borderlList= new ArrayList<String>();
					backgroundlList.add("rgba(255, 99, 132, 0.2)");
					borderlList.add("rgba(255,99,132,1)");
					
					double score=((double)res.getTotalCount()-(double)res.getWrongCount()) / (double)res.getTotalCount() * 100.0;
					int scorepercent =(int)score;
					String scP=Integer.toString(scorepercent);
					scorelList.add(scP);
					
					data.setBorderColor(borderlList);
					data.setBackgroundColor(backgroundlList);
					data.setBorderWidth("1");
					data.setLabel(res.getStName());
					data.setData(scorelList);
					
					str.setDatasets(data);
					
					stu_resList.add(str);
				}else{
					
					for(int i=0;i<stu_resList.size();i++){
						StudentTestResult str=stu_resList.get(i);
						if(str.getStNum().equals(res.getStNum())){
							ArrayList labelList=str.getLabels();
							labelList.add(format.format(res.getThTestDate())+"-"+ res.getThTestNum()+"회");
							str.setLabels(labelList);
							
							Data data= str.getDatasets();
							
							
							ArrayList<String> scorelList= data.getData();
							ArrayList<String> backgroundlList= data.getBackgroundColor();
							ArrayList<String> borderlList= data.getBorderColor();
							
							backgroundlList.add("rgba(255, 99, 132, 0.2)");
							borderlList.add("rgba(255,99,132,1)");
							double score=((double)res.getTotalCount()-(double)res.getWrongCount()) / (double)res.getTotalCount() * 100.0;
							int scorepercent =(int)score;
							String scP=Integer.toString(scorepercent);
							scorelList.add(scP);
							
							data.setBorderColor(borderlList);
							data.setBackgroundColor(backgroundlList);
							data.setBorderWidth("1");
							data.setLabel(res.getStName());
							data.setData(scorelList);
							
							str.setDatasets(data);
							
						}
					}
					
					
				}
			}
			
			
			
			JSONArray jsonArray= new JSONArray().fromObject(stu_resList);
			String jsonString = jsonArray.toString();
			
			
			model.addAttribute("jsonString", jsonString);
			model.addAttribute("selecttimer", timerdao.getSelectTimer());
			model.addAttribute("texttimer", timerdao.getTextTimer());
			return "admin";
		}else if(session.getAttribute("AID")!=null){
			return "redirect:/makeTest";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/setSelectTimer", method = RequestMethod.POST)
	public String setSelectTimer(
			@RequestParam(value="timer") int timer) throws IOException {
		
		timerdao.updateSelectTimer(timer);
		return "redirect:/admin";
	}
	@RequestMapping(value = "/setTextTimer", method = RequestMethod.POST)
	public String setTextTimer(
			@RequestParam(value="timer") int timer) throws IOException {
		
		timerdao.updateTextTimer(timer);
		return "redirect:/admin";
	}
	
	public ModelAndView grouplist(){
		ModelAndView modelAndView =new ModelAndView();
		List<StudentGroup> sglist =new ArrayList<StudentGroup>();
		JSONArray jsonArray = new JSONArray();
		
		sglist=studentgroupService.getStudentGroupList();
		modelAndView.addObject("jsonsgList", jsonArray.fromObject(sglist));
        // Keypoint ! setViewName에 들어갈 String 파라미터는 JsonView bean 설정해줬던 id와 같아야 한다.
        modelAndView.setViewName("jsonView");
		
		return modelAndView;
	}

	
	@RequestMapping(value = "/groupInsert",method=RequestMethod.GET)
	public ModelAndView groupInsert(
			@ModelAttribute StudentGroup sgvo ){
		
		studentgroupService.studentGroupInsert(sgvo);
		
		return grouplist();
	}
	
	
	@RequestMapping(value = "/groupStudentlist",method=RequestMethod.GET)
	public ModelAndView groupStudentlist(
			@RequestParam(value="sgIdx") Integer sgIdx
			){
		ModelAndView modelAndView =new ModelAndView();
		List<Student> slist =new ArrayList<Student>();
		JSONArray jsonArray = new JSONArray();
		
		slist=studentService.getGroupStudentList(sgIdx);
		modelAndView.addObject("jsonsgList", jsonArray.fromObject(slist));
        // Keypoint ! setViewName에 들어갈 String 파라미터는 JsonView bean 설정해줬던 id와 같아야 한다.
        modelAndView.setViewName("jsonView");
		
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/deletetesthistoy",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView deletetesthistoy(
			@RequestParam Map param
			) throws IOException {
			
			JSONArray jsonArray =  JSONArray.fromObject(param.get("arrays"));
			int []arr_resIdx =new int[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++){
				arr_resIdx[i]=Integer.parseInt(jsonArray.get(i).toString());
			} 
			HashMap map=new HashMap();
			map.put("arr_resIdx", arr_resIdx);
			testhistoryService.deleteTestHistory(map);
			ModelAndView modelAndView =new ModelAndView();
			
	        modelAndView.setViewName("jsonView");
	        
			return modelAndView;
	}
	
	@RequestMapping(value = "/updateStudentGroup",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateStudentGroup(
			@RequestParam Map param
			) throws IOException {
			
			String flag=param.get("flag").toString();
			String str_sgIdx=param.get("sgIdx").toString();
			int sgIdx=Integer.parseInt(str_sgIdx);
			JSONArray jsonArray =  JSONArray.fromObject(param.get("arrays"));
			int []arr_stIdx =new int[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++){
				arr_stIdx[i]=Integer.parseInt(jsonArray.get(i).toString());
			} 
			 if(param.get("flag").toString().equals("insert")){
				 Map map =new HashMap<String, Object>();
				  map.put("sgIdx", sgIdx);
				  map.put("arr_stIdx", arr_stIdx);
				 studentService.groupUpdate(map);
			 }else if(param.get("flag").toString().equals("trans")){
				 Map map =new HashMap<String, Object>();
				  map.put("sgIdx", 1);
				  map.put("arr_stIdx", arr_stIdx);
				 studentService.groupUpdate(map);
			 }else{
				 Map map =new HashMap<String, Object>();
				 map.put("arr_stIdx", arr_stIdx);
				 studentService.studentDelete(map);
			 }
			 
			ModelAndView modelAndView =new ModelAndView();
			modelAndView.addObject("sjsonlist", new JSONArray().fromObject(studentService.getStudentList()));
			modelAndView.addObject("sgjsonlist", new JSONArray().fromObject(studentService.getGroupStudentList(sgIdx)));
						
	        // Keypoint ! setViewName에 들어갈 String 파라미터는 JsonView bean 설정해줬던 id와 같아야 한다.
	        modelAndView.setViewName("jsonView");
		
			return modelAndView;
	}
	
	
	@RequestMapping(value = "/studentListExcelUpload",method=RequestMethod.POST)
	@ResponseBody
	public String studentExcelUpload(
			MultipartHttpServletRequest req
			) throws UnknownHostException{
		
		//엑셀 파일이 xls일때와 xlsx일때 서비스 라우팅
		String excelType = req.getParameter("excelType");
		if(excelType.equals("xlsx")){
			excelUploadService.studentListExcelUpload(req); 
		}else if(excelType.equals("xls")){
			excelUploadService.studentListExcelUpload(req);
		}
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/wordListExcelUpload",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView wordExcelUpload(Model model,
			MultipartHttpServletRequest req
			) throws UnknownHostException{
		ArrayList errlist= new ArrayList<Word>();
		
		//엑셀 파일이 xls일때와 xlsx일때 서비스 라우팅
		String excelType = req.getParameter("excelType");
		if(excelType.equals("xlsx")){
			errlist=excelUploadService.wordListExcelUpload(req); 
		}else if(excelType.equals("xls")){
			errlist=excelUploadService.wordListExcelUpload(req);
		}
		ModelAndView modelAndView=new ModelAndView();
		if(errlist.size()>0){
			Word word =(Word) errlist.get(0);
			if(word.getWoMeaning()==null){
				modelAndView.addObject("flag", "중복");
				modelAndView.addObject("jsonErrList", new JSONArray().fromObject(errlist));
				modelAndView.setViewName("jsonView");
			}else if(word.getWoMeaning()!=null){
				modelAndView.addObject("flag", "실패");
				modelAndView.addObject("jsonErrList", new JSONArray().fromObject(errlist));
				modelAndView.setViewName("jsonView");
			}
		}else{
			modelAndView.addObject("flag", "정상");
			modelAndView.setViewName("jsonView");
		}
		return modelAndView;
	}
	
}
