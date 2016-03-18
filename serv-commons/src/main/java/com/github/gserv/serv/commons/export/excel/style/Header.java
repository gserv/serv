package com.github.gserv.serv.commons.export.excel.style;

import com.github.gserv.serv.commons.export.excel.ExcelExportHelper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class Header implements ExcelExportHelper.ExcelStyleFactory {

	@Override
	public HSSFCellStyle getHSSFCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);		// 填充
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		// 居中
		//
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 16); // 字体高度
		font.setFontName("黑体"); // 字体
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		return style;
	}

}
