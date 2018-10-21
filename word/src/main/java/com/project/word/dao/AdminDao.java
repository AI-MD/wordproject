package com.project.word.dao;


import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import com.project.word.vo.Admin;


@Repository
public class AdminDao {
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public Integer findByAdminCheck(Admin avo) {
		
		return sqlSession.selectOne(
				"findByADCheck",avo
				);
	}
	
	public Integer findByAdmin(Admin avo) {
		
		return sqlSession.selectOne(
				"findByAD",avo
				);
	}
	
	public void updateAdminFlag(String adNum){
		 sqlSession.update("updateADFlag",adNum);
	}
	
	
	public void updateAdminPasswd(Admin admin){
		 sqlSession.update("updateADPasswd",admin);
	}
}