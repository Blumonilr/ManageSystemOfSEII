package main.businesslogic.salebl;

import main.businesslogicservice.saleblservice.SaleblService;
import main.vo.ClientVO;
import main.vo.OutGoodsVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;
import main.vo.StrategyVO;

import java.util.ArrayList;
import java.util.Calendar;

public class SaleblServer_Stub implements SaleblService {

	ArrayList<SaleReceiptBothVO> receiptList;
	ArrayList<OutGoodsVO> goodsList;
	ArrayList<ClientVO> clientList;
	ArrayList<StrategyVO> strategyList;
	
	@Override
	public ArrayList<OutGoodsVO> showGoods() {
		// TODO Auto-generated method stub
		return goodsList;
	}

	@Override
	public ArrayList<ClientVO> showClient() {
		// TODO Auto-generated method stub
		return clientList;
	}

	@Override
	public SaleReceiptVO makeSaleReceipt(SaleReceiptVO obj) {
		// TODO Auto-generated method stub
		receiptList.add(obj);
		return obj;
	}

	@Override
	public SaleReturnReceiptVO makeSaleReturnReceipt(SaleReturnReceiptVO obj) {
		// TODO Auto-generated method stub
		receiptList.add(obj);
		return obj;
	}

	@Override
	public StockGiftReceiptVO chooseStrategy(StrategyVO strategy) {
		// TODO Auto-generated method stub
		if(strategy.getType().equals("总价赠品促销策略")){
			//只有giftStrategy需要
			 ArrayList<StockGiftReceiptVOLineItem> goodsList=new  ArrayList<StockGiftReceiptVOLineItem>();
			 //11/21有待strategy完善后再写
		}
		return null;
	}

	@Override
	public ArrayList<SaleReceiptBothVO> showReceipts(int operand, Calendar startTime, Calendar endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StrategyVO> showValidStrategy(SaleReceiptBothVO saleReceipt) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
