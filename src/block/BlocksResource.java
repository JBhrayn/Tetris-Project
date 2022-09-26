package block;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
public class BlocksResource {

	private static BlocksResource instance = null;

	private BufferedImage[] tileImages = new BufferedImage[9];
	private BufferedImage[] blockImages = new BufferedImage[7];
	private BufferedImage[] ghostTilesImages = new BufferedImage[7];

	private BlocksResource() {
		this.loadImageAtlas("Tiles.png", tileImages);
		this.loadImageAtlas("Blocks.png", blockImages);
		this.loadImageAtlas("ghost_tiles.png", ghostTilesImages);
	}
	
	private void loadImageAtlas(String fileName, BufferedImage[] images) {
		BufferedImage imageAtlas = null;

		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName)){
			imageAtlas = ImageIO.read(is);
		}catch (Exception e) {
			e.printStackTrace();
		}

		initializeImages(imageAtlas, images);
	}
	
	private void initializeImages(BufferedImage atlas, BufferedImage[] images) {
		int imgWidth = atlas.getWidth() / images.length;
		int imgHeight = atlas.getHeight();
		
		for (int i = 0; i < images.length; i++) {
			images[i] = atlas.getSubimage(i * imgWidth, 0, imgWidth, imgHeight);
		}
	}

	public static BlocksResource getInstance() {
		if (instance == null)
			instance = new BlocksResource();

		return instance;
	}

	public BufferedImage getTileImage(BlockType blockType) {
		return tileImages[blockType.ordinal()];
	}
	
	public BufferedImage getBlockImage(BlockType blockType) {
		return blockImages[blockType.ordinal()];
	} 
	
	public BufferedImage getGhostTileImage(BlockType blockType) {
		return ghostTilesImages[blockType.ordinal()];
	} 


}
