package main.businesslogic.logbl;

import main.data.logdata.LogDataHelper;
import main.dataservice.logdata.LogDataService;
import main.businesslogic.userbl.User;
import main.businesslogic.userbl.UserInfo;
import main.businesslogicservice.logblservice.LogblService;;

public class Log implements LogblService {

	private LogDataService lds;
	private UserInfo users;
	
	public Log(){
		lds=new LogDataHelper();
		users=new User();
	}
	
	@Override
	public boolean saveLog(String userId, String content) {
		// TODO Auto-generated method stub
		if(!isIdValid(userId))
			return false;
		
		return lds.saveLog(userId, content);
	}

	@Override
	public String readLog(String userId) {
		// TODO Auto-generated method stub
		if(!isIdValid(userId))
			return null;
		
		return lds.readLog(userId);
	}

	private boolean isIdValid(String id){
		if(users.showUser(id)==null)
			return false;
		else
			return true;
	}
}
