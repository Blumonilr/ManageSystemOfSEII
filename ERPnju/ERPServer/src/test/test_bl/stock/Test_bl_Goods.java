package test.test_bl.stock;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import main.businesslogic.stockbl.Goods;
import main.businesslogic.stockbl.IdAssigner;
import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;

public class Test_bl_Goods {

	static Goods goods;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		goods=new Goods();
	}

	@Test
	public void test_GoodsIdAssign() {
		String s="food";
		System.out.println(IdAssigner.assignId(s));
	}

	@Test
	public void test_getAllGoods(){
		ArrayList<OutGoodsVO>list=goods.getGoods();
		assertEquals("0001",list.get(0).getId());
	}
	
	@Test
	public void test_getGoodsById(){
		OutGoodsVO g1=goods.getGoods("0001");
		assertEquals("goods1",g1.getName());
		assertEquals("class1",g1.getClassName());
		OutGoodsVO g2=goods.getGoods("0002");
		assertEquals(null,g2);
	}
	
	@Test
	public void test_findGoods(){
		GoodsQueryItem gqi=new GoodsQueryItem();
		gqi.setName("iphone x");
		gqi.setClassName("digital device");
		ArrayList<OutGoodsVO>list=goods.findGoods(gqi);
		for(OutGoodsVO g:list){
			System.out.println(g.getName());
		}
	}
	
	@Test
	public void test_addGoods(){
		InGoodsVO g1=new InGoodsVO("iphone x", "256g", "digital device", "id10", 8588.0, 9888.0, 50, 10);
		InGoodsVO g2=new InGoodsVO("iphone 8", "256g", "digital device", "id10", 7688, 8388, 150, 20);
		InGoodsVO g3=new InGoodsVO("huawei mate 10 pro", "256g", "digital device", "id10", 4388, 5088, 200, 30);
//		assertEquals(true,goods.addGoods(g1));
//		assertEquals(true,goods.addGoods(g2));
//		assertEquals(true,goods.addGoods(g3));
		assertEquals(false,goods.addGoods(g1));
		assertEquals(false,goods.addGoods(g2));
		assertEquals(false,goods.addGoods(g3));
	}
	
	@Test
	public void test_delGoods(){
		InGoodsVO g1=new InGoodsVO("iphone 7p", "256g", "digital device", "id10", 8588.0, 9888.0, 50, 10);
		assertEquals(true,goods.addGoods(g1));
		GoodsQueryItem gqi=new GoodsQueryItem();
		gqi.setClassName("digital device");
		gqi.setName("iphone 7p");
		OutGoodsVO g=goods.findGoods(gqi).get(0);
		assertEquals(true,goods.delGoods(g.getId()));
		assertEquals(false,goods.delGoods(g.getId()));
	}
	
	@Test
	public void test_modifyGoods(){
		ArrayList<OutGoodsVO>list=goods.getGoods();
		OutGoodsVO g=list.get(1);
		g.setStockNum(1);
		assertEquals(true,goods.modifyGoods(g));
		OutGoodsVO gg=goods.getGoods(g.getId());
		assertNotNull(gg);
		assertEquals(1,gg.getStockNum());
	}
}
