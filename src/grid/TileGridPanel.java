package grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TileGridPanel extends JPanel {

	ArrayList<ArrayList<Tile>> gridTiles;

	public TileGridPanel() {
		this.setPreferredSize(
				new Dimension(TileGrid.GRID_COLUMNS * Tile.TILE_SIZE, TileGrid.GRID_ROWS * Tile.TILE_SIZE));
		this.setLayout(new GridLayout(TileGrid.GRID_ROWS, TileGrid.GRID_COLUMNS));
		this.gridTiles = new ArrayList<>();
		this.setBackground(new Color(43, 43, 43));
	}
	
	public void setTiles(ArrayList<ArrayList<Tile>> gridTiles) {
		this.gridTiles = gridTiles;
	}

	public void loadTiles() {
		for (ArrayList<Tile> row : gridTiles)
			for (Tile tile : row) {
				this.add(tile);
				tile.refreshTile();
			}
	}
}
