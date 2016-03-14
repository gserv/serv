package serv.commons.export.excel;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestExcelExportHelper {
	
	
	public static void main(String[] arges) throws IOException {
		ExportTask task = ExcelExportHelper.createExcelExportTask();
		ExportTask.Sheet sheet = task.appendSheet("默认");
		//
		sheet.appendRow().appendCell("ddddddddddddddddddddddd").setStyle("header").setColumnWidthAuto();
		//
		ExportTask.Row row = sheet.appendRow();
		row.appendCell().setValue(new Date()).setStyle("normal").setColumnWidthAuto();
		row.appendCell().setValue("fffffffdadfddddddddddddddd").setStyle("normal").setColumnWidthAuto();
		//
		File file = task.export();
		System.out.println(file);
	}

}
