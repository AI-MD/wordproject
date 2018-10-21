package com.project.word.service;





import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.word.dao.AdminDao;
import com.project.word.dao.StudentDao;
import com.project.word.vo.Admin;
import com.project.word.vo.Student;



@Repository
public class LoginService_Impl implements LoginService {
	@Autowired
	public StudentDao studentDao;

	@Autowired
	public AdminDao adminDao;

	@Override
	public boolean loginWithStudent(Student svo) {
		/*
		 * 학생안증
		 */
		 int cnt=studentDao.findByStudentCheck(svo);
		 if(cnt==1){
			 return true;
		 }else{
			 return false;
		 }
	}

	@Override
	public int loginWithAdmin(Admin avo) {
		/*
		 * 관리자 인증
		 */
		if(adminDao.findByAdmin(avo)==1){// 관리자 첫 로그인시 
			return 2;
		}else{
			avo.setAdPw(sha256(avo.getAdPw())); //비밀번호 암호화하여 셋팅
			return adminDao.findByAdminCheck(avo);
		}
		
	}

	@Override
	public void changeAdminPassword(Admin avo) {
		/* 
		 * 비밀번호변경
		 */
		avo.setAdPw(sha256(avo.getAdPw())); //비밀번호 암호화
		adminDao.updateAdminPasswd(avo);
		adminDao.updateAdminFlag(avo.getAdNum());
	}

	@Override
	public String sha256(String str) {

		String SHA = ""; 

		try{

			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 

			sh.update(str.getBytes()); 

			byte byteData[] = sh.digest();

			StringBuffer sb = new StringBuffer(); 

			for(int i = 0 ; i < byteData.length ; i++){

				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

			}

			SHA = sb.toString();

			

		}catch(NoSuchAlgorithmException e){

			e.printStackTrace(); 

			SHA = null; 

		}

		return SHA;
	}

	
	
	
}