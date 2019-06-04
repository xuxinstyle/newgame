package com.resource.reader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 14:57
 */
public class ReadXlsx {
   public static void readXlsx(String location, InputStream inputStream){
       //excel文件路径

       try {

           File excel = new File(location);
           if (excel.isFile() && excel.exists()) { //判断文件是否存在
               String[] split = excel.getName().split("\\.");
               // .是特殊字符，需要转义！！！！！
               Workbook wb;
               // 根据文件后缀（xls/xlsx）进行判断
               if ("xls".equals(split[1])) {
                   //FileInputStream fis = new FileInputStream(excel);
                   // 文件流对象
                   wb = new HSSFWorkbook(inputStream);
               } else if ("xlsx".equals(split[1])) {
                   //FileInputStream fis = new FileInputStream(excel);
                   wb = new XSSFWorkbook(inputStream);
               } else {
                   System.out.println("文件类型错误!");
                   return;
               } //开始解析
               Sheet sheet = wb.getSheetAt(0);
               //读取sheet 0
               int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
               int lastRowIndex = sheet.getLastRowNum();
               System.out.println("firstRowIndex: " + firstRowIndex);
               System.out.println("lastRowIndex: " + lastRowIndex);
               for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                   //遍历行
                   System.out.println("rIndex: " + rIndex);
                   Row row = sheet.getRow(rIndex);
                   if (row != null) {
                       int firstCellIndex = row.getFirstCellNum();
                       int lastCellIndex = row.getLastCellNum();
                       for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                           //遍历列
                           Cell cell = row.getCell(cIndex);
                           if (cell != null) {
                               System.out.println(cell.toString());
                           }
                       }
                   }
               }
           } else {
               System.out.println("找不到指定的文件");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
