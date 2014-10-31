package net.funkitech.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FunkiFile extends File {

	private static final long serialVersionUID = 1749496973889950474L;
	
	
	private final FunkiFileReader reader;
	private final FunkiFileWriter writer;
	
	public FunkiFile(File dir, String name) {
		super(dir, name);
		
		try {
			if (createNewFile()) {
				onCreate();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setReadable(true);
		setWritable(true);
		
		
		reader = new FunkiFileReader(this);
		writer = new FunkiFileWriter(this);
			

		
		
	}
	
	public FunkiFile(String name) {
		this(null, name);
	}
	
	public void onCreate() {
		
	}
	
	public FunkiFileReader getReader() {
		return reader;
	}
	
	public FileInputStream getNewFileInputStream() {
		try {
			return new FileInputStream(this);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public FunkiFileWriter getWriter() {
		return writer;
	}
	
	public FileOutputStream getNewFileOutputStream() {
		try {
			return new FileOutputStream(this);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public void dump() {
		for (String line : reader.getLines()) {
			System.out.println(line);
		}
	}

}
