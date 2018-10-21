package com.project.word.dao;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;






@Repository
public class TimerDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	
	public void updateSelectTimer(int timer){
		sqlSession.update("updateSelectTimer",timer);
	}
	
	public int getSelectTimer(){
		return sqlSession.selectOne("getSelectTimer");
	}
	
	public void updateTextTimer(int timer){
		sqlSession.update("updateTextTimer",timer);
	}
	
	public int getTextTimer(){
		return sqlSession.selectOne("getTextTimer");
	}
	
	
}