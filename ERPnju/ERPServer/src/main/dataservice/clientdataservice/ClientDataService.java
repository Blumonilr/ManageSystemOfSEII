package main.dataservice.clientdataservice;

import java.util.ArrayList;

import main.PO.*;

public interface ClientDataService {

	public ClientPO showClient(String id);
	
	public ArrayList<ClientPO> show();
	
	public boolean addClient(ClientPO obj);
	
	public boolean deleteClient(String id);
	
	public boolean modifyClient(ClientPO obj);
	
}
