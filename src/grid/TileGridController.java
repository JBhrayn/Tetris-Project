package grid;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

import block.BlockDropper;
import block.BlockType;

public class TileGridController {
	private TileGrid tileGridModel;
	private TileGridPanel tileGridPanel;
	private String lineClearSound = "lineClear_sound.wav";
	
	public TileGridController(TileGrid tileGridModel, TileGridPanel tileGridPanel) {
		this.tileGridModel = tileGridModel;
		this.tileGridPanel = tileGridPanel;
	}

	public void initializeTileGrid() {
		tileGridModel.createTileGrid();
		tileGridPanel.setTiles(tileGridModel.getGridTiles());
		tileGridPanel.loadTiles();
	}
	
	public ArrayList<ArrayList<Tile>> getGridTiles() {
		return tileGridModel.getGridTiles();
	}
	
	public void clearCompleteRow(BlockDropper blockDropper) {
		ArrayList<ArrayList<Tile>> completeRows = new ArrayList<>(getGridTiles().stream()
		        																.filter(row -> row.stream().allMatch(tile -> tile.getOccupiedBlockType() != BlockType.EMPTY))
		        																.collect(Collectors.toList()));

		if(completeRows.isEmpty()) return;
		
		for(ArrayList<Tile> rows : completeRows)
			destroyTileRow(rows);
		
		blockDropper.playSound(lineClearSound);
		blockDropper.delay(50);
		
		for(ArrayList<Tile> rows : completeRows)
			clearTileRow(rows);
		
		blockDropper.delay(50);
		
		for(ArrayList<Tile> rows : completeRows)
			adjustTileGrid(rows);
	}
		
	private void destroyTileRow(ArrayList<Tile> tileRow) {
		for (Tile tile : tileRow)
			tile.destroyTile();
	}
	
	private void clearTileRow(ArrayList<Tile> tileRow) {
		for (Tile tile : tileRow)
			tile.clearTile();
	}

	public void adjustTileGrid(ArrayList<Tile> tileRow) {
		int clearedRowIndex = tileRow.get(0).getxPos(); // GET ROW OF CLEARED ROW

		ArrayList<Tile> previousTileRow;
		ArrayList<Tile> currentTileRow;

		Tile previousTile;
		Tile currentTile;

		ArrayList<ArrayList<Tile>> tileGrid = tileGridModel.getGridTiles();

		System.out.println(clearedRowIndex);

		for (int x = clearedRowIndex; x > 0; x--) {
			previousTileRow = tileGrid.get(x - 1);
			currentTileRow = tileGrid.get(x);

			for (int y = 0; y < TileGrid.GRID_COLUMNS; y++) {
				previousTile = previousTileRow.get(y);
				currentTile = currentTileRow.get(y);

				currentTile.setOccupiedBlockType(previousTile.getOccupiedBlockType());
			}
		}
		System.out.println("Adjusted");
	}
	
	public void refreshGridTiles() {
		tileGridPanel.repaint();
	}

}
