package com.github.gserv.serv.commons.export.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel导出类
 * 
 * @author shiying
 *
 */
public class ExcelExportHelper {
	
	
	/**
	 * 创建Excel导出任务
	 * @return
	 */
	public static ExportTask createExcelExportTask() {
		return new ExportTask();
	}
	
	public static interface ExcelStyleFactory {
		HSSFCellStyle getHSSFCellStyle(HSSFWorkbook workbook);
	}
	

}
