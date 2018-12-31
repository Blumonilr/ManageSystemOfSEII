package main.businesslogic.revisebl;

import main.vo.ReceiptVO;
import main.businesslogicservice.reviseblservice.ReviseblService;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ReviseblServiceImpl_Stub implements ReviseblService {
    @Override
    public ArrayList<ReceiptVO> showAllReceipt() throws RemoteException {
        ArrayList<ReceiptVO> receiptVO=new ArrayList<>();
        return receiptVO;
    }

    @Override
    public ReceiptVO showDetailReceipt(String id) throws RemoteException {
        ReceiptVO receiptVO=new ReceiptVO();
        return receiptVO;
    }

    @Override
    public void revise(ReceiptVO receiptVO) throws RemoteException {

    }

    @Override
    public void reviseMany(ArrayList<ReceiptVO> receiptList, int operand) throws RemoteException {

    }


}
