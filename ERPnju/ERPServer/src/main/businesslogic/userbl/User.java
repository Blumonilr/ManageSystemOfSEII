/*@Author: 吴林漾
 * 
 */
package main.businesslogic.userbl;

import java.util.ArrayList;

import main.PO.UserPO;
import main.data.userdata.UserData;
import main.vo.UserVO;

public class User implements UserInfo{
	UserData userData;
	
	public User(){
		userData=new UserData();
	}
	
	@Override
	public int getUserLevel(String id){
		UserPO userpo=userData.find(id);
		return userpo.getPower();
	}
		
	public boolean login(String id,String password){
		UserPO userpo=userData.find(id);
		if(userpo==null)
			return false;
		else{
			if(userpo.getPassword().equals(password))
				return true;
		}
			return false;
		}
		
	public boolean logout(String id){
		UserPO userpo=userData.find(id);
		if(userpo==null)
			return false;
		return true;
	}
		
	public boolean add(int type,String id,String password){
		UserPO userpo=new UserPO(type, id, password, 1);
		if(userData.insert(userpo))
			return true;
		return false;
	}
		
	public boolean remove(String id) {
		UserPO userpo=userData.find(id);
		if(userpo==null)
			return false;
		if(userData.delete(userpo))
			return true;
		return false;
	}
		
	public boolean modify(UserVO obj){
		UserPO userpo=userData.find(obj.getId());
		if(userpo!=null){
			userpo.setPower(obj.getPower());
			userpo.setPassword(obj.getPassword());
			if(userData.update(userpo))
				return true;
			else
				return false;
		}
		return false;
	}

	public ArrayList<UserVO> showUsers(String Info) {
		ArrayList<UserPO> list=userData.finds(Info);
		ArrayList<UserVO> newlist=new ArrayList<UserVO>();
		for(UserPO obj:list){
			if(obj!=null){
				UserVO uservo=new UserVO(obj.getId(),obj.getPassword(),obj.getType(),obj.getPower());
				newlist.add(uservo);
			}
		}
		return newlist;
	}

	@Override
	public UserVO showUser(String id) {
		// TODO 自动生成的方法存根
		UserPO obj=userData.find(id);
		if(obj==null)
			return null;
		return new UserVO(obj.getId(),obj.getPassword(),obj.getType(),obj.getPower());
	}

}
