package test.test_data.stock;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.PO.ClassPO;
import main.PO.GoodsPO;
import main.data.stockdata.ClassDataHelper;
import main.data.stockdata.GoodsDataHelper;

public class Test_ClassDataHelper {

	static ClassDataHelper cdh;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cdh=new ClassDataHelper();
		cdh.setGDH(new GoodsDataHelper());
	}

	@Test
	public void test_addClass1() {
		ClassPO c=new ClassPO("id1","class1",null);
//		assertEquals(true,cdh.addClass(c));
		assertEquals(false,cdh.addClass(c));
	}

	@Test
	public void test_addClass2() {
		ClassPO c=new ClassPO("id3", "class3", "id1");
//		assertEquals(true,cdh.addClass(c));
		assertEquals(false,cdh.addClass(c));
	}
	
	@Test
	public void test_show1(){
		ClassPO c=cdh.show("id1");
		assertEquals("class1",c.getName());
		assertEquals(null,c.getFatherId());
		ClassPO c1=cdh.show("id3");
		assertEquals("class3",c1.getName());
		assertEquals("id1",c1.getFatherId());
	}
	
	@Test
	public void test_show2(){
		assertEquals(null,cdh.show(null));
	}
	
	@Test
	public void test_delClass1(){
		ClassPO c=new ClassPO("id2", "class2", "id1");
		;
		assertEquals(true,cdh.addClass(c));
		assertEquals(true,cdh.delClass("id2"));
		assertEquals(false,cdh.delClass("id2"));
	}
	
	@Test
	public void test_delClass2(){
		ClassPO f=new ClassPO("id4","dad",null);
		ClassPO c=new ClassPO("id5", "son", "id4");
		assertEquals(true,cdh.addClass(f));
		assertEquals(true,cdh.addClass(c));
		assertEquals(true,cdh.delClass("id4"));
		assertEquals(null,cdh.show("id5"));
		assertEquals(null,cdh.show("id4"));
	}
	
	@Test
	public void test_delClass3(){
		ClassPO c=new ClassPO("id6","class6","id3");
		assertEquals(true,cdh.addClass(c));
		ArrayList<String>list=cdh.show("id3").getSubclasses();
		assertEquals(true,list.size()==1&&list.get(0).equals("id6"));
		
		assertEquals(true,cdh.delClass("id6"));
		list=cdh.show("id3").getSubclasses();
		assertEquals(true,list.size()==0);
		
	}
	
	@Test
	public void test_delClass4(){
		GoodsDataHelper gdh=new GoodsDataHelper();
		
		ClassPO c=cdh.show("746573745f64656c676f6f6473");
		assertNotNull(c);
		
		ArrayList<String>subc=c.getSubclasses();
		ArrayList<String>subg=c.getSubgoods();
		for(String s:subc)
			System.out.println(s);
		for(String s:subg)
			System.out.println(s);
//		assertEquals(0,c.getSubclasses().size());
//		assertEquals(1,c.getSubgoods().size());
//		
//		String goodsid=c.getSubgoods().get(0);
//		GoodsPO g=gdh.getGoods(goodsid);
//		assertNotNull(g);
//		
//		assertEquals(true,cdh.delClass("746573745f64656c676f6f6473"));
//		g=gdh.getGoods(goodsid);
//		assertNull(g);
	}
	
	@Test
	public void test_showClass(){
		ArrayList<ClassPO>list=cdh.showClass();
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i));
	}
	
	@Test
	public void test_showClassByFatherId(){
		ArrayList<ClassPO>list=cdh.showClass("id1");
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i));
	}
}
