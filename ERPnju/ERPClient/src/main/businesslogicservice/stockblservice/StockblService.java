package main.businesslogicservice.stockblservice;

import main.vo.ClassVO;
import main.vo.GoodsQueryItem;
import main.vo.InGoodsVO;
import main.vo.OutGoodsVO;
import main.vo.StockRecordVO;
import main.vo.ReceiptVO;
import main.vo.StockCheckResultVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * 
 * @author qyc
 *
 */
public interface StockblService extends Remote{
	public boolean addClass(ClassVO obj)throws RemoteException;
	public boolean delClass(String id)throws RemoteException;
	public boolean modifyClass(ClassVO obj)throws RemoteException;
	public ArrayList<ClassVO>showClass()throws RemoteException;
	public ClassVO showClass(String id)throws RemoteException;
	public boolean addGoods(InGoodsVO obj)throws RemoteException;
	public boolean delGoods(String id)throws RemoteException;
	public boolean modifyGoods(OutGoodsVO obj)throws RemoteException;
	public ArrayList<OutGoodsVO>findGoods(GoodsQueryItem obj)throws RemoteException;
	public OutGoodsVO getGoods(String id)throws RemoteException;
	public ArrayList<OutGoodsVO>getGoods()throws RemoteException;
	//查看某段时间内某商品的出入库情况
	public StockCheckResultVO stockCheck(String goodsId,Calendar start,Calendar end)throws RemoteException;
	public ReceiptVO stockCheckDetail(String id)throws RemoteException;
	public ArrayList<OutGoodsVO>stockInventory()throws RemoteException;
	public boolean addReceipt(ReceiptVO obj)throws RemoteException;
	
}
