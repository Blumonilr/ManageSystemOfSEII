package main.businesslogic.salebl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.businesslogic.stockbl.GoodsInfo;
import main.businesslogic.stockbl.ReceiptInfo;
import main.businesslogic.stockbl.Stock;
import main.businesslogic.strategybl.Strategy;
import main.businesslogic.strategybl.StrategyInfo;
import main.data.stockdata.StockDataHelper;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.ClientVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;
import main.vo.StrategyVO;

/**
 * 
 * @author Kate
 *
 */
public class Sale {
	ClientInfo clientInfo;
	StrategyInfo strategyInfo;
	GoodsInfo goodsInfo;
	ReceiptInfo receiptInfo;
	ReviseInfo reviseInfo;
	
	public Sale(){
		clientInfo=new Client();
		strategyInfo=new Strategy();
		receiptInfo=new Stock();
		reviseInfo=new Revise();
	}
	
	
	public ArrayList<OutGoodsVO> showGoods() {//done
		ArrayList<OutGoodsVO> goodsList=goodsInfo.getGoods();
		return goodsList;
	}

	
	public ArrayList<ClientVO> showClient() {//done
		ArrayList<ClientVO> clientList=clientInfo.show();
		return clientList;
	}

	
	public SaleReceiptVO makeSaleReceipt(SaleReceiptVO obj) {//done
		boolean result=reviseInfo.insertReceipt(obj);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public SaleReturnReceiptVO makeSaleReturnReceipt(SaleReturnReceiptVO obj) {//done
		boolean result=reviseInfo.insertReceipt(obj);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public ArrayList<StrategyVO> showValidStrategy(SaleReceiptBothVO saleReceipt) {//done
		ArrayList<StrategyVO> list=strategyInfo.adviseStrategy(saleReceipt);
		
		return list;
	}
	
	/**
	 * 赠品策略,包括总价赠品策略（5）和客户等级赠品策略（2）
	 * @param strategy
	 * @return
	 */
	public StockGiftReceiptVO chooseGiftStrategy(String strategyId,String userId,SaleReceiptBothVO receipt) {
		StrategyVO strategy=strategyInfo.getStrategyById(strategyId);
		int type=strategy.getType();
		StockGiftReceiptVO giftReceipt=null;
		switch(type){
		case 2:
			//客户等级赠品策略
			
			giftReceipt=this.makeClientGiftReceipt((ClientGiftStrategyVO)strategy, userId);
			
			reviseInfo.insertReceipt(giftReceipt);
			
			receipt.setStrategyNote("客户等级赠品策略");
			
			break;
		case 5:
			//总价赠品策略
			
			giftReceipt=this.makeGiftReceipt((GiftStrategyVO)strategy, userId);
			
			reviseInfo.insertReceipt(giftReceipt);
			
			receipt.setStrategyNote("总价赠品策略");
			
			break;
		default:
			//错误
			
		}
		return giftReceipt;
	}
	
	/**
	 * 其他销售策略1.客户等级折让  3.客户等级代金券 4.总价折让  6.特价包
	 * @param strategyId
	 * @return
	 */
	public boolean chooseOtherStrategy(String strategyId,String userId,SaleReceiptBothVO receipt) {//
		// TODO Auto-generated method stub
		StrategyVO vo=strategyInfo.getStrategyById(strategyId);
		switch(vo.getType()){
		case 1:
			ClientDiscountStrategyVO strategy1=(ClientDiscountStrategyVO)(strategyInfo.getStrategyById(strategyId));
			
			receipt.setDiscount(strategy1.getDiscount());
			
			receipt.setStrategyNote("客户等级折让策略");
			break;
		case 3:
			ClientCouponStrategyVO strategy3=(ClientCouponStrategyVO)(strategyInfo.getStrategyById(strategyId));
			
			receipt.setValueOfVoucher(strategy3.getCoupon());
			
			receipt.setStrategyNote("客户等级代金券策略");
			break;
		case 4:
			DiscountStrategyVO strategy4=(DiscountStrategyVO)(strategyInfo.getStrategyById(strategyId));
			
			receipt.setDiscount(strategy4.getDiscount());
			
			receipt.setStrategyNote("总价折让策略");
			break;
		case 6://有点麻烦
			BargainPackageStrategyVO strategy6=(BargainPackageStrategyVO)(strategyInfo.getStrategyById(strategyId));
			
			Map<OutGoodsVO, Integer> list=strategy6.getGoodslist();
			Iterator iterator=list.entrySet().iterator();
			String strategyNote="特价包内容:"+'\n';
			double originValue=0.0;
			double totalOfPackage=strategy6.getTotal();
			while(iterator.hasNext()){
				Map.Entry<OutGoodsVO, Integer> entry=(Entry<OutGoodsVO, Integer>) iterator.next();
				OutGoodsVO goods=entry.getKey();
				double price=goodsInfo.getGoods(goods.getId()).getInPrice();//或是直接从outGoodsVO拿
				int number=entry.getValue();
				double subtotal=price*number;
				originValue+=subtotal;
				strategyNote+="商品：  "+goods.getName()+"  "+"数量：  "+number+'\n';
			}
					
			double reductionOfPackage=originValue-totalOfPackage;
			strategyNote+="特价包总价： "+totalOfPackage+'\n';
			strategyNote+="特价包原总价： "+originValue+'\n';
			strategyNote+="特价包优惠： "+reductionOfPackage+'\n';

			receipt.setReductionOfPackage(reductionOfPackage);
			
			receipt.setStrategyNote(strategyNote);
			break;
		default:
			return false;	
		}
		return true;
	}


	
	public ArrayList<SaleReceiptBothVO> showMyUnrevisedReceipts(String userId,int operand, Calendar startTime, Calendar endTime) {//done
		ArrayList<ReceiptVO> list=reviseInfo.showAllReceipt(userId);
		ArrayList<SaleReceiptBothVO> list31=new ArrayList<SaleReceiptBothVO>();
		ArrayList<SaleReceiptBothVO> list32=new ArrayList<SaleReceiptBothVO>();
		for(ReceiptVO vo:list){
			if(vo.getReceiptType()==31&&vo.getCreatorId().equals(userId)){
				//purchase
				if(vo.getMakeTime().after(startTime)&&vo.getMakeTime().before(endTime)){
					list31.add((SaleReceiptBothVO)vo);
				}
			}
			else if(vo.getReceiptType()==32&&vo.getCreatorId().equals(userId)){
				//puchasereturn 
				if(vo.getMakeTime().after(startTime)&&vo.getMakeTime().before(endTime)){
					list32.add((SaleReceiptBothVO)vo);
				}
			}
			else{
				//doNothing
			}
		}
		
		if(operand==31){
			return list31;
		}
		else if(operand==32){
			return list32;
		}
		else{
			return null;
		}
	}
	
	
	public SaleReceiptVO makeSaleReceiptDraft(SaleReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean res=receiptInfo.addDraft(obj, userId);
		if(res){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public SaleReturnReceiptVO makeSaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean res=receiptInfo.addDraft(obj, userId);
		if(res){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public ArrayList<SaleReceiptVO> showMySaleReceiptDrafts(String userId) {//done
		ArrayList<ReceiptVO> list=receiptInfo.readDraft(userId);
		ArrayList<SaleReceiptVO> result=new ArrayList<SaleReceiptVO>();
		for(ReceiptVO r:list){
			if(r.getReceiptType()==31){
				result.add((SaleReceiptVO)r);
			}
		}
		return result;
	}


	public ArrayList<SaleReturnReceiptVO> showMySaleReturnReceiptDrafts(String userId) {//done
		// TODO Auto-generated method stub
		ArrayList<ReceiptVO> list=receiptInfo.readDraft(userId);
		ArrayList<SaleReturnReceiptVO> result=new ArrayList<SaleReturnReceiptVO>();
		for(ReceiptVO r:list){
			if(r.getReceiptType()==32){
				result.add((SaleReturnReceiptVO)r);
			}
		}
		return result;
	}
	
	
	public SaleReceiptVO modifySaleReceiptDraft(SaleReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean res=receiptInfo.modifyDraft(obj, userId);
		if(res){
			return obj;
		}
		else{
			return null;
		}
	}
	

	public SaleReturnReceiptVO modifySaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean res=receiptInfo.modifyDraft(obj, userId);
		if(res){
			return obj;
		}
		else{
			return null;
		}
	}
	

	public boolean deleteDraft(String draftId, String userId) {//done
		// TODO Auto-generated method stub
		return receiptInfo.delDraft(draftId, userId);
	}
	
	private StockGiftReceiptVO makeClientGiftReceipt(ClientGiftStrategyVO strategy,String userId){
		Map<OutGoodsVO,Integer> giftlist=strategy.getList();
		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
		Set set=giftlist.entrySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Map.Entry<OutGoodsVO, Integer> entry=(Map.Entry<OutGoodsVO, Integer>) iterator.next();
			OutGoodsVO g=entry.getKey();
			String goodsId=g.getId();
			String goodsName=g.getName();
			String goodsXh=g.getXh();
			int amount=entry.getValue();
			double value=amount*g.getOutPrice();
			StockGiftReceiptVOLineItem item=new StockGiftReceiptVOLineItem(goodsName,goodsXh,amount,value,goodsId);
			list.add(item);
		}
		StockGiftReceiptVO giftReceipt=new StockGiftReceiptVO(null/*编号，要调用numbering方法或交给其他人添加*/,Calendar.getInstance(),null/*审批时设置*/,userId,list);
		return giftReceipt;
	}

	private StockGiftReceiptVO makeGiftReceipt(GiftStrategyVO strategy,String userId){
		Map<OutGoodsVO,Integer> giftlist=strategy.getGiftlist();
		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
		Set set=giftlist.entrySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Map.Entry<OutGoodsVO, Integer> entry=(Map.Entry<OutGoodsVO, Integer>) iterator.next();
			OutGoodsVO g=entry.getKey();
			String goodsId=g.getId();
			String goodsName=g.getName();
			String goodsXh=g.getXh();
			int amount=entry.getValue();
			double value=amount*g.getOutPrice();
			StockGiftReceiptVOLineItem item=new StockGiftReceiptVOLineItem(goodsName,goodsXh,amount,value,goodsId);
			list.add(item);
		}
		StockGiftReceiptVO giftReceipt=new StockGiftReceiptVO(null/*编号，要调用numbering方法或交给其他人添加*/,Calendar.getInstance(),null/*审批时设置*/,userId,list);
		return giftReceipt;
	}
	
	public StrategyVO getStrategyById(String id){
		return strategyInfo.getStrategyById(id);
	}
	
	public boolean makeGiftReceipt(StockGiftReceiptVO receipt){
		return reviseInfo.insertReceipt(receipt);
	}
}
