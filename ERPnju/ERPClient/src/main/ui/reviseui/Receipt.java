package main.ui.reviseui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;

public class Receipt {
	
	private SimpleStringProperty id;
	private SimpleStringProperty type;
	private SimpleStringProperty time;
	
	public Receipt(String id,int type,Calendar time){
		this.id=new SimpleStringProperty(id);
		switch(type){
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
		this.time=new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd-HH").format(time.getTime()));
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);;
	}

	public String getType() {
		return type.get();
	}
	
	public String getTime(){
		return time.get();
	}

}
