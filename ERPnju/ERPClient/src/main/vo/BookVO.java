package main.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/15 21:14
*/
public class BookVO implements Serializable{
    private static final long serialVersionUID = 5814944634628123532L;
    private ArrayList<InGoodsVO> goodsList;
    private ArrayList<ClientVO> clientList;
    private ArrayList<AccountVO> accountList;
    private ArrayList<ClassVO> classList;
    private Calendar time;

    public BookVO(){
        goodsList=null;
        clientList=null;
        accountList=null;
        classList=null;
        time=Calendar.getInstance();
    }

    public BookVO(ArrayList<InGoodsVO> goodsList, ArrayList<ClientVO> clientList, ArrayList<AccountVO> accountList, Calendar time, ArrayList<ClassVO> classList) {
        this.goodsList = goodsList;
        this.clientList = clientList;
        this.accountList = accountList;
        this.classList = classList;
        this.time = Calendar.getInstance();
    }



    public ArrayList<InGoodsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<InGoodsVO> goodsList) {
        this.goodsList = goodsList;
    }

    public ArrayList<ClientVO> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<ClientVO> clientList) {
        this.clientList = clientList;
    }

    public ArrayList<AccountVO> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<AccountVO> accountList) {
        this.accountList = accountList;
    }

    public Calendar getTime() {
        return time;
    }

    public String getStringTime() {
        DateFormat df=new SimpleDateFormat("yyyyMM");
        return df.format(time.getTime());
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public ArrayList<ClassVO> getClassList() {
        return classList;
    }

    public void setClassList(ArrayList<ClassVO> classList) {
        this.classList = classList;
    }
}
