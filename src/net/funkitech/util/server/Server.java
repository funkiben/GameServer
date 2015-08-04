package net.funkitech.util.server;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageListeningManager;

public class Server {
	
	private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("kk:mm:ss");
	private static final String LOG_FORMAT = "[%date% %thread%] %msg%";
	
	private final String name;
	private final int port;
	private final List<ClientHandler> clients = new ArrayList<ClientHandler>();
	private final MessageListeningManager msgListeningManager;
	private final ConnectionListener connectionListener;
	private boolean stopped = false;
	
	public Server(String name, int port) throws IOException {
		this.name = name;
		this.port = port;
		
		msgListeningManager = new MessageListeningManager();
		
		connectionListener = new ConnectionListener(this);
		
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
	
	public ConnectionListener getConnectionListener() {
		return connectionListener;
	}
	
	public List<ClientHandler> getClients() {
		return clients;
	}
	
	void addClient(ClientHandler client) {
		clients.add(client);
	}
	
	void removeClient(ClientHandler client) {
		clients.remove(client);
	}
	
	public MessageListeningManager getMessageListeningManager() {
		return msgListeningManager;
	}
	
	public String formatMSG(String msg) {
		return LOG_FORMAT
				.replace("%date%", LOG_DATE_FORMAT.format(new Date()))
				.replace("%thread%", Thread.currentThread().getName())
				.replace("%msg%", msg);
	}
	
	public void log(String msg) {
		System.out.println(formatMSG(msg));
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void stop() {
		stopped = true;
		
		onStop();
		
		connectionListener.exit();
		
		List<ClientHandler> clientsCopy = new ArrayList<ClientHandler>();
		clientsCopy.addAll(clients);
		for (ClientHandler h : clientsCopy) {
			try {
				h.sendMessage(new Message("serverStopped"));
				h.disconnect("Server has stopped");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		afterStopped();
		
	}
	
	public ClientHandler getNewClientHandler(Socket socket) throws IOException {
		return new ClientHandler(this, socket);
	}
	
	public void onStop() {
		
	}
	
	public void afterStopped() {
		
	}
	
	public void onConnect(ClientHandler client) {
		
	}
	
	public void onDisconnect(ClientHandler client) {
		
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
