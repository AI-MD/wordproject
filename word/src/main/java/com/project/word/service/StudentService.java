package com.project.word.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.word.vo.Student;

import net.sf.json.JSONArray;




@Repository
public interface StudentService {
	
	
	
	public List<Student> getStudentList();
	public List<Student> getGroupStudentList(int sgIdx);
	public void groupUpdate(Map map);
	public void studentDelete(Map map);
	public Integer findIdxByNum(String stNum);
	public Student findByNum(String stNum);
}