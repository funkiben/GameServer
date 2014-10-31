package net.funkitech.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionListener extends ListenerThread {
	
	private final Server server;
	private final ServerSocket serverSocket;
	
	public ConnectionListener(Server server) throws IOException {
		super(server.getName() + "ConnectionListener");
		
		this.server = server;
		
		serverSocket = new ServerSocket(server.getPort());
		
		start();
		
	}

	@Override
	public void listen() {
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

	@Override
	public void onFinish() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
