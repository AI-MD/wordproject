package com.project.word.service;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.word.vo.Word;
@Repository
public interface  ExcelUploadService {
	public void studentListExcelUpload(MultipartHttpServletRequest req);
	public ArrayList<Word> wordListExcelUpload(MultipartHttpServletRequest req);
}
