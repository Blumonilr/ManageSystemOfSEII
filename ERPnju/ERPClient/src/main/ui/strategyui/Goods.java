package main.ui.strategyui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Goods {
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private SimpleDoubleProperty price;
	private SimpleStringProperty num;
	
	public Goods(String id,String name,int num,double price){
		this.id=new SimpleStringProperty(id);
		this.name=new SimpleStringProperty(name);
		this.price=new SimpleDoubleProperty(price);
		this.num=new SimpleStringProperty(num+"");
	}
	
	public void setNum(String n){
		this.num.set(n);
	}
	
	public String getId(){
		return id.get();
	}
	
	public String getName(){
		return name.get();
	}
	
	public double getPrice(){
		return price.get();
	}
	
	public String getNum(){
		return num.get();
	}
}
