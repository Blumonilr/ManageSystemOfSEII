package main.ui.logui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.businesslogicservice.logblservice.LogblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;

public class LogUIController {

	@FXML public TextArea board;
	private String userid;
	private File log;
	private LogblService logbl;
	
	@FXML
	public void onRefresh(){
		showLog();
	}
	
	public void showLog() {
		board.setText(null);
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(log));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line=null;
		try {
			while((line=reader.readLine())!=null){
				board.appendText(line+System.lineSeparator());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 此方法在实例化此类后应立即调用！！！
	 * @param userid 当前登录用户
	 */
	public void init() {
		userid=ClientRunner.getUser();
		logbl=RemoteHelper.getInstance().getLogblService();
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
	
}
