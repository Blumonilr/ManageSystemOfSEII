package main.businesslogic.chatbl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import main.businesslogicservice.chatservice.ChatService;
import main.data.chatdata.ChatData;
import main.dataservice.chatdataservice.ChatDataService;
import main.runner.ServerRunner;

public class Chat implements ChatService {

	@Override
	public void remove(String name) throws RemoteException {

		ServerRunner.getChatServer().removeClient(name);
	}

	@Override
	public void uploadTempMsg(String name,ArrayList<String> msgs) throws RemoteException {

		ChatDataService cds=new ChatData();
		for(String msg:msgs)
			cds.saveMsg(name, msg);

	}

}
