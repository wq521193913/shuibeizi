package com.shuibeizi.common.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wq
 * @description: httpUtils
 * @date: Create in 2018/3/15 0015 下午 8:59
 * @modified:
 */
public class HttpUtils {

    private final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils(){}

    private static class InstanceObj{
        private static final HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getInstance(){
        return InstanceObj.instance;
    }

    /**
     * @author: wq
     * @description: http get method
     * @param url
     * @return: string
     * @date: Create in 2018/3/15 0015 下午 9:52
     * @modified:
     */
    public String requestGet(String url){
        try {
           return this.requestGet(url, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author: wq
     * @description: http get method
     * @param url
     * @param params
     * @return: string
     * @date: Create in 2018/3/15 0015 下午 9:53
     * @modified:
     */
    public String requestGet(String url, Map<String, Object> params){
        logger.info("request url: {}", url);
        logger.info("request params: {}", JSONObject.fromObject(params));
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            StringBuilder urlStr = new StringBuilder(url);
            if(urlStr.indexOf("?") <= 0){
                urlStr.append("?random="+System.nanoTime());
            }

            if(null != params){
                for (String key : params.keySet()){
                    urlStr.append(String.format("&%s=%s",key,params.get(key)));
                }
            }

            HttpGet httpGet = new HttpGet(urlStr.toString());
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if(null != httpEntity){
                result = EntityUtils.toString(httpEntity);
                logger.debug("response data:" + result);
                return result;
            }

        }catch (Exception e){
            logger.error("HttpUtils Error:",e);
        }
        return result;
    }

    /**
     * @author: wq
     * @description: http get method
     * @param url
     * @param params
     * @return: string
     * @date: Create in 2018/3/15 0015 下午 9:53
     * @modified:
     */
    public String httpsPost(String url, String params){
        logger.info("request url: {}", url);
        logger.info("request params: {}", JSONObject.fromObject(params));
        String result = "";
        try {
            CloseableHttpClient httpClient = new SSLClient();
            StringEntity se = new StringEntity(params, "UTF-8");
            HttpPost httpPost = new HttpPost(url.toString());
            httpPost.setEntity(se);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if(null != httpEntity){
                result = EntityUtils.toString(httpEntity);
                logger.info("response data: {}", result);
                return result;
            }

        }catch (Exception e){
            logger.error("HttpUtils Error:",e);
        }
        return result;
    }

    /**
     * @author: wq
     * @description: http get method
     * @param url
     * @param params
     * @return: string
     * @date: Create in 2018/3/15 0015 下午 9:53
     * @modified:
     */
    public String requestPost(String url, Map<String, Object> params){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(JSONObject.fromObject(params).toString());
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);

            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"UTF-8");
                }
            }
        }catch(Exception ex){
            logger.error("HttpUtils Error:",ex);
        }
        return result;
    }

    public String requestPost(String url, String params){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(params, "UTF-8");
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);

            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"UTF-8");
                }
            }
        }catch(Exception ex){
            logger.error("HttpUtils Error:",ex);
        }
        return result;
    }

    /**
     * 微信退款双向证书请求
     * @param url
     * @param certFilePath
     * @param mch_id
     * @param xmlParams
     * @return
     * @throws Exception
     */
    public String certRequestPost(String url, String certFilePath, String mch_id, String xmlParams) throws Exception{

        String result = "";
        //商户id
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(this.getClass().getResource(certFilePath).getPath());
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, mch_id.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(xmlParams, "UTF-8");
            httppost.setEntity(reqEntity);

            System.out.println("Executing request: " + httppost.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            logger.error("HttpUtils Error:",e);
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("HttpUtils Error:",e);
            }
        }
        return result;
    }



    public static void main(String[] args){
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=11_VJFr4ipGytD4Gb_JcgsI67zKNhZ8l3xFaLMyWZPlhzgDQW_TyAjqDY_I9ALx4COHZc-Ap_TB8UPL19S9y8_U2OTu6VnA9Ugs3iX6DI7geKgBDc0XhVfD58QJfxd1KSBdXKUOVULIxg2ePKWHWBXeACAEJF";
        Map<String, Object> params = new HashMap<>();
        params.put("openid","osZRV40IuQ0MT5UbgiLytkdTW7Yg");
        params.put("templateId","rJS8GInfW7FlW7FT_r-4AotPCbTFLHMyQIpbXH_G5mY");
        params.put("formId","72f48a1874bd047b140f9287a47bc0cb");
        params.put("content","{\"keyword1\":{\"value\":\"抽奖活动倒计时通知\", \"color\": \"#173177\"},\"keyword2\":{\"value\":\"卡通木垫\", \"color\": \"#173177\"},\"keyword3\":{\"value\":\"距离抽奖结束仅剩一小时,您还未获得抽奖资格，立即邀请好友获得抽奖机会.\", \"color\": \"#173177\"}}");
        params.put("accessToken","11_VJFr4ipGytD4Gb_JcgsI67zKNhZ8l3xFaLMyWZPlhzgDQW_TyAjqDY_I9ALx4COHZc-Ap_TB8UPL19S9y8_U2OTu6VnA9Ugs3iX6DI7geKgBDc0XhVfD58QJfxd1KSBdXKUOVULIxg2ePKWHWBXeACAEJF");

        System.out.println(HttpUtils.getInstance().httpsPost(url, JSONObject.fromObject(params).toString()));

    }

    class SSLClient extends DefaultHttpClient {
        public SSLClient() throws Exception{
            super();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        }
    }

}
