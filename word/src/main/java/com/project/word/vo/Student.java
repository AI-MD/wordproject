package com.project.word.vo;

import java.util.Date;

//�ɹ����
public class Student {

//  �ѹ���
private Integer stIdx;

//  �л��̸�
private String stName;

//  �й�(AK)
private String stNum;

//  �׷�
private Integer sgIdx;

//�׷��
private String sgName;


public String getSgName() {
	return sgName;
}

public void setSgName(String sgName) {
	this.sgName = sgName;
}

//  ������
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

// Student �� ����
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
