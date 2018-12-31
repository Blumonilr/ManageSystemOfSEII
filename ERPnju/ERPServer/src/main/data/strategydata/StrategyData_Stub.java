package main.data.strategydata;

import java.util.ArrayList;
import java.util.Iterator;

import main.PO.StrategyPO;
import main.PO.TimePO;
import main.dataservice.strategydataservice.StrategyDataService;

public class StrategyData_Stub implements StrategyDataService {

	ArrayList<StrategyPO>list;
	
	public StrategyData_Stub(ArrayList<StrategyPO> list) {
		super();
		this.list = list;
	}

	@Override
	public boolean insert(StrategyPO strategy) {
		// TODO Auto-generated method stub
		Iterator<StrategyPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getID().equals(strategy))
				return false;
		list.add(strategy);
		return true;
	}

	@Override
	public boolean update(StrategyPO strategy) {
		// TODO Auto-generated method stub
		Iterator<StrategyPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getID().equals(strategy)){
				it.remove();
				list.add(strategy);
				return true;
			}
		return false;
	}

	@Override
	public boolean delete(StrategyPO strategy) {
		// TODO Auto-generated method stub
		Iterator<StrategyPO>it=list.iterator();
		while(it.hasNext())
			if(it.next().getID().equals(strategy)){
				it.remove();
				return true;
			}
		return false;
	}

	@Override
	public StrategyPO find(String id) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.size();i++){
			if(list.get(i).getID().equals(id))
				return list.get(i);
		}
		return null;
	}

	@Override
	public ArrayList<StrategyPO> finds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
