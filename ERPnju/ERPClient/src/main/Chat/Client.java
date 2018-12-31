package main.Chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.util.MessageController;

public class Client {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String name;
	private MessageController mc;
	private ArrayList<String>tempMsg;
	
	public Client() throws UnknownHostException, IOException{
		socket=new Socket("127.0.0.1",5404);
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(socket.getOutputStream());
		tempMsg=new ArrayList<>();
		name=ClientRunner.getUser();
		out.println(name);
		out.flush();
	}
	
	public void run(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String line=null;
				try {
					while((line=in.readLine())!=null){
						/**
						 * 消息界面打开着，就显示
						 * 否则暂存
						 */
						if(mc!=null){
							mc.showMsg(line);
						}else{
							tempMsg.add(line);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		
	}
	
	public void getInput(String msg){
		/**
		 * line 格式:clientName_msg
		 */
		out.println(msg);
		out.flush();
		
	}
	
	/**
	 * 将消息模块与对应的controller绑定
	 * @param mc
	 */
	public void setController(MessageController mc){
		this.mc=mc;
	}
	
	public ArrayList<String> getTempMsg(){
		ArrayList<String>msgs=new ArrayList<>(tempMsg);
		tempMsg.clear();
		return msgs;
	}
	
	/**
	 * 此方法用于退出程序时
	 * 上传未读的消息
	 * 关闭socket
	 */
	public void onExit(){
			
		try {
			RemoteHelper.getInstance().getChatService().uploadTempMsg(name,tempMsg);
			RemoteHelper.getInstance().getChatService().remove(name);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
