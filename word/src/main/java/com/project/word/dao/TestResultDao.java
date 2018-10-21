package com.project.word.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.word.vo.Student;
import com.project.word.vo.TestResult;



@Repository
public class TestResultDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public int insertTestResult(TestResult res ){
		
		sqlSession.insert("insertTestResult" ,res);
		
		return res.getResIdx();
	}
	
	public void updateWrongnote(HashMap map){
		/*
		 * 
		 * ¹Ì¿Ï¼º 
		 */
		sqlSession.update("updateWrongnote",map);
	}
	
	public List getTestResultList(HashMap map){
		return sqlSession.selectList("getTestResultList",map);
	}
}