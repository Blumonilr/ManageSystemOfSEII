package main.businesslogic.clientbl;

import java.util.ArrayList;

import main.vo.ClientVO;

public interface ClientInfo {
	public ClientVO showClient(String id);
	public ArrayList<ClientVO> show();
	public boolean addClient(ClientVO obj);
	public boolean modifyClient(ClientVO obj);
}
