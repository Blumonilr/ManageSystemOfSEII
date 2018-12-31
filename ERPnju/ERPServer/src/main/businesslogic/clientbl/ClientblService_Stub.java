package main.businesslogic.clientbl;

import main.businesslogicservice.clientblservice.ClientblService;
import main.vo.ClientVO;

import java.util.ArrayList;

public class ClientblService_Stub implements ClientblService {
	
	ArrayList<ClientVO> clientList;
	
	public ClientblService_Stub(ArrayList<ClientVO> clientList) {
		super();
		this.clientList = clientList;
	}

	@Override
	public boolean addClient(ClientVO obj) {
		// TODO Auto-generated method stub
		for(ClientVO c:clientList){
			if(c.getID().equals(obj.getID())){
				return false;
			}
		}
		clientList.add(obj);
		return true;
	}

	@Override
	public boolean delClient(String id) {
		// TODO Auto-generated method stub
		for(ClientVO c:clientList){
			if(c.getID().equals(id)){
				clientList.remove(c);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean modifyClient(ClientVO obj) {
		// TODO Auto-generated method stub
		for(ClientVO c:clientList){
			if(c.getID().equals(obj.getID())){
				clientList.remove(c);
				clientList.add(obj);
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<ClientVO> searchClient(ClientVO obj) {
		// TODO Auto-generated method stub
		//根据许多属性查找匹配客户
		//暂不实现
		return null;
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
	
}
