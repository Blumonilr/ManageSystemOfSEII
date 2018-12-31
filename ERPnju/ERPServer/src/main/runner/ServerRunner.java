package main.runner;

import java.io.IOException;

import main.Chat.ChatServer;
import main.rmi.RemoteHelper;

public class ServerRunner {

	static RemoteHelper remotehelper;
	private static ChatServer chatServer;
	
	public static void main(String[] args) {
		remotehelper=new RemoteHelper();
		
		try {
			chatServer=new ChatServer();
			chatServer.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static ChatServer getChatServer(){
		return chatServer;
	}
}
