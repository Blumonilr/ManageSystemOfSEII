package main.businesslogic.clientbl;

import java.util.ArrayList;

import main.PO.ClientPO;
import main.businesslogic.stockbl.IdAssigner;
import main.data.clientdata.ClientDataImpl;
import main.dataservice.clientdataservice.ClientDataService;
import main.vo.ClientVO;
/**
 * 
 * @author Kate
 * setupTime: 2017/11/7
 *
 */
public class Client implements ClientInfo {
	
	private ClientDataService cds;
	
	public Client(){
		cds=new ClientDataImpl();
	}
	
	/**
	 * 姓名、电话相同就认为是同一个客户！！！
	 */
	public synchronized boolean addClient(ClientVO obj) {
		if(obj==null||!searchClient(obj.getClientName(), obj.getPhone(), null, 0, 0).isEmpty())
			return false;
		
		String id=calculateId(obj.getClientName());
		obj.setID(id);
		return cds.addClient(vo2po(obj));
	}

	private String calculateId(String name){
		String id="";
		for(char c:name.toCharArray()){
			id+=Integer.toHexString(c);
		}
		return id;
	}
	
	public synchronized boolean delClient(String id) {
		if(id==null||showClient(id)==null)
			return false;
		return cds.deleteClient(id);
	}

	
	public synchronized boolean modifyClient(ClientVO obj) {
		if(obj==null||showClient(obj.getID())==null)
			return false;
		return cds.modifyClient(vo2po(obj));
	}
	
	public ClientVO showClient(String id) {
		
		return po2vo(cds.showClient(id));
	}

	
	public ArrayList<ClientVO> show() {
		ArrayList<ClientVO>res=new ArrayList<>();
		ArrayList<ClientPO>list=cds.show();
		for(ClientPO c:list)
			res.add(po2vo(c));
		return res;
	}

	public ArrayList<ClientVO> searchClient(String name, String tel, String counterman, int type, int level) {
		// TODO Auto-generated method stub
		ArrayList<ClientVO>res=new ArrayList<>();
		ArrayList<ClientPO>list=cds.show();
		
		for(ClientPO c:list){
			if(match(name, tel, counterman, type, level, c))
				res.add(po2vo(c));
		}
		return res;
	}
	
	private boolean match(String name, String tel, String counterman, int type, int level,ClientPO obj){
//		boolean flagN=true,flagTe=true,flagC=true,flagTy=true,flagL=true;
		
		if(name!=null&&!obj.getName().contains(name))
			return false;
		if(tel!=null&&!obj.getTel().contains(tel))
			return false;
		if(counterman!=null&&!obj.getCounterman().contains(counterman))
			return false;
		if(type!=0&&type!=obj.getType())
			return false;
		if(level!=0&&level!=obj.getLevel())
			return false;
		return true;
	}
	
	private ClientPO vo2po(ClientVO obj){
		if(obj==null)
			return null;
		
		int type=1;
		
		if(obj.getType().equals("供应商")){
			type=2;
		}
		ClientPO c=new ClientPO(obj.getID(), obj.getClientName(), obj.getPhone(),
				obj.getAddress(), obj.getPostcode(), obj.getEmail(), obj.getDefaultUserID(),
				type, obj.getLevel(), obj.getPay(), 
				obj.getCollect(), obj.getCollectTop());
		return c;
	}
	
	private ClientVO po2vo(ClientPO obj){
		if(obj==null)
			return null;
		String type="销售商";
		if(obj.getType()==2){
			type="供应商";
		}
		ClientVO c=new ClientVO(obj.getName(), obj.getId(),type,
				obj.getLevel(), obj.getTel(), obj.getAddr(), obj.getEmail(), 
				obj.getPostNum(), obj.getCollectTop(), obj.getCollect(), 
				obj.getPay(), obj.getCounterman());
		return c;
	}
}
