package net.funkitech.util.server.messaging;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MessagingSocket extends Thread {
	
	private final Socket socket;
	private final MessageListeningManager msgListeningManager;
	private ObjectInputStream input;
	private final ObjectOutputStream output;
	private volatile boolean exitted = false;
	
	public MessagingSocket(MessageListeningManager msgListeningManager, Socket socket) throws IOException {
		super("MessageListener" + socket.getInetAddress());
		
		this.msgListeningManager = msgListeningManager;
		this.socket = socket;
		
		output = new ObjectOutputStream(socket.getOutputStream());
		
		start();
	}
	
	public MessagingSocket(MessageListeningManager msgListeningManager, String address, int port) throws UnknownHostException, IOException {
		this(msgListeningManager, new Socket(address, port));
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InetAddress getAddress() {
		return socket.getInetAddress();
	}
	
	public void exit(String reason) {
		exitted = true;
		interrupt();
		onExit(reason);
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		exit(null);
	}
	
	public boolean hasExited() {
		return exitted;
	}
	
	public ObjectInputStream getInput() {
		return input;
	}
	
	public ObjectOutputStream getOutput() {
		return output;
	}
	
	public void sendMessage(Message...msgs) throws IOException {
		for (Message msg : msgs) {
			output.writeObject(msg);	
		}
	}
	
	
	
	@Override
	public void run() {
		
		try {
			
			input = new ObjectInputStream(socket.getInputStream());
			
			while (!exitted && !socket.isClosed()) {
				
				try {
						
					Object obj = input.readObject();
						
					if (obj instanceof Message) {
						msgListeningManager.call((Message) obj, this);
					} else {
						System.out.println("Recieved object that was not of the Message class");
					}
						
				} catch (EOFException | SocketException e) {
					break;
					
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
					
			}
			
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!exitted) {
			onExit("Connection closed");
			exitted = true;
		}
		
		
	}
	
	public void onExit(String reason) {
		
	}
	
}
