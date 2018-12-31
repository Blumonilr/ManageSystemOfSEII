package main.businesslogicservice.bookblservice;

import main.vo.BookVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *@Author:王一博
 *@Description:
 *@Date:2017/11/7 22:11
 */
public interface BookblService extends Remote {
    public boolean initBook() throws RemoteException;

    public boolean saveDraft() throws RemoteException;

    public void cancelChange() throws RemoteException;

    public BookVO showBook() throws RemoteException;

    public BookVO getNow() throws RemoteException;

    public void creatingBook() throws RemoteException;
}
