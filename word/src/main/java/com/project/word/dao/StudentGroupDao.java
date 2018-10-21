package com.project.word.dao;


import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import com.project.word.vo.StudentGroup;




@Repository
public class StudentGroupDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public List<StudentGroup> getStudentGroupList() {
		//System.out.println(sqlSession.toString() + no);
		
		return sqlSession.selectList(
				"getStudentGroupList"
				);
	}
	
	public void studentGroupInsert(StudentGroup sgvo){
		sqlSession.insert("studentGroupInsert", sgvo);
	}
	
}