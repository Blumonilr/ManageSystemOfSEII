package main.businesslogic.stockbl;

import main.vo.ClassVO;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * 
 * @author qyc
 *
 */
public class Goods_MockObj {

	ArrayList<OutGoodsVO>list;
	
	public Goods_MockObj(){
		list=new ArrayList<>();
		ClassVO c1=new ClassVO("0001","总分类",null);
		ClassVO c2=new ClassVO("0002","食品","总分类");		
		ClassVO c3=new ClassVO("0003","数码产品","总分类");		
		ClassVO c4=new ClassVO("0004","手机","数码产品");
		ClassVO c5=new ClassVO("0005","蔬菜","食品");
		
		Calendar c=Calendar.getInstance();
		c.set(2017, 10, 16);
		OutGoodsVO g1=new OutGoodsVO("iphoneX", "0004-ipx", "256g", "手机", "0004", 1, 100, 1, 100, 100, 10, c);
		OutGoodsVO g2=new OutGoodsVO("tomato", "0002-tat", "100g", "蔬菜", "0005", 0.1, 1, 0.1, 1, 10000, 100, c);
		list.add(g2);
		list.add(g1);
	}
	
	public boolean addGoods(InGoodsVO obj){
		return true;
	}
	
	public boolean delGoods(String id){
		return true;
	}
	
	public boolean modifyGoods(OutGoodsVO obj){
		return true;
	}
	
	public ArrayList<OutGoodsVO>findGoods(OutGoodsVO obj){
		return list;
	}
	
	public OutGoodsVO getGoods(String id){
		Calendar c=Calendar.getInstance();
		c.set(2017, 10, 16);
		OutGoodsVO g1=new OutGoodsVO("iphoneX", id, "256g", "手机", "0004", 1, 100, 1, 100, 100, 10, c);
		
		return g1;
	}
	
	public ArrayList<OutGoodsVO>getGoods(){
		return list;
	}

}
