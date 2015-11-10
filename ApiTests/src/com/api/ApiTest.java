package com.api;

import static org.junit.Assert.*;

import org.databene.benerator.anno.Source;
import org.databene.feed4junit.Feeder;
import org.junit.runner.RunWith;
import org.junit.Test;


@RunWith(Feeder.class)
//指定运行器runner:使用参数化运行器来运行

public class ApiTest {
	ApiPost tc=new ApiPost();
	
	@Test
	@Source("Data.xlsx")
	public void test(String url,String method,int httphead, String paras,String expected,Object a)
    	{
			String parass=paras.replace('[', ' ').replace(']',' ');
			//String expect=expected.replace('[', ' ').replace(']',' ');
			switch(method){
			case "post":
    				tc.post(url,parass,httphead); 
    				System.out.println("实际返回值："+tc.getActual(expected));
    				System.out.println("预期"+a.toString());
    				assertEquals(a.toString(),tc.getActual(expected));
    				break;
			default:
    				tc.get(url,parass,httphead); 
    				System.out.println("实际返回值："+tc.getActuals( expected));
    				assertEquals("断言失败",true,tc.getActuals(expected));
    				System.out.println("实际返回值："+tc.getActuals( expected));
   				break;
    	}
    	//fail("Not yet implemented");

    	}
}


