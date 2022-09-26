package block;

import java.io.BufferedInputStream;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import grid.TileGridController;

public class BlockDropper extends Thread{
	
	private BlockQueuePanel heldBlockPanel;
	private BlockQueuePanel nextBlockPanel;
	
	private BlockController blockController;
	private TileGridController tileGridController;
	
	private BlockType heldBlockType;
	private BlockType nextBlockType;
	
	private BlocksResource blocksResource;
	private boolean isBlockHeld;
	private int delay;
	
	public BlockDropper(TileGridController tileGridController, BlockController blockController) {
		this.blocksResource = BlocksResource.getInstance();
		this.tileGridController = tileGridController;
		this.blockController = blockController;
		
		this.heldBlockType = BlockType.EMPTY;
		this.isBlockHeld = false;
		this.delay = 400;
		
		initializeNextBlock();
	}
	
	public void setHeldBlockPanel(BlockQueuePanel heldBlockPanel) {
		this.heldBlockPanel = heldBlockPanel;
	}

	public void setNextBlockPanel(BlockQueuePanel nextBlockPanel) {
		this.nextBlockPanel = nextBlockPanel;
	}
	
	public static Block generateRandomBlock() {
		int num = new Random().nextInt(BlockType.values().length-3);
		return new Block(BlockType.values()[num]);
	}
	
	private void initializeNextBlock() {
		nextBlockType = generateRandomBlock().getBlockType();
	}
	
	private Block getNextBlock() {
		return new Block(nextBlockType);
	}
	
	@Override
	public void run() {
		nextBlockPanel.setBlockImage(blocksResource.getBlockImage(nextBlockType));
		
		while(true) {
			delay(delay);
			blockController.moveBlock(1, 0, false);
			tileGridController.refreshGridTiles();
			
			Block currentBlock = blockController.getCurrentBlock();
					
			if(!currentBlock.hasDropped() && currentBlock.hasAnyCollisionBelow()) {
				delay(400);
			}
			
			if(currentBlock.hasAnyCollisionBelow()) {
				currentBlock.setHasDropped(true);
				
				tileGridController.clearCompleteRow(BlockDropper.this);
				
				blockController.setCurrentBlock(getNextBlock());
				blockController.initializeBlock();
				blockController.getCurrentBlock().renderGhostBlock();

				initializeNextBlock();
				nextBlockPanel.setBlockImage(blocksResource.getBlockImage(nextBlockType));
				
				isBlockHeld = false;
				delay = 400;
			}
		}
		
	}
	
	public void delay(int n) {
		try {
			sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void hardDropped() {
		delay = 0;
		blockController.getCurrentBlock().setHasDropped(true);
	}
	
	public void holdBlock() {
		if(isBlockHeld) {
			playSound("holdLocked_sound.wav");
			return;
		}
		
		Block currentBlock = blockController.getCurrentBlock();
		
		if(heldBlockType == BlockType.EMPTY) {
			heldBlockType = currentBlock.getBlockType();
			currentBlock.clearBlock();
			currentBlock.destroyGhostBlock();
			blockController.setCurrentBlock(new Block(nextBlockType));
			initializeNextBlock();
		}else {
			Block tempBlock = new Block(heldBlockType);
			heldBlockType = currentBlock.getBlockType();
			currentBlock.clearBlock();
			currentBlock.destroyGhostBlock();
			blockController.setCurrentBlock(tempBlock);
		}
		
		blockController.initializeBlock();
		currentBlock = blockController.getCurrentBlock();
		isBlockHeld = true;
		
		nextBlockPanel.setBlockImage(blocksResource.getBlockImage(nextBlockType));
		heldBlockPanel.setBlockImage(blocksResource.getBlockImage(heldBlockType));
	}
	
	
	public void playSound(String fileName) {
		try(AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(this.getClass().getResourceAsStream("/"+ fileName)))){
	        Clip clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        clip.start(); 
	        clip.drain();
		}catch (Exception e) {
	        System.err.println(e.getMessage());
		}
	}
}
