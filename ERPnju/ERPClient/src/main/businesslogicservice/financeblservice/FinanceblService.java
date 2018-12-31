package main.businesslogicservice.financeblservice;

import main.vo.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/8 14:34
*/
public interface FinanceblService extends Remote {
    public ArrayList<ClientVO> showClients() throws RemoteException;

    public ArrayList<String> showAccounts() throws RemoteException;

    public  AccountVO getAccount(String nickName) throws RemoteException;

    public boolean makeCashReceipt(FinanceCashReceiptVO cashReceipt) throws RemoteException;

    public boolean makePayReceipt(FinancePayReceiptVO payReceipt) throws RemoteException;

    public boolean makeCollectReceipt(FinanceCollectReceiptVO collectReceipt) throws RemoteException;

    public ReceiptVO readReceipt(String receiptID) throws RemoteException;


    public ArrayList<ReceiptVO> readRevisedReceiptList(String userId) throws RemoteException;

    public ArrayList<ReceiptVO> readUnrevisedReceiptList(String userId) throws RemoteException;

    public ReceiptVO readUnrevisedReceipt(String receiptID) throws RemoteException;

    public boolean addDraft(ReceiptVO receipt,String userID) throws RemoteException;

    public boolean modifyDraft(ReceiptVO receipt,String userID) throws RemoteException;

    public boolean delDraft(String draftID, String userID) throws RemoteException;

    public ArrayList<ReceiptVO> readDraftList(String userID) throws RemoteException;

    public ReceiptVO readDraft(String draftID, String userId) throws RemoteException;
}
