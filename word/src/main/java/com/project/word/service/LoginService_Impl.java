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
		 * �л�����
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
		 * ������ ����
		 */
		if(adminDao.findByAdmin(avo)==1){// ������ ù �α��ν� 
			return 2;
		}else{
			avo.setAdPw(sha256(avo.getAdPw())); //��й�ȣ ��ȣȭ�Ͽ� ����
			return adminDao.findByAdminCheck(avo);
		}
		
	}

	@Override
	public void changeAdminPassword(Admin avo) {
		/* 
		 * ��й�ȣ����
		 */
		avo.setAdPw(sha256(avo.getAdPw())); //��й�ȣ ��ȣȭ
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