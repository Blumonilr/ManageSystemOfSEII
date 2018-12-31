package main.businesslogicservice.saleblservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import main.vo.*;

public interface SaleblService extends Remote{
	public ArrayList<OutGoodsVO> showGoods()throws RemoteException;
	public ArrayList<ClientVO> showClient()throws RemoteException;
	public SaleReceiptVO makeSaleReceipt(SaleReceiptVO obj)throws RemoteException;
	public SaleReturnReceiptVO makeSaleReturnReceipt(SaleReturnReceiptVO obj)throws RemoteException;
	public StockGiftReceiptVO chooseGiftStrategy(String strategyId,String userId,SaleReceiptBothVO receipt)throws RemoteException;
	public boolean chooseOtherStrategy(String strategyId,String userId,SaleReceiptBothVO receipt)throws RemoteException;
	public ArrayList<SaleReceiptBothVO> showMyUnrevisedReceipts(String userId,int operand,Calendar startTime,Calendar endTime)throws RemoteException;
	public ArrayList<StrategyVO> showValidStrategy(SaleReceiptBothVO saleReceipt)throws RemoteException;
	public SaleReceiptVO makeSaleReceiptDraft(SaleReceiptVO obj,String userId)throws RemoteException;
	public SaleReturnReceiptVO makeSaleReturnReceiptDraft(SaleReturnReceiptVO obj,String userId)throws RemoteException;
	public ArrayList<SaleReceiptVO> showMySaleReceiptDrafts(String userId)throws RemoteException;
	public ArrayList<SaleReturnReceiptVO> showMySaleReturnReceiptDrafts(String userId)throws RemoteException;
	public SaleReceiptVO modifySaleReceiptDraft(SaleReceiptVO obj,String userId)throws RemoteException;
	public SaleReturnReceiptVO modifySaleReturnReceiptDraft(SaleReturnReceiptVO obj,String userId)throws RemoteException;
	public boolean deleteDraft(String draftId,String userId)throws RemoteException;
	public StrategyVO getStrategyById(String id)throws RemoteException;
	public boolean makeGiftReceipt(StockGiftReceiptVO receipt)throws RemoteException;
}
