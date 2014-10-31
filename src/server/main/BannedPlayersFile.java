package server.main;

import java.util.ArrayList;
import java.util.List;

import net.funkitech.util.ConfigurationFile;

public class BannedPlayersFile extends ConfigurationFile {
	
	private static final long serialVersionUID = -6795652113909917361L;
	
	
	public BannedPlayersFile(String name) {
		super(name);
		
		
	}
	
	public List<String> getAll() {
		List<String> list = new ArrayList<String>();
		list.addAll(getKeyValueMap().keySet());
		return list;
	}
	
	public boolean contains(String name) {
		return getKeyValueMap().containsKey(name);
	}
	
	public boolean add(String name, String reason) {
		if (contains(name)) return false;
		
		set(name, reason);
		
		save();
		
		return true;
	}
	
	@Override
	public boolean remove(String player) {
		boolean b = super.remove(player);
		
		if (b) {
			save();
		}
		
		return b;
	}
	
	public String getReason(String name) {
		return get(name);
	}

}
