package test.test_data.strategy;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import main.PO.ClientCouponStrategyPO;
import main.PO.ClientGiftStrategyPO;
import main.data.strategydata.StrategyData;
import main.vo.OutGoodsVO;

public class TestStrategy {
	static StrategyData strategyData;
	
	public TestStrategy(){
		strategyData=new StrategyData();
	}
	
/*	@Test
	public void test(){
		ClientCouponStrategyPO test=new ClientCouponStrategyPO();
		test.setCoupon(100.0);
		test.setID("0001");
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		c1.set(2017, 12, 1, 8, 00);
		c2.set(2017, 12, 3, 8, 00);
		test.setStartTime(c1);
		test.setEndTime(c2);
		test.setLevel(1);
		assertEquals(true,strategyData.insert(test));
		assertEquals(false,strategyData.insert(test));
		assertEquals("0001",strategyData.find("0001").getID());
		assertEquals(c1,strategyData.find("0001").getStartTime());
		assertEquals(true,strategyData.delete(test));
	}*/
	
/*	@Test
	public void test1(){
		ClientGiftStrategyPO po=new ClientGiftStrategyPO();
		po.setLevel(5);
		po.setID("test0005");
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		c1.set(2017, 12, 1, 8, 00);
		c2.set(2017, 12, 31, 8, 00);
		po.setStartTime(c1);
		po.setEndTime(c2);
		OutGoodsVO g1=new OutGoodsVO("iphone 8","12121","256g","digital device","",6988.0,8799.0,6988.0,8799.0,20,2,Calendar.getInstance());
		OutGoodsVO g2=new OutGoodsVO("iphone 7","12121","256g","digital device","",6988.0,8799.0,6988.0,8799.0,20,2,Calendar.getInstance());
		Map<OutGoodsVO,Integer> giftList=new HashMap<OutGoodsVO,Integer>();
		giftList.put(g1, 3);
		giftList.put(g2, 4);
		po.setList(giftList);giftList.put(g2, 4);
		
		assertEquals(true,strategyData.insert(po));
	}
	*/

}

