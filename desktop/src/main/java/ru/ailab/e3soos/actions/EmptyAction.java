package ru.ailab.e3soos.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;

import ru.ailab.e3soos.RuleRunner;
import ru.ailab.e3soos.model.Classification;
import models.Requirements;
import ru.ailab.e3soos.ui.AppWindow;

public class EmptyAction extends Action {
	
	private AppWindow window;
	private RuleRunner runner;
	
	public EmptyAction(AppWindow window) {
		super("Классифицировать", IAction.AS_PUSH_BUTTON);
		
		this.window = window;
		
		setAccelerator(SWT.CTRL + 'K');
		
		runner = new RuleRunner();
	}
	
	public void run(){
		Requirements reqs = window.getRequirementForm().getRequirements();
		Classification c = runner.classify(reqs);
		window.getClassificationForm().setData(c);
	}
}
