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
			 * ������ ���������� 
			 */
			return "maketest";
		}else{
			return "redirect:/";
		}
	}
	
	/**
	 * ���ܾ� ������ ���� ����
	 * 
	 * ������� �Է����κ��� ������� start~end����..
	 * � ���� cnt 
	 *
	 * ������(��-> ��) �� �ְ���(�� -> ��)  typeselect 
	 * 
	 * 
	 * �������ΰ�� 1. ������ ���� ������ 2. �������� ������ ������ =>DB�κ��� ������ ������ ������  ���� ��jsonString ���·� ��ȣȭ�Ͽ� Ŭ���̾�Ʈ�� ����
	 * �ְ����ΰ�� 1. ������ ���� ������ => DB�� ���� ������ �ְ��� ���� ������  ���� ���������� Ŭ���̾�Ʈ���� ���� 
	 * 
	 * ������ ������ ������, �����ϰ� ���׿� ���� ������ ������ 6���� �������� ������ ó�� 
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
			 * testtype��wrong�϶� ���ο� ���� 
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
				
				ArrayList<Question> questionViewList=new ArrayList<Question>();// Ŭ���̾�Ʈ�� ���� ������ ������ ������ ������ ���� �ڷᱸ��
				
				for(Word wo_question:questionList){// database�κ��� ������ ��������Ʈ�� �ݺ����� ����.
						
						Question question =new Question(); //Ŭ���̾�Ʈ�� ���� �����Ĺ����� ���� ���� ��ü
						
						question.setIdx(wo_question.getWoIdx());// �ܾ� �ε��� ����
						question.setWord(wo_question.getWoWord());	//�ܾ� ���� 
					
						HashSet<Integer> random_set=new HashSet();// �������� �ߺ��� �ܾ �����ʵ��� �Ÿ� �ڷᱸ�� 
						Random random= new Random();// �����Լ� 
						ArrayList<Choices> choicesList =new ArrayList<Choices>();// ������ ����Ʈ 
						
						int random_number=random.nextInt(6);// �����ѹ� 1~6���� ���ڸ� ���� �׷��� ������ ��� ���� �������� �����ϰ� ��ġ�ϱ�����...
						
						int i=0;
						
						
						while(i<6){// ������ �ܾ ���� ���� �ݺ��� 
							Choices choices = null;
							if(i==random_number){// �����ѹ��� �ݺ����� �ε����� ������ ���伱���� ���� 
								choices = new Choices(wo_question.getWoIdx(), wo_question.getWoMeaning());
							}
							else {
								int random_no = -1;// �׿ܿ��� �������� �����ϰ� ��ġ 
								
								do{
									//random_no= random.nextInt(question_len)+questionList.get(0).getWoNo()+1;// ���õ� ������ ���� �ѹ� �������� �����ϰ� ���� �� �ֵ��� 
									random_no= random.nextInt(max)+1;
									System.out.println("random_set.size()"+random_set.size());
									System.out.println("total_num"+total_num);
									if(random_set.size()>=total_num){
										
										/*
										 * choicesList�� ����� 6�� �ɶ����� 
										 * random_no�� ���� ���� �ε����� ��ġ�� �ʰ� 
										 * & random_set�� Random_no�� 
										 */
										//for(int a = 0;choicesList.size()>a;a++){
											//System.out.println("????"+choicesList.get(a).getIdx());
										//}
										return "redirect:/";//TODO : ERRORó��
									}
								}while((!random_set.add(random_no))//���� = 0 : �浹 = 1
										||(random_no==wo_question.getWoNo())); //������(�浹) = 1 : �ٸ���(����) = 0;
								
								Word word=(Word)option_meaningMap.get(random_no);// ������ �ܾ� ä���ֱ����� �۾� 
								
								if(word==null){
									continue;
									//redirectAttributes.addFlashAttribute("msg", "no test");
									//return "redirect:makeTest";
								}
								choices = new Choices(word.getWoIdx(), word.getWoMeaning());// ������ �� 
							}			
							choicesList.add(choices);// ������ ����Ʈ 
							i++;
						}
						question.setChoiceList(choicesList);// �� ���� 
						questionViewList.add(question);// ���� ����Ʈ
				}
				
				JSONArray  jsonArray= new JSONArray().fromObject(questionViewList);
				String jsonString = jsonArray.toString();
				
				/*
				 * ������ ��ȣȭ
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
				
				
				ArrayList<QuestionText> question_TextList =new ArrayList<QuestionText>(); //�ְ��� ���� ������ �ڷᱸ�� 
				
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
				 * �ְ��� ��ȣȭ
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
				 * ������ �ε���, ������ ������ �ܾ� �ε����� �����ǰ� �׸��� ������ �� �� ������ ���ִ� target
				 */
				HashMap<Integer,ArrayList<Target>> map=new HashMap<Integer,ArrayList<Target>>();
				
				for(String str:wrongnotedatalist.split(",")){
					/*
					 * �������� ������ ���� �ܾ�� ���� �ѵ����Ͱ� ����ִ� �迭�� ,�� �и�
					 */
					String[] wrongnotelist=str.split("_");
					/*
					 * �� �׸� ���� ������ _�� �и�
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
//					System.out.println("���� �ε���:"+ tr.getResIdx()+" ���õ� ������ ����flag ���ڿ�"+wflag);
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
		 * json �޾Ƽ� ó�� 
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
		Arrays.sort(wrongList);// ���������������� 
		
		JSONArray rightjsonArray =  JSONArray.fromObject(param.get("rightList"));
		
		int []rightList =new int[rightjsonArray.size()];
		for(int i=0;i<rightjsonArray.size();i++){
			rightList[i]=Integer.parseInt(rightjsonArray.get(i).toString());
		} 
		Arrays.sort(rightList);// ���������������� 
		
		
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
		int resIdx = testResultService.insertTestResult(testresult);//resIdx ������ ����  mapper �±�Ȯ��
		
		
		int stIdx=studentService.findIdxByNum(stNum);//�л��� �ε��� 
		
		String thTestdate="";
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate=new Date();
		thTestdate= sdf1.format(currentDate);
		
		/*
		 * �л��� ���� ������ ��� �ô��� ��ȸ �� ++���ִ� ����
		 */
		HashMap map =new HashMap();
		map.put("stIdx", stIdx);
		map.put("thTestdate", thTestdate);
		
		int pre_thTestnum= testhistoryService.testNumcheck(map);
		int thTestnum=pre_thTestnum+1;
		
		/*
		 *  test_history�� insert
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
			 * ���κ� �л� �̷� select
			 */
			ArrayList<ResultView> studentViewList= new ArrayList();
			studentViewList=(ArrayList) testhistoryService.getresultViewListwithStudent(session.getAttribute("SID").toString());
			
			for(int i=0;i< studentViewList.size();i++){
				String wrong_arr[]=studentViewList.get(i).getWrongList().split(",");
				String wrong_flagarr[]=studentViewList.get(i).getWrongflag().split(",");
				
				for(int j=0;j< wrong_arr.length;j++){
					
					WordView wv=new WordView();
					if(!"".equals(wrong_arr[j]) && !"".equals(wrong_flagarr[j]) &&	Integer.parseInt(wrong_flagarr[j].trim())==1 ){ //1�ΰ�� �����Ʈ�� ����
						wv=wordService.getWordbyWrongIdx(Integer.parseInt(wrong_arr[j].trim()));// index�� word ���̺��� ö�ڰ�����
						wv.setPosition(j+1);
						studentViewList.get(i).getWrongwordList().add(wv);//ȭ�鿡 ������ ����  ���ܾ� ö�ڸ���Ʈ
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
