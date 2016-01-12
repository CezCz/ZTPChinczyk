package ztp.chinczyk.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ztp.chinczyk.presenter.GamePresenter;
import ztp.chinczyk.presenter.PawnView;
import ztp.chinczyk.view.interfaces.GameViewInterface;
import ztp.chinczyk.view.interfaces.View;

public class GameView extends JPanel implements GameViewInterface {

	GamePresenter gamePresenter;

	private JLayeredPane gameField;
	private JLabel dice;
	private ArrayList<JLabel> playersLabels;
	private JLabel board;
	private JPanel infoField;
	private JButton readyToPlay;
	private PictureManager picasso = PictureManager.getInstance();
	private JButton passButton;
	private JLabel currentPlayerLabel;

	public GameView() {

		gameField = new JLayeredPane();
		board = new JLabel(new ImageIcon(picasso.getBoard()), new Integer(0));
		gameField.add(board);

		gameField.setLayout(null);
		gameField.setPreferredSize(new Dimension(550, 550));
		board.setLocation(0, 0);
		board.setSize(550, 550);

		infoField = new JPanel();
		infoField.setPreferredSize(new Dimension(300, 550));
		
		currentPlayerLabel = new JLabel();
		
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setPreferredSize(new Dimension(300, 50));
		currentPlayerPanel.add(currentPlayerLabel);
		
		readyToPlay = new JButton("Start");
		readyToPlay.setPreferredSize(new Dimension(150, 50));
		passButton = new JButton("Pass");
		passButton.setPreferredSize(new Dimension(150, 50));
		passButton.setVisible(false);
		
		JPanel navPanel = new JPanel();
		navPanel.setLayout(new GridLayout(1, 2));
		navPanel.add(readyToPlay);
		navPanel.add(passButton);
		
		infoField.add(currentPlayerPanel);
		infoField.add(navPanel);
		
		this.add(gameField);
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

		passButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.doPass();
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

	public void drawPawn(PawnView pawn) {
		BufferedImage bi = picasso.getPawn(pawn.getColor());
		JLabel pawnLabel = new JLabel();
		gameField.add(pawnLabel, new Integer(1));

		pawnLabel.setIcon(new ImageIcon(bi));
		pawnLabel.setSize(new Dimension(50, 50));
		pawnLabel.setLocation(pawn.getPosition());
		pawnLabel.addMouseListener(new MouseListener() {

			boolean pressed = false;

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (pressed == false) {
					pressed = true;
					gamePresenter.doMove(pawn);
				}
			}
		});
		gameField.repaint();
	}

	public void drawDice(int number) {
		BufferedImage bi = picasso.getDice(number);
		JLabel dice = new JLabel();
		gameField.add(dice, new Integer(1));

		dice.setIcon(new ImageIcon(bi));
		dice.setSize(new Dimension(50, 50));
		dice.setLocation(250, 250);

		gameField.repaint();
	}

	public void clearBoard() {

		gameField.removeAll();
		gameField.add(board, 0);
		repaint();

	}

	public void showPassButton() {
		passButton.setVisible(true);
		validate();
		repaint();
	}

	public void hidePassButton() {
		passButton.setVisible(false);
		validate();
		repaint();
	}

	public void setCurrentPlayer(String string) {

		currentPlayerLabel.setText(string);
		repaint();

	}

	public void hideStartButton() {
		readyToPlay.setVisible(false);
		repaint();
	}

	public void showStartButton() {
		readyToPlay.setVisible(true);
		repaint();
	}

	public void drawWinnerPrompt(String player) {

		JOptionPane.showMessageDialog(this,
			    "Wygra³ gracz " + player);
		
	}

}