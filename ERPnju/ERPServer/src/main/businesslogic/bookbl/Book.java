package main.businesslogic.bookbl;

import main.PO.BookPO;
import main.businesslogic.accountbl.Account;
import main.businesslogic.accountbl.AccountInfo;
import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.businesslogic.stockbl.ClassInfo;
import main.businesslogic.stockbl.Classes;
import main.businesslogic.stockbl.Goods;
import main.businesslogic.stockbl.GoodsInfo;
import main.data.bookdata.BookData;
import main.dataservice.bookdataservice.BookDataService;
import main.vo.*;

import java.util.ArrayList;

/**
*@Author: 王一博
*@Description:
*@Date:2017/11/30 11:14
*/
public class Book {
    BookDataService bookData=new BookData();
    AccountInfo accountInfo=new Account();
    ClientInfo clientInfo=new Client();
    GoodsInfo goodsInfo=new Goods();
    ClassInfo classInfo=new Classes();

    public boolean initBook() {
        BookVO book=getNow();
        BookPO po=new BookPO(book);
        bookData.initBook(po);
        return true;
    }

    public boolean saveDraft() {
        return bookData.saveDraft();
    }

    public void cancelChange() {
        bookData.cancelChange();
    }

    //返回期初信息
    public BookVO showBook() {
        return bookData.showBook();
    }

    /**
     * 新建一个时间为null的bookvo，用接口获得四个列表
     */

    public BookVO getNow(){
        ArrayList<InGoodsVO> goodsList=new ArrayList<>();
        ArrayList<OutGoodsVO> outGoods=goodsInfo.getGoods();
        ArrayList<ClientVO> clientList=clientInfo.show();
        ArrayList<AccountVO> accountList=accountInfo.getAccounts();
        ArrayList<ClassVO> classList=classInfo.showClass();

        ObjectsTransformer transer=new ObjectsTransformer();
        for(int i=0;i<outGoods.size();i++){
            goodsList.add(transer.toInGoodsVO(outGoods.get(i)));
        }
        return new BookVO(goodsList,clientList,accountList,null,classList);
    }

    public void creatingBook(){
        bookData.creatingBook();
    };
}
