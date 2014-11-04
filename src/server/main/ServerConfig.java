package server.main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.world.WorldGenerator;
import net.funkitech.util.ConfigurationFile;


public class ServerConfig extends ConfigurationFile {

	private static final long serialVersionUID = 8166304583399704945L;

	public ServerConfig() {
		super("server.cfg");
		
	}
	
	@Override
	public void onCreate() {
		delete();
		
		try {
			InputStream is = getClass().getResourceAsStream("/server.cfg");
			Path path = Paths.get("server.cfg");
			Files.copy(is, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPort() {
		return getInt("port");
	}
	
	public WorldGenerator getWorldGenerator() {
		return WorldGenerator.byName(get("world-generator"));
	}
	
	public float getTPS() {
		return getFloat("tps");
	}
	
}
