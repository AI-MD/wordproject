package com.project.word.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.word.vo.TestResult;






@Repository
public interface TestResultService {
	
	public int insertTestResult(TestResult res);
	public void updateWrongnote(HashMap map);
	
	public List getTestResultList(HashMap map);
}