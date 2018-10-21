package com.project.word.vo;

//영어단어 목록
public class WordView {

//  넘버링
private Integer woIdx;



//  영어단어 철자(UQ)
private String woWord;

private int position;


public int getPosition() {
	return position;
}

public void setPosition(int position) {
	this.position = position;
}

public Integer getWoIdx() {
    return woIdx;
}

public void setWoIdx(Integer woIdx) {
    this.woIdx = woIdx;
}


public String getWoWord() {
    return woWord;
}

public void setWoWord(String woWord) {
    this.woWord = woWord;
}




// Word 모델 복사
public void CopyData(WordView param)
{
    this.woIdx = param.getWoIdx();
   
    this.woWord = param.getWoWord();
  
   
}
}
