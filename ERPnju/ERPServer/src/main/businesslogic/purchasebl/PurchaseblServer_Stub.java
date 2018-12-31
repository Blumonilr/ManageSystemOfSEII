package main.businesslogic.purchasebl;

import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.vo.ClientVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;

import java.util.ArrayList;
import java.util.Calendar;

public class PurchaseblServer_Stub implements PurchaseblService{
	ArrayList<PurchaseReceiptBothVO> receiptList;
	ArrayList<OutGoodsVO> goodsList;
	ArrayList<ClientVO> clientList;
	
	/**
	 * @param receiptList
	 * @param goodsList
	 * @param clientList
	 */
	public PurchaseblServer_Stub(ArrayList<PurchaseReceiptBothVO> receiptList, ArrayList<OutGoodsVO> goodsList,
			ArrayList<ClientVO> clientList) {
		super();
		this.receiptList = receiptList;
		this.goodsList = goodsList;
		this.clientList = clientList;
	}

	@Override
	public ArrayList<OutGoodsVO> showGoods() {
		// TODO Auto-generated method stub
		return goodsList;
	}

	@Override
	public ArrayList<ClientVO> showClient() {
		// TODO Auto-generated method stub
		return clientList;
	}

	@Override
	public PurchaseReceiptVO makePurchaseList(PurchaseReceiptVO obj) {
		// TODO Auto-generated method stub
		receiptList.add(obj);
		
		return obj;
	}

	@Override
	public PurchaseReturnReceiptVO makePurchaseReturnList(PurchaseReturnReceiptVO obj) {
		// TODO Auto-generated method stub
		receiptList.add(obj);
		
		return obj;
	}

	@Override
	public ArrayList<PurchaseReceiptBothVO> showReceipts(int operand, Calendar startTime, Calendar endTime) {
		// TODO Auto-generated method stub
		ArrayList<PurchaseReceiptBothVO> result=new ArrayList<PurchaseReceiptBothVO>();
		for(PurchaseReceiptBothVO r:receiptList){
			if(r.getReceiptType()==operand){
				if(r.getMakeTime().after(startTime)&&r.getMakeTime().before(endTime)){
					result.add(r);
				}
			}
		}
		return result;
	}
}
