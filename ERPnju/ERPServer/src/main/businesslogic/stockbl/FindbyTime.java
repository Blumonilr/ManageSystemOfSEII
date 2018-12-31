package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;

import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class FindbyTime implements FindMethod {

	@Override
	public ArrayList<OutGoodsVO> find(ArrayList<OutGoodsVO> list, GoodsQueryItem item) {
		// TODO Auto-generated method stub
		
		if(!item.flags[4]&&!item.flags[5])
			return list;
		
		ArrayList<OutGoodsVO>res=new ArrayList<>();
		Calendar temp=null;
		for(OutGoodsVO g:list){
			if((temp=g.getDay())!=null){
				boolean select=true;
				
				if(item.flags[4]){
					Calendar start=item.getStart();
					if(start.compareTo(temp)==1)
						select=false;
				}
				if(item.flags[5]){
					Calendar end=item.getEnd();
					if(end.compareTo(temp)==-1)
						select=false;
				}
				if(select)
					res.add(g);
			}
		}
		return res;
	}

}
