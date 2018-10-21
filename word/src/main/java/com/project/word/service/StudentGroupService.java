package com.project.word.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.word.vo.StudentGroup;





@Repository
public interface StudentGroupService {
	
	
	public List<StudentGroup> getStudentGroupList();
	public void studentGroupInsert(StudentGroup sgvo);
}