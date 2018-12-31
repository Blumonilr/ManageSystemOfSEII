package main.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

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

public class RemoteHelper {

	private Remote remote;
	private static RemoteHelper remoteHelper;
	public static RemoteHelper getInstance(){
		if(remoteHelper==null)
			remoteHelper=new RemoteHelper();
		return remoteHelper;
	}
	
	private RemoteHelper() {
		try {
			remote=Naming.lookup("rmi://127.0.0.1:8887/DataRemoteObject");
			System.out.println("linked to rmi server");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connected to rmi server");
	}
	
	public void setRemote(Remote remote){
		this.remote = remote;
	}
	
	public AccountblService getAccountblService(){
		return (AccountblService)remote;
	}
	
	public BookblService getBookblService(){
		return (BookblService)remote;
	}
	
	public ClientblService getClientblService(){
		return (ClientblService)remote;
	}
	
	public FinanceblService getFinanceblService(){
		return (FinanceblService)remote;
	}
	
	public FormblService getFormblService(){
		return (FormblService)remote;
	}
	
	public LogblService getLogblService(){
		return (LogblService)remote;
	}
	
	public PurchaseblService getPurchaseblService(){
		return (PurchaseblService)remote;
	}
	
	public ReviseblService getReviseblService(){
		return (ReviseblService)remote;
	}
	
	public SaleblService getSaleblService(){
		return (SaleblService)remote;
	}
	
	public StockblService getStockblService(){
		return (StockblService)remote;
	}
	
	public StrategyblService getStrategyblService(){
		return (StrategyblService)remote;
	}
	
	public UserblService getUserblService(){
		return (UserblService)remote;
	}
	
	public ChatService getChatService(){
		return (ChatService)remote;
	}
}
