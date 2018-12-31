package main.businesslogic.stockbl;

import java.util.ArrayList;

import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public interface FindMethod {

	ArrayList<OutGoodsVO>find(ArrayList<OutGoodsVO>list,GoodsQueryItem item);
}
