package server.accounts;

import java.io.IOException;

import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;
import server.event.player.PlayerLoginEvent;
import server.world.object.Player;


public class LoginMessageListener implements MessageListener {
	
	private static final Message MSG_INVALID_PASSWORD = new Message("loginFailed", "Could not log in; invalid password.");
	private static final Message MSG_INVALID_USERNAME = new Message("loginFailed", "Could not find account; invalid username.");
	
	private final UserAccountDB db;
	
	public LoginMessageListener(UserAccountDB db) {
		this.db = db;
	}
	
	@MessageHandler(names = "login")
	public void login(ClientHandler client, String username, String password) throws IOException {
		
		UserAccount account = db.getAccount(username);
		
		if (account == null) {
			client.sendMessage(MSG_INVALID_USERNAME);
			return;
		}
		
		if (!account.getPassword().equals(password)) {
			client.sendMessage(MSG_INVALID_PASSWORD);
			return;
		}
		
		account.addIP(client.getAddress().toString());
		account.save();
		
		Player player = db.getServer().getPlayer(username);
		if (player != null) {
			player.kick("You logged in from another location!");
		}
		
		player = db.getServer().enablePlayer(account, client);
		
		client.sendMessage(new Message("loginSuccess", player.getLocation()));
		
		db.getServer().log(username + " logged in with ID " + player.getId() + " from " + client.getAddress() + " at " + player.getLocation());
		
		PlayerLoginEvent event = new PlayerLoginEvent(player);
		db.getServer().getEventManager().callEvent(event);
		
		
	}

}
