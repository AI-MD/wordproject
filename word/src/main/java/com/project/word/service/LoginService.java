package com.project.word.service;

import org.springframework.stereotype.Repository;

import com.project.word.vo.Admin;
import com.project.word.vo.Student;






@Repository
public interface LoginService {
	public boolean loginWithStudent(Student svo);
	public int loginWithAdmin(Admin avo);
	
	public void changeAdminPassword(Admin avo);
	
	public String sha256(String value);
}