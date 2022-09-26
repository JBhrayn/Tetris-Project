package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import block.BlockController;
import block.BlockDropper;
import block.BlockQueuePanel;
import grid.TileGrid;
import grid.TileGridController;
import grid.TileGridPanel;
import input_handlers.KeyInputHandler;

public class TileTest extends JFrame{

	TileGrid tileGridModel;
	TileGridPanel tileGridPanel;
	TileGridController tileGridController;
	
	BlockController blockController;	
	BlockDropper blockDropper; 
	
	String tickSound = "tick_sound.wav";
	String dropSound = "drop_sound.wav";
	String rotateSound = "rotate_sound.wav";
	
	JLabel nextBlockLbl;
	JLabel heldBlockLbl;
	
	BlockQueuePanel heldBlockPanel;
	BlockQueuePanel nextBlockPanel;
	

	
	public TileTest() {
		tileGridModel = new TileGrid();
		tileGridPanel = new TileGridPanel();

		tileGridController = new TileGridController(tileGridModel, tileGridPanel);
		tileGridController.initializeTileGrid();

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200, 20));
		leftPanel.setBackground(new Color(236,240,243));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 20));
		rightPanel.setBackground(new Color(236,240,243));
		
		heldBlockLbl = new JLabel("HELD BLOCK");
		heldBlockLbl.setForeground(Color.BLACK);
		leftPanel.add(heldBlockLbl);
		 
		heldBlockPanel = new BlockQueuePanel();
		leftPanel.add(heldBlockPanel);
		
		nextBlockLbl = new JLabel("NEXT BLOCK");
		nextBlockLbl.setForeground(Color.BLACK);
		rightPanel.add(nextBlockLbl);
		
		nextBlockPanel = new BlockQueuePanel();
		rightPanel.add(nextBlockPanel);
		
		
		add(leftPanel, BorderLayout.WEST);
		add(tileGridPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

		blockController = new BlockController(BlockDropper.generateRandomBlock(), tileGridModel.getGridTiles());
		blockController.initializeBlock();
		
		blockDropper = new BlockDropper(tileGridController, blockController);
		blockDropper.setHeldBlockPanel(heldBlockPanel);
		blockDropper.setNextBlockPanel(nextBlockPanel);
		
		KeyInputHandler keyInputHandler = new KeyInputHandler(tileGridController, blockController, blockDropper);
		addKeyListener(keyInputHandler);
		
		blockDropper.start();
		
		pack();

	    setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
	    setLocationRelativeTo(null);
	    
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			TileTest frame = new TileTest();
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);
		});
	}
}
