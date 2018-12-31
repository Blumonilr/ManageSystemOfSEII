package main.businesslogic.userbl;

import main.businesslogicservice.userblservice.UserblService;
import main.vo.UserVO;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserblServiceImpl_Stub implements UserblService {
    @Override
    public boolean login(String id, String password) throws RemoteException {
        return true;
    }

    @Override
    public boolean logout(String id) throws RemoteException {
        return true;
    }

    @Override
    public boolean add(String id, String password) throws RemoteException {
        return true;
    }

    @Override
    public boolean remove(String id) throws RemoteException {
        return true;
    }

    @Override
    public boolean modify(UserVO uservo,String id) throws RemoteException {
        return true;
    }

    @Override
    public ArrayList<UserVO> showUsers() throws RemoteException {
        ArrayList<UserVO> userList=new ArrayList<>();
        return userList;
    }
}