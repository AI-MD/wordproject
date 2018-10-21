package com.project.word.dao;



import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;




@Repository
public class ResultViewDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	public List getresultViewList(){
		return sqlSession.selectList("getresultViewList");
	}
	public List getresultViewListwithStudent(String stNum){
		
		
		return sqlSession.selectList("getresultViewListwithStudent",stNum);
	}
}