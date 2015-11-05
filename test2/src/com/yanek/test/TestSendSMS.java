package com.yanek.test;  
  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import net.sf.json.JSONObject;  
  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
  
public class TestSendSMS {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
      
        String uid="12345678";  
        String title="test";  
        String content="test a content";  
        String ret=sendSms(uid ,title,content);  
        System.out.println(ret);  
  
        if(ret.indexOf("失败")<0)  
        {  
            System.out.println("成功发送sms");  
        }  
        else  
        {  
            System.out.println("失败发送");  
        }  
  
    }  
      
      
  
    public static String sendSms(String uid,String title,String content){  
        HttpClient httpclient = new DefaultHttpClient();  
        String smsUrl="http://app5.plateno.com:9950/hotel/query/basic";  
        HttpPost httppost = new HttpPost(smsUrl);  
        String strResult = "";  
          
        try {  
              
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
                JSONObject json = new JSONObject();  
//                jobj.put("uid", uid);  
//                jobj.put("title", title);  
//                jobj.put("content",content);  
                json.put("brand", "4"); 
                json.put("brandType", ""); 
                json.put("city", "广州");  
                json.put("district","");  
                json.put("distance","0");  
                json.put("lng","113.381134");  
                json.put("lat","23.12682");  
                json.put("hasRoom","0");  
                json.put("cityId","1");  
                json.put("districtId","0");  
                json.put("maxPrice","0");  
                json.put("memberId","0");  
                json.put("minPrice","0");  
                json.put("page","1");  
                json.put("pageSize","20");  
                json.put("quotaId","0");  
                json.put("searchWay","4");  
                json.put("sort","4");  
                  
                  System.out.println("json:"+json);
                nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(json)));  
                httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
               // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));  
                System.out.println("执行："+new  StringEntity(json.toString()));
                httppost.setEntity(new  StringEntity(json.toString(),"UTF-8"));  
                System.out.println(1);
                HttpResponse response = httpclient.execute(httppost);  
                System.out.println(2);
                if (response.getStatusLine().getStatusCode() == 200) {  
                    /*读返回数据*/  
                    String conResult = EntityUtils.toString(response  
                            .getEntity());  
                    JSONObject sobj = new JSONObject();  
                    sobj = sobj.fromObject(conResult);  
                    System.out.println(3);
                    String result = sobj.getString("result");  
                    String code = sobj.getString("code");  
                    System.out.println(4);
                    if(result.equals("1")){  
                        strResult += "发送成功";  
                    }else{  
                        strResult += "发送失败，"+code;  
                    }  
                      
                } else {  
                    String err = response.getStatusLine().getStatusCode()+"";  
                    strResult += "发送失败:"+err;  
                }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return strResult;  
    }  
  
      
    private static String getStringFromJson(JSONObject adata) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("{");  
        for(Object key:adata.keySet()){  
            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
        }  
        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
        return rtn;  
    }  
} 