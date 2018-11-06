package org.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10 0010.
 */
public class StartUp {

    private static String codeFilePath = "F:/src";
    private static String className;
    private static String livingExample = "";

    public static void main(String[] args) throws URISyntaxException {
        codeFilePath = StartUp.class.getResource("/").getPath();
        codeFilePath = codeFilePath.substring(0,codeFilePath.indexOf("code-generator")+"code-generator".length());
        codeFilePath += "/src/main/codeSource";
        new StartUp().generateCode();
//        System.out.println(codeFilePath);
    }

    public void generateCode(){
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            InputStream is = classloader.getResourceAsStream("/generator-config.xml");

            String genCfg = "/generator-config.xml";//配置文件的路径:默认放到src下面
            URL url = StartUp.class.getResource(genCfg);
            String file = url.getFile();
            File configFile = new File(file);

            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//            myBatisGenerator.generate(null);
            List<TableConfiguration> tables = config.getContexts().get(0).getTableConfigurations();
            for(TableConfiguration tableConfiguration : tables){
                this.className = this.generateClassName(tableConfiguration.getTableName());
                this.livingExample = this.className.substring(0,1).toLowerCase() + this.className.substring(1);
                this.generateLogicCode(tableConfiguration.getTableName(),"Service","I", "com.broker.service");
                this.generateLogicCode(tableConfiguration.getTableName(), "ServiceImpl", null, "com.broker.service.impl");
                this.generateLogicCode(tableConfiguration.getTableName(), "Controller", null, "com.broker.controller");
            }
            //生成service controller
//            myBatisGenerator.
            System.out.println(111111);

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 逻辑代码
     * @param
     * @return
     * @author: Administrator
     * @date: 2016/12/31 15:26
     */
    public void generateLogicCode(String table,String type, String prefix, String packagePath){
        Writer writer = null;
        BufferedWriter bw = null;
        Reader reader = null;
        BufferedReader br = null;
        String codePath = this.codeFilePath;
        try {
            if(null != packagePath){
                String[] arr = packagePath.split("\\.");
                for (String str : arr){
                    codePath += "/" + str;
                }
                if(!(new File(codePath).exists())){
                    new File(codePath).mkdirs();
                }
            }
            String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            File templateFile;

            if(null == prefix){
                URL url = StartUp.class.getResource("/template/Template" + type + ".java");
                templateFile =  new File(url.getFile());
            }else {
                URL url = StartUp.class.getResource("/template/" + prefix + "Template" + type + ".java");
                templateFile = new File(url.getFile());
            }

            reader = new FileReader(templateFile);
            br = new BufferedReader(reader);
            File codeFile;
            if(null != prefix){
                codeFile = new File(codePath+"/" + prefix + this.className + type + ".java");
            }else {
                codeFile = new File(codePath+"/" + this.className + type + ".java");
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
}
