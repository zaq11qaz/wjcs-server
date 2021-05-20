package com.huihe.eg.comm.util;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/5/8 15:19
 * @ Description：
 * @ since: JDk1.8
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * excle样式工具类
 */
public class ExcelFormatUtil {
    /**
     *  导出Excel
     * @param sheetName 表格 sheet 的名称
     * @param headers  标题名称
     * @param dataList 需要显示的数据集合
     * @param exportExcelName 导出excel文件的名字
     */
    public  static void exportExcel(String sheetName, List<Map<String, Object>> dataList,
                                    String[] headers,String exportExcelName) {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        // 生成表格中非标题栏的样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成表格中非标题栏的字体
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);


        // 设置表格标题栏的样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置标题栏字体
        XSSFFont titleFont = workbook.createFont();
        titleFont.setColor(HSSFColor.WHITE.index);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBold(true);
        // 把字体应用到当前的样式
        titleStyle.setFont(titleFont);

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> data = it.next();
            int i = 0;
            for(String key : data.keySet()){
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                XSSFRichTextString text = new XSSFRichTextString(data.get(key)+"");
                cell.setCellValue(text);
                i++;
            }
        }
        OutputStream out = null;
        try {
            String tmpPath = "D:\\文档\\excel\\" + exportExcelName + ".xlsx";
            out = new FileOutputStream(tmpPath);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<>();
        // 使用 LinkedHashMap 保证有序，即标题和数据对应上
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("id", 1);
        map1.put("name", "张三");
        map1.put("age", 23);
        map1.put("sex", "男");

        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("id", 2);
        map2.put("name", "李四");
        map2.put("age", 20);
        map2.put("sex", "女");

        Map<String, Object> map3 = new LinkedHashMap<>();
        map3.put("id", 3);
        map3.put("name", "王五");
        map3.put("age", 19);
        map3.put("sex", "男");

        Map<String, Object> map4 = new LinkedHashMap<>();
        map4.put("id", 4);
        map4.put("name", "赵六");
        map4.put("age", 18);
        map4.put("sex", "女");

        Map<String, Object> map5 = new LinkedHashMap<>();
        map5.put("id", 5);
        map5.put("name", "小七");
        map5.put("age", 22);
        map5.put("sex", "男");

        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);

        return data;
    }

    /**
     * 创建每个 sheet 页的数据
     */
    private static void createSheetData(HSSFWorkbook aWorkbook, String[] aTitles, String aSheetName,
                                        List<String[]> aRowData) {
        HSSFSheet tmpSheet = aWorkbook.createSheet(aSheetName);
        // 设置sheet的标题
        HSSFRow tmpTitileRow = tmpSheet.createRow(0);
        for (int i = 0; i < aTitles.length; i++) {
            tmpTitileRow.createCell(i).setCellValue(aTitles[i]);
        }
        // 遍历填充每行的数据
        HSSFRow tmpRow = null;
        int tmpRowNumber = 1;
        for (String[] rowData : aRowData) {
            tmpRow = tmpSheet.createRow(tmpRowNumber);
            for (int i = 0; i < rowData.length; i++) {
                tmpRow.createCell(i).setCellValue(rowData[i]);
            }
            tmpRowNumber++;
        }
    }

    /**
     * 设置响应头
     */
    private static void setResponseHeader(HttpServletResponse aResponse, String aFileName){
        try {
            try {
                aFileName = new String(aFileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            aResponse.setContentType("application/octet-stream;charset=ISO8859-1");
            aResponse.setHeader("Content-Disposition", "attachment;filename=" + aFileName);
            aResponse.addHeader("Pargam", "no-cache");
            aResponse.addHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过页面导出
     * @param response
     * @throws IOException
     */
    public static void export(HttpServletResponse response) throws IOException {
        HSSFWorkbook tmpWorkbook = new HSSFWorkbook();
        String[] tmpUserTitles = {"姓名", "性别", "年龄", "工作"};
        List<String[]> tmpUsers = getUsers();
        createSheetData(tmpWorkbook, tmpUserTitles, "用户信息", tmpUsers);
        setResponseHeader(response, "用户信息表.xls");
        OutputStream tmpOutputStream = response.getOutputStream();
        response.setContentType("application/msdownload");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + URLEncoder.encode("", "UTF-8"));

        tmpWorkbook.write(tmpOutputStream);
        tmpOutputStream.flush();
        tmpOutputStream.close();
    }

    /**
     * 导出，不通过页面导出
     */
    public void export() throws IOException {
        HSSFWorkbook tmpWorkbook = new HSSFWorkbook();
        String[] tmpUserTitles = {"姓名", "性别", "年龄", "工作"};
        List<String[]> tmpUsers = getUsers();
        createSheetData(tmpWorkbook, tmpUserTitles, "用户信息", tmpUsers);
        String[] tmpAddressTitles = {"城市", "区域"};
        List<String[]> getAddress = getAddress();
        createSheetData(tmpWorkbook, tmpAddressTitles, "地址信息", getAddress);
        OutputStream tmpOutputStream = new FileOutputStream("D:\\文档\\excel\\" + System.currentTimeMillis() + ".xls");
        tmpWorkbook.write(tmpOutputStream);
        tmpOutputStream.flush();
        tmpOutputStream.close();
    }

    /**
     * 测试数据
     */
    private List<String[]> getAddress(){
        List<String[]> address = new ArrayList<>();
        for(int i = 1; i <= 5; i++){
            String[] addr = new String[2];
            addr[0] = "四川";
            addr[1] = "高新 - " + i;
            address.add(addr);
        }
        return address;
    }
    /**
     * 测试数据
     */
    private static List<String[]> getUsers(){
        List<String[]> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String[] user = new String[4];
            user[0] = "zhangsan - " + i;
            user[1] = "男";
            user[2] = "2" + i;
            user[3] = "Java - " + i;
            users.add(user);
        }
        return users;
    }

}