package main.businesslogic.revisebl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogicservice.reviseblservice.ReviseblService;
import main.vo.PurchaseReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptVO;
import main.vo.StockReceiptVO;

public class MockRevise implements ReviseblService{
	ArrayList<ReceiptVO> list;
	SaleReceiptVO a;
	PurchaseReceiptVO b;
	StockReceiptVO c;
	
	public MockRevise() {
		a.setID("1111");
		list.add(a);
		b.setID("2222");
		list.add(b);
		c.setID("3333");
		list.add(c);
	}

	@Override
	public ArrayList<ReceiptVO> showAllReceipt() throws RemoteException {
		return list;
	}

	@Override
	public ReceiptVO showDetailReceipt(String id) throws RemoteException {
		if(id=="1111")
			return a;
		else if(id=="2222")
			return b;
		else if(id=="3333")
			return c;
		return null;
	}

	@Override
	public void revise(ReceiptVO receiptVO) throws RemoteException {
		if(receiptVO.getID()=="1111"){
			a.setReviseTime(Calendar.getInstance());
		}
		else if(receiptVO.getID()=="2222")
			b.setReviseTime(Calendar.getInstance());
		else
			c.setReviseTime(Calendar.getInstance());
	}

	@Override
	public void reviseMany(ArrayList<ReceiptVO> receiptList, int operand) throws RemoteException {
		for(int i=0;i<receiptList.size();i++){
			receiptList.get(i).setReviseTime(Calendar.getInstance());
		}
		
	}
		
}