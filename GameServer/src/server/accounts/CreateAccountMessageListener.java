package server.accounts;

import java.io.IOException;

import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;
import server.main.GameServer;


public class CreateAccountMessageListener implements MessageListener {
	
	private static final Message MSG_INVALID_USERNAME = new Message("accountInvalid", "Username invalid; can only contain letters, numbers and underscores, and must be 4-16 characters long.");
	private static final Message MSG_USERNAME_TAKEN = new Message("accountInvalid", "Username taken.");
	private static final Message MSG_ACCOUNT_VALID = new Message("accountValid");
	
	
	@MessageHandler(names = "createAccount")
	public void createAccount(ClientHandler client, String username, String password) throws IOException {
		
		UserAccountDB db = GameServer.inst.getUserAccountDB();
		
		if (!db.checkUsername(username)) {
			client.sendMessage(MSG_INVALID_USERNAME);
			return;
		}
		
		if (!db.usernameAvailable(username)) {
			client.sendMessage(MSG_USERNAME_TAKEN);
			return;
		}
		
		db.createNewAccount(username, password, client.getAddress().toString());
		
		GameServer.inst.log("New account created: " + username + " @" + client.getAddress());
		
		client.sendMessage(MSG_ACCOUNT_VALID);
		
	}
	
	

}
