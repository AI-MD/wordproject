package com.project.word.vo;


public class Admin {

    //  ������ �ε���
    private Integer adIdx;

    //  ���� ��ȣ(AK)
    private String adNum;

    //  �̸�
    private String adName;

    //  ������ ��й�ȣ(������ġ �ʿ�)
    private String adPw;

    //  ������ ����÷���
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

    // Admin �� ����
    public void CopyData(Admin param)
    {
        this.adIdx = param.getAdIdx();
        this.adNum = param.getAdNum();
        this.adName = param.getAdName();
        this.adPw = param.getAdPw();
        this.adFlag = param.getAdFlag();
    }
}