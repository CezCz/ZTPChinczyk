package ztp.chinczyk.view.interfaces;

import ztp.chinczyk.presenter.interfaces.Presenter;

public interface View<K extends Presenter> {
	
	
	public void registerPresenter(K p);

	public void show();
	
	public void hide();

}
