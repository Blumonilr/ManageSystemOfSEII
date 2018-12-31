package main.dataservice.userdataservice;

import java.util.ArrayList;

import main.PO.*;

public interface UserDataService {
	public boolean insert(UserPO User);
	public boolean update(UserPO User);
	public boolean delete(UserPO User);
	public UserPO find(String id);
	public ArrayList<UserPO> finds(String info);
}
