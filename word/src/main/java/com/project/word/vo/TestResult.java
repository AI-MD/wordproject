package com.project.word.vo;

public class TestResult {

    //  첫번째 테스트 결과 인덱스
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

	//  틀린 갯수
    private Integer resWrong;

    //  시험본 문항갯수
    private Integer resTotal;

    //  틀린 문항
    private String resWlist;

    //  맞은 문항
    private String resRlist;
    
    //오답노트 사용 flag  사용 1
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

    // TestResult 모델 복사
    public void CopyData(TestResult param)
    {
        this.resIdx = param.getResIdx();
        this.resWrong = param.getResWrong();
        this.resTotal = param.getResTotal();
        this.resWlist = param.getResWlist();
    }
}