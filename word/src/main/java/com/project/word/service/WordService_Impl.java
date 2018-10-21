package com.project.word.service;





import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.project.word.dao.WordDao;
import com.project.word.vo.WordView;



@Repository
public class WordService_Impl implements WordService {
	@Autowired
	public WordDao wordDao;

	@Override
	public List getWordList() {
		
		return wordDao.getWordList();
	}

	@Override
	public List getWordTest(HashMap map) {
		// TODO Auto-generated method stub
		return wordDao.getWordTest(map);
	}

	@Override
	public List getWordOptionTest() {
		// TODO Auto-generated method stub
		return wordDao.getWordOptionTest();
	}

	@Override
	public List getWordTextTest(HashMap map) {
		// TODO Auto-generated method stub
		return wordDao.getWordTextTest(map);
	}

	@Override
	public List getWordTViewList(HashMap map) {
		// TODO Auto-generated method stub
		return wordDao.getWordTViewList(map);
	}

	@Override
	public int getMaxWordNo() {
		// TODO Auto-generated method stub
		 return wordDao.getMaxWordNo();
	}

	@Override
	public int getTotalNumberOfWords() {
		// TODO Auto-generated method stub
		 return wordDao.getTotalNumberOfWords();
	}

	@Override
	public WordView getWordbyWrongIdx(int idx) {
		// TODO Auto-generated method stub
		return wordDao.getWordbyWrongIdx(idx);
	}

	@Override
	public List getWrongWordTest(HashMap map) {
		// TODO Auto-generated method stub
		return wordDao.getWrongWordTest(map);
	}

	@Override
	public List getWrongWordTextTest(HashMap map) {
		// TODO Auto-generated method stub
		return wordDao.getWrongWordTextTest(map);
	}
	
	
	
	
}