package Test.rmi;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;

import main.businesslogicservice.reviseblservice.ReviseblService;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.vo.ReceiptVO;
import main.vo.UserVO;

public class TestRemoteHelper {
	
	ReviseblService reviseblservice;
	UserblService userblservice;
	RemoteHelper remoteHelper=RemoteHelper.getInstance();

//	@Test
//	public void test(){
//		reviseblservice=remoteHelper.getReviseblService();
//		try {
//			ArrayList<ReceiptVO> list=reviseblservice.showAllReceipt(null);
//			for(ReceiptVO r:list){
//				System.out.println(reviseblservice.showReceipt(r.getId()).getReceiptType());
//			} 
//		}
//			catch (RemoteException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void test1(){
		userblservice=remoteHelper.getUserblService();
		try {
			ArrayList<UserVO> list=userblservice.showUsers(null);
			for(UserVO u:list)
				System.out.println(u.getType());
			System.out.println(userblservice.showUser("0001").getPassword());
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test2(){
		userblservice=remoteHelper.getUserblService();
		try {
			assertEquals(true,userblservice.updateMessage("JHD-20171223-00001", "0001"));
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
