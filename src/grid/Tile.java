package grid;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import block.BlockType;
import block.BlocksResource;

public class Tile extends JPanel {

	public static final int TILE_SIZE = 45;

	private int xPos;
	private int yPos;
	private boolean isGhostTile;
	private BlocksResource blockImage;
	private BlockType occupiedBlockType;
	
	public Tile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.blockImage = BlocksResource.getInstance();
		this.occupiedBlockType = BlockType.EMPTY;
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(TILE_SIZE, TILE_SIZE);
	}
	
	public int getxPos() {
		return xPos;
	}
	
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public BlockType getOccupiedBlockType() {
		return occupiedBlockType;
	}

	public void setOccupiedBlockType(BlockType occupiedBlockType) {
		this.occupiedBlockType = occupiedBlockType;
	}

	public boolean isGhostTile() {
		return isGhostTile;
	}

	public void setGhostTile(boolean isGhostTile) {
		this.isGhostTile = isGhostTile;
	}
	
	// OPERATIONS
	public void destroyTile() {
		this.occupiedBlockType = BlockType.DESTROYED;
		refreshTile();
	}
	
	public void clearTile() {
		this.occupiedBlockType = BlockType.EMPTY;
		this.isGhostTile = false;
		refreshTile();
	}
	
	public void refreshTile() {
		repaint();
	}

	public boolean isOccupied() {
		return this.occupiedBlockType != BlockType.EMPTY;
	}

	@Override
	public void paintComponent(Graphics g) {
		if(isGhostTile)
			g.drawImage(blockImage.getGhostTileImage(occupiedBlockType), 0, 0, TILE_SIZE, TILE_SIZE, null);
		else
			g.drawImage(blockImage.getTileImage(occupiedBlockType), 0, 0, TILE_SIZE, TILE_SIZE, null);
	}
}
