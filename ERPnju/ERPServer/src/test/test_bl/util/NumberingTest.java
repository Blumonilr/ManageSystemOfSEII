package test.test_bl.util;

import main.businesslogic.util.Numbering;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/** 
* Numbering Tester. 
* 
* @author <王一博>
* @since <pre>十二月 4, 2017</pre> 
* @version 1.0 
*/ 
public class NumberingTest {
    static String filepath;
    static Numbering tool;
    static File file;
    static DateFormat df;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        filepath = Numbering.class.getResource("").getPath() + "time.txt";
        file = new File(filepath);
        tool = Numbering.getInstance();
        df = new SimpleDateFormat("yyyyMMdd");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
        tool.init();
    }


    /**
     * Method: number(int type)
     */
    @Test
    public void testNumber() throws Exception {
        tool.number(11);
        tool.number(11);
        tool.number(12);
        assertEquals("KCBSD-"+ df.format(Calendar.getInstance().getTime())+"-00002",tool.number(12));
        assertEquals("KCBYD-"+ df.format(Calendar.getInstance().getTime())+"-00003",tool.number(11));
    }

    @Test
    public void testNumber1() throws Exception {
        for(int i=0;i<1035;i++){
            tool.number(23);
        }
        assertEquals("XJFYD-"+df.format(Calendar.getInstance().getTime())+"-01036",tool.number(23));
    }

    @Test
    public void testNumber2() throws Exception {
        assertEquals("SKD-"+df.format(Calendar.getInstance().getTime())+"-00001",tool.number(21));
    }

    @Test
    public void testNumber3()throws Exception{
        for(int i=0;i<99998;i++){
            tool.number(23);
        }
        assertEquals("XJFYD-"+df.format(Calendar.getInstance().getTime())+"-99999",tool.number(23));
        assertEquals("max",tool.number(23));
    }

    @Test
    public void testConstructor() throws Exception{
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        Calendar time=Calendar.getInstance();
        time.set(Calendar.DATE,time.get(Calendar.DAY_OF_MONTH)-1);
        bw.write(df.format(time.getTime())+" 1 0 0 0 0 0 0 0 0 0");
        bw.flush();
        bw.close();
        assertEquals("XSD-"+df.format(Calendar.getInstance().getTime())+"-00001",tool.number(31));
    }


    @Test
    public void testFormat1() throws Exception {
        assertEquals(tool.format(15), "00015");
    }

    @Test
    public void testFormat2() throws Exception {
        assertEquals(tool.format(91547), "91547");
    }


}
