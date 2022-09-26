package input_handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import block.BlockController;
import block.BlockDropper;
import grid.TileGridController;

public class KeyInputHandler implements KeyListener{

	private TileGridController tileGridController;
	private BlockController blockController;
	private BlockDropper blockDropper;
	
	String tickSound = "tick_sound.wav";
	String dropSound = "drop_sound.wav";
	String rotateSound = "rotate_sound.wav";
	
	public KeyInputHandler(TileGridController tileGridController, BlockController blockController, BlockDropper blockDropper) {
		this.tileGridController = tileGridController;
		this.blockController = blockController;
		this.blockDropper = blockDropper;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			blockController.moveBlock(0, -1, true);
			tileGridController.refreshGridTiles();
			blockDropper.playSound(tickSound);
			System.out.println("LEFT");
			break;
		case KeyEvent.VK_UP:
			blockController.rotateBlock();
			tileGridController.refreshGridTiles();
			blockDropper.playSound(rotateSound);
			System.out.println("UP");
			break;
		case KeyEvent.VK_DOWN:
			blockController.moveBlock(1, 0, false);
			tileGridController.refreshGridTiles();
			blockDropper.playSound(tickSound);
			break;

		case KeyEvent.VK_RIGHT:
			blockController.moveBlock(0, 1, true);
			tileGridController.refreshGridTiles();
			blockDropper.playSound(tickSound);
			System.out.println("RIGHT");
			break;
			
		case KeyEvent.VK_SPACE:
			blockDropper.hardDropped();       
			blockDropper.playSound(dropSound);
			System.out.println("SPACE");
			break;
			
		case KeyEvent.VK_C:
			blockDropper.holdBlock();
			tileGridController.refreshGridTiles();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
