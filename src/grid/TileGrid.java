package grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import block.Block;

public class TileGrid {

	public static final int GRID_ROWS = 20;
	public static final int GRID_COLUMNS = 10;

	private ArrayList<ArrayList<Tile>> gridTiles;
	private BufferedImage backgroundImg;
	private String imgFileName;

	private Block currentBlock;

	public TileGrid() {
		gridTiles = new ArrayList<>();
	}

	public void createTileGrid() {
		ArrayList<Tile> row;
		for (int i = 0; i < GRID_ROWS; i++) {

			row = new ArrayList<>();

			for (int j = 0; j < GRID_COLUMNS; j++) {
				Tile tile = new Tile(i, j);
				tile.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent event) {
						System.out.println(tile.getxPos() + "," + tile.getyPos() + "=" + tile.getOccupiedBlockType() + " : " + tile.isGhostTile());
					}
				});
				row.add(tile);
			}
			gridTiles.add(row);
		}
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public BufferedImage getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(BufferedImage backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public ArrayList<ArrayList<Tile>> getGridTiles() {
		return gridTiles;
	}

	public void setGridTiles(ArrayList<ArrayList<Tile>> gridTiles) {
		this.gridTiles = gridTiles;
	}

	public Block getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(Block currentBlock) {
		this.currentBlock = currentBlock;
	}

	public static void main(String[] args) {
		JPanel panel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(Tile.TILE_SIZE * GRID_COLUMNS, Tile.TILE_SIZE * GRID_ROWS);
			}
		};
		panel.setBackground(new Color(0, 0, 0, 0));
		TileGrid grid = new TileGrid();
		grid.createTileGrid();

		for (ArrayList<Tile> row : grid.getGridTiles()) {
			for (Tile tile : row) {
				panel.add(tile);
				System.out.println(tile.getxPos() + " " + tile.getyPos());
			}
		}
		JFrame frame = new JFrame();
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setVisible(true);
	}

}
