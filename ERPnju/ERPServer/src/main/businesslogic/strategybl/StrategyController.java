package main.businesslogic.strategybl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import main.businesslogicservice.strategyblservice.StrategyblService;
import main.vo.StrategyVO;

public class StrategyController implements StrategyblService {
	Strategy strategy;

	public StrategyController() {
		// TODO 自动生成的构造函数存根
		strategy=new Strategy();
	}

	@Override
	public boolean makeStrategy(StrategyVO strategyVO) throws RemoteException {
		// TODO 自动生成的方法存根
		return strategy.makeStrategy(strategyVO);
	}

	@Override
	public boolean removeStrategy(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return strategy.removeStrategy(id);
	}

	@Override
	public StrategyVO findStrategy(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return strategy.getStrategyById(id);
	}

	@Override
	public ArrayList<StrategyVO> findAllStrategy() throws RemoteException {
		// TODO 自动生成的方法存根
		return strategy.findAllStrategy();
	}

}
