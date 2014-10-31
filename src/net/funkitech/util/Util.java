package net.funkitech.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<String> wrapString(String string, int charsPerLine) {
		
		String[] words = string.split(" ");
		
		List<String> lines = new ArrayList<String>();
		
		String line = new String();
		
		for (String w : words) {
			line += w + " ";
			
			if (line.length() > charsPerLine) {
				lines.add(line);
				line = new String();
				
			}
		}
		
		if (!line.isEmpty() && !line.equals(" ")) {
			lines.add(line);
		}
		
		return lines;
	}
	
	public static String timeMinutes(int sec) {
		String minutes = Integer.toString((int) Math.floor(sec / 60));
		String seconds = Integer.toString((sec % 60));
		if (seconds.length() == 1) seconds = 0 + seconds;
		return minutes + ":" + seconds;
	}
	
	public static String timeHours(int sec) {
    	String hours = Integer.toString(sec / 60 / 60);
		String minutes = Integer.toString((int) Math.floor((sec / 60) % 60));
		String seconds = Integer.toString((sec % 60));
		if (seconds.length() == 1) seconds = 0 + seconds;
		if (minutes.length() == 1) minutes = 0 + minutes;
		if (hours.length() == 1) hours = 0 + hours;
		return hours + ":" + minutes + ":" + seconds;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValueInEnum(String str, Class<T> T) {
		for (Object t : T.getEnumConstants()) {
			if (str.equalsIgnoreCase(t.toString())) {
				return (T) t;
			}
		}
		return null;
	}
	
	

	public static void removeFinalModifier(Field field) throws IllegalArgumentException, IllegalAccessException {
		try {
			Field mField = Field.class.getDeclaredField("modifiers");
			mField.setAccessible(true);
			mField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static void toggleFinalModifier(Field field) throws IllegalArgumentException, IllegalAccessException {
		try {
			Field mField = Field.class.getDeclaredField("modifiers");
			mField.setAccessible(true);
			mField.setInt(field, field.getModifiers() ^ Modifier.FINAL);
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static List<File> deepListFiles(File file) {
		List<File> files = new ArrayList<File>();
		
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				files.addAll(deepListFiles(f));
			} else {
				files.add(f);
			}
		}
		
		return files;
		
	}
}
