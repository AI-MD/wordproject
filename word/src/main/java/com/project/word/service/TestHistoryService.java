package com.project.word.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.word.vo.TestHistory;
import com.project.word.vo.TestResultwithHistory;






@Repository
public interface TestHistoryService {
	
	public Integer testNumcheck(Map map);
	
	public void insertTestHistory(TestHistory th);
	
	public List getresultViewList();
	public List getresultViewListwithStudent(String stNum);
	public void deleteTestHistory(Map map);
	
	public List<TestResultwithHistory> getWrongwordjoinlist(Integer stIdx);
}