package com.project.word.vo;

//����ܾ� ���
public class Word {

//  �ѹ���
private Integer woIdx;

//  �ܾ� �ѹ���(AK)
private Integer woNo;

//  ����ܾ� ö��(UQ)
private String woWord;

//  ����ܾ� ��(������ ".")?
private String woMeaning;

//  �ܾ� ����÷��� �ʱⰪ 1
private Boolean woFlag;

public Integer getWoIdx() {
    return woIdx;
}

public void setWoIdx(Integer woIdx) {
    this.woIdx = woIdx;
}

public Integer getWoNo() {
    return woNo;
}

public void setWoNo(Integer woNo) {
    this.woNo = woNo;
}

public String getWoWord() {
    return woWord;
}

public void setWoWord(String woWord) {
    this.woWord = woWord;
}

public String getWoMeaning() {
    return woMeaning;
}

public void setWoMeaning(String woMeaning) {
    this.woMeaning = woMeaning;
}

public Boolean getWoFlag() {
    return woFlag;
}

public void setWoFlag(Boolean woFlag) {
    this.woFlag = woFlag;
}

// Word �� ����
public void CopyData(Word param)
{
    this.woIdx = param.getWoIdx();
    this.woNo = param.getWoNo();
    this.woWord = param.getWoWord();
    this.woMeaning = param.getWoMeaning();
    this.woFlag = param.getWoFlag();
}
}
