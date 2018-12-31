package main.businesslogic.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import main.PO.AlarmReceiptPO;
import main.PO.FinanceCashReceiptPO;
import main.PO.FinanceCollectReceiptPO;
import main.PO.FinancePayReceiptPO;
import main.PO.GiftReceipt;
import main.PO.OverflowReceipt;
import main.PO.PurchaseReceipt;
import main.PO.PurchaseReturnReceipt;
import main.PO.ReceiptPO;
import main.PO.SaleReceipt;
import main.PO.SaleReturnReceipt;
import main.PO.UnderflowReceipt;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockAlarmReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;
import main.vo.StockOverflowReceiptVO;
import main.vo.StockUnderflowReceiptVO;

/**
 * 
 * @author qyc
 *
 * 此类用于receipt的vo、po各种转化
 */
public class ReceiptTransformer {
		

	public ReceiptVO po2vo(ReceiptPO obj){
		ReceiptVO r=null;
		switch(obj.getReceiptType()){
		case 11://overflow
			r=this.po2vo11(obj);
			break;
		case 12://underflow
			r=this.po2vo12(obj);
			break;
		case 13://gift
			r=this.po2vo13(obj);
			break;
		case 14://alarm
			r=this.po2vo14(obj);
			break;
		case 21://collect
			r=new FinanceCollectReceiptVO(((FinanceCollectReceiptPO)obj));
			break;
		case 22://pay
			r=new FinancePayReceiptVO((FinancePayReceiptPO)obj);
			break;
		case 23://cash
			r=new FinanceCashReceiptVO((FinanceCashReceiptPO)obj);
			break;
		case 31://sale
			r=this.po2vo31(obj);
			break;
		case 32://salereturn
			r=this.po2vo32(obj);
			break;
		case 41://purchase
			r=this.po2vo41(obj);
			break;
		case 42://purchasereturn
			r=this.po2vo42(obj);
			break;
		}
		return r;
	}
	
	public ReceiptPO vo2po(ReceiptVO obj){
		ReceiptPO po=null;
		switch(obj.getReceiptType()){
		case 11://overflow
			po=this.vo2po11(obj);
			break;
		case 12://underflow
			po=this.vo2po12(obj);
			break;
		case 13://gift
			po=this.vo2po13(obj);
			break;
		case 14://alarm
			po=this.vo2po14(obj);
			break;
		case 21://collect
			po=new FinanceCollectReceiptPO((FinanceCollectReceiptVO)obj);
			break;
		case 22://pay
			po=new FinancePayReceiptPO((FinancePayReceiptVO)obj);
			break;
		case 23://cash
			po=new FinanceCashReceiptPO((FinanceCashReceiptVO)obj);
			break;
		case 31://sale
			po=this.vo2po31(obj);
			break;
		case 32://salereturn
			po=this.vo2po32(obj);
			break;
		case 41://purchase
			po=this.vo2po41(obj);
			break;
		case 42://purchasereturn
			po=this.vo2po42(obj);
			break;
		}
		return po;
	}
	
	private StockOverflowReceiptVO po2vo11(ReceiptPO obj){
		OverflowReceipt po=(OverflowReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String goodsName=po.getGoodsName();
		String goodsId=po.getGoodsId();
		String goodsXh=po.getGoodsXh();
		int amount=po.getAmount();
		
		StockOverflowReceiptVO r=new StockOverflowReceiptVO(id,makeTime,reviseTime,creatorId,goodsName,goodsId,goodsXh,amount);
		return r;
	}
	
	private StockUnderflowReceiptVO po2vo12(ReceiptPO obj){
		UnderflowReceipt po=(UnderflowReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String goodsName=po.getGoodsName();
		String goodsId=po.getGoodsId();
		String goodsXh=po.getGoodsXh();
		int amount=po.getAmount();
		
		StockUnderflowReceiptVO r=new StockUnderflowReceiptVO(id,makeTime,reviseTime,creatorId,goodsName,goodsId,goodsXh,amount);
		return r;
	}
	
	private StockGiftReceiptVO po2vo13(ReceiptPO obj){
		GiftReceipt po=(GiftReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		ArrayList<String[]> goodsList=po.getGoodsList();
		ArrayList<StockGiftReceiptVOLineItem> itemList=new ArrayList<StockGiftReceiptVOLineItem>();
		for(int i=0;i<goodsList.size();i++){
			String[] im=goodsList.get(i);
			StockGiftReceiptVOLineItem item=new StockGiftReceiptVOLineItem(im[0],im[1],Integer.parseInt(im[2]),Double.parseDouble(im[3]),im[4] );
			itemList.add(item);
		}
		StockGiftReceiptVO vo=new StockGiftReceiptVO(id,makeTime,reviseTime,creatorId,itemList);
		
		return vo;
	}
	
	private StockAlarmReceiptVO po2vo14(ReceiptPO obj){
		AlarmReceiptPO po=(AlarmReceiptPO)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String goodsId=po.getGoodsId();
		String goodsName=po.getGoodsName();
		String goodsXh=po.getGoodsXh();
		long alarmNumber=po.getAlarmNumber();
		long currentNumber=po.getCurrentNumber();
		StockAlarmReceiptVO vo=new StockAlarmReceiptVO(id,makeTime,reviseTime,goodsId,goodsName,goodsXh,alarmNumber,currentNumber);
		
		return vo;
		
	}
	
	private SaleReceiptVO po2vo31 (ReceiptPO obj){
		SaleReceipt po=(SaleReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String clientId=po.getClientId();
		String clerkId=po.getClerkId();
		String stockId=po.getStockId();
		double discount=po.getDiscount();
		double valueOfVoucher=po.getValueOfVoucher();
		double reductionOfPackage=po.getReductionOfPackage();
		String note=po.getNote();
		String strategyNote=po.getStrategyNote();
		ArrayList<SaleReceiptBothVOLineItem> itemList=new ArrayList<SaleReceiptBothVOLineItem>();
		ArrayList<String[]> gl=po.getGoodsList();
		for(int i=0;i<gl.size();i++){
			String[] it=gl.get(i);
			SaleReceiptBothVOLineItem item=new SaleReceiptBothVOLineItem(it[0],it[1],it[2],Long.parseLong(it[3]),Double.parseDouble(it[4]),it[5]);
			itemList.add(item);
		}
		double friendlyDiscount=po.getFriendlyDiscount();
		SaleReceiptVO vo=new SaleReceiptVO(id,makeTime,reviseTime,creatorId,clientId,clerkId,stockId,discount,valueOfVoucher,note,itemList,strategyNote,reductionOfPackage,friendlyDiscount);
		
		return vo;
	}
	
	private SaleReturnReceiptVO po2vo32 (ReceiptPO obj){
		SaleReturnReceipt po=(SaleReturnReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String clientId=po.getClientId();
		String clerkId=po.getClerkId();
		String stockId=po.getStockId();
		double discount=po.getDiscount();
		double valueOfVoucher=po.getValueOfVoucher();
		double reductionOfPackage=po.getReductionOfPackage();
		String note=po.getNote();
		String strategyNote=po.getStrategyNote();
		ArrayList<SaleReceiptBothVOLineItem> itemList=new ArrayList<SaleReceiptBothVOLineItem>();
		ArrayList<String[]> gl=po.getGoodsList();
		for(int i=0;i<gl.size();i++){
			String[] it=gl.get(i);
			SaleReceiptBothVOLineItem item=new SaleReceiptBothVOLineItem(it[0],it[1],it[2],Long.parseLong(it[3]),Double.parseDouble(it[4]),it[5]);
			itemList.add(item);
		}
		double friendlyDiscount=po.getFriendlyDiscount();
		SaleReturnReceiptVO vo=new SaleReturnReceiptVO(id,makeTime,reviseTime,creatorId,clientId,clerkId,stockId,discount,valueOfVoucher,note,itemList,strategyNote,reductionOfPackage,friendlyDiscount);
		
		return vo;
	}

	private PurchaseReceiptVO po2vo41 (ReceiptPO obj){
		PurchaseReceipt po =(PurchaseReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String clientId=po.getClientId();
		String stockId=po.getStockId();
		String note=po.getNote();
		ArrayList<PurchaseReceiptBothVOLineItem> itemList=new ArrayList<PurchaseReceiptBothVOLineItem>();
		ArrayList<String[]> gl=po.getGoodsList();
		for(int i=0;i<gl.size();i++){
			String[] it=gl.get(i);
			PurchaseReceiptBothVOLineItem item=new PurchaseReceiptBothVOLineItem(it[0],it[1],it[2],Integer.parseInt(it[3]),Double.parseDouble(it[4]),it[6]);
			itemList.add(item);
		}
		PurchaseReceiptVO vo=new PurchaseReceiptVO(id,makeTime,reviseTime,creatorId,clientId,stockId,note,itemList);
	
		return vo;
	}
	
	private PurchaseReturnReceiptVO po2vo42 (ReceiptPO obj){
		PurchaseReturnReceipt po =(PurchaseReturnReceipt)obj;
		String id=po.getId();
		Calendar makeTime=po.getCreateTimebyCalendar();
		Calendar reviseTime=po.getReviseTimebyCalendar();
		String creatorId=po.getCreatorId();
		String clientId=po.getClientId();
		String stockId=po.getStockId();
		String note=po.getNote();
		ArrayList<PurchaseReceiptBothVOLineItem> itemList=new ArrayList<PurchaseReceiptBothVOLineItem>();
		ArrayList<String[]> gl=po.getGoodsList();
		for(int i=0;i<gl.size();i++){
			String[] it=gl.get(i);
			PurchaseReceiptBothVOLineItem item=new PurchaseReceiptBothVOLineItem(it[0],it[1],it[2],Integer.parseInt(it[3]),Double.parseDouble(it[4]),it[6]);
			itemList.add(item);
		}
		PurchaseReturnReceiptVO vo=new PurchaseReturnReceiptVO(id,makeTime,reviseTime,creatorId,clientId,stockId,note,itemList);
	
		return vo;
	}

	private OverflowReceipt vo2po11(ReceiptVO obj){
		StockOverflowReceiptVO vo=(StockOverflowReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String goodsName=vo.getGoodsName();
		String goodsId=vo.getGoodsId();
		String goodsXh=vo.getGoodsXh();
		int amount=vo.getAmount();
		OverflowReceipt po=new OverflowReceipt(id,createTime,reviseTime,creatorId,goodsName,goodsId,goodsXh,amount);
		
		return po;
	}
	
	private UnderflowReceipt vo2po12(ReceiptVO obj){
		StockUnderflowReceiptVO vo=(StockUnderflowReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String goodsName=vo.getGoodsName();
		String goodsId=vo.getGoodsId();
		String goodsXh=vo.getGoodsXh();
		int amount=vo.getAmount();
		UnderflowReceipt po=new UnderflowReceipt(id,createTime,reviseTime,creatorId,goodsName,goodsId,goodsXh,amount);
		
		return po;
	}
	
	private GiftReceipt vo2po13(ReceiptVO obj){
		StockGiftReceiptVO vo=(StockGiftReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		ArrayList<StockGiftReceiptVOLineItem> itemList=vo.getGoodsList();
		ArrayList<String[]> goodsList=new ArrayList<String[]>();
		for(int i=0;i<itemList.size();i++){
			StockGiftReceiptVOLineItem item=itemList.get(i);
			String[] it=new String[5];
			it[0]=item.getGoodsName();
			it[1]=item.getGoodsXh();
			it[2]=String.valueOf(item.getAmount());
			it[3]=String.valueOf(item.getValue());
			it[4]=String.valueOf(item.getGoodsId());
			goodsList.add(it);
		}
		
		GiftReceipt po=new GiftReceipt(id,createTime,reviseTime,creatorId,goodsList);
		
		return po;
	}
	
	private AlarmReceiptPO vo2po14(ReceiptVO obj){
		StockAlarmReceiptVO vo=(StockAlarmReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String goodsId=vo.getGoodsId();
		String goodsName=vo.getGoodsName();
		String goodsXh=vo.getGoodsXh();
		long alarmNumber=vo.getAlarmNumber();
		long currentNumber=vo.getCurrentNumber();
		AlarmReceiptPO po=new AlarmReceiptPO(id,createTime,reviseTime,goodsId,goodsName,goodsXh,alarmNumber,currentNumber);
		return po;
	}
	
	private SaleReceipt vo2po31(ReceiptVO obj){
		SaleReceiptVO vo=(SaleReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String clientId=vo.getClientId();
		String clerkId=vo.getClerkId();
		String stockId=vo.getStockId();
		double tbdov=vo.getTotalBeforeDiscountOrVoucher();
		double discount=vo.getDiscount();
		double valueOfVoucher=vo.getValueOfVoucher();
		double tad=vo.getTotalAfterDiscount();
		double tav=vo.getTotalAfterVoucher();
		double reductionOfPackage=vo.getReductionOfPackage();
		double totalAfterPackage=vo.getTotalAfterPackage();
		String note=vo.getNote();
		String strategyNote=vo.getStrategyNote();
		ArrayList<String[]> goodsList=new ArrayList<String[]>();
		ArrayList<SaleReceiptBothVOLineItem> itemList=vo.getItemList();
		for(int i=0;i<itemList.size();i++){
			SaleReceiptBothVOLineItem item=itemList.get(i);
			String[] s=new String[7];
			s[0]=item.getGoodsId();
			s[1]=item.getGoodsName();
			s[2]=item.getGoodsXh();
			s[3]=String.valueOf(item.getGoodsAmount());
			s[4]=String.valueOf(item.getGoodsPrice());
			s[5]=String.valueOf(item.getSubTotal());
			s[6]=item.getNote();
			
			goodsList.add(s);
		}
		double friendlyDiscount=vo.getFriendlyDiscount();
		SaleReceipt po=new SaleReceipt(id,createTime,reviseTime,creatorId,clientId,clerkId,stockId,tbdov,discount,valueOfVoucher,tad,tav,note,goodsList,strategyNote,reductionOfPackage,totalAfterPackage,friendlyDiscount);
		return po;
	}
	
	private SaleReturnReceipt vo2po32(ReceiptVO obj){
		SaleReturnReceiptVO vo=(SaleReturnReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String clientId=vo.getClientId();
		String clerkId=vo.getClerkId();
		String stockId=vo.getStockId();
		double tbdov=vo.getTotalBeforeDiscountOrVoucher();
		double discount=vo.getDiscount();
		double valueOfVoucher=vo.getValueOfVoucher();
		double tad=vo.getTotalAfterDiscount();
		double tav=vo.getTotalAfterVoucher();
		double reductionOfPackage=vo.getReductionOfPackage();
		double totalAfterPackage=vo.getTotalAfterPackage();
		String note=vo.getNote();
		String strategyNote=vo.getStrategyNote();
		ArrayList<String[]> goodsList=new ArrayList<String[]>();
		ArrayList<SaleReceiptBothVOLineItem> itemList=vo.getItemList();
		for(int i=0;i<itemList.size();i++){
			SaleReceiptBothVOLineItem item=itemList.get(i);
			String[] s=new String[7];
			s[0]=item.getGoodsId();
			s[1]=item.getGoodsName();
			s[2]=item.getGoodsXh();
			s[3]=String.valueOf(item.getGoodsAmount());
			s[4]=String.valueOf(item.getGoodsPrice());
			s[5]=String.valueOf(item.getSubTotal());
			s[6]=item.getNote();
			
			goodsList.add(s);
		}
		double friendlyDiscount=vo.getFriendlyDiscount();
		SaleReturnReceipt po=new SaleReturnReceipt(id,createTime,reviseTime,creatorId,clientId,clerkId,stockId,tbdov,discount,valueOfVoucher,tad,tav,note,goodsList,strategyNote,reductionOfPackage,totalAfterPackage,friendlyDiscount);
		return po;
	}
	
	private PurchaseReceipt vo2po41(ReceiptVO obj){
		PurchaseReceiptVO vo=(PurchaseReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String clientId=vo.getClientId();
		String stockId=vo.getStockId();
		String note=vo.getNote();
		double totalValue=vo.getTotalValue();
		ArrayList<PurchaseReceiptBothVOLineItem> itemList=vo.getItemList();
		ArrayList<String[]> goodsList=new ArrayList<String[]>();
		for(int i=0;i<itemList.size();i++){
			PurchaseReceiptBothVOLineItem item=itemList.get(i);
			String[] s=new String[7];
			s[0]=item.getGoodsId();
			s[1]=item.getGoodsName();
			s[2]=item.getGoodsXh();
			s[3]=String.valueOf(item.getGoodsAmount());
			s[4]=String.valueOf(item.getGoodsPrice());
			s[5]=String.valueOf(item.getSubtotal());
			s[6]=item.getNote();
			goodsList.add(s);
		}
		PurchaseReceipt po=new PurchaseReceipt(id,createTime,reviseTime,creatorId,clientId,stockId,note,totalValue,goodsList);
		
		return po;
	}
	
	private PurchaseReturnReceipt vo2po42(ReceiptVO obj){
		PurchaseReturnReceiptVO vo=(PurchaseReturnReceiptVO)obj;
		String id=vo.getId();
		String createTime=this.getStringTime(vo.getMakeTime());
		String reviseTime=this.getStringTime(vo.getReviseTime());
		String creatorId=vo.getCreatorId();
		String clientId=vo.getClientId();
		String stockId=vo.getStockId();
		String note=vo.getNote();
		double totalValue=vo.getTotalValue();
		ArrayList<PurchaseReceiptBothVOLineItem> itemList=vo.getItemList();
		ArrayList<String[]> goodsList=new ArrayList<String[]>();
		for(int i=0;i<itemList.size();i++){
			PurchaseReceiptBothVOLineItem item=itemList.get(i);
			String[] s=new String[7];
			s[0]=item.getGoodsId();
			s[1]=item.getGoodsName();
			s[2]=item.getGoodsXh();
			s[3]=String.valueOf(item.getGoodsAmount());
			s[4]=String.valueOf(item.getGoodsPrice());
			s[5]=String.valueOf(item.getSubtotal());
			s[6]=item.getNote();
			goodsList.add(s);
		}
		PurchaseReturnReceipt po=new PurchaseReturnReceipt(id,createTime,reviseTime,creatorId,clientId,stockId,note,totalValue,goodsList);
		
		return po;
	}
	
	
	/**
	 * 时间转化
	 * @param c
	 * @return
	 */
	private String getStringTime(Calendar c){
		if(c==null){
			return "1998-12-14-12-50";
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String time=df.format(c.getTime());
		return time;
	}
}
