package server.accounts;

import java.io.IOException;

import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;

public class CreateAccountMessageListener implements MessageListener {
	
	private static final Message MSG_INVALID_USERNAME = new Message("accountInvalid", "Username invalid; can only contain letters, numbers and underscores, and must be 4-16 characters long.");
	private static final Message MSG_USERNAME_TAKEN = new Message("accountInvalid", "Username taken.");
	private static final Message MSG_ACCOUNT_VALID = new Message("accountValid");
	
	private final UserAccountDB db;
	
	public CreateAccountMessageListener(UserAccountDB db) {
		this.db = db;
	}
	
	@MessageHandler(names = "createAccount")
	public void createAccount(ClientHandler client, String username, String password) throws IOException {
		
		if (!db.checkUsername(username)) {
			client.sendMessage(MSG_INVALID_USERNAME);
			return;
		}
		
		if (!db.usernameAvailable(username)) {
			client.sendMessage(MSG_USERNAME_TAKEN);
			return;
		}
		
		db.createNewAccount(username, password, client.getAddress().toString());
		
		db.getServer().log("New account created: " + username + " @" + client.getAddress());
		
		client.sendMessage(MSG_ACCOUNT_VALID);
		
	}
	
	

}
