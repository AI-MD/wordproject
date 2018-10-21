package com.project.word.dao;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.word.vo.Student;
import com.project.word.vo.Word;
import com.project.word.vo.WordView;


@Repository
public class WordDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public List getWordList(){
	 return	sqlSession.selectList("getWordList");
	}
	
	public List getWordTest(HashMap map){
		 return	sqlSession.selectList("getWordTest",map);
	}
	public List getWrongWordTest(HashMap map){
		 return	sqlSession.selectList("getWrongWordTest",map);
	}
	public List getWordOptionTest(){
		 return	sqlSession.selectList("getWordOptionTest");
	}
	
	public List getWordTextTest(HashMap map){
		 return	sqlSession.selectList("getWordTextTest",map);
	}
	public List getWrongWordTextTest(HashMap map){
		 return	sqlSession.selectList("getWrongWordTextTest",map);
	}
	public void insertWordExcel(List<Word> list){
		sqlSession.insert("insertWordExcel" ,list);
	}
	
	public List getWordTViewList(HashMap map){
		 return	sqlSession.selectList("getWordTViewList",map);
	}
	
	public int getMaxWordNo(){
		 return	sqlSession.selectOne("getMaxWordNo");
	}
	
	public int getTotalNumberOfWords(){
		 return	sqlSession.selectOne("getTotalNumberOfWords");
	}
	

	
	public WordView getWordbyWrongIdx(int idx){
		 return	sqlSession.selectOne("getWordbyWrongIdx",idx);
	}
}