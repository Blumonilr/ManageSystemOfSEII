package main.businesslogic.stockbl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import main.businesslogicservice.stockblservice.StockblService;
import main.vo.*;

/**
 * 
 * @author qyc
 *
 */
public class StockController_Stub implements StockblService {

	Stock_MockObj stock=new Stock_MockObj();
	Goods_MockObj goods=new Goods_MockObj();
	Class_MockObj classes=new Class_MockObj();
	@Override
	public boolean addClass(ClassVO obj) {
		// TODO Auto-generated method stub
		return classes.addClass(obj);
	}
	@Override
	public boolean delClass(String id) {
		// TODO Auto-generated method stub
		return classes.delClass(id);
	}
	@Override
	public boolean modifyClass(ClassVO obj) {
		// TODO Auto-generated method stub
		return classes.modifyClass(obj);
	}
	@Override
	public ArrayList<ClassVO> showClass() {
		// TODO Auto-generated method stub
		return classes.showClass();
	}
	@Override
	public boolean addGoods(InGoodsVO obj) {
		// TODO Auto-generated method stub
		return goods.addGoods(obj);
	}
	@Override
	public boolean delGoods(String id) {
		// TODO Auto-generated method stub
		return goods.delGoods(id);
	}
	@Override
	public boolean modifyGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		return goods.modifyGoods(obj);
	}
	@Override
	public ArrayList<OutGoodsVO> findGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		return goods.findGoods(obj);
	}
	@Override
	public OutGoodsVO getGoods(String id) {
		// TODO Auto-generated method stub
		return goods.getGoods(id);
	}
	@Override
	public ArrayList<OutGoodsVO> getGoods() {
		// TODO Auto-generated method stub
		return goods.getGoods();
	}
	@Override
	public ArrayList<StockRecordVO> stockCheck(Calendar start, Calendar end) {
		// TODO Auto-generated method stub
		return stock.stockCheck(start, end);
	}
	@Override
	public ReceiptVO stockCheckDetail(String id) {
		// TODO Auto-generated method stub
		return stock.readReceipt(id);
	}
	@Override
	public ArrayList<OutGoodsVO> stockInventory() {
		// TODO Auto-generated method stub
		return goods.getGoods();
	}
	@Override
	public ArrayList<String> stockInventoryExcel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean setAlarm(String id, long limit) {
		// TODO Auto-generated method stub
//		InGoodsVO obj=new InGoodsVO();
		OutGoodsVO g=new OutGoodsVO();
		g.setId(id);
		g.setAlarmNum(limit);
		return goods.modifyGoods(g);
	}
	@Override
	public ClassVO showClass(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<OutGoodsVO> findGoods(GoodsQueryItem obj) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StockCheckResultVO stockCheck(String goodsId, Calendar start, Calendar end) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean addReceipt(ReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
