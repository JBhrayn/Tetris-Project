package block;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import grid.Tile;

public class BlockController {

	private Block currentBlock;
	private ArrayList<ArrayList<Tile>> gridTiles;
	
	public BlockController(Block currentBlock, ArrayList<ArrayList<Tile>> gridTiles) {
		this.currentBlock = currentBlock;
		this.gridTiles = gridTiles;
	}
	
	//SETTERS AND GETTERS
	public Block getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(Block currentBlock) {
		this.currentBlock = currentBlock;
	}

	public ArrayList<ArrayList<Tile>> getgridTiles() {
		return gridTiles;
	}

	public void setGridTiles(ArrayList<ArrayList<Tile>> gridTiles) {
		this.gridTiles = gridTiles;
	}
	
	//OPERATIONS
	public void initializeBlock() {
			ArrayList<Point> defaultTileLayouts = new ArrayList<>(currentBlock.getBlockType().getTilesLayout());
			
			ArrayList<Tile> tiles = new ArrayList<>(Block.TILES_IN_BLOCK);
			
			
			
			for(Point point : defaultTileLayouts){
				tiles.add(gridTiles.get(point.x)
								  .get(point.y+4));
			}
			
			for(Tile tile: gridTiles.get(0)) {
				if(tile.isGhostTile()) {
					System.exit(0);
				}
			}
			
			currentBlock.setTiles(tiles);
			currentBlock.setGridTiles(gridTiles);
			currentBlock.refreshBlock();
			currentBlock.renderGhostBlock();
	}
	
	public void moveBlock(int xDir, int yDir, boolean rerenderGhostBlock) {
			if(currentBlock.hasAnyCollisions(xDir, yDir))
				return;
			

			if(!currentBlock.hasDropped())
				if(currentBlock.hasAnyCollisionBelow())
					if(yDir != 0)
						rerenderGhostBlock = false;
			
			ArrayList<Tile> tiles = currentBlock.getTiles();
			
			for(int i=0; i < Block.TILES_IN_BLOCK; i++) {
				Tile currentTile = tiles.get(i);
				currentTile.clearTile();
				
				int newXPos = xDir + currentTile.getxPos();
				int newYPos = yDir + currentTile.getyPos();
				
				Tile nextTile = gridTiles.get(newXPos).get(newYPos);
				nextTile.setOccupiedBlockType(currentBlock.getBlockType());
				nextTile.setGhostTile(false);
				
				tiles.set(i, nextTile);
			}
			currentBlock.refreshBlock();
			
			if(rerenderGhostBlock)
				currentBlock.renderGhostBlock();

	}
	
	private void rotateJLZSTBlock() {
		int centerPointIndex = currentBlock.getBlockType().getCenterPointIndex();
		
		ArrayList<Tile> tiles = currentBlock.getTiles();
		Tile centerTile = tiles.get(centerPointIndex);
		
		int centerXPos = centerTile.getxPos();
		int centerYPos = centerTile.getyPos();
		
		int temp, newXPos, newYPos;
		
		int i = 0;
		for(Tile tile : tiles) {
			newXPos = tile.getxPos() - centerXPos;
			newYPos = tile.getyPos() - centerYPos;
			
			temp = newXPos;
			newXPos = newYPos;
			newYPos = temp;
			
			newYPos = -newYPos;
			
			newXPos += centerXPos;
			newYPos += centerYPos;
			
			Tile newTile = gridTiles.get(newXPos).get(newYPos);
			tile.clearTile();
			tiles.set(i, newTile);
			
			i++;
		}
	}

	private void rotateIBlock() {
		ArrayList<Tile> tiles = currentBlock.getTiles();
		int[] newX= new int[4];
		int[] newY= new int[4];
		
		int firstX = tiles.get(0).getxPos();
		int lastX = tiles.get(3).getxPos();
		
		int firstY = tiles.get(0).getyPos();
		
		int commonPoint = firstX == lastX ? firstX : firstY;
		
		int rotation = currentBlock.getRotationCount();
		int tileSequenceStart = -2;
		int operator = 1;
		
		if(rotation == 2 || rotation == 3) {
			operator = -operator;
			tileSequenceStart = -tileSequenceStart;
		}
		
		final int operand = operator;
		tileSequenceStart = commonPoint + tileSequenceStart;
		System.out.println();
		
		if(firstX == lastX) {
			Arrays.fill(newY, tiles.get(1).getyPos());
			newX = IntStream.iterate(tileSequenceStart, n -> n + operand).limit(4).toArray();
		}
		else {
			Arrays.fill(newX, tiles.get(1).getxPos());
			newY = IntStream.iterate(tileSequenceStart, n -> n + operand).limit(4).toArray();
			System.out.println(newY.toString());
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			Tile newTile = gridTiles.get(newX[i]).get(newY[i]);
			tiles.get(i).clearTile();
			tiles.set(i, newTile);
		}
		currentBlock.setRotationCount(rotation+1);
		
		if(rotation+1 >= 4)
			currentBlock.setRotationCount(0);
	}	
	
	public void rotateBlock() {	
		if(currentBlock.hasDropped()) 
			return;
		if(currentBlock.getBlockType() == BlockType.O) 
			return;
		
		if(currentBlock.getBlockType() == BlockType.I)
			rotateIBlock();
		else 
			rotateJLZSTBlock();

		currentBlock.refreshBlock();

		if(!currentBlock.hasDropped() || !currentBlock.hasAnyCollisionBelow())
			currentBlock.renderGhostBlock();
	}
}
