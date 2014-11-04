package server.accounts;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.main.GameServer;
import server.world.object.Player;

public class UserAccountDB {
	
	private final GameServer server;
	private final File userDir = new File("users");
	private final Map<String,UserAccount> map = new HashMap<String,UserAccount>();
	
	public UserAccountDB(GameServer server) {		
		this.server = server;
		server.getMessageListeningManager().registerListeners(new CreateAccountMessageListener(this), new LoginMessageListener(this));
		
		userDir.mkdir();
		
		load();
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public void load() {
		map.clear();
		
		for (File file : userDir.listFiles()) {
			if (!file.getName().equals(".DS_Store")) {
				map.put(file.getName(), new UserAccount(file));
			}
		}
	}
	
	public boolean hasAccount(String username) {
		return map.containsKey(username);
	}
	
	public List<UserAccount> getAccounts() {
		List<UserAccount> list = new ArrayList<UserAccount>();
		list.addAll(map.values());
		return list;
	}
	
	public UserAccount getAccount(String username) {
		return map.get(username);
	}
	
	public boolean usernameAvailable(String username) {
		for (String str : userDir.list()) {
			if (str.equalsIgnoreCase(username)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkUsername(String username) {
		if (username.length() > 16 || username.length() < 4) {
			return false;
		}
		
		for (char c : username.toCharArray()) {
			if (!checkUsernameChar(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkUsernameChar(char chr) {
		return (chr >= 65 && chr <= 90) || (chr >= 97 && chr <= 122) || (chr >= 48 && chr <= 57) || chr == '_';
	}

	public void createNewAccount(String name, String pw, String ip) {
		UserAccount account = new UserAccount(new File(userDir, name));
		account.setPassword(pw);
		account.setJoinDate(new Date());
		account.addIP(ip);
		map.put(name, account);
		
		Player player = server.createNewPlayer(name, false);
		
		account.setObjectID(player.getId());
		
		account.save();
	}
	
}
