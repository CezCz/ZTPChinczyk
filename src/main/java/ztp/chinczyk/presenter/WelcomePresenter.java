package ztp.chinczyk.presenter;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.model.Settings;
import ztp.chinczyk.presenter.interfaces.Presenter;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.*;
import ztp.util.network.GameNetworkProvider;

public class WelcomePresenter implements WelcomePresenterInterface {

	MainPresenter parent;
	WelcomeView loginView;

	JoinView joinView;
	JoinPresenter joinPresenter;
	JFrame joinFrame;

	SettingsView settingsView;
	SettingsPresenter settingsPresenter;
	JFrame settingsFrame;

	HelpView helpView;
	HelpPresenter helpPresenter;
	JFrame helpFrame;

	public WelcomePresenter(WelcomeView loginView, MainPresenter parent) {
		this.loginView = loginView;
		this.parent = parent;
	}

	@Override
	public void run(Container c) {
		c.add(loginView);
		
	}

	@Override
	public void onStartNewGame() {
		
		GameView gameView = (GameView) ViewFactory.getView("GameView");
		GamePresenter gamePresenter = new GamePresenter(gameView, parent.getModelFacade(), parent.getGameNetworkProvider(),parent.getSettings() ,true);
		gameView.registerPresenter(gamePresenter);

		JFrame gameFrame = new JFrame();
		gameFrame.setSize(900, 600);
		gameFrame.setResizable(false);
		gamePresenter.run(gameFrame);
		gameFrame.setVisible(true);
		gamePresenter.beforeStart();
		
	}

	@Override
	public void onJoinGame() {
		if(joinFrame == null){
			joinView = (JoinView) ViewFactory.getView("JoinView");
			joinPresenter = new JoinPresenter(joinView,this);
			joinFrame = new JFrame();
			joinPresenter.run(joinFrame);
			joinFrame.setVisible(true);
			joinFrame.pack();
		}
		else if(joinFrame.isVisible()){
			joinFrame.setVisible(false);
		}
		else {
			joinFrame.setVisible(true);
		}
	}

	@Override
	public void onSettings() {
		if(settingsFrame == null){
			settingsView = (SettingsView) ViewFactory.getView("SettingsView");
			settingsPresenter = new SettingsPresenter(settingsView, this, parent.getSettings());
			settingsFrame = new JFrame();
			settingsPresenter.run(settingsFrame);
			settingsFrame.setVisible(true);
			settingsFrame.pack();
		}
		else if(settingsFrame.isVisible()){
			settingsFrame.setVisible(false);
		}
		else{
			settingsFrame.setVisible(true);
		}
	}

	@Override
	public void onHelp() {
		if(helpFrame == null){
			helpView = (HelpView) ViewFactory.getView("HelpView");
			helpPresenter = new HelpPresenter(helpView, this);
			helpFrame = new JFrame();
			helpPresenter.run(helpFrame);
			helpFrame.setVisible(true);
			helpFrame.pack();
		}
		else if(helpFrame.isVisible()){
			helpFrame.setVisible(false);
		}
		else {
			helpFrame.setVisible(true);
		}
	}

	@Override
	public void onJoinClose() {
		joinFrame.dispose();
		joinFrame = null;
		joinView = null;
		joinPresenter = null;
	}

	@Override
	public void onSettingsClose(Settings settings) {
		if(settings != null){
			parent.setSettings(settings);
		}
		settingsFrame.dispose();
		settingsFrame = null;
		settingsView = null;
		settingsPresenter = null;
	}

	@Override
	public void onHelpClose() {
		helpFrame.dispose();
		helpFrame = null;
		helpView = null;
		helpPresenter = null;
	}

	@Override
	public void onExit() {
		System.exit(1);
	}

	public ModelFacade getModelFacade(){
		return parent.getModelFacade();
	}

	public Settings getSettings(){
		return parent.getSettings();
	}

	public GameNetworkProvider getGameNetworkProvider(){
		return parent.getGameNetworkProvider();
	}
}
