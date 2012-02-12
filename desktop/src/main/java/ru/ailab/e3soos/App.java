package ru.ailab.e3soos;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;

import ru.ailab.e3soos.ui.AppWindow;

public class App {
	
	private static App instance;
	
	private ApplicationWindow window;
	
	private App(){
		window = new AppWindow();
	}
	
	public static App getInstance(){
		if(instance == null){
			instance = new App();
		}
		return instance;
	}
	
	public ApplicationWindow getWindow(){
		return window;
	}
	
    public static void main(String[] args) {
        App application = App.getInstance();
        
        application.getWindow().open();
        Display.getCurrent().dispose();
    }
}
