package ztp.chinczyk.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class PictureManager {
	private BufferedImage board = null;
	private BufferedImage pawnsDiceSprite = null;
	private static PictureManager singletonInstance = null;
	private HashMap<Integer, BufferedImage> imageMap = null;
	private HashMap<PawnColor, BufferedImage> pawnMap = null;

	private PictureManager() {
		try {
			board = ImageIO.read(new File("plansza.jpg"));
			pawnsDiceSprite = ImageIO.read(new File("pionki.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PictureManager getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new PictureManager();
		}
		return singletonInstance;
	}

	public BufferedImage getBoard() {
		return board;
	}

	public BufferedImage getDice(int number) {
		if (imageMap == null) {
			imageMap = new HashMap<>();
			imageMap.put(1, pawnsDiceSprite.getSubimage(50, 1, 49, 49));
			imageMap.put(2, pawnsDiceSprite.getSubimage(50, 50, 49, 49));
			imageMap.put(3, pawnsDiceSprite.getSubimage(50, 100, 49, 49));
			imageMap.put(4, pawnsDiceSprite.getSubimage(50, 150, 49, 49));
			imageMap.put(5, pawnsDiceSprite.getSubimage(50, 200, 49, 49));
			imageMap.put(6, pawnsDiceSprite.getSubimage(50, 250, 49, 49));
		}
		return imageMap.get(number);
	}

	public BufferedImage getPawn(PawnColor p) {
		if (pawnMap == null) {
			pawnMap = new HashMap<>();
			pawnMap.put(PawnColor.BLUE, pawnsDiceSprite.getSubimage(1, 1, 49, 49));
			pawnMap.put(PawnColor.RED, pawnsDiceSprite.getSubimage(1, 50, 49, 49));
			pawnMap.put(PawnColor.GREEN, pawnsDiceSprite.getSubimage(1, 100, 49, 49));
			pawnMap.put(PawnColor.YELLOW, pawnsDiceSprite.getSubimage(1, 150, 49, 49));
		}
		return pawnMap.get(p);
	}

}