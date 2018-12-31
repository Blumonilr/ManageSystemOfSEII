package main.businesslogic.strategybl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import main.PO.BargainPackageStrategyPO;
import main.PO.ClientCouponStrategyPO;
import main.PO.ClientDiscountStrategyPO;
import main.PO.ClientGiftStrategyPO;
import main.PO.DiscountStrategyPO;
import main.PO.GiftStrategyPO;
import main.PO.StrategyPO;
import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.data.strategydata.StrategyData;
import main.dataservice.strategydataservice.StrategyDataService;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.StrategyVO;

public class Strategy implements StrategyInfo{
	StrategyDataService StrategyData;
	ClientInfo client;
	
	public Strategy(){
		StrategyData=new StrategyData();
		client=new Client();
	}

	public boolean makeStrategy(StrategyVO strategyVO){
		//查重
		if(StrategyData.find(strategyVO.getID())!=null)
			return false;
		switch(strategyVO.getType()){
		case 1:
			ClientDiscountStrategyPO cdstrpo=new ClientDiscountStrategyPO();
			cdstrpo.setID(strategyVO.getID());
			cdstrpo.setStartTime(strategyVO.getStartTime());
			cdstrpo.setEndTime(strategyVO.getEndTime());
			cdstrpo.setDiscount(((ClientDiscountStrategyVO)strategyVO).getDiscount());
			cdstrpo.setLevel(((ClientDiscountStrategyVO)strategyVO).getLevel());
			if(StrategyData.insert(cdstrpo))
				return true;
			else
				return false;
		case 2:
			ClientGiftStrategyPO cgstrpo=new ClientGiftStrategyPO();
			cgstrpo.setID(strategyVO.getID());
			cgstrpo.setStartTime(strategyVO.getStartTime());
			cgstrpo.setEndTime(strategyVO.getEndTime());
			cgstrpo.setLevel(((ClientGiftStrategyVO)strategyVO).getLevel());
			cgstrpo.setList(((ClientGiftStrategyVO)strategyVO).getList());
			if(StrategyData.insert(cgstrpo))
				return true;
			else
				return false;
		case 3:
			ClientCouponStrategyPO ccstrpo=new ClientCouponStrategyPO();
			ccstrpo.setID(strategyVO.getID());
			ccstrpo.setStartTime(strategyVO.getStartTime());
			ccstrpo.setEndTime(strategyVO.getEndTime());
			ccstrpo.setLevel(((ClientCouponStrategyVO)strategyVO).getLevel());
			ccstrpo.setCoupon(((ClientCouponStrategyVO)strategyVO).getCoupon());
			StrategyData.insert(ccstrpo);
			break;
		case 4:
			DiscountStrategyPO dstrpo=new DiscountStrategyPO();
			dstrpo.setID(strategyVO.getID());
			dstrpo.setStartTime(strategyVO.getStartTime());
			dstrpo.setEndTime(strategyVO.getEndTime());
			dstrpo.setLeastTotal(((DiscountStrategyVO)strategyVO).getLeastTotal());
			dstrpo.setDiscount(((DiscountStrategyVO)strategyVO).getDiscount());
			if(StrategyData.insert(dstrpo))
				return true;
			else
				return false;
		case 5:
			GiftStrategyPO gstrpo=new GiftStrategyPO();
			gstrpo.setID(strategyVO.getID());
			gstrpo.setStartTime(strategyVO.getStartTime());
			gstrpo.setEndTime(strategyVO.getEndTime());
			gstrpo.setLeastTotal(((GiftStrategyVO)strategyVO).getLeastTotal());
			gstrpo.setList(((GiftStrategyVO)strategyVO).getGiftlist());
			if(StrategyData.insert(gstrpo))
				return true;
			else
				return false;
		case 6:
			BargainPackageStrategyPO bpstrpo=new BargainPackageStrategyPO();
			bpstrpo.setID(strategyVO.getID());
			bpstrpo.setStartTime(strategyVO.getStartTime());
			bpstrpo.setEndTime(strategyVO.getEndTime());
			bpstrpo.setTotal(((BargainPackageStrategyVO)strategyVO).getTotal());
			bpstrpo.setGoodslist(((BargainPackageStrategyVO)strategyVO).getGoodslist());
			if(StrategyData.insert(bpstrpo))
				return true;
			else
				return false;
		}
		return true;
		
	}

	public boolean removeStrategy(String id){
		StrategyPO obj=StrategyData.find(id);
		if(obj!=null){
			if(StrategyData.delete(obj))
				return true;
			return false;
		}
		else
			return false;
	}

	@Override
	public ArrayList<StrategyVO> adviseStrategy(ReceiptVO obj) {
		ArrayList<StrategyVO> relist=new ArrayList<StrategyVO>();
		if(obj.getReceiptType()==31){
			SaleReceiptVO sale=(SaleReceiptVO)obj;
			ArrayList<StrategyPO> list=StrategyData.finds();
			for(StrategyPO str:list){
				Calendar now=Calendar.getInstance();
				now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH)+1);
				if(str.getEndTime().before(now)){
					 //处理掉时效已过的促销策略
					StrategyData.delete(str);
					continue;
				}
				switch(str.getType()){
				case 1:
					if(((ClientDiscountStrategyPO)str).getLevel()>=(client.showClient(sale.getClientId())).getLevel()){
						relist.add(getStrategyById(str.getID()));
					}
					continue;
				case 2:
					if(((ClientGiftStrategyPO)str).getLevel()>=(client.showClient(sale.getClientId())).getLevel()){
						relist.add(getStrategyById(str.getID()));;
					}
					continue;
				case 3:
					if(((ClientCouponStrategyPO)str).getLevel()>=(client.showClient(sale.getClientId())).getLevel()){
						relist.add(getStrategyById(str.getID()));
					}
					continue;
				case 4:
					double total=sale.getTotalBeforeDiscountOrVoucher();
					if(((DiscountStrategyPO)str).getLeastTotal()<=total){
						relist.add(getStrategyById(str.getID()));
					}
					continue;
				case 5:
					double total1=sale.getTotalBeforeDiscountOrVoucher();
					if(((GiftStrategyPO)str).getLeastTotal()<=total1){
						relist.add(getStrategyById(str.getID()));
					}
					continue;
				case 6:
					ArrayList<SaleReceiptBothVOLineItem> saleList2=sale.getItemList();
					Map<OutGoodsVO,Integer> map=((BargainPackageStrategyPO)str).getGoodslist();
					ArrayList<String> Goodsid=new ArrayList<>();
					for(SaleReceiptBothVOLineItem s:saleList2){
						Goodsid.add(s.getGoodsId());
					}
					boolean judge=true;
					for(Map.Entry<OutGoodsVO, Integer> e:map.entrySet()){
						if(!Goodsid.contains(e.getKey().getId())){
							judge=false;
							break;
						}
						for(SaleReceiptBothVOLineItem s:saleList2){
							if(s.getGoodsId().equals(e.getKey().getId())){
								if(s.getGoodsAmount()<e.getValue().intValue()){
									judge=false;
									break;
								}
							}
						}
					}
					if(judge)
						relist.add(getStrategyById(str.getID()));
					continue;
				}
			}
			}
		return relist;
	}

	@Override
	public StrategyVO getStrategyById(String id) {
		StrategyPO str=StrategyData.find(id);
		if(str==null)
			return null;
		switch(str.getType()){
		case 1:
			ClientDiscountStrategyVO cdstrvo=new ClientDiscountStrategyVO();
			cdstrvo.setDiscount(((ClientDiscountStrategyPO)str).getDiscount());
			cdstrvo.setLevel(((ClientDiscountStrategyPO)str).getLevel());
			cdstrvo.setID(id);
			cdstrvo.setStartTime(str.getStartTime());
			cdstrvo.setEndTime(str.getEndTime());
			return cdstrvo;
		case 2:
			ClientGiftStrategyVO cgstrvo=new ClientGiftStrategyVO();
			cgstrvo.setList(((ClientGiftStrategyPO)str).getList());
			cgstrvo.setLevel(((ClientGiftStrategyPO)str).getLevel());
			cgstrvo.setID(id);
			cgstrvo.setStartTime(str.getStartTime());
			cgstrvo.setEndTime(str.getEndTime());
			return cgstrvo;
		case 3:
			ClientCouponStrategyVO ccstrvo=new ClientCouponStrategyVO();
			ccstrvo.setCoupon(((ClientCouponStrategyPO)str).getCoupon());
			ccstrvo.setLevel(((ClientCouponStrategyPO)str).getLevel());
			ccstrvo.setID(id);
			ccstrvo.setStartTime(str.getStartTime());
			ccstrvo.setEndTime(str.getEndTime());
			return ccstrvo;
		case 4:
			DiscountStrategyVO dstrvo=new DiscountStrategyVO();
			dstrvo.setDiscount(((DiscountStrategyPO)str).getDiscount());
			dstrvo.setLeastTotal(((DiscountStrategyPO)str).getLeastTotal());
			dstrvo.setID(id);
			dstrvo.setStartTime(str.getStartTime());
			dstrvo.setEndTime(str.getEndTime());
			return dstrvo;
		case 5:
			GiftStrategyVO gstrvo=new GiftStrategyVO();
			gstrvo.setList(((GiftStrategyPO)str).getGiftlist());
			gstrvo.setLeastTotal(((GiftStrategyPO)str).getLeastTotal());
			gstrvo.setID(id);
			gstrvo.setStartTime(str.getStartTime());
			gstrvo.setEndTime(str.getEndTime());
			return gstrvo;
		case 6:
			BargainPackageStrategyVO bpstrvo=new BargainPackageStrategyVO();
			bpstrvo.setTotal(((BargainPackageStrategyPO)str).getTotal());
			bpstrvo.setGoodslist(((BargainPackageStrategyPO)str).getGoodslist());
			bpstrvo.setID(id);
			bpstrvo.setStartTime(str.getStartTime());
			bpstrvo.setEndTime(str.getEndTime());
			return bpstrvo;
		}
		return null;
	}	
	
	public ArrayList<StrategyVO> findAllStrategy(){
		ArrayList<StrategyVO> list=new ArrayList<StrategyVO>();
		ArrayList<StrategyPO> polist=StrategyData.finds();
		if(polist.isEmpty())
			return null;
		for(StrategyPO po:polist){
			Calendar now=Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH)+1);
			if(po.getEndTime().before(now)){
				 //处理掉时效已过的促销策略
				StrategyData.delete(po);
				continue;
			}
			list.add(getStrategyById(po.getID()));
		}
		return list;
	}
}
