package main.businesslogicservice.userblservice;

import main.vo.UserVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UserblService extends Remote{
    public boolean userlogin(String id,String password) throws RemoteException;
    public boolean userlogout(String id) throws RemoteException;
    public boolean addUser(int type,String id,String password) throws RemoteException;
    public boolean removeUser(String id) throws RemoteException;
    public ArrayList<UserVO> showUsers(String Info) throws RemoteException;
    public boolean modifyUser(UserVO uservo) throws RemoteException;
    public UserVO showUser(String id) throws RemoteException;
}
