package ztp.chinczyk.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ztp.chinczyk.presenter.WelcomePresenter;
import ztp.chinczyk.view.interfaces.WelcomeViewInterface;
import ztp.chinczyk.view.interfaces.View;

public class WelcomeView extends JPanel implements WelcomeViewInterface {

	WelcomePresenter presenter;
	JButton startNewGame;
	JButton joinGame;
	JButton help;
	JButton settings;
	JButton exit;

	public WelcomeView() {
		this.setLayout(new GridLayout(5, 1));
		startNewGame = new JButton("New game");
		joinGame = new JButton("Join game");
		settings = new JButton("Settings");
		help = new JButton("Help");
		exit = new JButton("Exit");

		this.add(startNewGame);
		this.add(joinGame);
		this.add(settings);
		this.add(help);
		this.add(exit);
	}

	private static class Factory extends ViewFactory {
		protected View create() {
			return new WelcomeView();
		}
	}

	static {
		ViewFactory.addFactory("WelcomeView", new Factory());
	}

	@Override
	public void registerPresenter( WelcomePresenter p) {
		this.presenter = p;

		startNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.onStartNewGame();
			}
		});

		joinGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.onJoinGame();
			}
		});

		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.onHelp();
			}
		});

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.onSettings();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presenter.onExit();
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
}
