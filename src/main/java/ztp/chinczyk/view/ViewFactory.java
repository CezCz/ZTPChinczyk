package ztp.chinczyk.view;

import java.util.HashMap;
import java.util.Map;

import ztp.chinczyk.view.interfaces.View;

public abstract class ViewFactory {

	protected abstract View create();

	private static Map<String, ViewFactory> ViewMap = new HashMap<>();

	public static void addFactory(String id, ViewFactory factory) {
		ViewMap.put(id, factory);
	}

	public static final View getView(String id) {
		if (!ViewMap.containsKey(id)) {
			try {
				Class.forName("ztp.chinczyk.view." + id);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("No such view: " + id);
			}
			if (!ViewMap.containsKey(id)) {
				throw new RuntimeException("No such view: " + id);
			}
		}
		return ViewMap.get(id).create();
	}

}
