package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;

import main.PO.GoodsPO;
import main.PO.PurchaseReceiptPO;
import main.PO.ReceiptPO;
import main.PO.SaleReceiptPO;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.businesslogic.util.ReceiptTransformer;
import main.data.stockdata.StockDataServiceImpl;
import main.dataservice.stockdataservice.StockDataService;
import main.vo.ReceiptVO;
import main.vo.StockCheckResultVO;
import main.vo.StockRecordVO;

public class Stock implements ReceiptInfo {

	private StockDataService sds;
	private ReceiptTransformer trans;
	
	public Stock(){
		sds=new StockDataServiceImpl();
		trans=new ReceiptTransformer();
	}

	@Override
	public boolean delDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		return sds.delDraft(draftId, userId);
	}

	private String[][]operation={{"","",""},{"","",""},{"","",""},
			{"","销售","销售退货"},{"","进货","进货退货"}};
	private long[][]sign={{1,1,1},{1,1,1},{1,1,1},{1,-1,1},{1,1,-1}};
	/**
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 库存变动记录
	 * 查看某一种商品在某个时间段内的库存操作记录（ 商品名 日期  操作类型（进货、退    销售、退）  数量  金额），每一个商品有合计。
	 */
	public StockCheckResultVO stockCheck(String goodsId,Calendar start, Calendar end) {
		
		StockCheckResultVO res=new StockCheckResultVO();
		GoodsPO g=sds.getGoods(goodsId);
		if(g==null)
			return null;
		res.setGoodsName(g.getName());
		res.setGoodsId(goodsId);
		
		ArrayList<ReceiptPO>list=sds.readReceipt(31);
		list.addAll(sds.readReceipt(32));
		list.addAll(sds.readReceipt(41));
		list.addAll(sds.readReceipt(42));
		
		for(ReceiptPO r:list){
			
			boolean select=true;
			
			if(start!=null){
				if(r.getReviseTimebyCalendar().compareTo(start)<0)
					select=false;
			}
			if(end!=null){
				if(r.getReviseTimebyCalendar().compareTo(end)>0)
					select=false;
			}
			if(select){				
				int t=r.getReceiptType();
				if(t<40){
					//销售类单据
					SaleReceiptPO sr=(SaleReceiptPO)r;
					for(String[] s:sr.getGoodsList()){
						if(s[0].equals(goodsId)){
							StockRecordVO record=new StockRecordVO(sr.getReviseTimebyCalendar(),
									sr.getId(), operation[sr.getReceiptType()/10][sr.getReceiptType()%3], 
									sign[sr.getReceiptType()/10][sr.getReceiptType()%3]*Long.parseLong(s[3]),
									Double.parseDouble(s[5]));
							res.add(record);
						}
					}
				}else{
					//进货类单据
					PurchaseReceiptPO pr=(PurchaseReceiptPO)r;
					for(String[] s:pr.getGoodsList()){
						if(s[0].equals(goodsId)){
							StockRecordVO record=new StockRecordVO(pr.getReviseTimebyCalendar(),
									pr.getId(), operation[pr.getReceiptType()/10][pr.getReceiptType()%4], 
									sign[pr.getReceiptType()/10][pr.getReceiptType()%4]*Long.parseLong(s[3]),
									Double.parseDouble(s[5]));
							res.add(record);
						}
					}
				}
			}
		}
		return res;
	}



	@Override
	public ArrayList<ReceiptVO> readReceipt(int type, Calendar start, Calendar end) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptVO>res=new ArrayList<>();
		ArrayList<ReceiptPO>list=sds.readReceipt(type);
//		System.out.println(start.getTime());
//		System.out.println(end.getTime());
		for(ReceiptPO r:list){
			if(r.getCreateTimebyCalendar().compareTo(start)>=0&&
					r.getCreateTimebyCalendar().compareTo(end)<=0){
				res.add(trans.po2vo(r));
			}
		}
		return res;
	}



	@Override
	public ReceiptVO readReceipt(String id) {
		// TODO Auto-generated method stub
		return trans.po2vo(sds.readReceipt(id));
	}



	@Override
	public boolean writeReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		return sds.writeReceipt(trans.vo2po(obj));
	}



	@Override
	public ArrayList<ReceiptVO> readReceipt() {
		// TODO Auto-generated method stub
		ArrayList<ReceiptVO>res=new ArrayList<>();
		ArrayList<ReceiptPO>list=sds.readReceipt();
		for(ReceiptPO r:list)
			res.add(trans.po2vo(r));
		return res;
	}



	@Override
	public boolean modifyReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		return sds.modifyReceipt(trans.vo2po(obj));
	}



	@Override
	public boolean addDraft(ReceiptVO obj, String userId) {
		// 以时间分配一个非正式编号
		String id=IdAssigner.assignId("CG-");
		obj.setId(id);
		return sds.addDraft(trans.vo2po(obj), userId);
	}



	@Override
	public boolean modifyDraft(ReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return sds.modifyDraft(trans.vo2po(obj), userId);
	}



	@Override
	public ReceiptVO readDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		return trans.po2vo(sds.readDraft(draftId, userId));
	}



	@Override
	public ArrayList<ReceiptVO> readDraft(String userId) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptVO>res=new ArrayList<>();
		ArrayList<ReceiptPO>list=sds.readDraft(userId);
		for(ReceiptPO r:list)
			res.add(trans.po2vo(r));
		return res;
	}

	public boolean addReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		ReviseInfo revise=new Revise();
		return revise.insertReceipt(obj);
	}

}
