package test.test_bl.purchase;

import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogic.purchasebl.Purchase;
import main.vo.ClientVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.ReceiptVO;

public class Test_Purchase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<OutGoodsVO> goodsList=new ArrayList<OutGoodsVO>();
		ArrayList<ClientVO> clientList=new ArrayList<ClientVO>();
		ArrayList<ReceiptVO> receiptList=new ArrayList<ReceiptVO>();
		Calendar c=Calendar.getInstance();
		c.set(2017, 11, 23);
		OutGoodsVO goods1=new OutGoodsVO("商品1","00000001","A型","灯具类","0001",45.5,90.0,45.5,90.0,1000,50,c);
		c.set(2016, 11, 23);
		OutGoodsVO goods2=new OutGoodsVO("商品1","00000002","B型","灯具类","0001",50.8,120.0,51,130,1000,50,c);
		c.set(2013, 11, 23);
		OutGoodsVO goods3=new OutGoodsVO("商品3","00000003","A型","食品类","0002",5.5,9.0,5.5,9.0,1000,500,c);
		c.set(2017, 10, 23);
		OutGoodsVO goods4=new OutGoodsVO("商品4","00000004","A型","玩具类","0003",12.0,30.0,12.0,30.0,100,50,c);
		goodsList.add(goods1);
		goodsList.add(goods2);
		goodsList.add(goods3);
		goodsList.add(goods4);
		
		ClientVO client1=new ClientVO("qyc","001","进货商",1,"15952022680","1栋","161250103@smail","21000",10000.0,999.0,888.0,"0001");
		ClientVO client2=new ClientVO("jbs","002","销售商",1,"15952022680","6栋","161250103@smail","21000",10000.0,999.0,888.0,"0001");
		clientList.add(client1);
		clientList.add(client2);
		
		ArrayList<PurchaseReceiptBothVOLineItem> lineItemList=new ArrayList<>();
		PurchaseReceiptBothVOLineItem pr1=new PurchaseReceiptBothVOLineItem("0001", "iphone X", "256g dark grey", 100, 878800, "");
		PurchaseReceiptBothVOLineItem pr2=new PurchaseReceiptBothVOLineItem("0002", "iphone 8plus", "128g silver", 150, 1093200, "");
		lineItemList.add(pr1);
		lineItemList.add(pr2);
		
		ReceiptVO receipt1=new PurchaseReceiptVO("qyc","01仓","0001","备注",lineItemList);
		ReceiptVO receipt2=new PurchaseReceiptVO("jbs","01仓","0002","备注",lineItemList);
		receiptList.add(receipt1);
		receiptList.add(receipt2);
		
		
		
		Purchase purchase=new Purchase(receiptList,goodsList,clientList);
		
		ArrayList<OutGoodsVO> goods=purchase.showGoods();
		for(OutGoodsVO g: goods){
			System.out.println(g.getName());
		}
		ArrayList<ClientVO> client=purchase.showClient();
		for(ClientVO cl:client){
			System.out.println(cl.getClientName());
		}
		ArrayList<ReceiptVO> receipt=purchase.showReceipts(41,Calendar.getInstance().set(year, month, date);, endTime);
		
	}

}