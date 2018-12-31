package main.data.userdata;

import java.util.ArrayList;
import java.util.Iterator;

import main.PO.UserPO;
import main.dataservice.userdataservice.UserDataService;

public class UserData_Stub implements UserDataService {

	ArrayList<UserPO>list;
	
	
	public UserData_Stub(ArrayList<UserPO> list) {
		super();
		this.list = list;
	}

	@Override
	public boolean insert(UserPO User) {
		// TODO Auto-generated method stub
		Iterator<UserPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getId().equals(User.getId()))
				return false;
		list.add(User);
		return true;
	}

	@Override
	public boolean update(UserPO User) {
		// TODO Auto-generated method stub
		Iterator<UserPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getId().equals(User.getId())){
				it.remove();
				list.add(User);
				return true;
			}
		return false;
	}

	@Override
	public boolean delete(UserPO User) {
		// TODO Auto-generated method stub
		Iterator<UserPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getId().equals(User.getId())){
				it.remove();
				return true;
			}
		return false;
	}

	@Override
	public UserPO find(String id) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.size();i++){
			if(list.get(i).getId().equals(id))
				return list.get(i);
		}
		return null;
	}

	//根据客户类型、id、等级模糊查找
	@Override
	public ArrayList<UserPO> finds(String info) {
		// TODO Auto-generated method stub
		ArrayList<UserPO>res=new ArrayList<>();
		for(int i=0;i<list.size();i++){
			if(list.get(i).getId().indexOf(info)>=0
					||list.get(i).getType().equals(info)
					||list.get(i).getPower().equals(info)){
				res.add(list.get(i));
			}
		}
		return res;
	}
	
	

}
