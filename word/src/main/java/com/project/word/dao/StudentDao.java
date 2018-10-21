package com.project.word.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.word.vo.Student;




@Repository
public class StudentDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public Integer findByStudentCheck(Student svo) {
		int count =  sqlSession.selectOne(
				"findBySTCheck",svo
				);
		return count;
	}
	public Integer findIdxByNum(String stNum) {
		return  sqlSession.selectOne(
				"findIdxByNum",stNum
				);
	}
	
	public Student findByNum(String stNum) {
		return  sqlSession.selectOne(
				"findByNum",stNum
				);
	}
	public void insertStudentExcel(List<Student> list){
		sqlSession.insert("insertStudentExcel" ,list);
	}
	
	public List<Student> getStudentList(){
		
		return sqlSession.selectList("getStudentList");
	}
	public List<Student> getGroupStudentList(int sgIdx){
		
		return sqlSession.selectList("getGroupStudentList",sgIdx);
	}
	

	public void groupUpdate(Map map){
		
		sqlSession.update("groupUpdate",map);
	}
	public void studentDelete(Map map){
		
		sqlSession.delete("studentDelete",map);
	}
}