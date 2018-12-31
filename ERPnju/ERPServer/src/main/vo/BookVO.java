package main.vo;

import main.PO.*;
import main.businesslogic.bookbl.ObjectsTransformer;

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

    public BookVO( ArrayList<InGoodsVO> goodsList, ArrayList<ClientVO> clientList, ArrayList<AccountVO> accountList, Calendar time, ArrayList<ClassVO> classList) {
        this.goodsList = goodsList;
        this.clientList = clientList;
        this.accountList = accountList;
        this.classList = classList;
        this.time = Calendar.getInstance();
    }

    public BookVO(BookPO book){
        time=book.getTime();
        ArrayList<GoodsPO> goodsList=book.getGoodsList();
       ArrayList<ClientPO> clientList=book.getClientList();
       ArrayList<AccountPO> accountList=book.getAccountList();
        ArrayList<ClassPO> classList=book.getClassList();
        ObjectsTransformer transer=new ObjectsTransformer();
        this.goodsList=new ArrayList<>();
        this.clientList=new ArrayList<>();
        this.accountList=new ArrayList<>();
        this.classList=new ArrayList<>();
        for (GoodsPO aGoodsList : goodsList) {
            this.goodsList.add(transer.toGoodsVO(aGoodsList));
        }
        for (ClientPO aClientList : clientList) {
            this.clientList.add(transer.toClientVO(aClientList));
        }
        for (AccountPO anAccountList : accountList) {
            this.accountList.add(transer.toAccountVO(anAccountList));
        }
        for(ClassPO aClassList: classList){
            this.classList.add(transer.toClassVO(aClassList));
        }
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
