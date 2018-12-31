package main.businesslogic.clientbl;

import java.util.ArrayList;

import main.businesslogicservice.clientblservice.ClientblService;
import main.vo.ClientVO;

/**
 * 
 * @author Kate
 *
 */
public class ClientController implements ClientblService {

	private Client client;
	
	public ClientController(){
		client=new Client();
	}
	
	@Override
	public boolean addClient(ClientVO obj) {
		// TODO Auto-generated method stub
		return client.addClient(obj);
	}

	@Override
	public boolean delClient(String id) {
		// TODO Auto-generated method stub
		return client.delClient(id);
	}

	@Override
	public boolean modifyClient(ClientVO obj) {
		// TODO Auto-generated method stub
		return client.modifyClient(obj);
	}

	@Override
	public ClientVO showClient(String id) {
		// TODO Auto-generated method stub
		return client.showClient(id);
	}

	@Override
	public ArrayList<ClientVO> show() {
		// TODO Auto-generated method stub
		return client.show();
	}

	@Override
	public ArrayList<ClientVO> searchClientbyNameTelCounterTypeLevel(String name, String tel, String counterman,
			int type, int level) {
		// TODO Auto-generated method stub
		return client.searchClient(name,tel,counterman,type,level);
	}

}
