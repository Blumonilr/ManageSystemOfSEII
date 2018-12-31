package main.data.stockdata;

import java.util.ArrayList;

import main.PO.ClassPO;
import main.PO.GoodsPO;
import main.PO.ReceiptPO;
import main.dataservice.stockdataservice.StockDataService;
import main.vo.GoodsQueryItem;

public class StockDataServiceImpl implements StockDataService {

	private ClassDataHelper classes;
	private GoodsDataHelper goods;
	private StockDataHelper stock;
	
	public StockDataServiceImpl(){
		goods=new GoodsDataHelper();
		classes=new ClassDataHelper();
		classes.setGDH(goods);
		goods.setCDH(classes);
		stock=new StockDataHelper();
	}
	
	@Override
	public ArrayList<ReceiptPO> readReceipt(int type) {
		// TODO Auto-generated method stub
		return stock.readReceipt(type);
	}

	@Override
	public ReceiptPO readReceipt(String id) {
		// TODO Auto-generated method stub
		return stock.readReceipt(id);
	}

	@Override
	public boolean writeReceipt(ReceiptPO obj) {
		// TODO Auto-generated method stub
		return stock.writeReceipt(obj);
	}

	@Override
	public boolean addGoods(GoodsPO obj) {
		// TODO Auto-generated method stub
		return goods.addGoods(obj);
	}

	@Override
	public boolean delGoods(String id) {
		// TODO Auto-generated method stub
		return goods.delGoods(id);
	}

	@Override
	public boolean modifyGoods(GoodsPO obj) {
		// TODO Auto-generated method stub
		return goods.modifyGoods(obj);
	}

	@Override
	public GoodsPO getGoods(String id) {
		// TODO Auto-generated method stub
		return goods.getGoods(id);
	}

	@Override
	public ArrayList<GoodsPO> getGoods() {
		// TODO Auto-generated method stub
		return goods.getGoods();
	}

	@Override
	public boolean addClass(ClassPO obj) {
		// TODO Auto-generated method stub
		return classes.addClass(obj);
	}

	@Override
	public boolean delClass(String id) {
		// TODO Auto-generated method stub
		return classes.delClass(id);
	}

	@Override
	public boolean modifyClass(ClassPO obj) {
		// TODO Auto-generated method stub
		return classes.modifyClass(obj);
	}

	@Override
	public ArrayList<ClassPO> showClass() {
		// TODO Auto-generated method stub
		return classes.showClass();
	}

	@Override
	public ArrayList<ClassPO> showClass(String fatherId) {
		// TODO Auto-generated method stub
		return classes.showClass(fatherId);
	}

	@Override
	public ClassPO show(String id) {
		// TODO Auto-generated method stub
		return classes.show(id);
	}

	@Override
	public ArrayList<ReceiptPO> readReceipt() {
		// TODO Auto-generated method stub
		return stock.readReceipt();
	}

	@Override
	public boolean modifyReceipt(ReceiptPO obj) {
		// TODO Auto-generated method stub
		return stock.modifyReceipt(obj);
	}

	@Override
	public boolean addDraft(ReceiptPO obj, String userId) {
		// TODO Auto-generated method stub
		return stock.addDraft(obj, userId);
	}

	@Override
	public boolean delDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		return stock.delDraft(draftId, userId);
	}

	@Override
	public boolean modifyDraft(ReceiptPO obj,String userId) {
		// TODO Auto-generated method stub
		return stock.modifyDraft(obj,userId);
	}

	@Override
	public ReceiptPO readDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		return stock.readDraft(draftId, userId);
	}

	@Override
	public ArrayList<ReceiptPO> readDraft(String userId) {
		// TODO Auto-generated method stub
		return stock.readDraft(userId);
	}
	

}
