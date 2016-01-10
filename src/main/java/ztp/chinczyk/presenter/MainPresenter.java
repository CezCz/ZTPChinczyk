package ztp.chinczyk.presenter;

import java.awt.Container;

import javax.swing.JFrame;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.model.Settings;
import ztp.chinczyk.presenter.interfaces.Presenter;
import ztp.chinczyk.view.WelcomeView;
import ztp.chinczyk.view.ViewFactory;
import ztp.util.network.GameNetworkProvider;

public class MainPresenter implements Presenter {

	private ModelFacade modelFacade;
	private Settings settings;
	private GameNetworkProvider gameNetworkProvider;
	private final JFrame mainFrame = new JFrame();

	public MainPresenter(ModelFacade modelFacade) {
		this.modelFacade = modelFacade;
		this.gameNetworkProvider = GameNetworkProvider.getGameNetworkProvider();
	}

	public void run(Container c) {

		WelcomeView welcomeView = (WelcomeView) ViewFactory.getView("WelcomeView");

		WelcomePresenter WelcomePresenter = new WelcomePresenter(welcomeView, this);
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

	public ModelFacade getModelFacade() {
		return modelFacade;
	}

	public void setModelFacade(ModelFacade modelFacade) {
		this.modelFacade = modelFacade;
	}
}