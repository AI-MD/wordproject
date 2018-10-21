package com.project.word.vo;


public class Admin {

    //  관리자 인덱스
    private Integer adIdx;

    //  교직 번호(AK)
    private String adNum;

    //  이름
    private String adName;

    //  관리자 비밀번호(보안조치 필요)
    private String adPw;

    //  관리자 사용플래그
    private Boolean adFlag;

    public Integer getAdIdx() {
        return adIdx;
    }

    public void setAdIdx(Integer adIdx) {
        this.adIdx = adIdx;
    }

    public String getAdNum() {
        return adNum;
    }

    public void setAdNum(String adNum) {
        this.adNum = adNum;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdPw() {
        return adPw;
    }

    public void setAdPw(String adPw) {
        this.adPw = adPw;
    }

    public Boolean getAdFlag() {
        return adFlag;
    }

    public void setAdFlag(Boolean adFlag) {
        this.adFlag = adFlag;
    }

    // Admin 모델 복사
    public void CopyData(Admin param)
    {
        this.adIdx = param.getAdIdx();
        this.adNum = param.getAdNum();
        this.adName = param.getAdName();
        this.adPw = param.getAdPw();
        this.adFlag = param.getAdFlag();
    }
}