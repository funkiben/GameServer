package server.world.object;

public enum WorldObjectType {
	
	
	PLAYER 				(0x001),
	CONTROLLED_PLAYER   (0x002),
	ARROW				(0x003),
	
	TILE_COBBLE			(0x010),
	TILE_WATER			(0x011),
	TILE_GRASS			(0x012),
	
	TREE				(0x020)
	;
	
	
	
	private final int id;
	
	WorldObjectType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
