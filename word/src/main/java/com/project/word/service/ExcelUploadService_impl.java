package com.project.word.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.word.dao.StudentDao;
import com.project.word.dao.WordDao;
import com.project.word.vo.Student;
import com.project.word.vo.Word;

@Repository
public class ExcelUploadService_impl implements ExcelUploadService {
	@Autowired
	StudentDao studentDao;

	@Autowired
	WordDao wordDao;
	
	@Override
	public void studentListExcelUpload(MultipartHttpServletRequest req) {
		List list = new ArrayList();
		MultipartFile file = req.getFile("excel");
		XSSFWorkbook workbook = null;

		try {
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new XSSFWorkbook(file.getInputStream());
			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			Student student;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						student = new Student();
						String value;
						
						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									if (true) {
										value = "";
										// cell 스타일이 다르더라도 String으로 반환 받음
										switch (curCell.getCellType()) {
										case HSSFCell.CELL_TYPE_FORMULA:
											value = curCell.getCellFormula();
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											value = NumberToTextConverter.toText(curCell.getNumericCellValue());
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = curCell.getStringCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = curCell.getBooleanCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = curCell.getErrorCellValue() + "";
											break;
										default:
											value = new String();
											break;
										} // end switch

										
										// 현재 colum index에 따라서 vo입력
										switch (cellIndex) {
										case 0: // 이름
											student.setStName(value);
											break;
										case 1: // 학번
											student.setStNum( Integer.toString((int)Math.round(Double.parseDouble(value))) );
											break;
										default:
											break;
										}
									} // end if
								} // end for
									// cell 탐색 이후 vo 추가
								list.add(student);
							} // end
						} // end if
						
					}
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 디비에 insert
		studentDao.insertStudentExcel(list);
	}
	
	
	@Override
	public ArrayList<Word> wordListExcelUpload(MultipartHttpServletRequest req) {
		List list = new ArrayList();
		MultipartFile file = req.getFile("excel_word");
		XSSFWorkbook workbook = null;
		ArrayList<Word> errList=new ArrayList<Word>();
		boolean check =true;
		boolean duplicatieCheck=true;
		HashSet<String> duplicateWordList=new HashSet();
		/*
		 * ArrayList errorList 생성 
		 */
		try {
			
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new XSSFWorkbook(file.getInputStream());

			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			Word word;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					
					
					
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						word = new Word();
						String value;

						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getNumericCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										// cell 스타일이 다르더라도 String으로 반환 받음
										switch (curCell.getCellType()) {
										case HSSFCell.CELL_TYPE_FORMULA:
											value = curCell.getCellFormula();
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											value = curCell.getNumericCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = curCell.getStringCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = curCell.getBooleanCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = curCell.getErrorCellValue() + "";
											break;
										default:
											value = new String();
											break;
										} // end switch
										
											
										// 현재 colum index에 따라서 vo입력
										switch (cellIndex) {
										case 0: // 단어 넘버링
											
											word.setWoNo((int)Math.round(Double.parseDouble(value)));
											
											break;
										case 1: // 단어 철자 
											word.setWoWord(value);
											if(duplicateWordList.add(value)){
												
											}else{
												duplicatieCheck=false;
												errList.add(word);
											}
											
											break;
										case 2: // 단어 뜻
											int count =0;
											char[] valuearr= value.toCharArray();
											
											for(int i=0;i<valuearr.length;i++){
												if(valuearr[i]==','){
													count++;
												}
											}
											String[] strarr=value.split(",");
											
											if(strarr.length<=count){
												check=false;
											}
											if(duplicatieCheck){
												word.setWoMeaning(value);
											}
											break;
										default:
											break;
										}
									} // end if
								} // end for
									// cell 탐색 이후 vo 추가
								if(check){
									
									list.add(word);
								}else{
									errList.add(word);
									check=true;
								}
							} // end
						} // end if
						
					}
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 디비에 insert
		if(duplicatieCheck){
			wordDao.insertWordExcel(list);
		}
		return errList;
	}
}
