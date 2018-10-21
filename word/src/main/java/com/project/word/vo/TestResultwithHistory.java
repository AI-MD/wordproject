package com.project.word.vo;

import java.util.Date;

public class TestResultwithHistory {
	private Integer resIdx;
//  틀린 문항
    private String resWlist;

   
    
    public Integer getResIdx() {
		return resIdx;
	}
	public void setResIdx(Integer resIdx) {
		this.resIdx = resIdx;
	}
	public String getResWlist() {
		return resWlist;
	}
	public void setResWlist(String resWlist) {
		this.resWlist = resWlist;
	}
	public String getResWflag() {
		return resWflag;
	}
	public void setResWflag(String resWflag) {
		this.resWflag = resWflag;
	}
	public Integer getStIdx() {
		return stIdx;
	}
	public void setStIdx(Integer stIdx) {
		this.stIdx = stIdx;
	}
	//오답노트 사용 flag  사용 1
    private String resWflag;
    private Integer stIdx;
}