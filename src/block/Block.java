package block;

import java.awt.Point;
import java.util.ArrayList;

import grid.Tile;
import grid.TileGrid;

public class Block {

	public static final BlockType DEFAULT_BLOCK_TYPE = BlockType.EMPTY;
	public static final int TILES_IN_BLOCK = 4;

	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<ArrayList<Tile>> gridTiles = new ArrayList<>();
	
	private BlockType blockType;
	private Point centerPoint;
	private int rotationCount = 1 ;
	private boolean hasDropped;

	private GhostBlock ghostBlock;
	
	//CONSTRUCTOR
	public Block(BlockType blockType) {
		this.blockType = blockType;
		this.ghostBlock = new GhostBlock();
		this.tiles.ensureCapacity(TILES_IN_BLOCK);
	}
	
	//SETTERS AND GETTERS
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = new ArrayList<>(tiles);
	}
	
	public ArrayList<ArrayList<Tile>> getGridTiles() {
		return gridTiles;
	}

	public void setGridTiles(ArrayList<ArrayList<Tile>> gridTiles) {
		this.gridTiles = gridTiles;
	}

	public BlockType getBlockType() {
		return blockType;
	}

	public void setBlockType(BlockType blockType) {
		this.blockType = blockType;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}
	
	public int getRotationCount() {
		return rotationCount;
	}

	public void setRotationCount(int rotationCount) {
		this.rotationCount = rotationCount;
	}
	
	public boolean hasDropped() {
		return hasDropped;
	}

	public void setHasDropped(boolean hasDropped) {
		this.hasDropped = hasDropped;
	}
	
	private boolean fromThisBlock(Tile otherTile) {
		return tiles.contains(otherTile);
	}
	
	//COLLIISON CHECKERS
	private boolean hasHitFloor() {
		return tiles.stream().anyMatch(tile -> tile.getxPos() >= TileGrid.GRID_ROWS-1);
	}

	private boolean hasHitLeftWall() {
		return tiles.stream().anyMatch(tile -> tile.getyPos() <= 0);
	}
	
	private boolean hasHitRightWall() {
		return tiles.stream().anyMatch(tile -> tile.getyPos() >= TileGrid.GRID_COLUMNS-1);
	}
	
	private boolean hasHitOtherTile(Tile otherTile) {
		if(otherTile.isOccupied() && !fromThisBlock(otherTile)) {
			if(otherTile.isGhostTile())
				return false;
			else
				return true;
		}
		return false;
	}
	
	private boolean hasHitLeftTile() {
		Tile leftTile;
		for(Tile tile: tiles) {
			leftTile = gridTiles.get(tile.getxPos())
								.get(tile.getyPos()-1);
			if(hasHitOtherTile(leftTile))
				return true;
		}
		return false;
	}
	
	private boolean hasHitRightTile() {
		Tile rightTile;
		for(Tile tile: tiles) {
			rightTile = gridTiles.get(tile.getxPos())
								 .get(tile.getyPos()+1);
			
			if(hasHitOtherTile(rightTile))
				return true;
		}
		return false;
	}
	
	private boolean hasAnyCollisionOnLeft() {
		return (hasHitLeftWall() || hasHitLeftTile()) ? true : false;
	}
	
	private boolean hasAnyCollisionOnRight() {
		return (hasHitRightWall() || hasHitRightTile()) ? true : false;
	}
	
	private boolean hasHitOtherTileBelow() {
		Tile belowTile;
		for(Tile tile: tiles) {
			belowTile = gridTiles.get(tile.getxPos()+1)
								 .get(tile.getyPos());
			if(hasHitOtherTile(belowTile)) return true;
		}
		return false;
	}

	public boolean hasAnyCollisionBelow() {
		return (hasHitFloor() || hasHitOtherTileBelow()) ? true : false;
	}
	
	public boolean hasAnyCollisions(int xDir, int yDir) {
		if(hasDropped() && yDir !=0) 
			return true;
		if(hasAnyCollisionBelow() && xDir == 1) 
			return true ;
		if(hasAnyCollisionOnLeft() && yDir == -1) 
			return true;
		if(hasAnyCollisionOnRight() && yDir == 1) 
			return true;
		
		return false;
	}

	public void clearBlock() {
		for(Tile tile: tiles) {
			tile.clearTile();
			tile.setGhostTile(false);
		}
	}
	
	public void refreshBlock() {
		for(Tile tile: tiles) {
			tile.setOccupiedBlockType(blockType);
			tile.refreshTile();
		}
	}
	
	public void renderGhostBlock() {
			destroyGhostBlock();
			ghostBlock.initializeGhostBlock();
			ghostBlock.dropGhostBlock();
			
			for(Tile tile : ghostBlock.getGhostTiles()) {
				tile.setOccupiedBlockType(blockType);
				tile.setGhostTile(true);
				tile.refreshTile();
			}
	}
	
	public void destroyGhostBlock() {
			for(Tile tile: ghostBlock.ghostTiles){
				tile.clearTile();
			}
			ghostBlock.ghostTiles.clear();
	}
	
	private class GhostBlock{
		private ArrayList<Tile> ghostTiles = new ArrayList<>(TILES_IN_BLOCK);
		
		private ArrayList<Tile> getGhostTiles() {
			return ghostTiles;
		}

		private void initializeGhostBlock(){
			for(Tile tile : tiles) {
				ghostTiles.add(gridTiles.get(tile.getxPos())
 	    			      		 		.get(tile.getyPos()));
			}
		}

		private boolean ghostHasAnyCollisionBelow() {
			if(ghostTiles.stream().anyMatch(tile -> tile.getxPos() >= TileGrid.GRID_ROWS-1))
				return true;
			
			Tile belowTile;
			for(Tile tile: ghostTiles) {
				belowTile = gridTiles.get(tile.getxPos()+1)
									 .get(tile.getyPos());
				if(belowTile.isOccupied() && !ghostTiles.contains(belowTile))
					return true;
			}
			return false;
		}
		
		private void moveGhostBlock() {
			for(int i=0; i < Block.TILES_IN_BLOCK; i++) {
				Tile currentTile = ghostTiles.get(i);
				
				int newXPos = 1 + currentTile.getxPos();
				
				Tile nextTile = gridTiles.get(newXPos).get(currentTile.getyPos());
				
				ghostTiles.set(i, nextTile);
			}
		}
		
		private void dropGhostBlock() {
			while(!ghostHasAnyCollisionBelow())
				moveGhostBlock();
		}
		
		
		
	}
}
