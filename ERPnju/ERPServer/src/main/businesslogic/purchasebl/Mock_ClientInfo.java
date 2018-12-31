package main.businesslogic.purchasebl;

import java.util.ArrayList;

import main.businesslogic.clientbl.ClientInfo;
import main.vo.ClientVO;

public class Mock_ClientInfo implements ClientInfo {

	ArrayList<ClientVO> clientList;
	
	/**
	 * @param clientList
	 */
	public Mock_ClientInfo(ArrayList<ClientVO> clientList) {
		super();
		this.clientList = clientList;
	}

	@Override
	public ClientVO showClient(String id) {
		// TODO Auto-generated method stub
		for(ClientVO c:clientList){
			if(c.getID().equals(id)){
				return c;
			}
		}
		return null;
	}

	@Override
	public ArrayList<ClientVO> show() {
		// TODO Auto-generated method stub
		return clientList;
	}

	@Override
	public boolean addClient(ClientVO obj) {
		// TODO Auto-generated method stub
		clientList.add(obj);
		return true;
	}

}
