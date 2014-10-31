package net.funkitech.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;


public class ConfigurationFile extends FunkiFile {
	
	private static final long serialVersionUID = 3045822689036339737L;
	
	
	
	private final Map<String, String> entryMap = new LinkedHashMap<String, String>();
	
	public ConfigurationFile(File dir, String name) {
		super(dir, name);
		
		load();
		
	}
	
	public ConfigurationFile(String name) {
		this(null, name);
	}
	
	public void load() {
		for (String line : getReader().getLines()) {
			
			if (line.startsWith("#")) {
				continue;
			}
			
			int index = line.indexOf('=');
			
			if (index == -1) {
				continue;
			}
			
			String key = line.substring(0, index);
			String value = line.substring(index + 1, line.length());
			
			entryMap.put(key, value);
			
		}
	}
	
	public Map<String, String> getKeyValueMap() {
		return new LinkedHashMap<String, String>(entryMap);
	}
	
	public String get(String key) {
		return entryMap.get(key);
	}
	
	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}
	
	public double getDouble(String key) {
		return Double.parseDouble(get(key));
	}
	
	public float getFloat(String key) {
		return Float.parseFloat(get(key));
	}
	
	public short getShort(String key) {
		return Short.parseShort(get(key));
	}
	
	public byte getByte(String key) {
		return Byte.parseByte(get(key));
	}
	
	public long getLong(String key) {
		return Long.parseLong(get(key));
	}
	
	private int lineOf(String key) {
		String[] keys = entryMap.keySet().toArray(new String[entryMap.size()]);
		
		for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals(key)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void set(String key, String value) {
		entryMap.put(key, value);
		
		int line = lineOf(key);
		
		if (line == -1) {
			line = getReader().getLines().size();
		}
		
		getWriter().setLine(line, key + "=" + value);
		
	}
	
	public boolean remove(String key) {
		int line = lineOf(key);
		
		if (entryMap.remove(key) == null) return false;
		
		getReader().getLines().remove(line);
		
		return true;
	
	}
	
	public void save() {
		getWriter().write();
	}
	

	

}
