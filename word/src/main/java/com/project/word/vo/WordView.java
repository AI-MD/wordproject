package com.project.word.vo;

//����ܾ� ���
public class WordView {

//  �ѹ���
private Integer woIdx;



//  ����ܾ� ö��(UQ)
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




// Word �� ����
public void CopyData(WordView param)
{
    this.woIdx = param.getWoIdx();
   
    this.woWord = param.getWoWord();
  
   
}
}
