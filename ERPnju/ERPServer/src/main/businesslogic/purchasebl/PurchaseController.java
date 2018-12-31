package main.businesslogic.purchasebl;

import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.vo.ClientVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;

/**
 * 
 * @author Kate
 *
 */
public class PurchaseController implements PurchaseblService {

	Purchase purchase;//持有的领域对象引用
	
	/**
	 * @param purchase
	 */
	public PurchaseController() {
		purchase = new Purchase();
	}

	@Override
	public ArrayList<OutGoodsVO> showGoods_p() {
		// TODO Auto-generated method stub
		return purchase.showGoods_p();
	}

	@Override
	public ArrayList<ClientVO> showClient_p() {
		// TODO Auto-generated method stub
		return purchase.showClient_p();
	}

	@Override
	public PurchaseReceiptVO makePurchaseList(PurchaseReceiptVO obj) {
		// TODO Auto-generated method stub
		return purchase.makePurchaseList(obj);
	}

	@Override
	public PurchaseReturnReceiptVO makePurchaseReturnList(PurchaseReturnReceiptVO obj) {
		// TODO Auto-generated method stub
		return purchase.makePurchaseReturnList(obj);
	}

	@Override
	public ArrayList<PurchaseReceiptBothVO> showMyUnrevisedReceipts_p(String userId,int operand, Calendar startTime, Calendar endTime) {
		// TODO Auto-generated method stub
		return purchase.showMyUnrevisedReceipts(userId,operand, startTime, endTime);
	}

	@Override
	public PurchaseReceiptVO makePurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return purchase.makePurchaseReceiptDraft(obj, userId);
	}

	@Override
	public PurchaseReturnReceiptVO makePurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return purchase.makePurchaseReturnReceiptDraft(obj, userId);
	}

	@Override
	public ArrayList<PurchaseReceiptVO> showMyPurchaseReceiptDrafts(String userId) {
		// TODO Auto-generated method stub
		return purchase.showMyPurchaseReceiptDrafts(userId);
	}

	@Override
	public ArrayList<PurchaseReturnReceiptVO> showMyPurchaseReturnReceiptDrafts(String userId) {
		// TODO Auto-generated method stub
		return purchase.showMyPurchaseReturnReceiptDrafts(userId);
	}

	@Override
	public PurchaseReceiptVO modifyPurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return purchase.modifyPurchaseReceiptDraft(obj, userId);
	}

	@Override
	public PurchaseReturnReceiptVO modifyPurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) {
		// TODO Auto-generated method stub
		return purchase.modifyPurchaseReturnReceiptDraft(obj, userId);
	}

	@Override
	public boolean deleteDraft_p(String draftId, String userId) {
		// TODO Auto-generated method stub
		return purchase.deleteDraft_p(draftId, userId);
	}

}
