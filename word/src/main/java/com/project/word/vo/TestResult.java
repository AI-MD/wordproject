package com.project.word.vo;

public class TestResult {

    //  ù��° �׽�Ʈ ��� �ε���
    private Integer resIdx;

    public String getResRlist() {
		return resRlist;
	}
	public void setResRlist(String resRlist) {
		this.resRlist = resRlist;
	}
	public String getResWflag() {
		return resWflag;
	}
	public void setResWflag(String resWflag) {
		this.resWflag = resWflag;
	}

	//  Ʋ�� ����
    private Integer resWrong;

    //  ���躻 ���װ���
    private Integer resTotal;

    //  Ʋ�� ����
    private String resWlist;

    //  ���� ����
    private String resRlist;
    
    //�����Ʈ ��� flag  ��� 1
    private String resWflag;
    
    public Integer getResIdx() {
        return resIdx;
    }
    public void setResIdx(Integer resIdx) {
        this.resIdx = resIdx;
    }
    public Integer getResWrong() {
        return resWrong;
    }
    public void setResWrong(Integer resWrong) {
        this.resWrong = resWrong;
    }

    public Integer getResTotal() {
        return resTotal;
    }

    public void setResTotal(Integer resTotal) {
        this.resTotal = resTotal;
    }

    public String getResWlist() {
        return resWlist;
    }

    public void setResWlist(String resWlist) {
        this.resWlist = resWlist;
    }

    // TestResult �� ����
    public void CopyData(TestResult param)
    {
        this.resIdx = param.getResIdx();
        this.resWrong = param.getResWrong();
        this.resTotal = param.getResTotal();
        this.resWlist = param.getResWlist();
    }
}