package main.PO;

import main.businesslogic.bookbl.ObjectsTransformer;
import main.vo.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookPO  implements Serializable{
	private static final long serialVersionUID = -4558910675078118254L;
	private ArrayList<GoodsPO> goodsList;//名称，类别，型号，进价/售价(默认为上年平均)，最近进价和最近售价留空
	private ArrayList<ClientPO> clientList;//客户分类，某一个客户的名称，联系方式等，应收应付(之前遗留)
	private ArrayList<AccountPO> accountList;//名称，余额
	private ArrayList<ClassPO> classList;//商品分类信息
	private Calendar time;
	
	//构造
	public BookPO(ArrayList<GoodsPO> goodsList, ArrayList<ClientPO> clientList, ArrayList<AccountPO> accountList,
				  ArrayList<ClassPO> classList) {
		super();
		this.goodsList = goodsList;
		this.clientList = clientList;
		this.accountList = accountList;
		this.classList = classList;
	}

	public BookPO(BookVO book) {
		time=book.getTime();
		ObjectsTransformer transer=new ObjectsTransformer();
		this.goodsList=new ArrayList<>();
		this.clientList=new ArrayList<>();
		this.accountList=new ArrayList<>();
		this.classList=new ArrayList<>();
		ArrayList<InGoodsVO> goodsList=book.getGoodsList();
		ArrayList<ClientVO> clientList=book.getClientList();
		ArrayList<AccountVO> accountList=book.getAccountList();
		ArrayList<ClassVO> classList=book.getClassList();
		for (InGoodsVO aGoodsList : goodsList) {
			this.goodsList.add(transer.toGoodsPO(aGoodsList));
		}
		for (ClientVO aClientList : clientList) {
			this.clientList.add(transer.toClientPO(aClientList));
		}
		for (AccountVO anAccountList : accountList) {
			this.accountList.add(transer.toAccountPO(anAccountList));
		}
		for(ClassVO aClassList: classList){
			this.classList.add(transer.toClassPO(aClassList));
		}
	}


	public ArrayList<ClientPO> getClientList() {
		return clientList;
	}
	public void setClientList(ArrayList<ClientPO> clientList) {
		this.clientList = clientList;
	}
	public ArrayList<AccountPO> getAccountList() {
		return accountList;
	}
	public void setAccountList(ArrayList<AccountPO> accountList) {
		this.accountList = accountList;
	}




	public ArrayList<ClassPO> getClassList() {
		return classList;
	}

	public void setClassList(ArrayList<ClassPO> classList) {
		this.classList = classList;
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

	public ArrayList<GoodsPO> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(ArrayList<GoodsPO> goodsList) {
		this.goodsList = goodsList;
	}
}
