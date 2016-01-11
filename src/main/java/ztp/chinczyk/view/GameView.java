package ztp.chinczyk.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ztp.chinczyk.model.pawn.IPawn;
import ztp.chinczyk.presenter.GamePresenter;
import ztp.chinczyk.presenter.PawnView;
import ztp.chinczyk.view.interfaces.GameViewInterface;
import ztp.chinczyk.view.interfaces.View;

public class GameView extends JPanel implements GameViewInterface {

	GamePresenter gamePresenter;

	private JPanel gameField;
	private ArrayList<JLabel> playersLabels;
	private JLabel board;
	private JPanel infoField;
	private JButton readyToPlay;
	private PictureManager picasso = PictureManager.getInstance();

	public GameView() {
		gameField = new JPanel();
		board = new JLabel(new ImageIcon(picasso.getBoard()));
		gameField.add(board);

		infoField = new JPanel();
		readyToPlay = new JButton("Start");

		this.add(gameField);

		infoField.add(readyToPlay);

		this.add(infoField);

	}

	private static class Factory extends ViewFactory {
		protected View create() {
			return new GameView();
		}
	}

	static {
		ViewFactory.addFactory("GameView", new Factory());
	}

	@Override
	public void registerPresenter(GamePresenter p) {
		gamePresenter = p;

		readyToPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p.onStartGame();
			}
		});

	}

	@Override
	public void show() {
		super.setVisible(true);
	}

	@Override
	public void hide() {
		super.setVisible(false);
	}

	public void addPlayer(String playerName) {
		if (playersLabels == null) {
			playersLabels = new ArrayList<>();
		}

		JLabel tmp = new JLabel(playerName);
		playersLabels.add(tmp);
		infoField.add(tmp);

		revalidate();
		repaint();
	}

	public void removePlayer(String playerName) {
		JLabel labelToRemove = null;

		for (JLabel l : playersLabels) {
			if (l.getText().equals(playerName))
				labelToRemove = l;
		}

		infoField.remove(labelToRemove);
		playersLabels.remove(playersLabels.indexOf(labelToRemove));

		revalidate();
		repaint();
	}
	
	public void drawPawn(IPawn<Point> pawn) {
		BufferedImage bi = picasso.getPawn(((PawnView) pawn).getColor());
		JLabel pawnLabel = new JLabel();
		pawnLabel.setIcon(new ImageIcon(bi));

	}


}

class PictureManager {
	private BufferedImage board = null;
	private BufferedImage pawnsDiceSprite = null;
	private static PictureManager singletonInstance = null;
	private HashMap<Integer, BufferedImage> imageMap = null;
	private HashMap<PawnColor, BufferedImage> pawnMap = null;

	private PictureManager() {
		try {
			board = ImageIO.read(new File("plansza.jpg"));
			pawnsDiceSprite = ImageIO.read(new File("pionki.jpg"));
		} catch (IOException e) {

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
			pawnMap.put(PawnColor.YELLOW, pawnsDiceSprite.getSubimage(1, 100, 49, 49));
			pawnMap.put(PawnColor.GREEN, pawnsDiceSprite.getSubimage(1, 150, 49, 49));
		}
		return pawnMap.get(p);
	}

}