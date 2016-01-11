package ztp.chinczyk.presenter;

import java.awt.Container;

import javax.swing.JFrame;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.model.Settings;
import ztp.chinczyk.presenter.interfaces.Presenter;
import ztp.chinczyk.view.WelcomeView;
import ztp.chinczyk.view.ViewFactory;

public class MainPresenter implements Presenter {

	private ModelFacade modelFacade;
	private Settings settings;
	private final JFrame mainFrame = new JFrame();

	public MainPresenter(ModelFacade modelFacade) {
		this.modelFacade = modelFacade;
	}

	public void run(Container c) {

		WelcomeView welcomeView = (WelcomeView) ViewFactory.getView("WelcomeView");

		WelcomePresenter WelcomePresenter = new WelcomePresenter(welcomeView, modelFacade, this);
		welcomeView.registerPresenter(WelcomePresenter);
		WelcomePresenter.run(mainFrame);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();

	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		System.out.println(settings);
	}
}