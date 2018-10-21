package com.project.word.service;





import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.project.word.dao.TestResultDao;
import com.project.word.vo.TestResult;



@Repository
public class TestResultService_Impl implements TestResultService {
	@Autowired
	public TestResultDao testresultDao;

	@Override
	public int insertTestResult(TestResult res) {
		// TODO Auto-generated method stub
		return testresultDao.insertTestResult(res);
	}

	@Override
	public void updateWrongnote(HashMap map) {
		testresultDao.updateWrongnote(map);
		
	}

	@Override
	public List getTestResultList(HashMap map) {
		// TODO Auto-generated method stub
		return testresultDao.getTestResultList(map);
	}

	

}