//package com.api;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import test.APIHttpClient;
//import test.test001;
//
//@SuppressWarnings({ "unused", "deprecation" })
//public class ApiHttpClient {
//    
//    ApiPost a = new ApiPost();
//    @Before
//    public void setUp() throws Exception {
//        System.out.println("********************************************准备调用接口********************************************");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        System.out.println("********************************************接口执行完毕********************************************");
//    }
//
//    @Test
//    public void test1() {       
//        String url=  "http://app5.plateno.com:9950/hotel/detail";
//        String newjson = " { \"authority\": { \"token\": \"uu8rHdrAA0NjaalK4XuPzy7XDq/usE9g+a+ewrVry4Vr/FZNwut0y5GuyhcJ8Ji4\"},\"chainId\": 621}"; 
//        a.post(url,newjson,1);
//    }
//    @Test
//    public void test2() {
//        String url= "http://php.weather.sina.com.cn/xml.php?";
//        String parameters = "city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0";
//        int n = 1;
//        a.get(url,parameters,1);
//    }
//}
