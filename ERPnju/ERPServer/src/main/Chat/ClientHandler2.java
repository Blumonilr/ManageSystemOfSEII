package main.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import main.data.chatdata.ChatData;
import main.dataservice.chatdataservice.ChatDataService;

public class ClientHandler2 {

	private BufferedReader in;
	private PrintWriter out;
	private ChatServer server;
	private ArrayList<String>list;
	private String name;
	
	public ClientHandler2(BufferedReader br,PrintWriter pw,ChatServer s,String n){
		in=br;
		out=pw;
		server=s;
		list=new ArrayList<>();
		name=n;
	}
	
	/**
	 * 从数据库读取未读消息
	 */
	private void init(){
		ChatDataService cds=new ChatData();
		list=cds.readMsg(name);
	}
	
	/**
	 * 读取客户端发送来的消息并发送给对应客户端
	 */
	public void run(){
		new Thread(new Runnable(){

			@Override
			public void run() {

				String line=null;
				
				try {
					while((line=in.readLine())!=null){
						/**
						 * line格式：clientName_msg
						 */
						String[]tokens=line.split("_");
						System.out.println("server ready to send msg to "+tokens[0]+" content = "+tokens[1]);
						ClientHandler2 handler=server.getHandler(tokens[0]);
						if(handler!=null){
							handler.sendMsg(tokens[1]);
						}else{
							//目标客户端已经下线，消息存到数据库
							ChatDataService cds=new ChatData();
							cds.saveMsg(tokens[0], tokens[1]);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	public void sendMsg(String msg){
		
		/**
		 * 先把未读消息发送给客户端
		 */
		init();
		Iterator<String> it=list.iterator();
		while(it.hasNext()){
			String line=it.next();
			out.println(line);
			out.flush();
			it.remove();
		}
		System.out.println("server send msg : "+msg);
		out.println(msg);
		out.flush();
	}
}
