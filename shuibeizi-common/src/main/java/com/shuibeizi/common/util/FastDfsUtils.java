package com.shuibeizi.common.util;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.*;

/**
 * @author: Administrator
 * @description: fastDfs文件服务
 * @date: 2018/4/11 0011 10:24
 * @modified:
 */
public class FastDfsUtils {

    private FastDfsUtils(){}

    private static class InstanceObj{
        private static final FastDfsUtils instance = new FastDfsUtils();
    }

    public static FastDfsUtils getInstance(){
        return InstanceObj.instance;
    }

    public String uploadFile(File file) throws Exception {
        if (!file.exists()) {
            return null;
        }
         byte[] byteList = File2byte(file);
         String name = file.getName();
        FileInputStream fis= new FileInputStream(file);
        Long size =  (new FileInputStream(file)).getChannel().size();

        String path = null;

        //trackerclient
        TrackerClient trackerclient = new TrackerClient();
        TrackerServer trackerServer = trackerclient.getConnection();
        //storageclient
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
        //文件扩展名
        String ext = FilenameUtils.getExtension(name);
        //mata list是表文件的描述
        NameValuePair[] mata_list = new NameValuePair[3];
        mata_list[0] = new NameValuePair("fileName", name);
        mata_list[1] = new NameValuePair("fileExt", ext);
        mata_list[2] = new NameValuePair("fileSize", String.valueOf(size));
        path = storageClient1.upload_file1(byteList, ext, mata_list);
        fis.close();
        return path;
    }

    public String uploadFile(byte[] bytes, String fileName) throws Exception{
        if(null == bytes || null == fileName) return null;
        ClientGlobal.init("config/fastDfs.properties");
        //trackerclient
        TrackerClient trackerclient = new TrackerClient();
        TrackerServer trackerServer = trackerclient.getConnection();
        //storageclient
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
        //文件扩展名
        String ext = FilenameUtils.getExtension(fileName);
        //mata list是表文件的描述
        NameValuePair[] mata_list = new NameValuePair[3];
        mata_list[0] = new NameValuePair("fileName", fileName);
        mata_list[1] = new NameValuePair("fileExt", ext);
        mata_list[2] = new NameValuePair("fileSize", String.valueOf(bytes.length));
        String path = storageClient1.upload_file1(bytes, ext, mata_list);
        return path;
    }




    /**
     * File 与 byte[]的转换
     * @param _file
     * @return
     */
    public byte[] File2byte(File _file)
    {
        byte[] buffer = null;
        try
        {
            File file = _file;
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    public void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
