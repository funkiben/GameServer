package server.main;

import java.util.List;

import server.event.EventHandler;
import server.event.EventPriority;
import server.event.Listener;
import server.event.player.PlayerLoginEvent;
import server.world.object.Player;


public class BanManager implements Listener {
	
	private final BannedPlayersFile bannedPlayers = new BannedPlayersFile("banned-players.txt");
	private final BannedPlayersFile bannedIPs = new BannedPlayersFile("banned-ips.txt");
	
	public boolean isIPBanned(String ip) {
		return bannedIPs.contains(ip);
	}
	
	public boolean isPlayerBanned(String player) {
		return bannedPlayers.contains(player);
	}
	
	public boolean banIP(String ip, String reason) {
		return bannedIPs.add(ip, reason);
	}
	
	public boolean banPlayer(String player, String reason) {
		return bannedPlayers.add(player, reason);
	}
	
	public boolean unbanIP(String ip) {
		return bannedIPs.remove(ip);
	}
	
	public boolean unbanPlayer(String player) {
		return bannedPlayers.remove(player);
	}
	
	public List<String> getBannedPlayers() {
		return bannedPlayers.getAll();
	}
	
	public List<String> getBannedIPs() {
		return bannedIPs.getAll();
	}
	
	public boolean isBanned(Player player) {
		return bannedIPs.contains(player.getIPAddress()) || bannedPlayers.contains(player.getName());
	}
	
	public String getPlayerBanReason(String player) {
		return bannedPlayers.getReason(player);
	}
	
	public String getIPBanReason(String ip) {
		return bannedIPs.getReason(ip);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		String ip = player.getIPAddress().replace("/", "");
		
		if (isIPBanned(ip)) {
			player.kick("IP Banned: " + getIPBanReason(ip));
		}
		
		if (isPlayerBanned(player.getName())) {
			player.kick("Banned: " + getPlayerBanReason(player.getName()));
		}
	}
	
}
