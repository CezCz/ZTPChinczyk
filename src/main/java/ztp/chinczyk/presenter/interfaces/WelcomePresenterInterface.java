package ztp.chinczyk.presenter.interfaces;

import ztp.chinczyk.model.Settings;

public interface WelcomePresenterInterface extends Presenter {
	
	public void onStartNewGame();
	
	public void onJoinGame();

	public void onSettings();
	
	public void onExit();
	
	public void onHelp();

	public void onJoinClose();

	public void onSettingsClose(Settings settings);

}
