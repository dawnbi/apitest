package com.api;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.*;


public class ApiPost {
  
    CloseableHttpClient httpClient=HttpClients.createDefault();  
    private Log  logger = LogFactory.getLog(this.getClass());
    private long startTime  = 0L;
    private long endTime    = 0L;
    int status=0;   
    
    /**
     * json格式化
     * @param jsonString
     * @return body
     */
    public static String jsonFormatter(String jsonString){
    	Gson gson=new GsonBuilder().setPrettyPrinting().create();
    	JsonParser jp=new JsonParser();
    	JsonElement je=jp.parse(jsonString);
    	String body=gson.toJson(je);
    	return body;
    }
    
    /**
     * 发送http post请求
     * @param url：请求地址
     * @param json ：post json格式的参数
     * @param n：请求头
     * @return 服务器返回的数据
     */
     public String post(String url,String json,int n) {
         HttpPost httppost=new HttpPost(url);
            String body = null;
              try {
            	  logger.info("打印调用的api接口地址："+url);
                  logger.info("打印post的json字符串："+json);

                    switch(n){
                        case 1:
                            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
                        break;
                        default :
                            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
                            break;
                    }   
                    
                    httppost.setEntity(new StringEntity(json, "UTF-8"));
                    startTime = System.currentTimeMillis();
                    HttpResponse response = httpClient.execute(httppost);
                    endTime = System.currentTimeMillis();
                    int statusCode = response.getStatusLine().getStatusCode();
                    logger.info("statusCode:" + statusCode);
                    logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
                    assertEquals("很遗憾，返回值statusCode不等于200，请检查。。。",200,statusCode);
                    
                    /**
                     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
                     * 
                     * @return the status
                     */

                    if (statusCode != HttpStatus.SC_OK) {
                        System.out.println("Method failed:" + response.getStatusLine());
                        status = 1;
                    }
                    // Read the response body
                    body = EntityUtils.toString(response.getEntity());

                } catch (IOException e) {
                    // 发生网络异常
                    System.out.println("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
                    status = 3;
                } finally {
                    logger.info("调用接口状态：" + status);
                }
              	logger.info("post执行完毕，准备返回body\n" + jsonFormatter(body));
              	return body;
        }

     /**
      * http get 请求
      * @param url 请求的地址
      * @param parameters 请求的参数
      * @param n 请求头
      * @return 服务器返回的数据
      */
       public String get(String url,String parameters,int n ) {
            String body = null;
              try {
                  url=url+parameters;
                  logger.info("打印get请求url地址："+url);
                  HttpGet httpget=new HttpGet(url);
                  
                   switch(n){
                       case 1:
                           httpget.addHeader("Content-type", "application/x-www-form-urlencoded");
                       break;
                       default :
                           httpget.addHeader("Content-type", "application/x-www-form-urlencoded");
                           break;
                   }
                   
                    startTime = System.currentTimeMillis();
                    HttpResponse response = httpClient.execute(httpget);
                    endTime = System.currentTimeMillis();
                    int statusCode = response.getStatusLine().getStatusCode();
                    System.out.println("statusCode:" + statusCode);
                    logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
                    assertEquals("很遗憾，返回值statusCode不等于200，请检查。。。",200,statusCode);


                    /**
                     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
                     * 
                     * @return the status
                     */
                    if (statusCode != HttpStatus.SC_OK) {
                        System.out.println("Method failed:" + response.getStatusLine());
                        status = 1;
                    }
                    // Read the response body
                    body = EntityUtils.toString(response.getEntity());
                   // body.getBytes(body).toString();


                } catch (IOException e) {
                    // 发生网络异常
                    System.out.println("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
                    status = 3;
                } finally {
                    System.out.println("调用接口状态：" + status);
                }
              		System.out.println("get执行完毕，准备返回body\n" + body);
              		return body;
        }  
   
    }
