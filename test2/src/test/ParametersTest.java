package test;

import org.databene.benerator.anno.Source;
import org.databene.feed4junit.Feeder;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Feeder.class)
// 指定运行器runner:使用参数化运行器来运行
public class ParametersTest
{


    //private Calculator calculator = null;

    @Test
    @Source("Data.xlsx")
    public void testAdd_Excel(String method,int input1,int input2,int expected)
    {
    	Calculator calculator = new Calculator();
    	switch(method){
    	case "post":
    		 assertEquals(expected,calculator.add(input1,input2));
    		 break;
    	default:
    			 assertEquals(expected,calculator.subtract(input1,input2));
    		 break;
    	}

    }

}
