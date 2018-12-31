package main.businesslogic.salebl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogicservice.saleblservice.SaleblService;
import main.vo.ClientVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StrategyVO;

/**
 * 
 * @author Kate
 *
 */
public class SaleController implements SaleblService {

	Sale sale;
	
	public SaleController(){
		sale=new Sale();
	}
	
	@Override
	public ArrayList<OutGoodsVO> showGoods() {
		// TODO Auto-generated method stub
		return sale.showGoods();
	}

	@Override
	public ArrayList<ClientVO> showClient() {
		// TODO Auto-generated method stub
		return sale.showClient();
	}

	@Override
	public SaleReceiptVO makeSaleReceipt(SaleReceiptVO obj) {
		// TODO Auto-generated method stub
		return sale.makeSaleReceipt(obj);
	}

	@Override
	public SaleReturnReceiptVO makeSaleReturnReceipt(SaleReturnReceiptVO obj) {
		// TODO Auto-generated method stub
		return sale.makeSaleReturnReceipt(obj);
	}

	@Override
	public StockGiftReceiptVO chooseGiftStrategy(String strategyId,String userId,SaleReceiptBothVO receipt) {
		// TODO Auto-generated method stub
		return sale.chooseGiftStrategy(strategyId,userId,receipt);
	}

	@Override
	public boolean chooseOtherStrategy(String strategyId,String userId,SaleReceiptBothVO receipt) {
		// TODO Auto-generated method stub
		return sale.chooseOtherStrategy(strategyId, userId,receipt);
	}
	
	@Override
	public ArrayList<SaleReceiptBothVO> showMyUnrevisedReceipts(String userId,int operand, Calendar startTime, Calendar endTime) {
		// TODO Auto-generated method stub
		return sale.showMyUnrevisedReceipts(userId,operand, startTime, endTime);
	}

	@Override
	public ArrayList<StrategyVO> showValidStrategy(SaleReceiptBothVO saleReceipt) {
		// TODO Auto-generated method stub
		return sale.showValidStrategy(saleReceipt);
	}

	@Override
	public SaleReceiptVO makeSaleReceiptDraft(SaleReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return sale.makeSaleReceiptDraft(obj, userId);
	}

	@Override
	public SaleReturnReceiptVO makeSaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return sale.makeSaleReturnReceiptDraft(obj, userId);
	}

	@Override
	public ArrayList<SaleReceiptVO> showMySaleReceiptDrafts(String userId) {
		// TODO Auto-generated method stub
		return sale.showMySaleReceiptDrafts(userId);
	}

	@Override
	public ArrayList<SaleReturnReceiptVO> showMySaleReturnReceiptDrafts(String userId) {
		// TODO Auto-generated method stub
		return sale.showMySaleReturnReceiptDrafts(userId);
	}

	@Override
	public SaleReceiptVO modifySaleReceiptDraft(SaleReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return sale.modifySaleReceiptDraft(obj, userId);
	}

	@Override
	public SaleReturnReceiptVO modifySaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return sale.modifySaleReturnReceiptDraft(obj, userId);
	}

	@Override
	public boolean deleteDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		return sale.deleteDraft(draftId, userId);
	}

	@Override
	public StrategyVO getStrategyById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return sale.getStrategyById(id);
	}

	@Override
	public boolean makeGiftReceipt(StockGiftReceiptVO receipt) throws RemoteException {
		// TODO Auto-generated method stub
		return sale.makeGiftReceipt(receipt);
	}

}
