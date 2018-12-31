package test.test_bl.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import main.businesslogic.clientbl.Client;
import main.businesslogic.strategybl.Strategy;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.ClientVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.StrategyVO;

public class TestStrategybl {
	static Strategy s;
	static Client c;
	
	@BeforeClass
	public static void setUpTest(){
		s=new Strategy();
		c=new Client();
	}
	
/*	@Test
	public void Test(){
		Map<OutGoodsVO,Integer> g=new HashMap<>();
		OutGoodsVO g1=new OutGoodsVO(); g1.setName("灯"); g1.setOutPrice(10.0); g1.setId("001");
		OutGoodsVO g2=new OutGoodsVO(); g2.setName("灯泡"); g2.setOutPrice(5.0); g2.setId("002");
		g.put(g1, 1);
		g.put(g2, 2);
		Calendar c1=new GregorianCalendar(); c1.set(2017, 12,22);
		Calendar c2=new GregorianCalendar(); c2.set(2017,12,23);
		ClientGiftStrategyVO s1=new ClientGiftStrategyVO(); s1.setID("0001"); s1.setLevel(1); s1.setList(g); s1.setStartTime(c1); s1.setEndTime(c2);
		ClientCouponStrategyVO s2=new ClientCouponStrategyVO(); s2.setID("0002"); s2.setLevel(2); s2.setCoupon(100);
		ClientDiscountStrategyVO s3=new ClientDiscountStrategyVO(); s3.setID("0003"); s3.setLevel(2); s3.setDiscount(0.85);
		GiftStrategyVO s4=new GiftStrategyVO(); s4.setID("0004"); s4.setLeastTotal(200); s4.setList(g);
		DiscountStrategyVO s5=new DiscountStrategyVO(); s5.setID("0005"); s5.setLeastTotal(100); s5.setDiscount(0.88);
		BargainPackageStrategyVO s6=new BargainPackageStrategyVO(); s6.setID("0006"); s6.setGoodslist(g); s6.setTotal(15); 
		s.removeStrategy("0001"); s.removeStrategy("0002"); s.removeStrategy("0003"); s.removeStrategy("0004"); s.removeStrategy("0005"); s.removeStrategy("0006");
		s.makeStrategy(s1); s.makeStrategy(s2); s.makeStrategy(s3); s.makeStrategy(s4); s.makeStrategy(s5); s.makeStrategy(s6);
		assertEquals(1,((ClientGiftStrategyVO)s.getStrategyById("0001")).getLevel());
		Map<OutGoodsVO,Integer> m=((ClientGiftStrategyVO)s.getStrategyById("0001")).getList();
		for(OutGoodsVO key:m.keySet()){
			System.out.println(m.get(key));
		}
		assertEquals(0.85,((ClientDiscountStrategyVO)s.getStrategyById("0003")).getDiscount(),0.01);
		assertEquals(100,((ClientCouponStrategyVO)s.getStrategyById("0002")).getCoupon(),0.01);
		assertEquals(200,((GiftStrategyVO)s.getStrategyById("0004")).getLeastTotal(),0.01);
		assertEquals(0.88,((DiscountStrategyVO)s.getStrategyById("0005")).getDiscount(),0.01);
		assertEquals(15,((BargainPackageStrategyVO)s.getStrategyById("0006")).getTotal(),0.01);
		assertEquals(c1,((ClientGiftStrategyVO)s.getStrategyById("0001")).getStartTime());
		assertEquals(null,s.getStrategyById("0007"));
		
		ArrayList<SaleReceiptBothVOLineItem> list=new ArrayList<>();
		SaleReceiptBothVOLineItem sr1=new SaleReceiptBothVOLineItem("001","灯","01",1,10.0,null);
		SaleReceiptBothVOLineItem sr2=new SaleReceiptBothVOLineItem("002","灯泡","02",2,5.0,null);
		list.add(sr1); list.add(sr2);
		
		SaleReceiptVO sale=new SaleReceiptVO("0011",null,null, "creator1", "clientid1", "clerk1","stock1", 0, 0, null, list, null, 0);
		System.out.println(sale.getClientId());
		ArrayList<StrategyVO> strs=s.adviseStrategy(sale);
		for(StrategyVO str:strs){
			System.out.println(str.getID());
		}
		
		c.delClient("client1");
		s.removeStrategy("0001"); s.removeStrategy("0002"); s.removeStrategy("0003"); s.removeStrategy("0004"); s.removeStrategy("0005"); s.removeStrategy("0006");
	}
	*/
	
	@Test
	public void Test1(){
		ArrayList<StrategyVO> slist=s.findAllStrategy();
		for(StrategyVO s:slist){
			if(s.getType()==2){
				System.out.println(s.getID());
				Map<OutGoodsVO,Integer> glist1=((ClientGiftStrategyVO)s).getList();
				for(Map.Entry<OutGoodsVO, Integer> e:glist1.entrySet()){
					System.out.println(e.getKey().getName()+" ");
				}
			}
		}
	}
}
