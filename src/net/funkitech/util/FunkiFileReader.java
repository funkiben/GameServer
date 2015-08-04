package net.funkitech.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FunkiFileReader {

	private final File file;
	private final List<String> lines = new ArrayList<String>();
	
	public FunkiFileReader(File file) {
		
		this.file = file;
		
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public String getLine(int index) {
		return lines.get(index);
	}
	
	public List<String> getLines() {
		return lines;
	}

}
