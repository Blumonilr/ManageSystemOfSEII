package main.ui.formui;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import main.vo.SaleFormLineItem;

public class SaleGoods{
	SimpleStringProperty name;
	SimpleStringProperty time;
	SimpleStringProperty xh;
	SimpleDoubleProperty num;
	SimpleDoubleProperty price;
	SimpleDoubleProperty total;
	
	public SaleGoods(SaleFormLineItem sale){
		name=new SimpleStringProperty(sale.getGoodName());
		time=new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(sale.getTime().getTime()));
		xh=new SimpleStringProperty(sale.getModelNumber());
		num=new SimpleDoubleProperty(sale.getQuantity());
		price=new SimpleDoubleProperty(sale.getPrice());
		total=new SimpleDoubleProperty(sale.getTotalPrice());
	}

	public String getName(){
		return name.get();
	}
	
	public String getTime(){
		return time.get();
	}
	
	public String getXh(){
		return xh.get();
	}
	
	public double getNum(){
		return num.get();
	}
	
	public double getPrice(){
		return price.get();
	}
	
	public double getTotal(){
		return total.get();
	}
}