package com.insight.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.insight.axiswevservice.pojo.AllForExport;

public class CSVUtils {
	/**
	 * 导出
	 * 
	 * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList 数据
	 * @return
	 */
	public static boolean exportCsv(File file, List headList, List<String> dataList) {
		boolean isSucess = false;

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			if (headList.size() > 0) {
				// String [] headLists=headList.get(0).toString().split(",");
				for (int i = 0; i < headList.size(); i++) {
					if (i < headList.size() - 1) {
						bw.write(headList.get(i).toString() + ";");
					} else {
						bw.write(headList.get(i).toString());
					}
				}
				bw.newLine();
			}
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r");
				}
			}
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSucess;
	}

	/**
	 * 导入
	 * 
	 * @param file csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataList;
	}

	/**
	 * 
	 * @Title: getCellStyle
	 * @Description: TODO（设置表头样式）
	 * @param wb
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
		// style.setWrapText(true);//设置自动换行
		style.setFont(font);
		return style;
	}
	
	public static void writeAllInExcel(File file, List<String> headList,List<AllForExport> allForExportList,  String[] cols) {
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle style = getCellStyle(wb);
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		
		/**
		 * 写表头
		 */
		for (int i = 0; i < headList.size(); i++) {
			Cell headCell = row.createCell(i);
			headCell.setCellType(Cell.CELL_TYPE_STRING);// 设置这个单元格的数据的类型,是文本类型还是数字类型
			headCell.setCellStyle(style);// 设置表头样式
			headCell.setCellValue(String.valueOf(headList.get(i)));// 给这个单元格设置值
		}
		
		/**
		 * 写表中
		 */
		for (int i = 0; i < allForExportList.size(); i++) {
			Row rowdata = sheet.createRow(i + 1);// 创建数据行
			AllForExport allForExport = allForExportList.get(i);
			String tempData = "";
			for (int j = 0; j < cols.length; j++) {
				switch (cols[j]) {
					case "department" : tempData = allForExport.getDepartment();break;
					case "numberPatient" : tempData = allForExport.getNumberPatient();break;
					case "onsetOfNumberPatient" : tempData = allForExport.getOnsetOfNumberPatient();break;
					case "vteRiskAssessmentPatient" : tempData = allForExport.getVteRiskAssessmentPatient();break;
					case "vteRiskAssessmentPatientSum" : tempData = allForExport.getVteRiskAssessmentPatientSum();break;
					case "bleedingRiskAssessmentPatient" : tempData = allForExport.getBleedingRiskAssessmentPatient();break;
					case "bleedingSum" : tempData = allForExport.getBleedingSum();break;
					case "capriniMiddleRisk" : tempData = allForExport.getCapriniMiddleRisk();break;
					case "capriniHighRisk" : tempData = allForExport.getCapriniHighRisk();break;
					case "paduaHighRisk" : tempData = allForExport.getPaduaHighRisk();break;
					case "medicinePreventiveRate" : tempData = allForExport.getMedicinePreventiveRate();break;
					case "mechanicalPreventiveRate" : tempData = allForExport.getMechanicalPreventiveRate();break;
					case "middleHighRiskRate" : tempData = allForExport.getMiddleHighRiskRate();break;
					case "oneDayVteRiskAssessmentRate" : tempData = allForExport.getOneDayVteRiskAssessmentRate();break;
					case "recentlyBleedingRate" : tempData = allForExport.getRecentlyBleedingRate();break;
					case "oneDayBleedingRate" : tempData = allForExport.getOneDayBleedingRate();break;
					case "countDP" : tempData = allForExport.getCountDP();break;
					case "countD" : tempData = allForExport.getCountD();break;
					case "countP" : tempData = allForExport.getCountP();break;
					default: tempData = "";
				}
				Cell celldata = rowdata.createCell(j);// 在一行中创建某列..
				celldata.setCellType(Cell.CELL_TYPE_STRING);
				celldata.setCellValue(tempData);
			}
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				wb.write(os);
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 写Excel
	 * 
	 * @param file  目标文件
	 * @param heads 头
	 * @param datas 数据
	 */
	public static void writeExcel(File file, List<String> headList, List<Map> dataList, String[] cols) {
		// 创建一个新的excel的文档对象
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		// 设置表头样式
		CellStyle style = getCellStyle(wb);
		// 在文档对象中创建一个表单..默认是表单名字是Sheet0、Sheet1....
		Sheet sheet = wb.createSheet();
		/**
		 * 设置Excel表的第一行即表头
		 */
		Row row = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			Cell headCell = row.createCell(i);
			headCell.setCellType(Cell.CELL_TYPE_STRING);// 设置这个单元格的数据的类型,是文本类型还是数字类型
			headCell.setCellStyle(style);// 设置表头样式
			headCell.setCellValue(String.valueOf(headList.get(i)));// 给这个单元格设置值
		}
		/**
		 * 写数据
		 */
		for (int i = 0; i < dataList.size(); i++) {
			Row rowdata = sheet.createRow(i + 1);// 创建数据行
			Map mapdata = dataList.get(i);
			for (int j = 0; j < cols.length; j++) {
//			Iterator it = mapdata.keySet().iterator();
//			int j = 0;
//			while (it.hasNext()) {
				String strdata = String.valueOf(mapdata.get(cols[j]) == null ? "" : mapdata.get(cols[j]));
				Cell celldata = rowdata.createCell(j);// 在一行中创建某列..
				celldata.setCellType(Cell.CELL_TYPE_STRING);
				celldata.setCellValue(strdata);
//				j++;
//			}
			}
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				wb.write(os);
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
