package block;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class BlockQueuePanel extends JPanel{

	private String blockName;
	private BufferedImage blockImage;
	private Border blackline = BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(214,221,232), new Color(236,240, 243));	
	public BlockQueuePanel() {
		setPreferredSize(new Dimension(200,100));
		setBackground(Color.WHITE);
		setBorder(blackline);
	}
	
	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public BufferedImage getBlockImage() {
		return blockImage;
	}

	public void setBlockImage(BufferedImage blockImage) {
		this.blockImage = blockImage;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(blockImage, 0, 0, 200, 100, null);
	}
}
