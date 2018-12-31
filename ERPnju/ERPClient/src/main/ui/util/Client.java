package main.ui.util;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public class Client extends RecursiveTreeObject<Client>{
	SimpleStringProperty id;
	SimpleStringProperty type;
	SimpleStringProperty name;
	SimpleStringProperty level;
	
	public Client(String id,String type,String name,int level){
		this.id=new SimpleStringProperty(id);
		if(type.equals("1"))
			this.type=new SimpleStringProperty("销售商");
		else if(type.equals("2"))
			this.type=new SimpleStringProperty("供应商");
		else
			this.type=new SimpleStringProperty("销售&供应商");
		this.name=new SimpleStringProperty(name);
		this.level=new SimpleStringProperty(level+"");
	}
}
