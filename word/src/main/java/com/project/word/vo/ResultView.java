package com.project.word.vo;

import java.util.ArrayList;
import java.util.Date;

public class ResultView {
	private Integer thIdx;
	private String thType;
	private Integer thTestNum;
	private Date thTestDate;
    private boolean thWflag;
	private String stNum;
	private String stName;
	private String groupName;
	private Integer wrongCount;
	private Integer totalCount;
	private Integer resIdx;
	private String wrongList;
    private String rightlist;
    private String wrongflag;
	
    private ArrayList<WordView> wrongwordList = new ArrayList<WordView>();
    private Integer positioncount;
    
    
    
    
	
	public Integer getResIdx() {
		return resIdx;
	}
	public void setResIdx(Integer resIdx) {
		this.resIdx = resIdx;
	}
	public Integer getPositioncount() {
		return positioncount;
	}
	public void setPositioncount(Integer positioncount) {
		this.positioncount = positioncount;
	}
	public ArrayList<WordView> getWrongwordList() {
		return wrongwordList;
	}
	public void setWrongwordList(ArrayList<WordView> wrongwordList) {
		this.wrongwordList = wrongwordList;
	}
	public boolean isThWflag() {
		return thWflag;
	}
	public void setThWflag(boolean thWflag) {
		this.thWflag = thWflag;
	}
	public String getRightlist() {
		return rightlist;
	}
	public void setRightlist(String rightlist) {
		this.rightlist = rightlist;
	}
	public String getWrongflag() {
		return wrongflag;
	}
	public void setWrongflag(String wrongflag) {
		this.wrongflag = wrongflag;
	}
	public Integer getThIdx() {
		return thIdx;
	}
	public void setThIdx(Integer thIdx) {
		this.thIdx = thIdx;
	}
	public String getThType() {
		return thType;
	}
	public void setThType(String thType) {
		this.thType = thType;
	}
	public Integer getThTestNum() {
		return thTestNum;
	}
	public void setThTestNum(Integer thTestNum) {
		this.thTestNum = thTestNum;
	}
	public Date getThTestDate() {
		return thTestDate;
	}
	public void setThTestDate(Date thTestDate) {
		this.thTestDate = thTestDate;
	}
	public String getStNum() {
		return stNum;
	}
	public void setStNum(String stNum) {
		this.stNum = stNum;
	}
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getWrongList() {
		return wrongList;
	}
	public void setWrongList(String wrongList) {
		this.wrongList = wrongList;
	}
	
	
}
