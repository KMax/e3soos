package ru.ailab.e3soos.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

public class ExitAction extends Action {
	
	private ApplicationWindow window;
	
	public ExitAction(ApplicationWindow window) {
		super("Выход");
		
		this.window = window;
	}
	
	@Override
	public void run() {
		window.close();
	}
}
