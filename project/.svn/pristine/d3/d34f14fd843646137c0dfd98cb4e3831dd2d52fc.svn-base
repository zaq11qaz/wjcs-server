
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)//1.先更换测试运行器
public class ATest {

    //2.定义参数用来存储测试方法的参数和期望值
    int expe=0;
    int input1=0;
    int input2=0;

    //该方法用于生成批量参数
    @Parameters
    public static Collection<Object[]> canshu(){
        return  Arrays.asList(new Object[][]{
                {3,1,2},{5,2,3}});
    }

    //构造器用于将生成的批量参数赋值到类成员变量中
    public ATest(int expe,int input1,int input2){
        this.expe=expe;
        this.input1=input1;
        this.input2=input2;
    }

    //测试方法开始测试
    @Test
    public void add() {
    }
}