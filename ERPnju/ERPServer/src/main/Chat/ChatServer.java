package main.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.data.chatdata.ChatData;
import main.dataservice.chatdataservice.ChatDataService;

public class ChatServer {

	private ServerSocket socket;
	private HashMap<String,Socket>clientSocket;
	private HashMap<String,ClientHandler2>handlers;
	
	public ChatServer() throws IOException{
			socket=new ServerSocket(5404);
			clientSocket=new HashMap<>();
			handlers=new HashMap<>();
	}
	
	public void run() throws IOException{
		while(true){
			System.out.println("listening on port "+socket.getLocalPort());
			Socket client=socket.accept();
			/*
			 * 建立连接后
			 * 客户端立刻输入名字
			 * 服务器维护一个客户列表
			 */
			BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out=new PrintWriter(client.getOutputStream());
			String clientName=in.readLine();
			ClientHandler2 handler=new ClientHandler2(in,out,this,clientName);
			handler.run();
			System.out.println("got a client : "+clientName);
			clientSocket.put(clientName, client);
			handlers.put(clientName, handler);
		}
	}
	
	public ClientHandler2 getHandler(String name){
		return handlers.get(name);
	}
	
	public void removeClient(String name){
		
		System.out.println("remove a client : "+name);
		
		handlers.remove(name);
		clientSocket.remove(name);
	}
	
	/**
	 * 此方法供系统其他模块给客户端发消息
	 * @param name
	 * @param msg
	 */
	public void sendMsg(String name,String msg){
		ClientHandler2 handler=handlers.get(name);
		if(handler!=null){
			handler.sendMsg(msg);
		}else{
			//目标客户端已下线，存入数据库
			ChatDataService cds=new ChatData();
			cds.saveMsg(name, msg);
		}
		
	}
}
