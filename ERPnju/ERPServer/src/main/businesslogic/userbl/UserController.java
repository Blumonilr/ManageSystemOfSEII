package main.businesslogic.userbl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import main.businesslogicservice.userblservice.UserblService;
import main.vo.UserVO;

public class UserController implements UserblService{
	User user;

	public UserController() {
		user=new User();
	}
	
	public boolean userlogin(String id,String password) throws RemoteException{
		return user.login(id, password);
	}
	
	public boolean userlogout(String id) throws RemoteException{
		return user.logout(id);
	}
	
	public boolean addUser(int type,String id,String password) throws RemoteException{
		return user.add(type, id, password);
	}
	
	public boolean removeUser(String id) throws RemoteException{
		return user.remove(id);
	}
	
	public boolean modifyUser(UserVO obj) throws RemoteException{
		return user.modify(obj);
	}

	public ArrayList<UserVO> showUsers(String Info) throws RemoteException{
		return user.showUsers(Info);
	}

	@Override
	public UserVO showUser(String id) throws RemoteException {
		return user.showUser(id);
	}
}
