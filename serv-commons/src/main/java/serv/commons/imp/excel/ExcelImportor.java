package serv.commons.imp.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import serv.commons.JsonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by shiyi on 2015/12/19.
 */
public class ExcelImportor {

    private List<Expression> expression;

    private ExcelImportor() {}

    public static ExcelImportor compile(String express) {
        ExcelImportor ei = new ExcelImportor();
        ei.expression = resolveExpression(express);
        return ei;
    }

    public static interface TreatAction {
        public void treat(int sheetIndex, String sheetName, Object...data);
    }

    public static void main(String[] arges) {
        File excel = new File("C:\\Users\\Administrator\\Downloads\\全省营业厅资料汇总（201512181846）.xlsx");
        ExcelImportor.compile("{index:0},{index:1},{name:mobile},{name:local_address},{name:local_gps},[name:兑换手机号]").excelImport(excel, new TreatAction() {
            @Override
            public void treat(int sheetIndex, String sheetName, Object...data) {
                System.out.println("" + sheetIndex + " : " + sheetName + " : " + JsonUtils.toJson(data));
            }
        });
    }
    
    public void excelImport(File excel, TreatAction action) {
        //
        try {
            @SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(new FileInputStream(excel));
            for (int sheetIndex=0; sheetIndex<workbook.getNumberOfSheets(); sheetIndex++) {
            	excelImport(excel, sheetIndex, action);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }

    @SuppressWarnings("unchecked")
	public void excelImport(File excel, int sheetIndex, TreatAction action) {
        //
        try {
            @SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(new FileInputStream(excel));
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            // 创建临时保存表
            List<List<Object>> tmpList = new ArrayList<List<Object>>();
            for (int e = 0; e < expression.size(); e++) {
                tmpList.add(new ArrayList<Object>());
            }
            //
            Row head = sheet.getRow(sheet.getFirstRowNum());
            for (int r = sheet.getFirstRowNum()+1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                //
                @SuppressWarnings("rawtypes")
				List lineTmpList = new ArrayList();     // 行数据暂存表
                //
                for (int c = 0; c < row.getLastCellNum(); c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        lineTmpList.add("");
                    } else {
	                    cell.setCellType(Cell.CELL_TYPE_STRING);
	                    // 遍历检查表达式
	                    for (int e = 0; e < expression.size(); e++) {
	                        Expression exp = expression.get(e);
	                        Cell heacCell = head.getCell(c);
	                        if (heacCell != null) {
		                        heacCell.setCellType(Cell.CELL_TYPE_STRING);
		                        if ((exp.searchType.equals(SearchType.index) && c == Integer.parseInt(exp.value))
		                                || (exp.searchType.equals(SearchType.name) && heacCell.getStringCellValue().equals(exp.value))) {
		                            //
		                            lineTmpList.add(cell.getStringCellValue());
		                        }
	                        }
	                    }
                    }
                }
                // 是否需要发送通知并清空数组
                boolean needEndLine = false;
                if (tmpList.get(0).isEmpty()) {
                    needEndLine = false;
                } else {
                    for (int e = 0; e < expression.size(); e++) {
                        if (!expression.get(e).isArray
                                && lineTmpList.get(e).toString().trim().length() > 0
                                && !tmpList.get(e).get(0).equals(lineTmpList.get(e))) {     // 非数组值不一致
                            needEndLine = true;
                        }
                    }
                }
                if (needEndLine) {
                    // 发送通知并清空数组
                    sendTreatNotice(sheetIndex, sheet.getSheetName(), action, tmpList);
                    for (@SuppressWarnings("rawtypes") List l : tmpList) {
                        l.clear();
                    }
                }
                // 行数据写入正式列表
                for (int e = 0; e < expression.size(); e++) {
                    tmpList.get(e).add(lineTmpList.get(e));
                }

            }
            sendTreatNotice(sheetIndex, sheet.getSheetName(), action, tmpList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendTreatNotice(int sheetIndex, String sheetName, TreatAction action, List<List<Object>> list) {
        // 发送通知并清空数组
        List<Object> nlist = new ArrayList<Object>();
        for (int en = 0; en < expression.size(); en++) {
            if (expression.get(en).isArray) {
                nlist.add(list.get(en));
            } else {
                nlist.add(list.get(en).get(0));
            }
        }
        action.treat(sheetIndex, sheetName, new ArrayList(nlist).toArray());
    }

    private static enum SearchType {
        index, name
    }

    private static class Expression {
        SearchType searchType;
        String value;
        boolean isArray;
    }

    private static List<Expression> resolveExpression(String expression) {
        Stack<String> stack = new Stack<String>();
        List<String> left = Arrays.asList(new String[] {"[", "{"});
        List<String> right = Arrays.asList(new String[] {"]", "}"});

        // 平衡检测
        for (int i=0; i<expression.length(); i++) {
            String c = ""+expression.charAt(i);
            if (left.contains(c)) {
                stack.push(c);
            } else if (right.contains(c)) {
                if (stack.peek().equals(left.get(right.indexOf(c)))) {
                    stack.pop();
                } else {
                    throw new RuntimeException("exception error");
                }

            } else {

            }
        }
        if (!stack.empty()) {
            throw new RuntimeException("exception error");
        }

        List<Expression> rs = new ArrayList<Expression>();

        // 有效字符检测
        for (String group : expression.split(",")) {
            if (
                group.length() <= 3 // 最小字符限制
                || !right.get(left.indexOf(group.substring(0, 1))).equals(group.substring(group.length()-1, group.length())) // 左右括号不平衡
                || group.split(":").length != 2 // 拆分字符错误（检索方式:检索值）
                || !Arrays.asList(new String[] {"index", "name"}).contains(group.substring(1, group.indexOf(":"))) // 检索方式枚举值错误（索引或名称）
                || (group.substring(1, group.indexOf(":")).equals("index") && !group.substring(group.indexOf(":")+1, group.length()-1).matches("\\d+")) // 索引值必须为数字
            ) {
                throw new RuntimeException("exception error");
            }

            Expression e = new Expression();
            e.searchType = SearchType.valueOf(group.substring(1, group.indexOf(":")));
            e.value = group.substring(group.indexOf(":")+1, group.length()-1);
            e.isArray = group.substring(0, 1).equals("[");
            rs.add(e);
        }
        return rs;
    }

}
