package main.businesslogic.stockbl;

import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;

import java.util.ArrayList;
/**
 * 
 * @author qyc
 *
 */
public interface GoodsInfo {

	public OutGoodsVO getGoods(String id);
	public ArrayList<OutGoodsVO>getGoods();
	public ArrayList<OutGoodsVO> findGoods(GoodsQueryItem obj);
	public boolean addGoods(InGoodsVO obj);
	public boolean modifyGoods(OutGoodsVO obj);
}
