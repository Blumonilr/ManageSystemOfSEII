package main.businesslogicservice.strategyblservice;

import main.vo.StrategyVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface StrategyblService extends Remote{
    public boolean makeStrategy(StrategyVO strategyVO) throws RemoteException;
    public boolean removeStrategy(String id) throws RemoteException;
    public StrategyVO findStrategy(String id) throws RemoteException;
    public ArrayList<StrategyVO> findAllStrategy() throws RemoteException;
}
