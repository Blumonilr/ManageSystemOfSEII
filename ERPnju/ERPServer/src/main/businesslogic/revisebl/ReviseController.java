package main.businesslogic.revisebl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import main.businesslogicservice.reviseblservice.ReviseblService;
import main.vo.ReceiptVO;

public class ReviseController implements ReviseblService{
	Revise revise;

	public ReviseController(){
		// TODO 自动生成的构造函数存根
		revise=new Revise();
	}

	@Override
	public ArrayList<ReceiptVO> showAllReceipt(String Userid) throws RemoteException {
		// TODO 自动生成的方法存根
		return revise.showAllReceipt(Userid);
	}

	@Override
	public ReceiptVO showReceipt(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return revise.showReceipt(id);
	}

	@Override
	public boolean revise(ReceiptVO receiptVO,int operand) throws RemoteException {
		// TODO 自动生成的方法存根
		return revise.revise(receiptVO,operand);
	}


	@Override
	public boolean updateReceipt(ReceiptVO r) throws RemoteException {
		// TODO 自动生成的方法存根
		return revise.update(r);
	}

}
