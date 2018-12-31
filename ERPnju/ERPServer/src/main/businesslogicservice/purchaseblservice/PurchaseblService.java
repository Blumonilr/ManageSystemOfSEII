package main.businesslogicservice.purchaseblservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import main.vo.*;

public interface PurchaseblService extends Remote{
	public ArrayList<OutGoodsVO> showGoods_p()throws RemoteException;
	public ArrayList<ClientVO> showClient_p()throws RemoteException;
	public PurchaseReceiptVO makePurchaseList(PurchaseReceiptVO obj)throws RemoteException;
	public PurchaseReturnReceiptVO makePurchaseReturnList(PurchaseReturnReceiptVO obj)throws RemoteException;
	public ArrayList<PurchaseReceiptBothVO> showMyUnrevisedReceipts_p(String userId,int operand,Calendar startTime,Calendar endTime)throws RemoteException;
	public PurchaseReceiptVO makePurchaseReceiptDraft(PurchaseReceiptVO obj,String userId)throws RemoteException;
	public PurchaseReturnReceiptVO makePurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj,String userId)throws RemoteException;
	public ArrayList<PurchaseReceiptVO> showMyPurchaseReceiptDrafts(String userId)throws RemoteException;
	public ArrayList<PurchaseReturnReceiptVO> showMyPurchaseReturnReceiptDrafts(String userId)throws RemoteException;
	public PurchaseReceiptVO modifyPurchaseReceiptDraft(PurchaseReceiptVO obj,String userId)throws RemoteException;
	public PurchaseReturnReceiptVO modifyPurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj,String userId)throws RemoteException;
	public boolean deleteDraft_p(String draftId,String userId)throws RemoteException;
}
