package main.businesslogic.stockbl;

import java.util.ArrayList;

import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class FindbyName implements FindMethod {

	@Override
	public ArrayList<OutGoodsVO> find(ArrayList<OutGoodsVO> list, GoodsQueryItem item) {
		// TODO Auto-generated method stub
		
		if(!item.flags[0])
			return list;
		
		ArrayList<OutGoodsVO>res=new ArrayList<>();
		for(OutGoodsVO g:list){
			if(g.getName().contains(item.getName()))
				res.add(g);
		}
		return res;
	}

}
