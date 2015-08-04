package net.funkitech.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionListener extends Thread {
	
	private final Server server;
	private final ServerSocket serverSocket;
	private volatile boolean finished = false;
	
	public ConnectionListener(Server server) throws IOException {
		super("ConnectionListener");
		
		this.server = server;
		
		serverSocket = new ServerSocket(server.getPort());
		
		setDaemon(true);
		
		start();
		
	}
	
	public void exit() {
		finished = true;
	}
	
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void run() {
		while (!finished) {
			try {
				
				Socket toClient = serverSocket.accept();
				
				server.log(toClient.getInetAddress() + " has connected");
				
				ClientHandler client = server.getNewClientHandler(toClient);
				
				server.addClient(client);
				
				server.onConnect(client);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
