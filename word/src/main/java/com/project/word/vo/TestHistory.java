package com.project.word.vo;

import java.util.Date;

public class TestHistory {

    //  넘버링
    private Integer thIdx;

    //  시험유형
    private String thType;
    
    //  오답노트시험여부(오답노트 시험인경우 1)
    private boolean thWflag;
   
    //  그날 시험 본횟수
    private Integer thTestnum;

    //  시험본날짜
    private Date thTestdate;

    //  학생넘버링
    private Integer stIdx;

    private Integer resIdx;

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

    public Integer getThTestnum() {
        return thTestnum;
    }

    public void setThTestnum(Integer thTestnum) {
        this.thTestnum = thTestnum;
    }

    public Date getThTestdate() {
        return thTestdate;
    }

    public void setThTestdate(Date thTestdate) {
        this.thTestdate = thTestdate;
    }

    public Integer getStIdx() {
        return stIdx;
    }

    public void setStIdx(Integer stIdx) {
        this.stIdx = stIdx;
    }

    public Integer getResIdx() {
        return resIdx;
    }

    public void setResIdx(Integer resIdx) {
        this.resIdx = resIdx;
    }

    // TestHistory 모델 복사
    public void CopyData(TestHistory param)
    {
        this.thIdx = param.getThIdx();
        this.thType = param.getThType();
        this.thTestnum = param.getThTestnum();
        this.thTestdate = param.getThTestdate();
        this.stIdx = param.getStIdx();
        this.resIdx = param.getResIdx();
    }

	public boolean isThWflag() {
		return thWflag;
	}

	public void setThWflag(boolean thWflag) {
		this.thWflag = thWflag;
	}
}