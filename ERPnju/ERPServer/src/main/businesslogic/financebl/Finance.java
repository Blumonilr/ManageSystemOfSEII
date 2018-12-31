package main.businesslogic.financebl;

import main.businesslogic.accountbl.Account;
import main.businesslogic.accountbl.AccountInfo;
import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.businesslogic.stockbl.ReceiptInfo;
import main.businesslogic.stockbl.Stock;
import main.vo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/25 14:32
*/
public class Finance {
    AccountInfo accountInfo=new Account();
    ReceiptInfo receiptInfo=new Stock();
    ReviseInfo reviseInfo=new Revise();
    private final String pattern = "yyyy-MM-dd";
    private SimpleDateFormat format = new SimpleDateFormat(pattern);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public ArrayList<ClientVO> showClients() {
        ClientInfo receiptInfo=new Client();
        return receiptInfo.show();
    }

    public ArrayList<String> showAccounts() {
        AccountInfo receiptInfo=new Account();
        return receiptInfo.showAccounts();
    }

    public AccountVO getAccount(String nickName) {
        return accountInfo.findAccountByNickname(nickName);
    }

    public boolean makeCashReceipt(FinanceCashReceiptVO cashReceipt) {
        return reviseInfo.insertReceipt(cashReceipt);
    }

    public boolean makePayReceipt(FinancePayReceiptVO payReceipt) {
        return reviseInfo.insertReceipt(payReceipt);
    }

    public boolean makeCollectReceipt(FinanceCollectReceiptVO collectReceipt) {
        return reviseInfo.insertReceipt(collectReceipt);
    }

    public ReceiptVO readReceipt(String receiptID) {
        return receiptInfo.readReceipt(receiptID);
    }

    
    public ArrayList<ReceiptVO> readRevisedReceiptList(String userId) {
        ArrayList<ReceiptVO> list=receiptInfo.readReceipt();
        if(list==null)
            return new ArrayList<ReceiptVO>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getReviseTime()==null||list.get(i).getCreatorId()==null) {
                list.remove(i);
                i--;
                continue;
            }
            if(!list.get(i).getCreatorId().equals(userId)||list.get(i).getReviseTime().before(convert(LocalDate.now().minusDays(7)))){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    
    public ArrayList<ReceiptVO> readUnrevisedReceiptList(String userId) {
        return reviseInfo.showAllReceipt(userId);
    }

    
    public ReceiptVO readUnrevisedReceipt(String receiptID) {
        return reviseInfo.showReceipt(receiptID);
    }

    
    public boolean addDraft(ReceiptVO receipt,String userID) {
        return receiptInfo.addDraft(receipt,userID);
    }

    
    public boolean modifyDraft(ReceiptVO receipt,String userID) {
        return receiptInfo.modifyDraft(receipt,userID);
    }

    
    public boolean delDraft(String draftID, String userID) {
        return receiptInfo.delDraft(draftID,userID);
    }

    
    public ArrayList<ReceiptVO> readDraftList(String userID) {
        return receiptInfo.readDraft(userID);
    }

    
    public ReceiptVO readDraft(String draftID, String userId) {
        return receiptInfo.readDraft(draftID, userId);
    }

    private Calendar convert(LocalDate a){
        Calendar time=Calendar.getInstance();
        try {
            time.setTime(format.parse(formatter.format(a)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
