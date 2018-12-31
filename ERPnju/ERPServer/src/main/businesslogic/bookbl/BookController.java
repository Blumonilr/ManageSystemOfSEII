package main.businesslogic.bookbl;

import main.businesslogicservice.bookblservice.BookblService;
import main.vo.BookVO;

import java.rmi.RemoteException;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/30 11:14
*/
public class BookController implements BookblService{
    Book tool=new Book();
    @Override
    public boolean initBook() throws RemoteException {
        return tool.initBook();
    }

    @Override
    public boolean saveDraft() throws RemoteException {
        return tool.saveDraft();
    }

    @Override
    public void cancelChange() throws RemoteException {
        tool.cancelChange();
    }

    @Override
    public BookVO showBook() throws RemoteException {
        return tool.showBook();
    }

    @Override
    public BookVO getNow() throws RemoteException {
        return tool.getNow();
    }

    @Override
    public void creatingBook() throws RemoteException{
        tool.creatingBook();
    }
}
