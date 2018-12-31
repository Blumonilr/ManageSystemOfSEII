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
public class GoodsInfo_MockObj implements GoodsInfo {

	ArrayList<OutGoodsVO>list;
	private static int counter=1;
	
	public GoodsInfo_MockObj(){
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
	

	@Override
	public ArrayList<OutGoodsVO> getGoods() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public OutGoodsVO getGoods(String id) {
		// TODO Auto-generated method stub
		Calendar c=Calendar.getInstance();
		c.set(2017, 10, 16);
		OutGoodsVO g1=new OutGoodsVO("iphoneX", id, "256g", "手机", "0004", 1, 100, 1, 100, 100, 10, c);
		
		return g1;
	}

	@Override
	public ArrayList<OutGoodsVO> findGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		ArrayList<OutGoodsVO>res=new ArrayList<>();
		OutGoodsVO g=new OutGoodsVO();
		if(obj.getName()==null){
			g.setName("testGoods");
		}else{
			g.setName(obj.getName());
		}
		if(obj.getId()==null){
			g.setId("0000-test");
		}else{
			g.setId(obj.getId());
		}
		if(obj.getXh()==null){
			g.setXh("testGoods");
		}else{
			g.setXh(obj.getXh());
		}
		if(obj.getClassName()==null){
			g.setClassName("testGoods");
		}else{
			g.setClassName(obj.getClassName());
		}
		if(obj.getClassId()==null){
			g.setClassId("testGoods");
		}else{
			g.setClassId(obj.getClassId());
		}
		if(obj.getDay()==null){
			Calendar c=Calendar.getInstance();
			c.set(2017, 10, 16);
			g.setDay(c);
		}else{
			g.setDay(obj.getDay());
		}
		
		res.add(g);
		return res;
	}

	@Override
	public boolean addGoods(InGoodsVO obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean modifyGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		return true;
	}

}
