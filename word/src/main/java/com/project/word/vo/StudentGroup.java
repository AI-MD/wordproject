package com.project.word.vo;

public class StudentGroup {

    private Integer sgIdx;

    //  그룹이름(AK)
    private String sgName;

    public Integer getSgIdx() {
        return sgIdx;
    }

    public void setSgIdx(Integer sgIdx) {
        this.sgIdx = sgIdx;
    }

    public String getSgName() {
        return sgName;
    }

    public void setSgName(String sgName) {
        this.sgName = sgName;
    }

    // StudentGroup 모델 복사
    public void CopyData(StudentGroup param)
    {
        this.sgIdx = param.getSgIdx();
        this.sgName = param.getSgName();
    }
}
