package main.businesslogic.stockbl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogic.util.ReceiptTransformer;
import main.businesslogicservice.stockblservice.StockblService;
import main.vo.ClassVO;
import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;
import main.vo.ReceiptVO;
import main.vo.StockCheckResultVO;
import main.vo.StockRecordVO;

public class StockController implements StockblService{

	private Classes classes;
	private Goods goods;
	private Stock stock;
	
	public StockController(){
		classes=new Classes();
		goods=new Goods();
		stock=new Stock();
	}
	
	@Override
	public boolean addClass(ClassVO obj) {
		// TODO 自动生成的方法存根
		return classes.addClass(obj);
	}

	@Override
	public boolean delClass(String id) {
		// TODO 自动生成的方法存根
		return classes.delClass(id);
	}

	@Override
	public boolean modifyClass(ClassVO obj) {
		// TODO 自动生成的方法存根
		return classes.modifyClass(obj);
	}

	@Override
	public ArrayList<ClassVO> showClass() {
		// TODO 自动生成的方法存根
		return classes.showClass();
	}

	@Override
	public boolean addGoods(InGoodsVO obj) {
		// TODO 自动生成的方法存根
		return goods.addGoods(obj);
	}

	@Override
	public boolean delGoods(String id) {
		// TODO 自动生成的方法存根
		return goods.delGoods(id);
	}

	@Override
	public boolean modifyGoods(OutGoodsVO obj) {
		// TODO 自动生成的方法存根
		return goods.modifyGoods(obj);
	}

	@Override
	public ArrayList<OutGoodsVO> findGoods(GoodsQueryItem obj) {
		// TODO 自动生成的方法存根
		return goods.findGoods(obj);
	}

	@Override
	public OutGoodsVO getGoods(String id) {
		// TODO 自动生成的方法存根
		return goods.getGoods(id);
	}

	@Override
	public ArrayList<OutGoodsVO> getGoods() {
		// TODO 自动生成的方法存根
		return goods.getGoods();
	}

	@Override
	public StockCheckResultVO stockCheck(String goodsId,Calendar start, Calendar end) {
		// TODO 自动生成的方法存根
		return stock.stockCheck(goodsId,start,end);
	}

	@Override
	public ReceiptVO stockCheckDetail(String id) {
		// TODO 自动生成的方法存根
		return stock.readReceipt(id);
	}

	@Override
	public ArrayList<OutGoodsVO> stockInventory() {
		// TODO 自动生成的方法存根
		return goods.getGoods();
	}

	@Override
	public ClassVO showClass(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return classes.showClass(id);
	}

	@Override
	public boolean addReceipt(ReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stock.addReceipt(obj);
	}

//	在客户端导出excel
//	@Override
//	public ArrayList<String> stockInventoryExcel() {
//		// TODO 自动生成的方法存根
//		ArrayList<OutGoodsVO>list=goods.getGoods();
//		return null;
//	}

}
