package main.ui.formui;

import java.rmi.RemoteException;

import javafx.beans.property.SimpleStringProperty;
import main.businesslogicservice.clientblservice.ClientblService;
import main.rmi.RemoteHelper;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

public class FormReceipt{
	SimpleStringProperty id;
	SimpleStringProperty time;
	SimpleStringProperty type;
	SimpleStringProperty client;
	SimpleStringProperty counter;
	SimpleStringProperty stock;
	
	ClientblService clientblservice=RemoteHelper.getInstance().getClientblService();
	
	public FormReceipt(ReceiptVO r){
		id=new SimpleStringProperty(r.getId());
		time=new SimpleStringProperty(r.getStringMakeTime());
		switch(r.getReceiptType()){
		case 11:this.type=new SimpleStringProperty("库存报溢单"); break;
		case 12:this.type=new SimpleStringProperty("库存报损单"); break;
		case 13:this.type=new SimpleStringProperty("库存赠送单"); break;
		case 14:this.type=new SimpleStringProperty("库存报警单"); break;
		case 21:this.type=new SimpleStringProperty("收款单"); break;
		case 22:this.type=new SimpleStringProperty("付款单"); break;
		case 23:this.type=new SimpleStringProperty("现金费用单"); break;
		case 31:this.type=new SimpleStringProperty("销售单"); break;
		case 32:this.type=new SimpleStringProperty("销售退货单"); break;
		case 41:this.type=new SimpleStringProperty("进货单"); break;
		case 42:this.type=new SimpleStringProperty("进货退货单"); break;
		}
		
		if(r.getReceiptType()==11||r.getReceiptType()==12||r.getReceiptType()==13||r.getReceiptType()==14||r.getReceiptType()==23)
			client=new SimpleStringProperty("无客户需求");
		else {
			try {
				switch(r.getReceiptType()){
				case 21:client=new SimpleStringProperty(clientblservice.showClient(((FinanceCollectReceiptVO)r).getClient()).getClientName()); break;
				case 22:client=new SimpleStringProperty(clientblservice.showClient(((FinancePayReceiptVO)r).getClient()).getClientName()); break;
				case 31:client=new SimpleStringProperty(clientblservice.showClient(((SaleReceiptVO)r).getClientId()).getClientName()); break;
				case 32:client=new SimpleStringProperty(clientblservice.showClient(((SaleReturnReceiptVO)r).getClientId()).getClientName()); break;
				case 41:client=new SimpleStringProperty(clientblservice.showClient(((PurchaseReceiptVO)r).getClientId()).getClientName()); break;
				case 42:client=new SimpleStringProperty(clientblservice.showClient(((PurchaseReturnReceiptVO)r).getClientId()).getClientName()); break;
					} 
				}catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} 
			}
		
		counter=new SimpleStringProperty(r.getCreatorId());
		
		if(r.getReceiptType()==11||r.getReceiptType()==12||r.getReceiptType()==13||r.getReceiptType()==14||r.getReceiptType()==21||r.getReceiptType()==22||r.getReceiptType()==23)
			stock=new SimpleStringProperty("无仓库需求");
		else{
			switch(r.getReceiptType()){
			case 31:stock=new SimpleStringProperty(((SaleReceiptVO)r).getStockId()); break;
			case 32:stock=new SimpleStringProperty(((SaleReturnReceiptVO)r).getStockId()); break;
			case 41:stock=new SimpleStringProperty(((PurchaseReceiptVO)r).getStockId()); break;
			case 42:stock=new SimpleStringProperty(((PurchaseReturnReceiptVO)r).getStockId()); break;
			}
		}
	}

	public String getId() {
		return id.get();
	}


	public String getTime() {
		return time.get();
	}


	public String getType() {
		return type.get();
	}

	public String getClient() {
		return client.get();
	}

	public String getCounter() {
		return counter.get();
	}


	public String getStock() {
		return stock.get();
	}

}