package com.project.word.vo;

//영어단어 목록
public class Word {

//  넘버링
private Integer woIdx;

//  단어 넘버링(AK)
private Integer woNo;

//  영어단어 철자(UQ)
private String woWord;

//  영어단어 뜻(구분자 ".")?
private String woMeaning;

//  단어 사용플래그 초기값 1
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

// Word 모델 복사
public void CopyData(Word param)
{
    this.woIdx = param.getWoIdx();
    this.woNo = param.getWoNo();
    this.woWord = param.getWoWord();
    this.woMeaning = param.getWoMeaning();
    this.woFlag = param.getWoFlag();
}
}
