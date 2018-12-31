package main.businesslogicservice.reviseblservice;

import main.vo.ReceiptVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ReviseblService extends Remote{
    public ArrayList<ReceiptVO> showAllReceipt(String Userid) throws RemoteException;
    public ReceiptVO showReceipt(String id) throws RemoteException;
    public boolean revise(ReceiptVO receiptVO,int operand) throws RemoteException;
	public boolean updateReceipt(ReceiptVO r)throws RemoteException;
}
