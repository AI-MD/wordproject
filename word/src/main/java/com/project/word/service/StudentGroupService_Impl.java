package com.project.word.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.project.word.dao.StudentGroupDao;
import com.project.word.vo.StudentGroup;



@Repository
public class StudentGroupService_Impl implements StudentGroupService {
	@Autowired
	public StudentGroupDao studentgroupDao;

	@Override
	public List<StudentGroup> getStudentGroupList() {
		// TODO Auto-generated method stub
		return studentgroupDao.getStudentGroupList();
	}
	@Override
	public void studentGroupInsert(StudentGroup sgvo) {
		studentgroupDao.studentGroupInsert(sgvo);
		
	}
	
}