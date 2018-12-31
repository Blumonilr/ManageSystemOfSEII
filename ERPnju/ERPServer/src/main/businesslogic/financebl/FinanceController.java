package main.businesslogic.financebl;

import main.businesslogicservice.financeblservice.FinanceblService;
import main.vo.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class FinanceController implements FinanceblService {
    Finance tool = new Finance();

    @Override
    public ArrayList<ClientVO> showClients() throws RemoteException {
        return tool.showClients();
    }

    @Override
    public ArrayList<String> showAccounts() throws RemoteException {
        return tool.showAccounts();
    }

    @Override
    public AccountVO getAccount(String nickName) throws RemoteException {
        return tool.getAccount(nickName);
    }

    @Override
    public boolean makeCashReceipt(FinanceCashReceiptVO cashReceipt) throws RemoteException {
        return tool.makeCashReceipt(cashReceipt);
    }

    @Override
    public boolean makePayReceipt(FinancePayReceiptVO payReceipt) throws RemoteException {
        return tool.makePayReceipt(payReceipt);
    }

    @Override
    public boolean makeCollectReceipt(FinanceCollectReceiptVO collectReceipt) throws RemoteException {
        return tool.makeCollectReceipt(collectReceipt);
    }

    @Override
    public ReceiptVO readReceipt(String receiptID) throws RemoteException {
        return tool.readReceipt(receiptID);
    }

    @Override
    public ArrayList<ReceiptVO> readRevisedReceiptList(String userId) throws RemoteException {
        return tool.readRevisedReceiptList(userId);
    }

    @Override
    public ArrayList<ReceiptVO> readUnrevisedReceiptList(String userId) throws RemoteException {
        return tool.readUnrevisedReceiptList(userId);
    }

    @Override
    public ReceiptVO readUnrevisedReceipt(String receiptID) throws RemoteException {
        return tool.readUnrevisedReceipt(receiptID);
    }

    @Override
    public boolean addDraft(ReceiptVO receipt,String userID) throws RemoteException {
        return tool.addDraft(receipt, userID);
    }

    @Override
    public boolean modifyDraft(ReceiptVO receipt,String userID) throws RemoteException {
        return tool.modifyDraft(receipt, userID);
    }

    @Override
    public boolean delDraft(String draftID, String userID) throws RemoteException {
        return tool.delDraft(draftID, userID);
    }

    @Override
    public ArrayList<ReceiptVO> readDraftList(String userID) throws RemoteException {
        return tool.readDraftList(userID);
    }

    @Override
    public ReceiptVO readDraft(String draftID, String userId) throws RemoteException {
        return tool.readDraft(draftID, userId);
    }
}
