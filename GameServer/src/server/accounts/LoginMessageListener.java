package server.accounts;

import java.io.IOException;

import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;
import server.event.player.PlayerLoginEvent;
import server.main.GameServer;
import server.world.object.Player;


public class LoginMessageListener implements MessageListener {
	
	private static final Message MSG_INVALID_PASSWORD = new Message("loginFailed", "Could not log in; invalid password.");
	private static final Message MSG_INVALID_USERNAME = new Message("loginFailed", "Could not find account; invalid username.");

	@MessageHandler(names = "login")
	public void login(ClientHandler client, String username, String password) throws IOException {
		
		UserAccountDB db = GameServer.inst.getUserAccountDB();
		
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
		
		GameServer.inst.log(username + " logged in from " + client.getAddress() + " at " + account.getLocation());
		
		client.sendMessage(new Message("loginSuccess", account.getLocation()));
		
		Player player = GameServer.inst.getPlayer(username);
		if (player != null) {
			player.kick("You logged in from another location!");
		}
		
		player = GameServer.inst.addPlayer(account, client);
		
		PlayerLoginEvent event = new PlayerLoginEvent(player);
		GameServer.inst.getEventManager().callEvent(event);
		
		
	}

}
