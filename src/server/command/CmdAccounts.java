package server.command;

import java.util.Map;

import server.accounts.UserAccount;


public class CmdAccounts extends CmdServer {

	public CmdAccounts() {
		super("accounts", "Lists all accounts");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String ls = new String();
		
		for (UserAccount a : server.getUserAccountDB().getAccounts()) {
			ls += a.getName() + "\n";
		}
		
		return ls;
	}

}
