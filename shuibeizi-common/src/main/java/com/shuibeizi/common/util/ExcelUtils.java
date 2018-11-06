package com.shuibeizi.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel操作Utils
 * @author:wanqing
 * @date:2017/2/27 11:09
 */
public class ExcelUtils {

    private ExcelUtils(){}

    private static class InstanceObj{
        private static final ExcelUtils instance = new ExcelUtils();
    }

    public static ExcelUtils getInstance(){
        return InstanceObj.instance;
    }

    /**
     * 获取批量导入员工的模版文件
     * @return
     * @throws Exception
     */
    public void getUserUploadModel(OutputStream outputStream) throws Exception{
        final String excelPath = new ExcelUtils().getClass().getResource("/").getPath() + "/config/source/excel-upload.xls";
        String suffix = excelPath.substring(excelPath.lastIndexOf(".") + 1);
        Workbook workbook = null;
        File file = new File(excelPath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        if(suffix.equals("xls")){
            workbook = new HSSFWorkbook(bis);
        }else if(suffix.equals("xlsx")){
            workbook = new XSSFWorkbook(bis);
        }
        workbook.write(outputStream);
    }

    /**
     * 读取Excel文件
     * @param fileName
     * @param inputStream
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> readerExcel(String fileName, InputStream inputStream) throws Exception{
        ExcelUtils excelUtils = new ExcelUtils();
        if(null == fileName || null == inputStream) return null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Workbook workbook = null;
        if(suffix.equals("xls")){
            workbook = new HSSFWorkbook(inputStream);
        }else if(suffix.equals("xlsx")){
            workbook = new XSSFWorkbook(inputStream);
        }
        List<String> fieldList = excelUtils.userUploadField();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        int rowNum = sheet.getFirstRowNum();
        for (int i = 1; i <= rowNum; i++){
            row = sheet.getRow(i);
            Map<String, Object> sourceMap = new HashMap<String, Object>();
            for (int j = 0; j < row.getLastCellNum(); i++){
                Cell cell = row.getCell(j);
                sourceMap.put(fieldList.get(j), cell.getStringCellValue());
            }
            resultList.add(sourceMap);
        }
        return resultList;
    }

    /**
     * 读取Excel文件.带所在行
     * @param fileName
     * @param inputStream
     * @return
     * @throws Exception
     */
    public Map<Integer, Map<String, Object>> readerExcelAndRow(String fileName, InputStream inputStream) throws Exception{
        ExcelUtils excelUtils = new ExcelUtils();
        if(null == fileName || null == inputStream) return null;
        Map<Integer, Map<String, Object>> resultMap = new HashMap<Integer, Map<String, Object>>();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Workbook workbook = null;
        if(suffix.equals("xls")){
            workbook = new HSSFWorkbook(inputStream);
        }else if(suffix.equals("xlsx")){
            workbook = new XSSFWorkbook(inputStream);
        }
        List<String> fieldList = excelUtils.userUploadField();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        int rowNum = sheet.getLastRowNum();
        Object cellValue = null;
        for (int i = 1; i <= rowNum; i++){
            row = sheet.getRow(i);
            Map<String, Object> sourceMap = new HashMap<String, Object>();
            for (int j = 0; j < row.getLastCellNum(); j++){
                Cell cell = row.getCell(j);
                if(null == cell) continue;
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA://暂不支持公式
                    case Cell.CELL_TYPE_NUMERIC:
                        if(DateUtil.isCellDateFormatted(cell)){
                            cellValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                        }else {
                            cellValue = new DecimalFormat().format(cell.getNumericCellValue()).replace(",","");
                        }
                        break;
                    default:
                        //暂不支持公式boolean error
                        cellValue = null;
                        break;
                }
                sourceMap.put(fieldList.get(j), cellValue);
            }
            resultMap.put(i, sourceMap);
        }
        return resultMap;
    }

    /**
     * 读取Excel文件
     * @param <T>
     * @param fileName
     * @param inputStream
     * @return
     * @throws Exception
     */
    public <T> List<T> readerExcel(String fileName, InputStream inputStream, Class<T> tagClass) throws Exception{
        List<Map<String, Object>> resultList = this.readerExcel(fileName, inputStream);
        List<T> tagList = new ArrayList<T>();
        for (Map<String, Object> sourceMap : resultList){
            T tag = tagClass.newInstance();
            TransformMapEntity.mapToEntity(sourceMap, tagClass);
            tagList.add(tag);
        }
        return tagList;
    }

    /**
     * 获取员工导入的字段
     * @return
     */
    private List<String> userUploadField(){
        List<String> fieldList= new ArrayList<String>();
        String fieldSource = PropertiesUtils.getSourceProperties("userUploadField");
        String[] fieldArray = fieldSource.split(",");
        for (String field : fieldArray){
            fieldList.add(field);
        }
        return fieldList;
    }
}
