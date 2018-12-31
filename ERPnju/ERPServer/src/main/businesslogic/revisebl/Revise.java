/*@author:吴林漾
 * 
 */
package main.businesslogic.revisebl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import main.PO.ReceiptPO;
import main.businesslogic.accountbl.Account;
import main.businesslogic.accountbl.AccountInfo;
import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.businesslogic.stockbl.Goods;
import main.businesslogic.stockbl.GoodsInfo;
import main.businesslogic.stockbl.ReceiptInfo;
import main.businesslogic.stockbl.Stock;
import main.businesslogic.util.Numbering;
import main.businesslogic.util.ReceiptTransformer;
import main.data.revisedata.ReviseData;
import main.dataservice.revisedataservice.ReviseDataService;
import main.runner.ServerRunner;
import main.vo.ClientVO;
import main.vo.FinanceActTransLineItem;
import main.vo.FinanceCashReceiptLineItem;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;
import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockOverflowReceiptVO;
import main.vo.StockUnderflowReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;

public class Revise implements ReviseInfo{
	ReviseDataService reviseData;
	ReceiptInfo receiptInfo;
	GoodsInfo goodsInfo;
	AccountInfo accountInfo;
	ClientInfo clientInfo;
	ReceiptTransformer trans;

	public Revise() {
		// TODO 自动生成的构造函数存根
		reviseData=new ReviseData();
		receiptInfo=new Stock();
		accountInfo=new Account();
		clientInfo=new Client();
		trans=new ReceiptTransformer();
	}
	
	public ArrayList<ReceiptVO> showAllReceipt(String userId){
		/*
		 * userId为null时为总经理审批时返回所有待审批单据,为其他id时返回该用户的未审批单据
		 */
		ArrayList<ReceiptVO> list=new ArrayList<ReceiptVO>();
		ArrayList<ReceiptPO> polist=reviseData.finds();
		if(!polist.isEmpty())
			for(ReceiptPO obj:polist){
				if(userId==null)
					list.add(trans.po2vo(obj));
				else if(obj.getCreatorId()!=null){
					if(obj.getCreatorId().equals(userId))
						list.add(trans.po2vo(obj));
				}
			}
		return list;
	}
	 
	 public boolean revise(ReceiptVO receiptVO,int operand){
		 goodsInfo=new Goods();
		 //operand==1为审批，operand==2为待修改
		 if(operand==1){
			 Calendar now = Calendar.getInstance();
			 
			 /**
			  * 生成发送给客户端的消息
			  * 发送消息
			  */
			 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			 String msg="单据 : "+receiptVO.getId()+" 通过审批,时间: "+df.format(now.getTime());
			 System.out.println("server reader to send msg to "+receiptVO.getCreatorId()+" content = "+msg);
			 ServerRunner.getChatServer().sendMsg(receiptVO.getCreatorId(), msg);
			 
			 receiptVO.setReviseTime(now);
			 if(reviseData.delete(receiptVO.getId())){
				 receiptInfo.writeReceipt(receiptVO);
			 }
			 else{
				 return false;
			 }
			 switch(receiptVO.getReceiptType()){
			 //审批后修改银行账户属性，客户信息，商品信息
			 case 11:
				 OutGoodsVO overflowgoods=goodsInfo.getGoods(((StockOverflowReceiptVO)receiptVO).getGoodsId());
				 overflowgoods.setStockNum(overflowgoods.getStockNum()+((StockOverflowReceiptVO)receiptVO).getAmount());
				 goodsInfo.modifyGoods(overflowgoods);
				 break;
			 case 12:
				 OutGoodsVO underflowgoods=goodsInfo.getGoods(((StockUnderflowReceiptVO)receiptVO).getGoodsId());
				 underflowgoods.setStockNum(underflowgoods.getStockNum()-((StockUnderflowReceiptVO)receiptVO).getAmount());
				 goodsInfo.modifyGoods(underflowgoods);
				 break;
			 case 13:
				 ArrayList<StockGiftReceiptVOLineItem> giftlist=((StockGiftReceiptVO)receiptVO).getGoodsList();
				 for(StockGiftReceiptVOLineItem item:giftlist){
					 OutGoodsVO giftgoods=goodsInfo.getGoods(item.getGoodsId());
					 giftgoods.setStockNum(giftgoods.getStockNum()-item.getAmount());
					 goodsInfo.modifyGoods(giftgoods);
				 }
				 break;
			 case 21:
				 ClientVO c21=clientInfo.showClient(((FinanceCollectReceiptVO)receiptVO).getClient());
				 ArrayList<FinanceActTransLineItem> list21=((FinanceCollectReceiptVO)receiptVO).getItemList();
				 for(FinanceActTransLineItem item:list21){
					 accountInfo.updateAccountBalance(item.getBankAccount(), item.getAmount());
				 }
				 c21.changePay(-((FinanceCollectReceiptVO)receiptVO).getTotalAmount());
				 clientInfo.modifyClient(c21);
				 break;
			 case 22:
				 ClientVO c22=clientInfo.showClient(((FinancePayReceiptVO)receiptVO).getClient());
				 ArrayList<FinanceActTransLineItem> list22=((FinancePayReceiptVO)receiptVO).getItemList();
				 for(FinanceActTransLineItem item:list22){
					 accountInfo.updateAccountBalance(item.getBankAccount(), -item.getAmount());
				 }
				 c22.changeCollect(-((FinancePayReceiptVO)receiptVO).getTotalAmount());
				 clientInfo.modifyClient(c22);
				 break;
			 case 23:
				 ArrayList<FinanceCashReceiptLineItem> list23=((FinanceCashReceiptVO)receiptVO).getItemList();
				 for(FinanceCashReceiptLineItem item:list23){
					 accountInfo.updateAccountBalance(((FinanceCashReceiptVO)receiptVO).getAccountName(), -item.getAmount());
				 }
				 break;
			 case 31:
				 ClientVO c31=clientInfo.showClient(((SaleReceiptVO)receiptVO).getClientId());
				 ArrayList<SaleReceiptBothVOLineItem> list31=((SaleReceiptVO)receiptVO).getItemList();
				 for(SaleReceiptBothVOLineItem item:list31){
					 OutGoodsVO goods=goodsInfo.getGoods(item.getGoodsId());
					 goods.setStockNum(goods.getStockNum()-item.getGoodsAmount());
					 goodsInfo.modifyGoods(goods);
				 }
				 c31.changePay(((SaleReceiptVO)receiptVO).getTotalAfterDiscount());
				 clientInfo.modifyClient(c31);
				 break;
			 case 32:
				 ClientVO c32=clientInfo.showClient(((SaleReturnReceiptVO)receiptVO).getClientId());
				 ArrayList<SaleReceiptBothVOLineItem> list32=((SaleReturnReceiptVO)receiptVO).getItemList();
				 for(SaleReceiptBothVOLineItem item:list32){
					 OutGoodsVO goods=goodsInfo.getGoods(item.getGoodsId());
					 goods.setStockNum(goods.getStockNum()+item.getGoodsAmount());
					 goodsInfo.modifyGoods(goods);
				 }
				 c32.changeCollect(((SaleReturnReceiptVO)receiptVO).getTotalAfterDiscount());
				 clientInfo.modifyClient(c32);
				 break;
			 case 41:
				 ClientVO c41=clientInfo.showClient(((PurchaseReceiptVO)receiptVO).getClientId());			 	
				 ArrayList<PurchaseReceiptBothVOLineItem> list41=((PurchaseReceiptVO)receiptVO).getItemList();
				 for(PurchaseReceiptBothVOLineItem item:list41){
					 OutGoodsVO goods=goodsInfo.getGoods(item.getGoodsId());
					 goods.setStockNum(goods.getStockNum()+item.getGoodsAmount());
					 goodsInfo.modifyGoods(goods);
				 }
				 c41.changeCollect(((PurchaseReceiptVO)receiptVO).getTotalValue());
				 clientInfo.modifyClient(c41);
				 break;
			 case 42:
				 ClientVO c42=clientInfo.showClient(((PurchaseReturnReceiptVO)receiptVO).getClientId());				 	
				 ArrayList<PurchaseReceiptBothVOLineItem> list42=((PurchaseReturnReceiptVO)receiptVO).getItemList();
				 for(PurchaseReceiptBothVOLineItem item:list42){
					 OutGoodsVO goods=goodsInfo.getGoods(item.getGoodsId());
					 goods.setStockNum(goods.getStockNum()-item.getGoodsAmount());
					 goodsInfo.modifyGoods(goods);
				 }
				 c42.changePay(((PurchaseReturnReceiptVO)receiptVO).getTotalValue());
				 clientInfo.modifyClient(c42);
				 break;
			 }
		 }
		 else if(operand==2)
			 //返回为草稿
			 if(!receiptInfo.addDraft(receiptVO, receiptVO.getCreatorId())){
				 return false;
			 }
		return true;
	 }

	@Override
	/*
	 * 
	 * 未审批单据存入数据库
	 */
	public boolean insertReceipt(ReceiptVO obj) {	
		//查重
		if(obj==null||reviseData.find(obj.getId())!=null)
			return false;
		obj.setId(Numbering.getInstance().number(obj.getReceiptType())); //新建单据赋编号
		reviseData.add(trans.vo2po(obj));
		return true;
	}

	@Override
	public ReceiptVO showReceipt(String id) {
		ReceiptPO obj=reviseData.find(id);		
		return trans.po2vo(obj);
	}
	
	public boolean delete(String id){
		/*
		 * 仅限test用,客户端单据不允许被删除
		 */
		ReceiptPO rp=reviseData.find(id);
		if(rp!=null){
			reviseData.delete(id);
			return true;
		}
		return false;
	}
	
	public boolean update(ReceiptVO r){
		if(r.getId()!=null&&r.getCreatorId()!=null&&r.getMakeTime()!=null)
			return reviseData.update(trans.vo2po(r));
		return false;
	}
}
