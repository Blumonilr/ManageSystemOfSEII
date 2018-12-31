package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import main.PO.GoodsPO;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.data.stockdata.StockDataServiceImpl;
import main.dataservice.stockdataservice.StockDataService;
import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;
import main.vo.StockAlarmReceiptVO;

public class Goods implements GoodsInfo {

	//若有新的筛选条件，加在这里，记得修改goodsqueryitem的flags大小！
	private FindMethod[]methods={
			new FindbyName(),
			new FindbyClassName(),
			new FindbyPrice(),
			new FindbyPrice(),
			new FindbyTime(),
			new FindbyTime()
	};
	private StockDataService sds;
//	private ReviseInfo revise;
	
	public Goods(){
		sds=new StockDataServiceImpl();
//		revise=new Revise();
	}
	
	@Override
	public OutGoodsVO getGoods(String id) {
		// TODO Auto-generated method stub
		return po2vo(sds.getGoods(id));
	}

	@Override
	public ArrayList<OutGoodsVO> getGoods() {
		// TODO Auto-generated method stub
		ArrayList<OutGoodsVO>res=new ArrayList<>();
		ArrayList<GoodsPO>list=sds.getGoods();
		for(GoodsPO g:list){
			res.add(po2vo(g));
		}
		return res;
	}

	@Override
	public ArrayList<OutGoodsVO> findGoods(GoodsQueryItem obj) {
		// TODO Auto-generated method stub
		ArrayList<OutGoodsVO>res=getGoods();
		for(int i=0;i<obj.flags.length;i++){
			res=methods[i].find(res,obj);
		}
		return res;
	}

	@Override
	public synchronized boolean addGoods(InGoodsVO obj) {
		// TODO Auto-generated method stub
		//分配一个商品id
		obj.setId(IdAssigner.assignId(obj.getClassId()));
		return sds.addGoods(vo2po(obj));
	}

	@Override
	/**
	 * 商品数量、价格为负数或商品不存在时会return false
	 * 只允许通过此方法更改和数量、价格有关的属性！！！
	 */
	public synchronized boolean modifyGoods(OutGoodsVO obj) {
		//消除循环依赖!!!
		ReviseInfo revise=new Revise();
		// 先检查状态是否合法
		if(obj.getAlarmNum()<0||obj.getInPrice()<0||obj.getOutPrice()<0
				||obj.getStockNum()<0)
			return false;
		
		StockAlarmReceiptVO sar=null;
		if(obj.getStockNum()<obj.getAlarmNum()){
			String id=IdAssigner.assignId("BJD-");
			Calendar mt=new GregorianCalendar();
			//审批后才加上审批时间
			sar=new StockAlarmReceiptVO(id, mt, null, obj.getId(), obj.getName()
					, obj.getXh(),  obj.getAlarmNum(), obj.getStockNum());
			//new StockAlarmReceiptVO(id,obj.getId(), ,);
		}
		boolean flag=sds.modifyGoods(vo2po(obj));
		//修改成功才报警
		if(flag){
			revise.insertReceipt(sar);
		}
		
		return flag;
	}

	public synchronized boolean delGoods(String id) {
		// TODO Auto-generated method stub
		return sds.delGoods(id);
	}

	private OutGoodsVO po2vo(GoodsPO obj){
		if(obj==null)
			return null;
		OutGoodsVO g=new OutGoodsVO(obj.getName(), obj.getId(), obj.getXh(),
				obj.getClassName(), obj.getClassId(), obj.getInPrice(), obj.getOutPrice(), 
				obj.getLastInPrice(), obj.getLastOutPrice(), obj.getNum(), obj.getAlarmNum(),
				obj.getDay());
		return g;
	}
	private GoodsPO vo2po(InGoodsVO obj){
		if(obj==null)
			return null;
		//lastinPrice\lastoutPrice应该在构造器里自动赋值
		GoodsPO g=new GoodsPO( obj.getId(), obj.getName(), obj.getXh(), obj.getInPrice(),
				obj.getOutPrice(), obj.getIniNum(), obj.getAlarmNum(), 
				obj.getClassName(), obj.getClassId(), obj.getDay());
		return g;
		
	}
	private GoodsPO vo2po(OutGoodsVO obj){
		if(obj==null)
			return null;
		GoodsPO g=new GoodsPO(obj.getId(), obj.getName(), obj.getXh(), obj.getInPrice(), 
				obj.getLatestip(), obj.getOutPrice(), obj.getLatestop(), obj.getStockNum(), 
				obj.getAlarmNum(), obj.getClassName(), obj.getClassId(), obj.getDay());
		return g;
		
	}
}
