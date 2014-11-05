package server.world;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.event.world.NewChunkEvent;
import server.event.world.object.NewWorldObjectEvent;
import server.event.world.object.WorldObjectRemoveEvent;
import server.world.object.Player;
import server.world.object.WorldObject;
import server.world.object.WorldObjectType;
import net.funkitech.util.Location;


public class Chunk {
	
	public static final int size = 800;
	
	public static int toChunkX(double x) {
		if (x <= 0) {
			x++;
		}
		
		int cx = (int) (x / size);
		
		if (x <= 0) {
			cx--;
		}
		
		return cx;
	}
	
	public static int toChunkY(double y) {
		if (y <= 0) {
			y++;
		}
		
		int cy = (int) (y / size);
		
		if (y <= 0) {
			cy--;
		}
		
		return cy;
	}
	
	public static Location getChunkLocation(int x, int y) {
		return new Location(x * size, y * size);
	}
	
	
	
	
	private final World world;
	private final int x;
	private final int y;
	private final File file;
	private final Map<Integer, WorldObject> objects = new HashMap<Integer, WorldObject>();
	private Object[] data = new Object[0];
	private boolean needsSave = false;
	
	public Chunk(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		file = new File(world.getFolder(), x + "," + y + ".chunk");
		
		if (!file.exists()) {
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			world.getGenerator().populateChunk(this);
			
			NewChunkEvent event = new NewChunkEvent(this);
			world.getServer().getEventManager().callEvent(event);
			
		} else {
		
			load();
		
		}
		
	}
	
	public Chunk(World world, File file) {
		this.world = world;
		this.file = file;
		
		String[] coords = file.getName().replace(".chunk", "").split(",");
		x = Integer.parseInt(coords[0]);
		y = Integer.parseInt(coords[1]);
		
		load();
		
	}
	
	public boolean needsSave() {
		return needsSave;
	}
	
	public void setNeedsSave(boolean b) {
		needsSave = b;
	}
	
	public Location getLocation() {
		return getChunkLocation(x, y);
	}
	
	public World getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Object[] getData() {
		return data;
	}
	
	public void setData(Object...data) {
		this.data = data;
		needsSave = true;
	}
	
	public List<WorldObject> getObjects() {
		return new ArrayList<WorldObject>(objects.values());
	}
	
	public List<WorldObject> getObjects(WorldObjectType type) {
		List<WorldObject> list = new ArrayList<WorldObject>();
		
		for (WorldObject obj : objects.values()) {
			if (obj.getType() == type) {
				list.add(obj);
			}
		}
		
		return list;
	}
	
	public WorldObject getObject(int id) {
		return objects.get(id);
	}
	
	public double getSize() {
		return file.length() / 1000D;
	}
	
	public void changeObjectsChunk(WorldObject object, Chunk newChunk) {
		objects.remove(object.getId());
		newChunk.objects.put(object.getId(), object);
		needsSave = true;
	}
	
	public void addObject(WorldObject object) {
		NewWorldObjectEvent event = new NewWorldObjectEvent(object);
		world.getServer().getEventManager().callEvent(event);
		
		if (event.isCancelled()) return;
		
		objects.put(object.getId(), object);
		
		object.updateWithPlayers();
		
		needsSave = true;
		
	}
	
	public boolean removeObject(int id) {
		WorldObject object = objects.get(id);
		
		if (object == null) {
			return false;
		}
		
		WorldObjectRemoveEvent event = new WorldObjectRemoveEvent(object);
		world.getServer().getEventManager().callEvent(event);
		
		if (event.isCancelled()) {
			return false;
		}
		
		
		object.disableServerTickListener();
		object.removeFromPlayers();
		objects.remove(id);
		
		needsSave = true;
		
		return true;
	}
	
	public boolean removeObject(WorldObject obj) {
		return removeObject(obj.getId());
	}
	
	public boolean contains(Location location) {
		double thisX = getLocation().getX();
		double x = location.getX();
		double thisY = getLocation().getX();
		double y = location.getY();
		
		return x < thisX + size && x >= thisX && y >= thisY && y < thisY + size;
	}
	
	public void save() {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
			
			for (WorldObject obj : objects.values()) {
				if (obj.canSave()) {
					os.writeObject(obj);
				}
			}
			
			for (Object obj : data) {
				os.writeObject(obj);
			}
			
			os.flush();
			os.close();
			
			needsSave = false;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		try {
			objects.clear();
			
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
			
			List<Object> nonWorldObjects = new ArrayList<Object>();
			
			while (true) {
				try {
					
					Object obj = is.readObject();
					
					if (obj instanceof WorldObject) {
						objects.put(((WorldObject) obj).getId(), (WorldObject) obj);
						((WorldObject) obj).initializeFromChunk();
					} else {
						nonWorldObjects.add(obj);
					}
					
				} catch (EOFException e) {
					break;
				}
				
			}
			
			data = nonWorldObjects.toArray();
			
			is.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	
	public List<Player> getViewingPlayers() {
		List<Player> list = new ArrayList<Player>();
		
		for (Player player : world.getServer().getOnlinePlayes()) {
			if (player.canSeeChunk(x, y)) {
				list.add(player);
			}
		}
		
		return list;
	}

}
