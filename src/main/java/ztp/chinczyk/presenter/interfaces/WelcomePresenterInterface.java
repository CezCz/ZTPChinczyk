package ztp.chinczyk.presenter.interfaces;

import ztp.chinczyk.model.Settings;

public interface WelcomePresenterInterface extends Presenter {
	
	void onStartNewGame();
	
	void onJoinGame();

	void onSettings();
	
	void onExit();
	
	void onHelp();

	void onJoinClose();

	void onSettingsClose(Settings settings);

	void onHelpClose();

}
