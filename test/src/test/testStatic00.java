package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class testStatic00 {
      
    public static void main(String[] args)
    {
        String result = "[{\"username\": \"your name\", \"user_json\": {\"username\": \"your name\", \"nickname\": \"your nickname\"}}]";
       // JSONArray resultArray = new JSONArray(result);
        result.replace("[", "").toString();
        System.out.println(result.replace("[", "").replace("]", "").toString());
        
        //JSONObject resultObj = resultArray.optJSONObject(0);
        
    }
}
