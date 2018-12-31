package main.businesslogic.formbl;

import main.businesslogicservice.formblservice.FormblService;
import main.vo.ReceiptVO;
import main.vo.SaleFormLineItem;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

public class FormController implements FormblService{
    Form tool=new Form();
    @Override
    public ArrayList<SaleFormLineItem> saleList(Calendar beginTime, Calendar endTime, String condition) throws RemoteException {
        return tool.saleList(beginTime, endTime, condition);
    }

    @Override
    public ArrayList<ReceiptVO> recordList(Calendar beginTime, Calendar endTime, String condition) throws RemoteException {
        return tool.recordList(beginTime, endTime, condition);
    }

    @Override
    public ReceiptVO showDetailReceipt(String id) throws RemoteException {
        return tool.showDetailReceipt(id);
    }

    @Override
    public boolean offsetAndCopy(ReceiptVO offSetReceipt) throws RemoteException {
        return tool.offsetAndCopy(offSetReceipt);
    }

    @Override
    public double[] businessList(Calendar beginTime, Calendar endTime) throws RemoteException {
        return tool.businessList(beginTime, endTime);
    }

    @Override
    public boolean offset(String id) throws RemoteException {
        return tool.offset(id);
    }
}
