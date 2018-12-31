package main.ui.strategyui;

import java.text.SimpleDateFormat;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.StrategyVO;

public class Strategy {

	SimpleStringProperty id;
	SimpleStringProperty type;
	SimpleStringProperty intro;
	SimpleStringProperty time;
	
	public Strategy(StrategyVO s){
		this.id=new SimpleStringProperty(s.getID());
		switch(s.getType()){
		case 2: this.type=new SimpleStringProperty("根据客户等级赠送赠品");
				String it1="赠品有";
				Map<OutGoodsVO,Integer> glist1=((ClientGiftStrategyVO)s).getList();
				for(Map.Entry<OutGoodsVO, Integer> e:glist1.entrySet()){
					it1+=e.getKey().getName()+" ";
				}
				this.intro=new SimpleStringProperty("客户等级为"+((ClientGiftStrategyVO)s).getLevel()+","+it1);
				break;
		case 1: this.type=new SimpleStringProperty("根据客户等级提供折扣");
				this.intro=new SimpleStringProperty("客户等级为"+((ClientDiscountStrategyVO)s).getLevel()+",折扣为"+((ClientDiscountStrategyVO)s).getDiscount());
				break;
		case 3: this.type=new SimpleStringProperty("根据客户等级提供代金券");
				this.intro=new SimpleStringProperty("客户等级为"+((ClientCouponStrategyVO)s).getLevel()+",代金券金额为"+((ClientCouponStrategyVO)s).getCoupon());
				break;
		case 5: this.type=new SimpleStringProperty("根据总价赠送赠品");
				String it2="赠品有";
				Map<OutGoodsVO,Integer> glist2=((GiftStrategyVO)s).getGiftlist();
				for(Map.Entry<OutGoodsVO, Integer> e:glist2.entrySet()){
					it2+=e.getKey().getName()+" ";
				}
				this.intro=new SimpleStringProperty("最低消费为"+((GiftStrategyVO)s).getLeastTotal()+","+it2);
				break;
		case 4: this.type=new SimpleStringProperty("根据总价提供折扣");
				this.intro=new SimpleStringProperty("最低消费为"+((DiscountStrategyVO)s).getLeastTotal()+",折扣为"+((DiscountStrategyVO)s).getDiscount());
				break;
		case 6: this.type=new SimpleStringProperty("特价包");
				String it3="商品有";
				Map<OutGoodsVO,Integer> glist3=((BargainPackageStrategyVO)s).getGoodslist();
				for(Map.Entry<OutGoodsVO, Integer> e:glist3.entrySet()){
					it3+=e.getKey().getName()+" ";
				}
				this.intro=new SimpleStringProperty(it3+",总价为"+((BargainPackageStrategyVO)s).getTotal());
				break;
		}
		String time=new SimpleDateFormat("YYYY-MM-dd").format(s.getStartTime().getTime())+"-"+new SimpleDateFormat("YYYY-MM-dd").format(s.getEndTime().getTime());
		this.time=new SimpleStringProperty(time);
	}
	
	public String getId(){
		return id.get();
	}
	
	public String getType(){
		return type.get();
	}
	
	public String getIntro(){
		return intro.get();
	}
	
	public String getTime(){
		return time.get();
	}
}
