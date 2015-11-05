package test;

import java.io.IOException;
import static org.junit.Assert.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
//import net.sf.json.JSONArray;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class test001 {
  
	CloseableHttpClient httpClient=HttpClients.createDefault();
	HttpPost url=new HttpPost("http://app5.plateno.com:9950/hotel/detail");
	int status=0;
	
	 public String post() {
	        String body = null;
	          try {
	                
	                String result = " [{ \"authority\": { \"token\": \"uu8rHdrAA0NjaalK4XuPzy7XDq/usE9g+a+ewrVry4Vr/FZNwut0y5GuyhcJ8Ji4\"},\"chainId\": 621}]";
	                String b = result.replace("[", "").replace("]", "").toString();
	                url.addHeader("Content-type", "application/x-www-form-urlencoded");
	                url.setEntity(new StringEntity(b, "UTF-8"));
	                
	                HttpResponse response = httpClient.execute(url);
	                int statusCode = response.getStatusLine().getStatusCode();
	                System.out.println("statusCode:" + statusCode);


	                if (statusCode != HttpStatus.SC_OK) {
	                    System.out.println("Method failed:" + response.getStatusLine());
	                    status = 1;
	                }
	                // Read the response body
	                body = EntityUtils.toString(response.getEntity());

	            } catch (IOException e) {
	                // 发生网络异常
	                System.out.println("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
	                // 网络错误
	                status = 3;
	            } finally {
	                System.out.println("调用接口状态：" + status);
	            }

	        
	       System.out.println("post执行完毕，准备返回body" + body);
	        return body;
	    }

	    /**
	     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
	     * 
	     * @return the status
	     */
	    public int getStatus() {
	        return status;
	    }

	    /**
	     * @param status
	     *            the status to set
	     */
	    public void setStatus(int status) {
	        this.status = status;
	    }

	    public String get() {
            String body = null;
              try {
                  String url3= "http://php.weather.sina.com.cn/xml.php?";
                  String abc = "city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0";
                  url3=url3+abc;
                  HttpGet url2=new HttpGet(url3);
                   
                    url2.addHeader("Content-type", "application/x-www-form-urlencoded");
                    
                   
                    HttpResponse response = httpClient.execute(url2);
                    int statusCode = response.getStatusLine().getStatusCode();
                    System.out.println("statusCode:" + statusCode);
                    assertEquals("Note 1 and/or Note 2 are not found", 300, statusCode); 

//                    ((AbstractHttpEntity) response).setContentType("text/xml;charset=UTF-8"); 
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
                    // 网络错误
                    status = 3;
                } finally {
                    System.out.println("调用接口状态：" + status);
                }

            
           System.out.println("get执行完毕，准备返回body" + body);
            return body;
        }  

	    public static void main(String[] args) {
	        test001 a = new test001();
	        a.post();
	        //a.get();
	    }
	}
