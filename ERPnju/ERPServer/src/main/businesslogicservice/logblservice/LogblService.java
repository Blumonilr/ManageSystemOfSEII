package main.businesslogicservice.logblservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogblService extends Remote{

	/**
	 * 
	 * @param userId 操作员id
	 * @param file 序列化的文件对象，txt文件
	 * @return
	 */
	public boolean saveLog(String userId,String content)throws RemoteException;
	/**
	 * 
	 * @param userId 操作员id
	 * @return 序列化的文件对象，所有日志均存在一个文本文件里
	 */
	public String readLog(String userId)throws RemoteException;
}
