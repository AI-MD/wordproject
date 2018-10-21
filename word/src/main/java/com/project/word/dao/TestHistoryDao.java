package com.project.word.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.word.vo.TestHistory;
import com.project.word.vo.TestResultwithHistory;





@Repository
public class TestHistoryDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	public Integer testNumcheck(Map map) {
		
		return sqlSession.selectOne(
				"testNumcheck",map
				);
	}
	public void insertTestHistory(TestHistory th){
		sqlSession.insert("insertTestHistory",th);
	}
	
	
	public void deleteTestHistory(Map map){
		sqlSession.delete("deleteTestHistory",map);
	}
	
	public List<TestResultwithHistory> getWrongwordjoinlist(int stIdx){
		return sqlSession.selectList("getWrongwordjoinlist",stIdx);
	}
	
}