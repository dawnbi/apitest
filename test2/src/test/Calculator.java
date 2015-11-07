package test;

public class Calculator
{
    public int add(int a, int b)
    {

    	System.out.println("a的值:"+a+",b的值:"+b+",a+b="+(a+b));
        return a + b;
    }

    public int subtract(int a, int b)
    {
    	System.out.println("a的值:"+a+",b的值:"+b+","+a+"-"+b+"="+(a-b));
        return a - b;
    }

    public int multiply(int a, int b)
    {
        return a * b;
    }

    public int divide(int a, int b) throws Exception
    {
        if (0 == b)
        {
            throw new Exception("除数不能为0");
        }
        return a / b;
    }

}