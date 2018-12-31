package main.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogic.accountbl.AccountController;
import main.businesslogic.bookbl.BookController;
import main.businesslogic.chatbl.Chat;
import main.businesslogic.clientbl.ClientController;
import main.businesslogic.financebl.FinanceController;
import main.businesslogic.formbl.FormController;
import main.vo.SaleFormLineItem;
import main.businesslogic.logbl.Log;
import main.businesslogic.purchasebl.PurchaseController;
import main.businesslogic.revisebl.ReviseController;
import main.businesslogic.salebl.SaleController;
import main.businesslogic.stockbl.StockController;
import main.businesslogic.strategybl.StrategyController;
import main.businesslogic.userbl.UserController;
import main.businesslogicservice.accountblservice.AccountblService;
import main.businesslogicservice.bookblservice.BookblService;
import main.businesslogicservice.chatservice.ChatService;
import main.businesslogicservice.clientblservice.ClientblService;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.businesslogicservice.formblservice.FormblService;
import main.businesslogicservice.logblservice.LogblService;
import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.businesslogicservice.reviseblservice.ReviseblService;
import main.businesslogicservice.saleblservice.SaleblService;
import main.businesslogicservice.stockblservice.StockblService;
import main.businesslogicservice.strategyblservice.StrategyblService;
import main.businesslogicservice.userblservice.UserblService;
import main.vo.AccountVO;
import main.vo.BookVO;
import main.vo.ClassVO;
import main.vo.ClientVO;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;
import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockCheckResultVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StrategyVO;
import main.vo.UserVO;

public class DataRemoteObject extends UnicastRemoteObject implements AccountblService,BookblService,ClientblService,FinanceblService,
																	 FormblService,PurchaseblService,ReviseblService,SaleblService,
																	 StockblService,StrategyblService,UserblService,LogblService
																	 ,ChatService{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8936520547245622846L;
	private AccountblService accountblservice;
	private BookblService bookblservice;
	private ClientblService clientblservice;
	private FinanceblService financeblservice;
	private FormblService formblservice;
	private LogblService logblservice;
	private PurchaseblService purchaseblservice;
	private ReviseblService reviseblservice;
	private SaleblService saleblservice;
	private StockblService stockblservice;
	private StrategyblService strategyblservice;
	private UserblService userblservice;
	private ChatService chatservice;
	
	public DataRemoteObject() throws RemoteException{
		// TODO 自动生成的构造函数存根
		accountblservice=new AccountController();
		bookblservice=new BookController();
		clientblservice=new ClientController();
		financeblservice=new FinanceController();
		formblservice=new FormController();
		logblservice=new Log();
		purchaseblservice=new PurchaseController();
		reviseblservice=new ReviseController();
		saleblservice=new SaleController();
		stockblservice=new StockController();
		strategyblservice=new StrategyController();
		userblservice=new UserController();
		chatservice=new Chat();
	}
	
	@Override
	public boolean saveLog(String userId, String content) throws RemoteException {
		// TODO Auto-generated method stub
		return logblservice.saveLog(userId, content);
	}

	@Override
	public String readLog(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return logblservice.readLog(userId);
	}

	@Override
	public boolean userlogin(String id, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.userlogin(id, password);
	}

	@Override
	public boolean userlogout(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.userlogout(id);
	}

	@Override
	public boolean addUser(int type, String id, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.addUser(type, id, password);
	}

	@Override
	public boolean removeUser(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.removeUser(id);
	}

	@Override
	public ArrayList<UserVO> showUsers(String Info) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.showUsers(Info);
	}

	@Override
	public boolean modifyUser(UserVO uservo) throws RemoteException {
		// TODO Auto-generated method stub
		return userblservice.modifyUser(uservo);
	}

	@Override
	public boolean makeStrategy(StrategyVO strategyVO) throws RemoteException {
		// TODO Auto-generated method stub
		return strategyblservice.makeStrategy(strategyVO);
	}

	@Override
	public boolean removeStrategy(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return strategyblservice.removeStrategy(id);
	}

	@Override
	public boolean addClass(ClassVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.addClass(obj);
	}

	@Override
	public boolean delClass(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.delClass(id);
	}

	@Override
	public boolean modifyClass(ClassVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.modifyClass(obj);
	}

	@Override
	public ArrayList<ClassVO> showClass() throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.showClass();
	}

	@Override
	public boolean addGoods(InGoodsVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.addGoods(obj);
	}

	@Override
	public boolean delGoods(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.delGoods(id);
	}

	@Override
	public boolean modifyGoods(OutGoodsVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.modifyGoods(obj);
	}

	@Override
	public ArrayList<OutGoodsVO> findGoods(GoodsQueryItem obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.findGoods(obj);
	}

	@Override
	public OutGoodsVO getGoods(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.getGoods(id);
	}

	@Override
	public ArrayList<OutGoodsVO> getGoods() throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.getGoods();
	}

	@Override
	public StockCheckResultVO stockCheck(String goodsId, Calendar start, Calendar end) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.stockCheck(goodsId, start, end);
	}

	@Override
	public ReceiptVO stockCheckDetail(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.stockCheckDetail(id);
	}

	@Override
	public ArrayList<OutGoodsVO> stockInventory() throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.stockInventory();
	}

	@Override
	public ArrayList<OutGoodsVO> showGoods() throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showGoods();
	}

	@Override
	public ArrayList<ClientVO> showClient() throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showClient();
	}

	@Override
	public SaleReceiptVO makeSaleReceipt(SaleReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.makeSaleReceipt(obj);
	}

	@Override
	public SaleReturnReceiptVO makeSaleReturnReceipt(SaleReturnReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.makeSaleReturnReceipt(obj);
	}

	@Override
	public StockGiftReceiptVO chooseGiftStrategy(String strategyId, String userId, SaleReceiptBothVO receipt) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.chooseGiftStrategy(strategyId, userId, receipt);
	}

	@Override
	public boolean chooseOtherStrategy(String strategyId, String userId, SaleReceiptBothVO receipt) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.chooseOtherStrategy(strategyId, userId, receipt);
	}

	@Override
	public ArrayList<SaleReceiptBothVO> showMyUnrevisedReceipts(String userId,int operand, Calendar startTime, Calendar endTime) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showMyUnrevisedReceipts(userId, operand, startTime, endTime);
	}

	@Override
	public ArrayList<StrategyVO> showValidStrategy(SaleReceiptBothVO saleReceipt) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showValidStrategy(saleReceipt);
	}

	@Override
	public SaleReceiptVO makeSaleReceiptDraft(SaleReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.makeSaleReceiptDraft(obj, userId);
	}

	@Override
	public SaleReturnReceiptVO makeSaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.makeSaleReturnReceiptDraft(obj, userId);
	}

	@Override
	public ArrayList<SaleReceiptVO> showMySaleReceiptDrafts(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showMySaleReceiptDrafts(userId);
	}

	@Override
	public ArrayList<SaleReturnReceiptVO> showMySaleReturnReceiptDrafts(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.showMySaleReturnReceiptDrafts(userId);
	}

	@Override
	public SaleReceiptVO modifySaleReceiptDraft(SaleReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.modifySaleReceiptDraft(obj, userId);
	}

	@Override
	public SaleReturnReceiptVO modifySaleReturnReceiptDraft(SaleReturnReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.modifySaleReturnReceiptDraft(obj, userId);
	}

	@Override
	public boolean deleteDraft(String draftId, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.deleteDraft(draftId, userId);
	}

	@Override
	public ArrayList<ReceiptVO> showAllReceipt(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return reviseblservice.showAllReceipt(userId);
	}

	@Override
	public boolean revise(ReceiptVO receiptVO, int operand) throws RemoteException {
		// TODO Auto-generated method stub
		return reviseblservice.revise(receiptVO, operand);
	}

	@Override
	public ArrayList<OutGoodsVO> showGoods_p() throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.showGoods_p();
	}

	@Override
	public ArrayList<ClientVO> showClient_p() throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.showClient_p();
	}

	@Override
	public PurchaseReceiptVO makePurchaseList(PurchaseReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.makePurchaseList(obj);
	}

	@Override
	public PurchaseReturnReceiptVO makePurchaseReturnList(PurchaseReturnReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.makePurchaseReturnList(obj);
	}

	@Override
	public ArrayList<PurchaseReceiptBothVO> showMyUnrevisedReceipts_p(String userId, int operand, Calendar startTime,
			Calendar endTime) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.showMyUnrevisedReceipts_p(userId, operand, startTime, endTime);
	}

	@Override
	public PurchaseReceiptVO makePurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.makePurchaseReceiptDraft(obj, userId);
	}

	@Override
	public PurchaseReturnReceiptVO makePurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.makePurchaseReturnReceiptDraft(obj, userId);
	}

	@Override
	public ArrayList<PurchaseReceiptVO> showMyPurchaseReceiptDrafts(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.showMyPurchaseReceiptDrafts(userId);
	}

	@Override
	public ArrayList<PurchaseReturnReceiptVO> showMyPurchaseReturnReceiptDrafts(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.showMyPurchaseReturnReceiptDrafts(userId);
	}

	@Override
	public PurchaseReceiptVO modifyPurchaseReceiptDraft(PurchaseReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.modifyPurchaseReceiptDraft(obj, userId);
	}

	@Override
	public PurchaseReturnReceiptVO modifyPurchaseReturnReceiptDraft(PurchaseReturnReceiptVO obj, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.modifyPurchaseReturnReceiptDraft(obj, userId);
	}

	@Override
	public boolean deleteDraft_p(String draftId, String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return purchaseblservice.deleteDraft_p(draftId, userId);
	}

	@Override
	public ArrayList<SaleFormLineItem> saleList(Calendar beginTime, Calendar endTime, String condition)
			throws RemoteException {
		return formblservice.saleList(beginTime,endTime,condition);
	}

	@Override
	public ArrayList<ReceiptVO> recordList(Calendar beginTime, Calendar endTime, String condition)
			throws RemoteException {
		return formblservice.recordList(beginTime,endTime,condition);
	}

	@Override
	public ReceiptVO showDetailReceipt(String id) throws RemoteException {
		return formblservice.showDetailReceipt(id);
	}

	@Override
	public boolean offsetAndCopy(ReceiptVO offSetReceipt) throws RemoteException {
		return formblservice.offsetAndCopy(offSetReceipt);
	}

	@Override
	public double[] businessList(Calendar beginTime, Calendar endTime) throws RemoteException {
		return formblservice.businessList(beginTime,endTime);
	}

	@Override
	public boolean offset(String id) throws RemoteException {
		return formblservice.offset(id);
		
	}

	@Override
	public ArrayList<ClientVO> showClients() throws RemoteException {
		return financeblservice.showClients();
	}

	@Override
	public ArrayList<String> showAccounts() throws RemoteException {
		return financeblservice.showAccounts();
	}

	@Override
	public AccountVO getAccount(String nickName) throws RemoteException {
		return financeblservice.getAccount(nickName);
	}

	@Override
	public boolean makeCashReceipt(FinanceCashReceiptVO cashReceipt) throws RemoteException {
		return financeblservice.makeCashReceipt(cashReceipt);
	}

	@Override
	public boolean makePayReceipt(FinancePayReceiptVO payReceipt) throws RemoteException {
		return financeblservice.makePayReceipt(payReceipt);
	}

	@Override
	public boolean makeCollectReceipt(FinanceCollectReceiptVO collectReceipt) throws RemoteException {
		return financeblservice.makeCollectReceipt(collectReceipt);
	}

	@Override
	public ReceiptVO readReceipt(String receiptID) throws RemoteException {
		return financeblservice.readReceipt(receiptID);
	}

	@Override
	public ArrayList<ReceiptVO> readRevisedReceiptList(String userId) throws RemoteException {
		return financeblservice.readRevisedReceiptList(userId);
	}

	@Override
	public ArrayList<ReceiptVO> readUnrevisedReceiptList(String userId) throws RemoteException {
		return financeblservice.readUnrevisedReceiptList(userId);
	}

	@Override
	public ReceiptVO readUnrevisedReceipt(String receiptID) throws RemoteException {
		return financeblservice.readUnrevisedReceipt(receiptID);
	}

	@Override
	public boolean addDraft(ReceiptVO receipt,String userID) throws RemoteException {
		return financeblservice.addDraft(receipt,userID);
	}

	@Override
	public boolean modifyDraft(ReceiptVO receipt,String userID) throws RemoteException {
		return financeblservice.modifyDraft(receipt,userID);
	}

	@Override
	public boolean delDraft(String draftID, String userID) throws RemoteException {
		return financeblservice.delDraft(draftID,userID);
	}

	@Override
	public ArrayList<ReceiptVO> readDraftList(String userID) throws RemoteException {
		return financeblservice.readDraftList(userID);
	}

	@Override
	public ReceiptVO readDraft(String draftID, String userId) throws RemoteException {
		return financeblservice.readDraft(draftID, userId);
	}


	@Override
	public boolean addClient(ClientVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.addClient(obj);
	}

	@Override
	public boolean delClient(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.delClient(id);
	}

	@Override
	public boolean modifyClient(ClientVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.modifyClient(obj);
	}

	@Override
	public ArrayList<ClientVO> searchClientbyNameTelCounterTypeLevel(String name, String tel, String counterman,
			int type, int level) throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.searchClientbyNameTelCounterTypeLevel(name, tel, counterman, type, level);
	}

	@Override
	public ClientVO showClient(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.showClient(id);
	}

	@Override
	public ArrayList<ClientVO> show() throws RemoteException {
		// TODO Auto-generated method stub
		return clientblservice.show();
	}

	@Override
	public boolean initBook() throws RemoteException {
		return bookblservice.initBook();
	}

	@Override
	public boolean saveDraft() throws RemoteException {
		return bookblservice.saveDraft();
	}

	@Override
	public void cancelChange() throws RemoteException {
		bookblservice.cancelChange();
	}

	@Override
	public BookVO showBook() throws RemoteException {
		return bookblservice.showBook();
	}

	@Override
	public BookVO getNow() throws RemoteException {
		return bookblservice.getNow();
	}

	@Override
	public void creatingBook() throws RemoteException {
		bookblservice.creatingBook();
	}

	@Override
	public AccountVO addAccount(AccountVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.addAccount(obj);
	}

	@Override
	public AccountVO findAccount(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.findAccount(id);
	}

	@Override
	public boolean deleteAccount(String accountId) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.deleteAccount(accountId);
	}

	@Override
	public boolean login(String id, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.login(id, password);
	}

	@Override
	public boolean checkUserLevel(String userId) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.checkUserLevel(userId);
	}

	@Override
	public boolean updateAccount(AccountVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.updateAccount(obj);
	}

	@Override
	public boolean updateBalance(String accountId, double difference) throws RemoteException {
		// TODO Auto-generated method stub
		return accountblservice.updateBalance(accountId, difference);
	}

	@Override
	public ArrayList<AccountVO> getAccounts() throws RemoteException {
		return accountblservice.getAccounts();
	}


	@Override
	public ClassVO showClass(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.showClass(id);
	}

	@Override
	public StrategyVO findStrategy(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return strategyblservice.findStrategy(id);
	}

	@Override
	public ArrayList<StrategyVO> findAllStrategy() throws RemoteException {
		// TODO 自动生成的方法存根
		return strategyblservice.findAllStrategy();
	}

	@Override
	public StrategyVO getStrategyById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.getStrategyById(id);
	}

	@Override
	public boolean makeGiftReceipt(StockGiftReceiptVO receipt) throws RemoteException {
		// TODO Auto-generated method stub
		return saleblservice.makeGiftReceipt(receipt);
	}

	@Override
	public ReceiptVO showReceipt(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return reviseblservice.showReceipt(id);
	}

	@Override
	public UserVO showUser(String id) throws RemoteException {
		// TODO 自动生成的方法存根
		return userblservice.showUser(id);
	}

	@Override
	public boolean addReceipt(ReceiptVO obj) throws RemoteException {
		// TODO Auto-generated method stub
		return stockblservice.addReceipt(obj);
	}

	@Override
	public void remove(String name) throws RemoteException {
		// TODO Auto-generated method stub
		chatservice.remove(name);
	}

	@Override
	public void uploadTempMsg(String name,ArrayList<String> msgs) throws RemoteException {
		chatservice.uploadTempMsg(name,msgs);
	}

	@Override
	public boolean updateReceipt(ReceiptVO r) throws RemoteException {
		// TODO 自动生成的方法存根
		return reviseblservice.updateReceipt(r);
	}
}
