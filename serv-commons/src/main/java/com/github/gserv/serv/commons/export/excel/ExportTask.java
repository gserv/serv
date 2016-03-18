package com.github.gserv.serv.commons.export.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.github.gserv.serv.commons.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportTask {
	
	/**
	 * 文档引用
	 */
	private HSSFWorkbook workbook = new HSSFWorkbook();
	
	/**
	 * Sheet包装类
	 * @author shiying
	 *
	 */
	public class Sheet {
		private HSSFSheet sheet = null;
		
		private int rowNum = 0;
		
		/**
		 * 添加Row
		 * @param name
		 * @return
		 */
		public ExportTask.Row appendRow() {
			int index = rowNum++;
			HSSFRow row = sheet.createRow(index);
			ExportTask.Row exportRow = new Row(this, index);
			exportRow.setRow(row);
			return exportRow;
		}
		
		public ExportTask.Sheet setStyleDefaultRowHeightInPoints(int height) {
			sheet.setDefaultRowHeightInPoints(height);
			return this;
		}
		
		public ExportTask.Sheet setStyleDefaultColumnWidth(int width) {
			sheet.setDefaultColumnWidth(10);
			return this;
		}

		public HSSFSheet getSheet() {
			return sheet;
		}
		public void setSheet(HSSFSheet sheet) {
			this.sheet = sheet;
		}
		public int getRowNum() {
			return rowNum;
		}
		public void setRowNum(int rowNum) {
			this.rowNum = rowNum;
		}
	}
	
	/**
	 * Row包装类
	 * @author shiying
	 *
	 */
	public class Row {
		private Sheet refSheet = null;
		private HSSFRow row = null;
		private int rowIndex = -1;
		
		public Row(Sheet refSheet, int rowIndex) {
			this.refSheet = refSheet;
			this.rowIndex = rowIndex;
		}
		
		/**
		 * 添加Row
		 * @param name
		 * @return
		 */
		public ExportTask.Cell appendCell() {
			int index = row.getLastCellNum();
			index = index < 0 ? 0 : index;
			HSSFCell cell = row.createCell(index);
			ExportTask.Cell exportCell = new Cell(this, index);
			exportCell.setCell(cell);
			return exportCell;
		}
		
		public Row setRowHeight(int height) {
			row.setHeight((short) height);
			return this;
		}

		public ExportTask.Cell appendCell(Object val) {
			if (val == null) {
				val = "";
			}
			if (val instanceof Date) {
				return appendCell().setValue((Date) val);
			} else if (val instanceof Long) {
				return appendCell().setValue((Long) val);
			} else if (val instanceof Integer) {
				return appendCell().setValue(((Integer) val).longValue());
			} else if (val instanceof Double) {
				return appendCell().setValue((Double) val);
			} else {
				return appendCell().setValue(val.toString());
			}
		}
		
		public HSSFRow getRow() {
			return row;
		}
		public void setRow(HSSFRow row) {
			this.row = row;
		}
		public Sheet getRefSheet() {
			return refSheet;
		}
		public void setRefSheet(Sheet refSheet) {
			this.refSheet = refSheet;
		}
		public int getRowIndex() {
			return rowIndex;
		}
		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}
	}
	
	
	public enum DateType {
		text, date, number, decimal2, percent2
	}
	
	
	public class Cell {
		private Row refRow = null;
		private HSSFCell cell = null;
		private int colIndex = -1;
		private DateType dateType = DateType.text;
		
		public Cell(Row refRow, int colIndex) {
			this.refRow = refRow;
			this.colIndex = colIndex;
		}
		
		private void setDateFormat(HSSFCellStyle style, DateType dateType) {
			switch (dateType) {
			case date :
				style.setDataFormat(workbook.createDataFormat().getFormat("yyyy年m月d日"));
				break;
			case number :
				style.setDataFormat(workbook.createDataFormat().getFormat("0"));
				break;
			case decimal2 :
				style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
				break;
			case percent2 :
				style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
				break;
			default:
				style.setDataFormat(workbook.createDataFormat().getFormat("@"));
				break;
			}
		}
		
		public Cell setValue(String val) {
			cell.setCellValue(val);
			dateType = DateType.text;
			setDateFormat(cell.getCellStyle(), dateType);
			return this;
		}
		
		public Cell setValue(Double val) {
			cell.setCellValue(val);
			dateType = DateType.decimal2;
			setDateFormat(cell.getCellStyle(), dateType);
			return this;
		}
		
		public Cell setValue(Long val) {
			cell.setCellValue(val);
			dateType = DateType.number;
			setDateFormat(cell.getCellStyle(), dateType);
			return this;
		}
		
		public Cell setValue(Date val) {
			cell.setCellValue(val);
			dateType = DateType.date;
			setDateFormat(cell.getCellStyle(), dateType);
			return this;
		}
		
		public Cell setColumnWidth(int width) {
			refRow.getRefSheet().getSheet().setColumnWidth(colIndex, width);
			return this;
		}
		
		public Cell setColumnWidthAuto() {
			refRow.getRefSheet().getSheet().autoSizeColumn(colIndex);
			return this;
		}
		
		public Cell setStyle(String name) {
			if (name.indexOf(".") == -1) {
				name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
				name = "com.github.gserv.serv.commons.export.excel.style." + name;
			}
			try {
				ExcelExportHelper.ExcelStyleFactory factory = (ExcelExportHelper.ExcelStyleFactory) Class.forName(name).newInstance();
				HSSFCellStyle style = factory.getHSSFCellStyle(workbook);
				setDateFormat(style, dateType);
				cell.setCellStyle(style);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this;
		}

		public HSSFCell getCell() {
			return cell;
		}

		public void setCell(HSSFCell cell) {
			this.cell = cell;
		}
		public Row getRefRow() {
			return refRow;
		}
		public void setRefRow(Row refRow) {
			this.refRow = refRow;
		}
		public int getColIndex() {
			return colIndex;
		}
		public void setColIndex(int colIndex) {
			this.colIndex = colIndex;
		}
		public DateType getDateType() {
			return dateType;
		}
		public void setDateType(DateType dateType) {
			this.dateType = dateType;
		}
	}
	
	
	/**
	 * 添加Sheet
	 * @param name
	 * @return
	 */
	public ExportTask.Sheet appendSheet(String name) {
		HSSFSheet sheet = workbook.createSheet(name);
		//
		Sheet s = new Sheet();
		s.setSheet(sheet);
		return s;
	}
	
	
	
	
	/**
	 * 导出文件
	 * @return
	 * @throws IOException
	 */
	public File export() throws IOException {
		File file = FileUtils.createTempFile("export_excel.xls");
		FileOutputStream os = new FileOutputStream(file);
	    try {
	    	workbook.write(os);
		    return file;
	    } finally {
	    	os.close();
	    	workbook.close();
	    }
	}
	
	
	

}
