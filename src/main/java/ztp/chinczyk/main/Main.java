package ztp.chinczyk.main;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.presenter.MainPresenter;
import ztp.chinczyk.presenter.interfaces.Presenter;

public class Main {
	public static void main(String[] args) {
		
		 Presenter p = new MainPresenter(new ModelFacade());
		 p.run(null);
		
	}

}
