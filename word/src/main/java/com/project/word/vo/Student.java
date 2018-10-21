package com.project.word.vo;

import java.util.Date;

//맴버목록
public class Student {

//  넘버링
private Integer stIdx;

//  학생이름
private String stName;

//  학번(AK)
private String stNum;

//  그룹
private Integer sgIdx;

//그룹명
private String sgName;


public String getSgName() {
	return sgName;
}

public void setSgName(String sgName) {
	this.sgName = sgName;
}

//  가입일
private Date stRegdate;

public Integer getStIdx() {
    return stIdx;
}

public void setStIdx(Integer stIdx) {
    this.stIdx = stIdx;
}

public String getStName() {
    return stName;
}

public void setStName(String stName) {
    this.stName = stName;
}

public String getStNum() {
    return stNum;
}

public void setStNum(String stNum) {
    this.stNum = stNum;
}

public Integer getSgIdx() {
    return sgIdx;
}

public void setSgIdx(Integer sgIdx) {
    this.sgIdx = sgIdx;
}

public Date getStRegdate() {
    return stRegdate;
}

public void setStRegdate(Date stRegdate) {
    this.stRegdate = stRegdate;
}

// Student 모델 복사
public void CopyData(Student param)
{
    this.stIdx = param.getStIdx();
    this.stName = param.getStName();
    this.stNum = param.getStNum();
    this.sgIdx = param.getSgIdx();
    this.stRegdate = param.getStRegdate();
}

@Override
	public String toString() {
		
	
		return "idx"+stIdx+"name"+stName;
	}
}
