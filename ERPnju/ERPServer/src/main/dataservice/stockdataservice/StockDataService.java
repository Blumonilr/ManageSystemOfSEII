package main.dataservice.stockdataservice;

import java.util.ArrayList;

import main.PO.*;

public interface StockDataService {
	//读某个类型的单据
	public ArrayList<ReceiptPO>readReceipt(int type);
	//读取所有单据信息
	public ArrayList<ReceiptPO>readReceipt();
	//根据id读单据信息
	public ReceiptPO readReceipt(String id);
	//写入单据信息
	public boolean writeReceipt(ReceiptPO obj);
	//修改单据信息
	public boolean modifyReceipt(ReceiptPO obj);
	//写入单据草稿
	public boolean addDraft(ReceiptPO obj,String userId);
	//删除单据草稿
	public boolean delDraft(String draftId,String userId);
	//修改单据草稿
	public boolean modifyDraft(ReceiptPO obj,String userId);
	//读取单个单据草稿
	public ReceiptPO readDraft(String draftId,String userId);
	//读取某用户的全部草稿
	public ArrayList<ReceiptPO>readDraft(String userId);
	//添加商品信息
	public boolean addGoods(GoodsPO obj);
	//根据id删除商品
	public boolean delGoods(String id);
	//修改商品信息
	public boolean modifyGoods(GoodsPO obj);
	//根据id查找商品
	public GoodsPO getGoods(String id);
	//显示所有商品信息
	public ArrayList<GoodsPO>getGoods();
	//添加商品分类信息
	public boolean addClass(ClassPO obj);
	//根据id删除商品分类
	public boolean delClass(String id);
	//修改商品分类信息
	public boolean modifyClass(ClassPO obj);
	//显示所有商品分类信息
	public ArrayList<ClassPO>showClass();
	//根据父类id显示商品分类信息
	public ArrayList<ClassPO>showClass(String fatherId);
	//根据id返回商品分类信息
	public ClassPO show(String id);
}
