package com.project.word;

import java.io.IOException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;


import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.word.dao.TimerDao;
import com.project.word.data.Choices;
import com.project.word.data.KeyAndIV;
import com.project.word.data.Question;
import com.project.word.data.QuestionText;
import com.project.word.data.Target;
import com.project.word.service.StudentService_Impl;
import com.project.word.service.TestHistoryService_Impl;
import com.project.word.service.TestResultService_Impl;
import com.project.word.service.WordService_Impl;
import com.project.word.util.CryptoUtil;
import com.project.word.vo.ResultView;
import com.project.word.vo.Student;
import com.project.word.vo.TestHistory;
import com.project.word.vo.TestResult;
import com.project.word.vo.Word;
import com.project.word.vo.WordView;

import net.sf.json.JSONArray;



/**
 * Handles requests for the application home page.
 */

@Controller
public class StudentController {
	
	
	@Autowired
	public StudentService_Impl studentService;
	
	@Autowired
	public WordService_Impl wordService;
	
	@Autowired
	public TestHistoryService_Impl testhistoryService;
	
	@Autowired
	public TestResultService_Impl testResultService;
	
	@Autowired
	public TimerDao timerdao;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	
	@RequestMapping(value = "/makeTest", method = RequestMethod.GET)
	public String maketestForm(Model model,HttpSession session) throws IOException {
		
		if(session.getAttribute("SID")!=null){
			/*
			 * 시험지 생성페이지 
			 */
			return "maketest";
		}else{
			return "redirect:/";
		}
	}
	
	/**
	 * 영단어 시험지 제작 로직
	 * 
	 * 사용자의 입력으로부터 시험범위 start~end까지..
	 * 몇개 볼지 cnt 
	 *
	 * 객관식(영-> 한) 및 주관식(한 -> 영)  typeselect 
	 * 
	 * 
	 * 객관식인경우 1. 범위내 문항 데이터 2. 선택지에 보여줄 데이터 =>DB로부터 가져와 문제와 선택지  가공 후jsonString 형태로 암호화하여 클라이언트로 보냄
	 * 주관식인경우 1. 범위내 문항 데이터 => DB로 부터 가져와 주관식 문제 가공후  위와 마찬가지로 클라이언트에게 제공 
	 * 
	 * 객관식 선택지 가공시, 랜덤하게 문항에 따라 정답을 포함한 6개의 선택지가 들어가도록 처리 
	 * 
	 */
	@RequestMapping(value = "/makeTest", method = RequestMethod.POST)
	public String maketest(Model model,HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(value="start",  defaultValue="1") int start,
			@RequestParam(value="end",  defaultValue="1") int end,
			@RequestParam(value="cnt") int cnt,
			@RequestParam(value="typeselect") String typeselect,
			@RequestParam(value="testtype", required=false) String testtype,
			@RequestParam(value="all_wrongIdx", required=false) String all_wrongIdx
			)  {
		if(session.getAttribute("SID")!=null){
			Student std= studentService.findByNum((String)session.getAttribute("SID"));
		
			/*
			 * testtype가wrong일때 새로운 쿼리 
			 */
			
			ArrayList<Word> questionList=null;
			ArrayList<Word> question_wordTextList=null;
			
			
			HashMap map =new HashMap();
			map.put("start", start);
			map.put("end", end);
			map.put("cnt", cnt);
			
			HashMap wrongmap =null;
			if(all_wrongIdx!=null){
				String[] arr_wrongIdx=all_wrongIdx.split(",");
				int[] arr_wrong=new int[arr_wrongIdx.length];
				for(int i=0;i<arr_wrongIdx.length;i++){
					arr_wrong[i]=Integer.parseInt(arr_wrongIdx[i].trim());
				}
				wrongmap=new HashMap();
				wrongmap.put("arr_wrongIdx", arr_wrong);
				wrongmap.put("cnt", cnt);
			}
			
			
			if(typeselect.equals("select")){
				
				if("wrong".equals(testtype)){
					questionList=(ArrayList<Word>) wordService.getWrongWordTest(wrongmap);
				}else{
					 questionList=
							(ArrayList<Word>) wordService.getWordTest(map);
				}
				
				
				
				if(questionList.size()==0){
					
					redirectAttributes.addFlashAttribute("msg", "no search");
					return "redirect:makeTest";
				}else if(questionList.size()<cnt){
					redirectAttributes.addFlashAttribute("msg", "samll question");
					return "redirect:makeTest";
				}
				int question_len =questionList.size();
				
				ArrayList<Word> optionList=
						(ArrayList<Word>)wordService.getWordOptionTest();
				
				HashMap<Integer,Word> option_meaningMap =new HashMap();
				for(Word wo: optionList){
					option_meaningMap.put(wo.getWoNo(),wo);
				}
				
				int max = wordService.getMaxWordNo();
				int total_num = wordService.getTotalNumberOfWords();
				
				ArrayList<Question> questionViewList=new ArrayList<Question>();// 클라이언트로 보낼 객관식 문제와 선택지 데이터 담을 자료구조
				
				for(Word wo_question:questionList){// database로부터 가져온 문제리스트를 반복문을 돈다.
						
						Question question =new Question(); //클라이언트로 보낼 객관식문제의 문항 넣을 객체
						
						question.setIdx(wo_question.getWoIdx());// 단어 인덱스 넣음
						question.setWord(wo_question.getWoWord());	//단어 넣음 
					
						HashSet<Integer> random_set=new HashSet();// 선택지에 중복된 단어가 들어가지않도록 거를 자료구조 
						Random random= new Random();// 랜덤함수 
						ArrayList<Choices> choicesList =new ArrayList<Choices>();// 선택지 리스트 
						
						int random_number=random.nextInt(6);// 랜덤넘버 1~6사이 숫자를 뽑음 그래서 선택지 가운데 정답 선택지를 랜덤하게 배치하기위한...
						
						int i=0;
						
						
						while(i<6){// 선택지 단어를 고르기 위한 반복문 
							Choices choices = null;
							if(i==random_number){// 랜덤넘버와 반복문의 인덱스가 같을때 정답선택지 셋팅 
								choices = new Choices(wo_question.getWoIdx(), wo_question.getWoMeaning());
							}
							else {
								int random_no = -1;// 그외에는 선택지를 랜덤하게 배치 
								
								do{
									//random_no= random.nextInt(question_len)+questionList.get(0).getWoNo()+1;// 선택된 범위내 영어 넘버 기준으로 랜덤하게 뽑힐 수 있도록 
									random_no= random.nextInt(max)+1;
									System.out.println("random_set.size()"+random_set.size());
									System.out.println("total_num"+total_num);
									if(random_set.size()>=total_num){
										
										/*
										 * choicesList의 사이즈가 6이 될때까지 
										 * random_no가 현재 문항 인덱스와 겹치지 않고 
										 * & random_set에 Random_no가 
										 */
										//for(int a = 0;choicesList.size()>a;a++){
											//System.out.println("????"+choicesList.get(a).getIdx());
										//}
										return "redirect:/";//TODO : ERROR처리
									}
								}while((!random_set.add(random_no))//정상 = 0 : 충돌 = 1
										||(random_no==wo_question.getWoNo())); //같으면(충돌) = 1 : 다르면(정상) = 0;
								
								Word word=(Word)option_meaningMap.get(random_no);// 선택지 단어 채워주기위한 작업 
								
								if(word==null){
									continue;
									//redirectAttributes.addFlashAttribute("msg", "no test");
									//return "redirect:makeTest";
								}
								choices = new Choices(word.getWoIdx(), word.getWoMeaning());// 선택지 들어감 
							}			
							choicesList.add(choices);// 선택지 리스트 
							i++;
						}
						question.setChoiceList(choicesList);// 각 문항 
						questionViewList.add(question);// 문제 리스트
				}
				
				JSONArray  jsonArray= new JSONArray().fromObject(questionViewList);
				String jsonString = jsonArray.toString();
				
				/*
				 * 객관식 암호화
				 */
				KeyAndIV data = CryptoUtil.autogenerateKeyAndIV(128,"SHA1PRNG",16);
				String cryptoText = CryptoUtil.encrypt(data.getKey(), data.getIv(), jsonString);
				
				model.addAttribute("sstd", std);
				model.addAttribute("type", typeselect);
				model.addAttribute("ques_cryptoText",cryptoText);
				model.addAttribute("ques_key",Base64.encodeBase64String((data.getKey().getEncoded())));
				model.addAttribute("ques_iv",Base64.encodeBase64String(data.getIv()));
				model.addAttribute("timer", timerdao.getSelectTimer());
				model.addAttribute("testtype", testtype);
			}else{
				if("wrong".equals(testtype)){
					question_wordTextList=(ArrayList<Word>)wordService.getWrongWordTextTest(wrongmap);
				}else{
					question_wordTextList=(ArrayList<Word>)wordService.getWordTextTest(map);
				}
				
				
				ArrayList<QuestionText> question_TextList =new ArrayList<QuestionText>(); //주관식 문제 가공할 자료구조 
				
				for(Word word : question_wordTextList){
					QuestionText questionText=new QuestionText();
					questionText.setIdx(word.getWoIdx());
					questionText.setMeaning(word.getWoMeaning());
					questionText.setWord(word.getWoWord());
					question_TextList.add(questionText);
				}
				
				JSONArray jsonArray= new JSONArray().fromObject(question_TextList);
				String jsonString = jsonArray.toString();
				
				
				/*
				 * 주관식 암호화
				 */
				
				KeyAndIV data = CryptoUtil.autogenerateKeyAndIV(128,"SHA1PRNG",16);
				String cryptoText = CryptoUtil.encrypt(data.getKey(), data.getIv(), jsonString);
				
				model.addAttribute("sstd", std);
				model.addAttribute("type", typeselect);
				model.addAttribute("question_cryptoText",cryptoText);
				model.addAttribute("ques_key",Base64.encodeBase64String((data.getKey().getEncoded())));
				model.addAttribute("ques_iv",Base64.encodeBase64String(data.getIv()));
				model.addAttribute("timer", timerdao.getTextTimer());
				model.addAttribute("testtype", testtype);
			}
			return "testform";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/wrongworddelete", method = RequestMethod.POST)
	public String wrongworddelete(TestHistory testhistory ,
			@RequestParam(value="wrongnotedatalist") String wrongnotedatalist,
						
			 HttpSession session){
			if(session.getAttribute("SID")!=null){
			
				Student std= studentService.findByNum((String)session.getAttribute("SID"));
				/*
				 * 시험결과 인덱스, 동일한 시험의 단어 인덱스와 포지션값 그리고 포지션 총 수 정보가 들어가있는 target
				 */
				HashMap<Integer,ArrayList<Target>> map=new HashMap<Integer,ArrayList<Target>>();
				
				for(String str:wrongnotedatalist.split(",")){
					/*
					 * 여러개의 제거할 오답 단어와 관련 한데이터가 들어있는 배열을 ,로 분리
					 */
					String[] wrongnotelist=str.split("_");
					/*
					 * 한 항목에 대한 정보를 _로 분리
					 */
				
						
					Target target =new Target();
					target.setPosition(Integer.parseInt(wrongnotelist[2]));
					target.setPositionlength(Integer.parseInt(wrongnotelist[4]));
					target.setWoIdx(Integer.parseInt(wrongnotelist[5]));
						
					ArrayList<Target> alist=new ArrayList<Target>();
						
					if(map.containsKey(Integer.parseInt(wrongnotelist[3]))){
						map.get(Integer.parseInt(wrongnotelist[3])).add(target);
						
					}else{
						
						alist.add(target);
						map.put(Integer.parseInt(wrongnotelist[3]), alist);
					}
				}
				
				Object resIdxarr_object[]=map.keySet().toArray();
				int resIdxarr[]=new int[resIdxarr_object.length];
				for(int i=0;i<resIdxarr.length;i++){
					resIdxarr[i]=(Integer)resIdxarr_object[i];
				}
				HashMap returnmap=new HashMap();
				returnmap.put("resIdxarr", resIdxarr);
				
				ArrayList<TestResult> trlist=(ArrayList<TestResult>)testResultService.getTestResultList(returnmap);
				
				for(TestResult tr: trlist){
					
					ArrayList<Target> alist=map.get(tr.getResIdx());
					String wflag=tr.getResWflag();
//					System.out.println("시험 인덱스:"+ tr.getResIdx()+" 선택된 시험의 오답flag 문자열"+wflag);
//					StringBuffer sb=new StringBuffer();
					String wflagarr[]= wflag.split(",");
					
					for(int i=0;i<alist.size();i++){
						wflagarr[alist.get(i).getPosition()-1]="0";
					}
					String resWflag=arrayJoin(",",wflagarr);
//					System.out.println(resWflag);
					HashMap parametermap= new HashMap();
					
					parametermap.put("resIdx", tr.getResIdx());
					parametermap.put("resWflag",resWflag);
					testResultService.updateWrongnote(parametermap);
				}
			return "redirect:/mypage";
		}else{
			return "redirect:/";
		}
	}
	
	public String arrayJoin(String glue, String array[]) {
	    String result = "";

	    for (int i = 0; i < array.length; i++) {
	      result += array[i];
	      if (i < array.length - 1) result += glue;
	    }
	    return result;
	  }
	
	@RequestMapping(value = "/testinsert", method = RequestMethod.POST)
	public String testinsert(TestHistory testhistory , TestResult testresult,
			@RequestParam Map param,HttpSession session){
		
		/*
		 * json 받아서 처리 
		 */
		if(session.getAttribute("SID")==null){
			return "redirect:/";
		}
		
		String stNum=session.getAttribute("SID").toString();
		String str_wrong=param.get("wrong").toString();
		String str_total=param.get("total").toString();
		String type=param.get("type").toString();
		String testtype=param.get("testtype").toString();
		
		int wrong=Integer.parseInt(str_wrong);
		int total=Integer.parseInt(str_total);
		
		
		JSONArray jsonArray =  JSONArray.fromObject(param.get("wrongList"));
		
		int []wrongList =new int[jsonArray.size()];
		for(int i=0;i<jsonArray.size();i++){
			wrongList[i]=Integer.parseInt(jsonArray.get(i).toString());
		} 
		Arrays.sort(wrongList);// 내림차순으로정렬 
		
		JSONArray rightjsonArray =  JSONArray.fromObject(param.get("rightList"));
		
		int []rightList =new int[rightjsonArray.size()];
		for(int i=0;i<rightjsonArray.size();i++){
			rightList[i]=Integer.parseInt(rightjsonArray.get(i).toString());
		} 
		Arrays.sort(rightList);// 내림차순으로정렬 
		
		
		StringBuffer sb_wrong=new StringBuffer();
		for(int i=0;i<wrongList.length;i++){
			if(i!=wrongList.length-1){
				sb_wrong.append(wrongList[i]+",");
			}else{
				sb_wrong.append(wrongList[i]);
			}
		}
		
	
		StringBuffer sb_right=new StringBuffer();
		for(int i=0;i<rightList.length;i++){
			if(i!=rightList.length-1){
				sb_right.append(rightList[i]+",");
			}else{
				sb_right.append(rightList[i]);
			}
		}
		
		StringBuffer sb_wrongflag=new StringBuffer();
		for(int i=0;i<wrongList.length;i++){
			if(i!=wrongList.length-1){
				sb_wrongflag.append(1+",");
			}else{
				sb_wrongflag.append(1);
			}
		}
		
		testresult.setResWrong(wrong);
		testresult.setResTotal(total);
		testresult.setResWlist(sb_wrong.toString());
		testresult.setResRlist(sb_right.toString());
		testresult.setResWflag(sb_wrongflag.toString());
		int resIdx = testResultService.insertTestResult(testresult);//resIdx 얻어오는 로직  mapper 태그확인
		
		
		int stIdx=studentService.findIdxByNum(stNum);//학생의 인덱스 
		
		String thTestdate="";
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate=new Date();
		thTestdate= sdf1.format(currentDate);
		
		/*
		 * 학생이 오늘 시험을 몇번 봤는지 조회 후 ++해주는 로직
		 */
		HashMap map =new HashMap();
		map.put("stIdx", stIdx);
		map.put("thTestdate", thTestdate);
		
		int pre_thTestnum= testhistoryService.testNumcheck(map);
		int thTestnum=pre_thTestnum+1;
		
		/*
		 *  test_history에 insert
		 */
		
		testhistory.setStIdx(stIdx);
		testhistory.setThTestnum(thTestnum);
		testhistory.setResIdx(resIdx);
		testhistory.setThType(type);
		if(testtype.equals("wrong")){
			testhistory.setThWflag(true);
		}else{
			testhistory.setThWflag(false);
		}
		
		testhistoryService.insertTestHistory(testhistory);
		
		return "redirect:/mypage";
	}
	
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(Model model,HttpSession session){
		
		if(session.getAttribute("SID")!=null){
			/*
			 * 개인별 학생 이력 select
			 */
			ArrayList<ResultView> studentViewList= new ArrayList();
			studentViewList=(ArrayList) testhistoryService.getresultViewListwithStudent(session.getAttribute("SID").toString());
			
			for(int i=0;i< studentViewList.size();i++){
				String wrong_arr[]=studentViewList.get(i).getWrongList().split(",");
				String wrong_flagarr[]=studentViewList.get(i).getWrongflag().split(",");
				
				for(int j=0;j< wrong_arr.length;j++){
					
					WordView wv=new WordView();
					if(!"".equals(wrong_arr[j]) && !"".equals(wrong_flagarr[j]) &&	Integer.parseInt(wrong_flagarr[j].trim())==1 ){ //1인경우 오답노트에 노출
						wv=wordService.getWordbyWrongIdx(Integer.parseInt(wrong_arr[j].trim()));// index로 word 테이블의 철자가져옴
						wv.setPosition(j+1);
						studentViewList.get(i).getWrongwordList().add(wv);//화면에 보여줄 오답  영단어 철자리스트
					}
					if(j==wrong_arr.length-1){
						studentViewList.get(i).setPositioncount(wrong_arr.length);
					}
				}
			}
			
			
			model.addAttribute("reslist",studentViewList);
			return "mypage";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public String word(Model model, @RequestParam Map param) throws IOException {
		
		
		
		model.addAttribute("wordList", wordService.getWordList()) ;
		return "word";
		
	}
	
	
	
	@RequestMapping(value = "/searchWord", method = RequestMethod.POST)
	public String searchWord(Model model,RedirectAttributes redirectAttributes
			,@RequestParam(value="start") int start,
			@RequestParam(value="end") int end) throws IOException {
		HashMap map=new HashMap();
		
		map.put("start", start);
		map.put("end", end);
		
		model.addAttribute("wordList", wordService.getWordTViewList(map));
		
		return "wordview";
		
	}
}
