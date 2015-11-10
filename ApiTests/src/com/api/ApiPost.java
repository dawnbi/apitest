package com.api;

import static org.junit.Assert.*;

import java.io.IOException;

//import net.sf.json.JSONObject;


import net.sf.json.JSONObject;

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
	CloseableHttpClient httpClient = null;
    private Log  logger = LogFactory.getLog(this.getClass());
    private long startTime  = 0L;//记录发送请求的开始时间
    private long endTime    = 0L;//记录请求发送后收到服务器返回结果的时间
    int status=0;   //记录请求发送的状态
    String body = null; //记录请求发送后服务器返回的内容
    
    /**
     * json格式化
     * @param jsonString-服务器返回的内容，即body
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
     * 获取json格式的返回结果中指定的参数值
     * @param result-返回结果中指定的参数
     * @return 指定参数的值 results
     */
    public Object getActual(Object result){
    	JSONObject jsonObj = JSONObject.fromObject(body);
    	// 检查字符串结果 
		if(jsonObj.get(result) instanceof String){
   		String results = (String) jsonObj.get(result);
           logger.info("检查执行完毕，返回检查点是字符串"+result+":" + results);
           return results;
		 }
		//检查整型类型结果 
   	 else{
   		Integer results= (Integer) jsonObj.get(result);
            logger.info("检查执行完毕，返回检查点是整型"+result+":" + results);
            return results.toString();
        }   	 
    }
 	 
    /**
     * 判断是否有预期结果中指定的字符串
     * @param result-预期结果中指定的字符串
     * @return 如果有返回true，否则返回false
     */
    public boolean getActuals(String result){
    	if(body.contains(result)){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 发送http post请求
     * @param url：请求地址
     * @param json ：post json格式的参数
     * @param n：请求头
     * @return 服务器返回的数据
     */
     public void post(String url,String json,int n) {
    	 //判断是否https请求
         if (url.startsWith("https")) {
             httpClient = HttpsSSLClient.createSSLInsecureClient();
         } else 
         	{
             	httpClient = HttpClients.createDefault();
         	}
         //声明并初始化一个HttpPost对象
         HttpPost httppost=new HttpPost(url);
         
         try {
        	 	logger.info("打印调用的api接口地址："+url);
            	logger.info("打印post的json字符串："+json);
            	//switch语句定义不一样的请求头
                switch(n){
                   case 1:
                	     httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
                         break;
                   default :
                         httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
                         break;
                    }   
                 //设置post的参数
                 httppost.setEntity(new StringEntity(json, "UTF-8"));
                 
                 startTime = System.currentTimeMillis();
                 //执行http post请求并把结果赋值给HttpResponse对象response
                 HttpResponse response = httpClient.execute(httppost);
                 endTime = System.currentTimeMillis();
                 
                 //获取http post请求的发送情况
                 int statusCode = response.getStatusLine().getStatusCode();
                 logger.info("statusCode:" + statusCode);
                 logger.info("调用API所需的时间(单位：毫秒)：" + (endTime - startTime));
                 assertEquals("很遗憾，返回值statusCode不等于200",200,statusCode);
                 //0.成功 1.执行方法失败 2.网络错误
                 if (statusCode != HttpStatus.SC_OK) {
                        System.out.println("Method failed:" + response.getStatusLine());
                        status = 1;
                    }
                 
                 //获取返回结果的值并赋值给body
                 body = EntityUtils.toString(response.getEntity());
         		
         		} catch (IOException e) {
                    // 发生网络异常
                    System.out.println("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
                    status = 2;
                } finally {
                    logger.info("调用接口状态：" + status);
                    }
         
         		System.out.println("post执行完毕，准备返回body\n" + jsonFormatter(body));
         		
        }

     /**
      * http get 请求
      * @param url 请求的地址
      * @param parameters 请求的参数
      * @param n 请求头
      * @return 服务器返回的数据
      */
       public String get(String url,String parameters,int n ) {
    	   //判断是否https请求
           if (url.startsWith("https")) {
               httpClient = HttpsSSLClient.createSSLInsecureClient();
           } else 
           		{
        	   		httpClient = HttpClients.createDefault();
           		}
           
            try {
            	  //get请求地址与参数拼接
                  url=url+parameters;
                  logger.info("打印get请求url地址："+url);
                  //声明并初始化一个HttpGet对象
                  HttpGet httpget=new HttpGet(url);
                  //设置请求头
                   switch(n){
                       case 1:
                           httpget.addHeader("Content-type", "application/x-www-form-urlencoded");
                       break;
                       default :
                           httpget.addHeader("Content-type", "application/x-www-form-urlencoded");
                           break;
                   }
                   
                   //执行http get请求并把结果赋值给HttpResponse对象response
                   startTime = System.currentTimeMillis();
                   HttpResponse response = httpClient.execute(httpget);
                   endTime = System.currentTimeMillis();
                   
                   //获取http get请求的发送情况
                   int statusCode = response.getStatusLine().getStatusCode();
                   logger.info("statusCode:" + statusCode);
                   logger.info("调用API所需的时间(单位：毫秒)：" + (endTime - startTime));
                   assertEquals("很遗憾，返回值statusCode不等于200",200,statusCode);
                   //0.成功 1.执行方法失败 2.网络错误
                   if (statusCode != HttpStatus.SC_OK) {
                          System.out.println("Method failed:" + response.getStatusLine());
                          status = 1;
                      }
                   
                   //获取返回结果的值并赋值给body
                   body = EntityUtils.toString(response.getEntity());
           		
           		} catch (IOException e) {
                      // 发生网络异常
                      System.out.println("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
                      status = 2;
                } finally {
                      logger.info("调用接口状态：" + status);
                     }
              		
            		System.out.println("get执行完毕，准备返回body\n" + body);
              		return body;
        }  
       
//       public static void main(String[] args){
//    	   ApiPost test=new ApiPost();
//    	   test.post("https://openapi.25pp.com/sdk/safety","{\"from\" : 106,\"username\" : \"是不是\",\"uid\" : 3430012,\"version\" : \"1.5.7.1\",\"token\" : \"e2303f56f6b4f30082ffe801d8828cf2\"}", 1);
//    	   test.get("http://php.weather.sina.com.cn/xml.php?", "city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0", 1);
//       }
   
  }


