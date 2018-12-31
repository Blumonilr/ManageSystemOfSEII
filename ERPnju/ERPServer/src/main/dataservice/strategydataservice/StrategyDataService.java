package main.dataservice.strategydataservice;

import java.util.ArrayList;

import main.PO.*;
public interface StrategyDataService {

	public boolean insert(StrategyPO strategy);
	
	public boolean update(StrategyPO strategy);
	
	public boolean delete(StrategyPO strategy);
	
	public StrategyPO find(String id);
	
	public ArrayList<StrategyPO> finds();
	
}
