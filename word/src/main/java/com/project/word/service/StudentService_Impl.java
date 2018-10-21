package com.project.word.service;





import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.word.dao.StudentDao;
import com.project.word.vo.Student;

import net.sf.json.JSONArray;


@Repository
public class StudentService_Impl implements StudentService {
	@Autowired
	public StudentDao studentDao;


	@Override
	public List<Student> getStudentList() {
		// TODO Auto-generated method stub
		return studentDao.getStudentList();
	}

	@Override
	public List<Student> getGroupStudentList(int sgIdx) {
		// TODO Auto-generated method stub
		return studentDao.getGroupStudentList(sgIdx);
	}

	

	@Override
	public void groupUpdate(Map map) {
		studentDao.groupUpdate(map);
		
	}

	@Override
	public void studentDelete(Map map) {
		studentDao.studentDelete(map);
		
	}
	
	@Override
	public Integer findIdxByNum(String stNum) {
		// TODO Auto-generated method stub
		return studentDao.findIdxByNum(stNum);
	}

	@Override
	public Student findByNum(String stNum) {
		// TODO Auto-generated method stub
		return studentDao.findByNum(stNum);
	}
}