package test.test_data.stock;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.PO.GoodsPO;
import main.data.stockdata.ClassDataHelper;
import main.data.stockdata.GoodsDataHelper;

public class test_GoodsDataHelper {

	static GoodsDataHelper gdh;
	static ClassDataHelper cdh;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gdh=new GoodsDataHelper();
		cdh=new ClassDataHelper();
		gdh.setCDH(cdh);
		cdh.setGDH(gdh);
	}

	@Test
	public void test_addGoods() {
		GoodsPO g=new GoodsPO("0001","goods1","id3");
		
//		assertEquals(true,gdh.addGoods(g));
		assertEquals(false,gdh.addGoods(g));
		GoodsPO g1=new GoodsPO("0010","goods1","id1");
		GoodsPO g2=new GoodsPO("0011","goods1","id31");
		assertEquals(false,gdh.addGoods(g1));
		assertEquals(false,gdh.addGoods(g2));
	}

	@Test
	public void test_getGoodsById(){
		assertEquals("goods1",gdh.getGoods("0001").getName());
		assertEquals("id3",gdh.getGoods("0001").getClassId());
		assertEquals(null,gdh.getGoods("0002"));
	}
	
	@Test
	public void test_delete(){
		GoodsPO g=new GoodsPO("0002","goods2","id10");
		assertEquals(true,gdh.addGoods(g));
		assertEquals(true,gdh.delGoods("0002"));
		assertEquals(false,gdh.delGoods("0002"));
	}
	
	@Test
	public void test_getGoods(){
		ArrayList<GoodsPO>list=gdh.getGoods();
		for(GoodsPO g:list)
			System.out.println(g.getName()+'/'+g.getClassId()+'/'+g.getId());
	}
	
	@Test
	public void test_modifyGoods(){
		GoodsPO g=gdh.getGoods("0001");
		g.setAlarmNum(1002);
		g.changeNum(100);
		g.changeNum(-100);
		g.setClassName("class1");
		assertEquals(true,gdh.modifyGoods(g));
		GoodsPO gg=gdh.getGoods("0001");
		assertEquals(1002,gg.getAlarmNum());
		assertEquals(0,gg.getNum());
		assertEquals("class1",gg.getClassName());
	}
}
