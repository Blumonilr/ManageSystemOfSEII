package main.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/7 22:10
*/
//销售明细表条目类
public class SaleFormLineItem implements Serializable{
    private static final long serialVersionUID = 4149181062206293712L;
    private Calendar time;
    private String goodName;
    private String modelNumber;
    private double quantity;
    private double price;
    private double totalPrice;

    public SaleFormLineItem(){
        time=null;
        goodName=null;
        modelNumber=null;
        quantity=0;
        price=0;
        totalPrice=0;
    }
    public SaleFormLineItem(Calendar time, String goodName, String modelNumber, double quantity, double price){
        this.time=time;
        this.goodName=goodName;
        this.modelNumber=modelNumber;
        this.price=price;
        this.quantity=quantity;
        this.totalPrice=price*quantity;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        totalPrice = quantity*price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        totalPrice = quantity*price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        totalPrice = quantity*price;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
