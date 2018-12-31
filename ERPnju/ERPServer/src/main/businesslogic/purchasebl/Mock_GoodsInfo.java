package main.businesslogic.purchasebl;

import java.util.ArrayList;

import main.businesslogic.stockbl.GoodsInfo;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;

public class Mock_GoodsInfo implements GoodsInfo {

	ArrayList<OutGoodsVO> goodsList;
	
	/**
	 * @param goodsList
	 */
	public Mock_GoodsInfo(ArrayList<OutGoodsVO> goodsList) {
		super();
		this.goodsList = goodsList;
	}

	@Override
	public OutGoodsVO getGoods(String id) {
		// TODO Auto-generated method stub
		for(OutGoodsVO g:goodsList){
			if(g.getId().equals(id)){
				return g;
			}
		}
		return null;
	}

	@Override
	public ArrayList<OutGoodsVO> getGoods() {
		// TODO Auto-generated method stub
		return goodsList;
	}

	@Override
	public ArrayList<OutGoodsVO> findGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		return goodsList;
	}

	@Override
	public boolean addGoods(InGoodsVO obj) {
		// TODO Auto-generated method stub
		
		return true;
	}

	@Override
	public boolean modifyGoods(OutGoodsVO obj) {
		// TODO Auto-generated method stub
		for(OutGoodsVO g:goodsList){
			if(g.getId().equals(obj.getId())){
				goodsList.remove(g);
				goodsList.add(obj);
				return true;
			}
		}
		return false;
	}

}
