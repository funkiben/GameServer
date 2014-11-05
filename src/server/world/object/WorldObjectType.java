package server.world.object;

public enum WorldObjectType {
	
	
	PLAYER 				(0x001),
	BLOCK  				(0x002),
	CONTROLLED_PLAYER   (0x003),
	ARROW				(0x004)
	;
	
	
	
	private final int id;
	
	WorldObjectType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
