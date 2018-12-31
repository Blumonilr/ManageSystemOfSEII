package main.businesslogic.userbl;

import main.vo.UserVO;

public interface UserInfo {
	public int getUserLevel(String id) ;	
	public UserVO showUser(String id);

}
