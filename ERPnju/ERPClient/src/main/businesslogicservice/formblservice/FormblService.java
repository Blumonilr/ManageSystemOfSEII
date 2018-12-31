package main.businesslogicservice.formblservice;

import main.vo.ReceiptVO;
import main.vo.SaleFormLineItem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/15 21:15
*/
public interface FormblService extends Remote {
    public ArrayList<SaleFormLineItem> saleList(Calendar beginTime, Calendar endTime, String condition) throws RemoteException;

    public ArrayList<ReceiptVO> recordList(Calendar beginTime,Calendar endTime,String condition) throws RemoteException;

    public ReceiptVO showDetailReceipt(String id) throws RemoteException;

    public boolean offsetAndCopy(ReceiptVO offSetReceipt) throws RemoteException;

    public double[] businessList(Calendar beginTime,Calendar endTime) throws RemoteException;

    public boolean offset(String id) throws RemoteException;
}
