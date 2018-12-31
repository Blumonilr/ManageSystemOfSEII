package main.ui.logui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.businesslogicservice.logblservice.LogblService;
import main.rmi.RemoteHelper;

public class LogHelper {

	private String userid;
	private File log;
	private BufferedWriter writer;

	/**
	 * 
	 * @param userid 当前登录用户
	 */
	public LogHelper(String userid){
		this.userid=userid;
		Calendar c=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		
		log=new File(this.userid+df.format(c.getTime())+".local");
		if(!log.exists())
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 每调用一个功能，记录一次信息
	 * @param msg 操作描述，如“添加商品成功”
	 */
	public void record(String msg){
		
		try {
			writer=new BufferedWriter(new FileWriter(log,true));//可以append到文件末尾，这样才不会清空文件内容
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar c=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String content=df.format(c.getTime())+'\n'+msg+'\n'+"----------------"+'\n';
		try {
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		update();
	}
	
	private void update(){
		BufferedReader reader=null;
		String content="";
		try {
			reader=new BufferedReader(new FileReader(log));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line=null;
		try {
			while((line=reader.readLine())!=null){
				content+=line;
				content+=System.lineSeparator();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		

		try {
			LogblService logbl=RemoteHelper.getInstance().getLogblService();
			logbl.saveLog(userid, content);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
