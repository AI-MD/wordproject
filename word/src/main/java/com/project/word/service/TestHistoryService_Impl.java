package com.project.word.service;





import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.word.dao.ResultViewDao;
import com.project.word.dao.TestHistoryDao;
import com.project.word.vo.TestHistory;






@Repository
public class TestHistoryService_Impl implements TestHistoryService {
	
	
	@Autowired
	public TestHistoryDao testhistoryDao;
	@Autowired
	public ResultViewDao resultViewDao;
	
	@Override
	public Integer testNumcheck(Map map) {
		// TODO Auto-generated method stub
		return testhistoryDao.testNumcheck(map);
	}

	@Override
	public void insertTestHistory(TestHistory th) {
		testhistoryDao.insertTestHistory(th);
	}

	@Override
	public List getresultViewList() {
		// TODO Auto-generated method stub
		return resultViewDao.getresultViewList();
	}

	@Override
	public List getresultViewListwithStudent(String stNum) {
		
		
		// TODO Auto-generated method stub
		return resultViewDao.getresultViewListwithStudent(stNum);
	}

	@Override
	public void deleteTestHistory(Map map) {
		testhistoryDao.deleteTestHistory(map);
	}

	@Override
	public List getWrongwordjoinlist(Integer stIdx) {
		// TODO Auto-generated method stub
		return testhistoryDao.getWrongwordjoinlist(stIdx);
	}

	
	
	
	
}