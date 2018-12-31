package main.ui.financeui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.vo.*;

import java.text.SimpleDateFormat;

class ReceiptItem extends RecursiveTreeObject<ReceiptItem> {
    StringProperty id;
    StringProperty target;
    StringProperty type;
    StringProperty time;
    StringProperty revisedTime;
    StringProperty description;
    SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");

    public ReceiptItem(ReceiptVO vo) {
        id=new SimpleStringProperty(vo.getId());
        if(vo.getReviseTime()!=null) {
            revisedTime = new SimpleStringProperty(format.format(vo.getReviseTime().getTime()));
        }
        if(vo.getReceiptType()==21){
            FinanceCollectReceiptVO detail=(FinanceCollectReceiptVO)vo;
            target=new SimpleStringProperty(detail.getClient());
            type=new SimpleStringProperty("收款单");
            time=new SimpleStringProperty(format.format(detail.getMakeTime().getTime()));
            if(detail.getItemList().size()!=0) {
                FinanceActTransLineItem item=detail.getItemList().get(0);
                description =new SimpleStringProperty( item.getBankAccount() + ":" +item.getAmount());
            }
        }else if(vo.getReceiptType()==22){
            FinancePayReceiptVO detail=(FinancePayReceiptVO)vo;
            target=new SimpleStringProperty(detail.getClient());
            type=new SimpleStringProperty("付款单");
            time=new SimpleStringProperty(format.format(detail.getMakeTime().getTime()));
            if(detail.getItemList().size()!=0) {
                FinanceActTransLineItem item=detail.getItemList().get(0);
                description = new SimpleStringProperty(item.getBankAccount() + ":" +item.getAmount());
            }
        }else if(vo.getReceiptType()==23){
            FinanceCashReceiptVO detail=(FinanceCashReceiptVO)vo;
            target=new SimpleStringProperty(detail.getAccountName());
            type=new SimpleStringProperty("现金费用单");
            time=new SimpleStringProperty(format.format(detail.getMakeTime().getTime()));
            if(detail.getItemList().size()!=0) {
                FinanceCashReceiptLineItem item=detail.getItemList().get(0);
                description = new SimpleStringProperty(item.getItemName() + ":" +item.getAmount());
            }
        }
    }
}
