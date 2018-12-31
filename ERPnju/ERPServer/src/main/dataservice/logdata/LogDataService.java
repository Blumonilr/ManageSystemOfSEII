package main.dataservice.logdata;

public interface LogDataService {

	/**
	 * 
	 * @param userId 操作员id
	 * @param file 序列化的文件对象，txt文件
	 * @return
	 */
	public boolean saveLog(String userId,String content);
	/**
	 * 
	 * @param userId 操作员id
	 * @return 序列化的文件对象，所有日志均存在一个文本文件里
	 */
	public String readLog(String userId);
}
