package main.businesslogic.purchasebl;

import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogic.clientbl.Client;
import main.businesslogic.clientbl.ClientInfo;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.businesslogic.stockbl.Goods;
import main.businesslogic.stockbl.GoodsInfo;
import main.businesslogic.stockbl.ReceiptInfo;
import main.businesslogic.stockbl.Stock;
import main.vo.ClientVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

/**
 * @author Kate
 * modify 11/19
 */
public class Purchase {
	
	GoodsInfo goodsInfo;//商品信息的接口
	ReceiptInfo receiptInfo;//单据操作的接口
	ClientInfo clientInfo;//客户信息的接口
	ReviseInfo reviseInfo;//审批信息的接口
	
	public Purchase(){
		goodsInfo=new Goods();//商品信息的接口
		receiptInfo=new Stock();//单据操作的接口
		clientInfo=new Client();//客户信息的接口
		reviseInfo=new Revise();//审批信息的接口
	}
	

	public ArrayList<OutGoodsVO> showGoods_p() {//done
		ArrayList<OutGoodsVO> goodsList=goodsInfo.getGoods();
		
		return goodsList;
	}

	
	public ArrayList<ClientVO> showClient_p() {//done
		ArrayList<ClientVO> clientList=clientInfo.show();
		
		return clientList;
	}

	
	public PurchaseReceiptVO makePurchaseList(PurchaseReceiptVO obj) {//done
		boolean result=reviseInfo.insertReceipt(obj);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public PurchaseReturnReceiptVO makePurchaseReturnList(PurchaseReturnReceiptVO obj) {//done
		boolean result=reviseInfo.insertReceipt(obj);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public ArrayList<PurchaseReceiptBothVO> showMyUnrevisedReceipts(String userId,int operand, Calendar startTime, Calendar endTime) {//done
		ArrayList<ReceiptVO> list=reviseInfo.showAllReceipt(userId);
		ArrayList<PurchaseReceiptBothVO> list41=new ArrayList<PurchaseReceiptBothVO>();
		ArrayList<PurchaseReceiptBothVO> list42=new ArrayList<PurchaseReceiptBothVO>();
		for(ReceiptVO vo:list){
			if(vo.getReceiptType()==41){
				//purchase
				if(vo.getMakeTime().after(startTime)&&vo.getMakeTime().before(endTime)){
					list41.add((PurchaseReceiptBothVO)vo);
				}
			}
			else if(vo.getReceiptType()==42){
				//puchasereturn 
				if(vo.getMakeTime().after(startTime)&&vo.getMakeTime().before(endTime)){
					list42.add((PurchaseReceiptBothVO)vo);
				}
			}
			else{
				//doNothing
			}
		}
		
		if(operand==41){
			return list41;
		}
		else if(operand==42){
			return list42;
		}
		else{
			return null;
		}
	}
	
	
	public PurchaseReceiptVO makePurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean result=receiptInfo.addDraft(obj, userId);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public PurchaseReturnReceiptVO makePurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean result=receiptInfo.addDraft(obj, userId);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public ArrayList<PurchaseReceiptVO> showMyPurchaseReceiptDrafts(String userId) {//done
		// TODO Auto-generated method stub
		ArrayList<PurchaseReceiptVO> result=new ArrayList<PurchaseReceiptVO>();
		ArrayList<ReceiptVO> list=receiptInfo.readDraft(userId);
		for(ReceiptVO r:list){
			if(r.getReceiptType()==41){
				result.add((PurchaseReceiptVO)r);
			}
		}
		return result;
	}

	
	public ArrayList<PurchaseReturnReceiptVO> showMyPurchaseReturnReceiptDrafts(String userId) {//done
		// TODO Auto-generated method stub
		ArrayList<PurchaseReturnReceiptVO> result=new ArrayList<PurchaseReturnReceiptVO>();
		ArrayList<ReceiptVO> list=receiptInfo.readDraft(userId);
		for(ReceiptVO r:list){
			if(r.getReceiptType()==42){
				result.add((PurchaseReturnReceiptVO)r);
			}
		}
		return result;
	}
	
	
	public PurchaseReceiptVO modifyPurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean result=receiptInfo.modifyDraft(obj, userId);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}


	public PurchaseReturnReceiptVO modifyPurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) {//done
		// TODO Auto-generated method stub
		boolean result=receiptInfo.modifyDraft(obj, userId);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public boolean deleteDraft_p(String draftId, String userId) {//done
		// TODO Auto-generated method stub
		return receiptInfo.delDraft(draftId, userId);
	}


}
