package net.funkitech.util.server;

import java.io.IOException;
import java.net.Socket;

import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessagingSocket;


public class ClientHandler extends MessagingSocket {
	
	private final Server server;
	
	public ClientHandler(Server server, Socket socket) throws IOException {
		super(server.getMessageListeningManager(), socket);
		
		this.server = server;
		
	}
	
	public void disconnect(String reason) {
		try {
			sendMessage(new Message("disconnect", reason));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		exit(reason);
	}
	
	@Override
	public void onExit(String reason) {
		server.removeClient(this);
		server.onDisconnect(this);
		server.log(getAddress() + " has disconnected: " + reason);
	}
	
	
	

	
}
