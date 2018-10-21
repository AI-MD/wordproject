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
			// HSSFWorkbook�� �������� ��ü ������ ��� �ִ� ��ü
			workbook = new XSSFWorkbook(file.getInputStream());
			// Ž���� ����� Sheet, Row, Cell ��ü
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			Student student;

			// Sheet Ž�� for��
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// ���� sheet ��ȯ
				curSheet = workbook.getSheetAt(sheetIndex);
				// row Ž�� for��
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0�� ��������̱� ������ ����
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						student = new Student();
						String value;
						
						// row�� ù��° cell���� ������� �ʴ� ��츸 cellŽ��
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell Ž�� for��
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									if (true) {
										value = "";
										// cell ��Ÿ���� �ٸ����� String���� ��ȯ ����
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

										
										// ���� colum index�� ���� vo�Է�
										switch (cellIndex) {
										case 0: // �̸�
											student.setStName(value);
											break;
										case 1: // �й�
											student.setStNum( Integer.toString((int)Math.round(Double.parseDouble(value))) );
											break;
										default:
											break;
										}
									} // end if
								} // end for
									// cell Ž�� ���� vo �߰�
								list.add(student);
							} // end
						} // end if
						
					}
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ��� insert
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
		 * ArrayList errorList ���� 
		 */
		try {
			
			// HSSFWorkbook�� �������� ��ü ������ ��� �ִ� ��ü
			workbook = new XSSFWorkbook(file.getInputStream());

			// Ž���� ����� Sheet, Row, Cell ��ü
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			Word word;

			// Sheet Ž�� for��
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				
				// ���� sheet ��ȯ
				curSheet = workbook.getSheetAt(sheetIndex);
				
				// row Ž�� for��
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					
					
					
					// row 0�� ��������̱� ������ ����
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						word = new Word();
						String value;

						// row�� ù��° cell���� ������� �ʴ� ��츸 cellŽ��
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getNumericCellValue())) {
								// cell Ž�� for��
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										// cell ��Ÿ���� �ٸ����� String���� ��ȯ ����
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
										
											
										// ���� colum index�� ���� vo�Է�
										switch (cellIndex) {
										case 0: // �ܾ� �ѹ���
											
											word.setWoNo((int)Math.round(Double.parseDouble(value)));
											
											break;
										case 1: // �ܾ� ö�� 
											word.setWoWord(value);
											if(duplicateWordList.add(value)){
												
											}else{
												duplicatieCheck=false;
												errList.add(word);
											}
											
											break;
										case 2: // �ܾ� ��
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
									// cell Ž�� ���� vo �߰�
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
		// ��� insert
		if(duplicatieCheck){
			wordDao.insertWordExcel(list);
		}
		return errList;
	}
}
