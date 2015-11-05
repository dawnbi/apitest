package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class APIHttpClient {

    // 接口地址
    private static String       apiURL     = "http://app5.plateno.com:9950/hotel/query/basic";
    private Log                 logger     = LogFactory.getLog(this.getClass());
    private static final String pattern    = "yyyy-MM-dd HH:mm:ss:SSS";
    private HttpClient          httpClient = null;
    private HttpPost            httpPost   = null;
    private long                startTime  = 0L;
    private long                endTime    = 0L;
    private int                 status     = 0;

    /**
     * 接口地址
     * 
     * @param url
     */
    public APIHttpClient(String url) {

        if (url != null) {
            this.apiURL = url;
        }
        if (apiURL != null) {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(apiURL);
            System.out.println("打印httpPost:" + httpPost);
            System.out.println("APIHttpClient构造方法执行完毕");
        }
    }

    /**
     * 调用 API
     * 
     * @param parameters
     * @return
     */
    public String post(String parameters) {
        String body = null;
        logger.info("parameters:" + parameters);

        if (httpPost != null & parameters != null && !"".equals(parameters.trim())) {
            // JSONArray jsonObject = JSONArray.fromObject(parameters);
            // logger.info("json:" + jsonObject.toString());
            try {
                logger.info("进入try方法，准备创建list对象");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                JSONObject json = new JSONObject();
                json.put("brand", "4");
                json.put("brandType", "");
                json.put("city", "广州");
                json.put("district", "");
                json.put("distance", "0");
                json.put("lng", "113.381134");
                json.put("lat", "23.12682");
                json.put("hasRoom", "0");
                json.put("cityId", "1");
                json.put("districtId", "0");
                json.put("maxPrice", "0");
                json.put("memberId", "0");
                json.put("minPrice", "0");
                json.put("page", "1");
                json.put("pageSize", "20");
                json.put("quotaId", "0");
                json.put("searchWay", "4");
                json.put("sort", "4");
                logger.info("创建json对象完成-------打印：" + json);


                // 建立一个NameValuePair数组，用于存储欲传送的参数
                // params.add(new BasicNameValuePair("data",parameters));
                params.add(new BasicNameValuePair("msg", getStringFromJson(json)));
                logger.info("list添加参数params：" + params);
                httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
                // httpPost.setEntity(new
                // UrlEncodedFormEntity(params,HTTP.UTF_8));
                logger.info("打印json字符串：" + json.toString());
                
                String result = "[{\"username\": \"your name\", \"user_json\": {\"username\": \"your name\", \"nickname\": \"your nickname\"}}]";
                // JSONArray resultArray = new JSONArray(result);
                 result.replace("[", "").toString();
                 System.out.println(result.replace("[", "").replace("]", "").toString());

              //  httpPost.setEntity(new StringEntity(result.replace("[", "").replace("]", "").toString(), "UTF-8"));

                logger.info("json:" + params.toString());
                startTime = System.currentTimeMillis();

                // 设置编码
                HttpResponse response = httpClient.execute(httpPost);
                endTime = System.currentTimeMillis();
                int statusCode = response.getStatusLine().getStatusCode();
                logger.info("statusCode:" + statusCode);
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));

                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed:" + response.getStatusLine());
                    status = 1;
                }
                // Read the response body
                body = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                // 发生网络异常
                logger.error("exception occurred!\n" + ExceptionUtils.getStackTrace(e));
                // 网络错误
                status = 3;
            } finally {
                logger.info("调用接口状态：" + status);
            }

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

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    private static String getStringFromJson(JSONObject adata) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (Object key : adata.keySet()) {
            sb.append("\"" + key + "\":\"" + adata.get(key) + "\",");
        }
        String rtn = sb.toString().substring(0, sb.toString().length() - 1) + "}";
        return rtn;
    }

    public static void main(String[] args) {
        APIHttpClient a = new APIHttpClient(apiURL);
        a.post("uowek");
    }
}
