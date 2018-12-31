package main.dataservice.chatdataservice;

import java.util.ArrayList;

public interface ChatDataService {

	public boolean saveMsg(String usedName,String msg);
	public ArrayList<String>readMsg(String userName);
}
