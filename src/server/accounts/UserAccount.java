package server.accounts;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.funkitech.util.FunkiFile;

public class UserAccount {
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
	
	// FILE SETUP: LINE NUMBERS
	private final static int PASSWORD = 0;
	private final static int IP_ADDRESSES = 1;
	private final static int JOIN_DATE = 2;
	private final static int LAST_ONLINE = 3;
	private final static int OBJECT_ID = 4;
	
	private final String name;
	private final File dir;
	private final FunkiFile infoFile;
	
	public UserAccount(File dir) {
		this.name = dir.getName();
		this.dir = dir;
		dir.mkdir();
		
		infoFile = new FunkiFile(dir, "user.info");
		
	}
	
	public String getName() {
		return name;
	}
	
	public File getDirectory() {
		return dir;
	}
	
	public FunkiFile getInfoFile() {
		return infoFile;
	}
	
	public String getPassword() {
		return infoFile.getReader().getLine(PASSWORD);
	}
	
	public Set<String> getIPs() {
		Set<String> ips = new HashSet<String>();
		Collections.addAll(ips, infoFile.getReader().getLine(IP_ADDRESSES).split(","));
		return ips;
	}
	
	public Date getJoinData() throws ParseException {
		return DATE_FORMAT.parse(infoFile.getReader().getLine(JOIN_DATE));
	}
	
	public Date getLastOnline() throws ParseException {
		return DATE_FORMAT.parse(infoFile.getReader().getLine(LAST_ONLINE));
	}
	
	public int getObjectID() {
		return Integer.parseInt(infoFile.getReader().getLine(OBJECT_ID));
	}
	
	public void setPassword(String pw) {
		infoFile.getWriter().setLine(PASSWORD, pw);
	}
	
	public void addIP(String ip) {
		if (getIPs().contains(ip)) {
			return;
		}
		
		String[] ips = infoFile.getReader().getLine(IP_ADDRESSES).split(",");
		
		String str = new String();
		for (int i = 0; i < ips.length; i++) {
			str += ips[i] + (i > 0 ? "," : "");
		}
		
		str += ip;
		
		infoFile.getWriter().setLine(IP_ADDRESSES, str);
	}
	
	public void setJoinDate(Date date) {
		infoFile.getWriter().setLine(JOIN_DATE, DATE_FORMAT.format(date));
	}
	
	public void updateLastOnlineDate() {
		infoFile.getWriter().setLine(LAST_ONLINE, DATE_FORMAT.format(new Date()));
	}
	
	public void setObjectID(int id) {
		infoFile.getWriter().setLine(OBJECT_ID, Integer.toString(id));
	}
	
	public void save() {
		infoFile.getWriter().write();
	}

}
