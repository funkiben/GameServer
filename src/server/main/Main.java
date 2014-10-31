package server.main;

import java.io.IOException;
import java.util.Scanner;

import server.command.*;


public class Main {

	
	public static void main(String[] args) throws IOException {
		ServerConfig cfg = new ServerConfig();
		
		long ms = System.currentTimeMillis();
		
		GameServer server = new GameServer(cfg);
		
		server.log("Server has started (" + (System.currentTimeMillis() - ms) + " ms)");
		
		CommandManager cmdMg = new CommandManager();
		cmdMg.register(
			new CmdStop(), 
			new CmdOnline(),
			new CmdAccounts(), 
			new CmdKick(),
			new CmdBan(),
			new CmdIPBan(),
			new CmdChunks(),
			new CmdSaveWorld()
		);
		
		Scanner scanner = new Scanner(System.in);
		
		while (!server.isStopped()) {
			String line = scanner.nextLine();
			
			System.out.println(cmdMg.parseCommand(line));
			
		}
		
		scanner.close();
		
	}
	
}
