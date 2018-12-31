package main.Chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import main.data.chatdata.ChatData;
import main.dataservice.chatdataservice.ChatDataService;

/**
 * 此类用于接收客户端的消息
 * @author 17678
 *
 */
public class ClientHandler implements Runnable {

	private BufferedReader in;
	private ChatServer server;
	private String name;
	private ArrayList<String>list;
	
	public ClientHandler(BufferedReader in,ChatServer server,String name) throws IOException{
		this.in=in;
		this.server=server;
		this.name=name;
		init();
	}
	
	/**
	 * 此方法用于客户端刚建立连接时检查是否有存在数据库的消息
	 * @throws IOException 
	 */
	private void init() throws IOException{
		ChatDataService cds=new ChatData();
		list=cds.readMsg(name);
	}
	
	@Override
	public void run() {
		
		Iterator<String> it=list.iterator();
		while(it.hasNext()){
			String line=it.next();
			try {
				server.sendMsg("client", line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			it.remove();
		}

		String line=null;
		
			try {
				while((line=in.readLine())!=null){
					/**
					 * line格式:clientName_msg
					 */
					String[]tokens=line.split("_");
					server.sendMsg(tokens[0], tokens[1]);					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
