package net.funkitech.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FunkiFileWriter {
	
	private final File file;
	private final FunkiFileReader reader;
	
	public FunkiFileWriter(File file, FunkiFileReader reader) {
		this.file = file;
		this.reader = reader;
		
	}
	
	public FunkiFileWriter(FunkiFile file) {
		this.file = file;
		reader = file.getReader();
		
	}
	
	public File getFile() {
		return file;
	}
	
	public FunkiFileReader getReader() {
		return reader;
	}
	
	public void write() {
		PrintWriter writer;
		
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
			
		}
		
		for (String line : reader.getLines()) {
			writer.println(line);
		}
		
		writer.close();
	}
	
	public void setLine(int i, String line) {
		if (i < reader.getLines().size()) {
			
			reader.getLines().set(i, line);
			
		} else {
			
			int size = reader.getLines().size();
			
			for (int j = 0; j <= i - size; j++) {
				
				if (j == i - size) {
					reader.getLines().add(line);
					
				} else {
					reader.getLines().add("");
					
				}
				
			}
			
			
		}
		
	}
	
}
