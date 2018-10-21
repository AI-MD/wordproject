package com.project.word.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.word.vo.WordView;






@Repository
public interface WordService {
	public List getWrongWordTextTest(HashMap map);
	public List getWordList();
	public List getWordTest(HashMap map);
	public List getWordOptionTest();
	public WordView getWordbyWrongIdx(int idx);
	public List getWordTextTest(HashMap map);
	public List getWordTViewList(HashMap map);
	public int getMaxWordNo();
	public int getTotalNumberOfWords();
	
	public List getWrongWordTest(HashMap map);
}