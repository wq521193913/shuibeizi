package com.broker.generator;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocument;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class GeneratedUtils {

    private DataTableAccess dataTableAccess;
    private String fileRoot = System.getProperty("user.dir")+"\\code-generator/source_file/";
    private String templateFilePath = System.getProperty("user.dir")+"\\code-generator/";
    private String codeFilePath = "";
    private String className = "";
    private String livingExample = "";
    private String columnsString = "";
    private String valuesString = "";
    private String packageName = "com.shuibeizi";
    private List<TableColumnInfo> tableColumnInfos;

    public GeneratedUtils(){
        this.dataTableAccess = new DataTableAccess();
    }

    public static void main(String[] args){
        GeneratedUtils generatedUtil = new GeneratedUtils();
//        String table = "accept_base,accept_inform,accept_item,app_module,business_visit,company,company_register,decoration,decoration_contract"+
//                ",decoration_order,decoration_own,delay_inform,dept,design_contract,earnest,flow_dept,img_path,img_source,measure,menu"+
//                ",model_detail,owner,project_progress,project_schedule,reformation,repeal_order,role,role_app_module,role_menu"+
//                ",schedule_detail,schedule_item,schedule_model,sys_type,user,user_register,flow_user";
        String table = "sys_user";
        generatedUtil.generateCode(table);
        System.out.println("generate code complete...");
    }

    public void generateCode(String tables){
        String[] tableArray = tables.split(",");
        for(String table : tableArray){
            this.className = this.generateClassName(table);
            this.livingExample = this.className.substring(0,1).toLowerCase() + this.className.substring(1);
            this.createDirector(table);

            File[] files = new File(this.templateFilePath + "/template").listFiles();
            String fileName = "";
            String codeFileName = "";

            Writer writer = null;
            BufferedWriter bw = null;
            Reader reader = null;
            BufferedReader br = null;
            File codeFile = null;
            String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            for(File file : files){
                fileName = file.getName();
                codeFileName = fileName.replace("Template", this.className);
                try {

                    reader = new FileReader(file);
                    br = new BufferedReader(reader);

                    codeFile = new File(this.codeFilePath + "/" + codeFileName);
                    if(codeFile.exists()){
                        codeFile.delete();
                    }
                    codeFile.createNewFile();
                    writer = new FileWriter(codeFile);
                    bw = new BufferedWriter(writer);
                    String codeLine = "";
                    while (br.ready()){
                        codeLine = br.readLine();
                        if(codeLine.indexOf("Template") >= 0){
                            codeLine = codeLine.replace("Template",this.className);
                        }

                        if(codeLine.indexOf("template") >= 0){
                            codeLine = codeLine.replace("template",this.livingExample);
                        }

                        if(codeLine.indexOf("#today") >= 0){
                            codeLine = codeLine.replace("#today",createDate);
                        }
                        bw.write(codeLine);
                        bw.write("\r\n");
                    }
                    br.close();
                    bw.flush();
                    bw.close();
                    reader.close();
                    writer.close();
                    codeFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            this.generateDomain(table);
//            this.generateController(table);
//            this.generateIService(table);
//            this.generateService(table);
//            this.generateDao(table);
            this.generateMapperXml(table);
        }
    }

    private synchronized void createDirector(String table) {
        this.codeFilePath = this.fileRoot + table;
        File file = new File(this.codeFilePath);
        if(file.exists()){
            file.delete();
        }
        file.mkdirs();
    }

    public void generateController(String table){
        this.generateLogicCode(table, "Controller", null);
    }

    public void generateIService(String table){
        this.generateLogicCode(table, "Service", "I");
    }

    public void generateService(String table){
        this.generateLogicCode(table, "ServiceImpl", null);
    }

    public void generateDao(String table){
            this.generateLogicCode(table, "Dao", null);
    }

    private void generateMapperXml(String table) {
        try {
            Document document = new DefaultDocument();
            document.setXMLEncoding("UTF-8");
            DocumentType documentType = new DOMDocumentType();
            documentType.setName("mapper");
            documentType.setPublicID("-//mybatis.org//DTD Mapper 3.0//EN");
            documentType.setSystemID("http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            document.setDocType(documentType);
            Element root = new DOMElement("mapper");
            root.addAttribute("namespace", packageName + ".dao." + this.className + "Dao");
            document.setRootElement(root);
            DOMElement insertEl = new DOMElement("insert");
            insertEl.addAttribute("id","insert"+this.className);
            insertEl.addAttribute("parameterType",packageName + ".domain."+this.className);
            insertEl.setText("INSERT INTO " + table +"(" + this.columnsString + ") \r\n VALUES (" + this.valuesString + ")");
            DOMElement idEl = new DOMElement("selectKey");
            idEl.addAttribute("resultType","java.lang.Integer");
            idEl.addAttribute("keyProperty", "uid");
            idEl.addAttribute("order", "AFTER");
            idEl.setText("SELECT LAST_INSERT_ID() ");
            insertEl.add(idEl);
            root.add(insertEl);
            DOMElement updateEl = new DOMElement("update");
            updateEl.addAttribute("id", "update" + this.className + "ById");
            updateEl.addAttribute("parameterType", packageName + ".domain."+this.className);
            updateEl.setText("UPDATE " + table);
            DOMElement setEl = new DOMElement("set");
            String columnName = "";
            for(TableColumnInfo tableColumnInfo : this.tableColumnInfos){
                columnName = getColumn(tableColumnInfo.getColumnName());
                if(columnName.equals("uid")) continue;
                DOMElement ifEl = new DOMElement("if");
                ifEl.addAttribute("test", columnName + "!=null");
                ifEl.setText(tableColumnInfo.getColumnName() + "=#{" + columnName + "},");
                setEl.add(ifEl);
            }
            updateEl.add(setEl);
            updateEl.addText("where uid = #{uid}");
            root.add(updateEl);

            DOMElement deleteEl = new DOMElement("delete");
            deleteEl.addAttribute("id", "delete" + this.className);
            deleteEl.addAttribute("parameterType", "java.lang.Integer");
            deleteEl.setText("DELETE FROM " + table + " WHERE uid = #{uid}");
            root.add(deleteEl);

            DOMElement querySingleEl = new DOMElement("select");
            querySingleEl.addAttribute("id","query" + this.className + "ById");
            querySingleEl.addAttribute("parameterType", "java.lang.Integer");
            querySingleEl.addAttribute("resultType", packageName + ".domain." + this.className);
            querySingleEl.setText("SELECT * FROM " + table + " WHERE uid = #{uid}");
            root.add(querySingleEl);

            DOMElement queryListEl = new DOMElement("select");
            queryListEl.addAttribute("id","query" + this.className + "List");
            queryListEl.addAttribute("parameterType", "java.util.Map");
            queryListEl.addAttribute("resultType", packageName + ".domain."+this.className);
            queryListEl.setText("SELECT * FROM " + table + "");
            DOMElement whereEl = new DOMElement("where");
            for(TableColumnInfo tableColumnInfo : this.tableColumnInfos){
                columnName = getColumn(tableColumnInfo.getColumnName());
                DOMElement ifEl = new DOMElement("if");
                ifEl.addAttribute("test", columnName + "!=null");
                ifEl.setText(" AND "+tableColumnInfo.getColumnName() + "=#{" + columnName + "}");
                whereEl.add(ifEl);
            }
            queryListEl.add(whereEl);
            root.add(queryListEl);

            File file = new File(this.codeFilePath+"/"+this.className+".xml");
            if(file.exists()){
                file.delete();
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setNewlines(true); //设置是否换行
            FileWriter fw = new FileWriter(file);
            XMLWriter xmlWriter = new XMLWriter(fw, format);
            xmlWriter.setEscapeText(false);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getColumn(String column){
        StringBuilder renCol = new StringBuilder();
        if(column.indexOf("_") > 0){
            int index = 0;
            String[] columnArr = column.split("_");
            for(String col : columnArr){
                if(index == 0){
                    renCol.append(col);
                }else {
                    renCol.append(col.substring(0,1).toUpperCase() + col.substring(1));
                }
                index++;
            }
        }else {
            renCol.append(column);
        }
        return renCol.toString();
    }

    /**
     * 逻辑代码
     * @param
     * @return
     * @author: Administrator
     * @date: 2016/12/31 15:26
     */
    private void generateLogicCode(String table,String type, String prefix){
        Writer writer = null;
        BufferedWriter bw = null;
        Reader reader = null;
        BufferedReader br = null;
        try {
            String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            File templateFile;
            if(null == prefix){
                templateFile = new File(this.templateFilePath + "template/Template" + type + ".java");
            }else {
                templateFile = new File(this.templateFilePath + "template/" + prefix + "Template" + type + ".java");
            }

            reader = new FileReader(templateFile);
            br = new BufferedReader(reader);
            File codeFile;
            if(null != prefix){
                codeFile = new File(this.codeFilePath+"/" + prefix + this.className + type + ".java");
            }else {
                codeFile = new File(this.codeFilePath+"/" + this.className + type + ".java");
            }

            if(codeFile.exists()){
                codeFile.delete();
            }
            writer = new FileWriter(codeFile);
            bw = new BufferedWriter(writer);
            String codeLine = "";
            while (br.ready()){
                codeLine = br.readLine();
                if(codeLine.indexOf("Template") >= 0){
                    codeLine = codeLine.replace("Template",this.className);
                }

                if(codeLine.indexOf("template") >= 0){
                    codeLine = codeLine.replace("template",this.livingExample);
                }

                if(codeLine.indexOf("#today") >= 0){
                    codeLine = codeLine.replace("#today",createDate);
                }
                bw.write(codeLine);
                bw.write("\r\n");
            }
            br.close();
            bw.flush();
            bw.close();
            reader.close();
            writer.close();
        }catch (Exception e){
            try {
                if(null != bw) bw.close();
                if(null != br) br.close();
                if(null != writer) writer.close();
                if(null != reader) reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * domain
     * @param
     * @return
     * @author: Administrator
     * @date: 2016/12/31 15:26
     */
    public void generateDomain(String table){
        FileWriter fw = null;
        this.columnsString = "";
        this.valuesString = "";
        try {
            this.tableColumnInfos = this.dataTableAccess.getTableColumn(table);
            String fieldStr = "";
            String fieldComment = "";
            StringBuffer codeStr = new StringBuffer();
            codeStr.append("package " + packageName + ".domain;");
            codeStr.append("\r\n");
            codeStr.append("/** \r\n" +
                    "* @author: Administrator \r\n" +
                    "* @description:" +
                    "* @date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n" +
                    "*/ \r\n"
            );
            codeStr.append("public class "+this.className +"{ \r\n");
            StringBuffer fileGetAndSetCode = new StringBuffer();
            String fieldName = "";
            String getAndSetName = "";
            String fieldType = "";
            String valueColumn = "";
            for(TableColumnInfo tableColumnInfo : this.tableColumnInfos){
                fieldComment = this.generateFieldComment(tableColumnInfo);
                fieldName = this.generateFieldName(tableColumnInfo.getColumnName());
                fieldType = this.getFileType(tableColumnInfo.getColumnType());
                if(StringUtils.isNotEmpty(fieldComment)){
                    codeStr.append(fieldComment);
                    codeStr.append("\r\n");
                }
                codeStr.append(String.format("private %s %s;", fieldType, fieldName));
                codeStr.append("\r\n");
                getAndSetName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                fileGetAndSetCode.append(String.format("public void set%s(%s %s) { \r\n", getAndSetName, fieldType, fieldName));
                fileGetAndSetCode.append(String.format("this.%s = %s;\r\n",fieldName, fieldName));
                fileGetAndSetCode.append("}\r\n");
                fileGetAndSetCode.append("\r\n");
                fileGetAndSetCode.append(String.format("public %s get%s() {\r\n", fieldType, getAndSetName));
                fileGetAndSetCode.append(String.format("return this.%s;\r\n", fieldName));
                fileGetAndSetCode.append("}\r\n");
                fileGetAndSetCode.append("\r\n");
                this.columnsString += tableColumnInfo.getColumnName() +",";
//                if(tableColumnInfo.getColumnName().indexOf("_") > 0){
//                    String[] columnNames = tableColumnInfo.getColumnName().split("_");
//                    for (String  column : columnNames){
//                        valueColumn += column.substring(0,1).toUpperCase() + column.substring(1);
//                    }
//                    valueColumn = valueColumn.substring(0,1).toLowerCase() + valueColumn.substring(1);
//                }else {
//                    valueColumn = tableColumnInfo.getColumnName();
//                }
                this.valuesString += String.format("#{%s},",fieldName);
            }
            this.columnsString = this.columnsString.substring(0,this.columnsString.length() - 1);
            this.valuesString = this.valuesString.substring(0,this.valuesString.length() - 1);
            codeStr.append(fileGetAndSetCode);
            codeStr.append("}");

            File file = new File(this.codeFilePath+"/"+this.className+".java");
            if(file.exists()){
                file.delete();
            }
            fw = new FileWriter(file);
            fw.write(codeStr.toString());
            fw.flush();
            fw.close();
        }catch (Exception e){
            try {
                if(null != fw) fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private String generateFieldName(String columnName) {
        String fieldName = "";
        if(columnName.indexOf("_")>0){
            String[] nameArray = columnName.split("_");
            for(String str : nameArray){
                fieldName += str.substring(0,1).toUpperCase()+str.substring(1);
            }
        }else {
            fieldName = columnName;
        }
        fieldName = fieldName.substring(0,1).toLowerCase() + fieldName.substring(1);
        return fieldName;
    }

    private String generateClassName(String table) {
        String resultStr = "";
        if(table.indexOf("_") > 0){
            String[] tableArray = table.split("_");
            for (String str : tableArray){
                    resultStr += str.substring(0,1).toUpperCase()+str.substring(1);
//                }
            }
        }else {
            resultStr = table.substring(0,1).toUpperCase() + table.substring(1);
        }
        return resultStr;
    }

    private String generateFieldComment(TableColumnInfo tableColumnInfo) {
        if(null == tableColumnInfo) return null;
        StringBuffer resultStr = new StringBuffer("/** \r\n");
        resultStr.append("* "+tableColumnInfo.getColumnComment() +"\r\n");
        resultStr.append("*/");
        return resultStr.toString();
    }

    private String getFileType(String columnType){
        String fieldType = "";
        if(columnType.equals("int") || columnType.equals("tinyint")){
            fieldType = "Integer";
        }else if(columnType.equals("varchar")){
            fieldType = "String";
        }else if(columnType.equals("bigint")){
            fieldType = "Long";
        }else if(columnType.equals("float")){
            fieldType = "Float";
        }else if(columnType.equals("double")){
            fieldType = "Double";
        }else if(columnType.equals("datetime") || columnType.equals("timestamp")){
            fieldType = "Date";
        }else if(columnType.equals("date")){
            fieldType = "String";
        }else if(columnType.equals("decimal")){
            fieldType = "BigDecimal";
        }
        return fieldType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLivingExample() {
        return livingExample;
    }

    public void setLivingExample(String livingExample) {
        this.livingExample = livingExample;
    }
}
