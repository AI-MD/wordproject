package com.project.word.data;

import java.util.ArrayList;

public class Question {
	
	Integer idx;
	String word;
	ArrayList<Choices> choiceList;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public ArrayList<Choices> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(ArrayList<Choices> choiceList) {
		this.choiceList = choiceList;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	

	
}
