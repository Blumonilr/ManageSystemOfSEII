package main.businesslogic.strategybl;

import java.util.ArrayList;

import main.vo.ReceiptVO;
import main.vo.StrategyVO;

public interface StrategyInfo{
	public ArrayList<StrategyVO> adviseStrategy(ReceiptVO obj);
	public StrategyVO getStrategyById(String id);
}
