package com.project.word.vo;

import java.util.Date;

public class TestHistory {

    //  �ѹ���
    private Integer thIdx;

    //  ��������
    private String thType;
    
    //  �����Ʈ���迩��(�����Ʈ �����ΰ�� 1)
    private boolean thWflag;
   
    //  �׳� ���� ��Ƚ��
    private Integer thTestnum;

    //  ���躻��¥
    private Date thTestdate;

    //  �л��ѹ���
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

    // TestHistory �� ����
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