package main.businesslogic.stockbl;

import java.util.ArrayList;

import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class FindbyPrice implements FindMethod {

	@Override
	public ArrayList<OutGoodsVO> find(ArrayList<OutGoodsVO> list, GoodsQueryItem item) {
		// TODO Auto-generated method stub
		
		if(!item.flags[2]&&!item.flags[3])
			return list;
		
		System.out.println("lower="+Double.parseDouble(item.getLower())+item.flags[3]);
		System.out.println("upper="+Double.parseDouble(item.getUpper())+item.flags[2]);
		
		ArrayList<OutGoodsVO>res=new ArrayList<>();
				
		for(OutGoodsVO g:list){
			boolean select=true;
			if(item.flags[2]){
				double lower=Double.parseDouble(item.getLower());
				if(lower>g.getOutPrice())
					select=false;
			}
			if(item.flags[3]){
				double upper=Double.parseDouble(item.getUpper());
				if(upper<g.getOutPrice())
					select=false;
			}
			if(select)
				res.add(g);
		}
		return res;
	}

}
