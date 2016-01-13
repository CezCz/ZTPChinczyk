package ztp.chinczyk.view.interfaces;

import ztp.chinczyk.presenter.interfaces.Presenter;

public interface View<K extends Presenter> {
	
	
	void registerPresenter(K p);

	void show();
	
	void hide();

}
