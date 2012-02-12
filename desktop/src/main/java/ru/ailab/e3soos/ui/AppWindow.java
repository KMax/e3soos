package ru.ailab.e3soos.ui;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ru.ailab.e3soos.actions.EmptyAction;
import ru.ailab.e3soos.actions.ExitAction;
import ru.ailab.e3soos.actions.HelpAction;

public class AppWindow extends ApplicationWindow {

	private RequirementForm reqForm;
	private ClassificationForm classForm;
	
	private EmptyAction emptyAction = new EmptyAction(this);
	private ExitAction exitAction = new ExitAction(this);
	
	public AppWindow() {
		super(null);
		setBlockOnOpen(true);
		addMenuBar();
	}
	
	public RequirementForm getRequirementForm(){
		return reqForm;
	}
	
	public ClassificationForm getClassificationForm(){
		return classForm;
	}

	@Override
	protected Control createContents(Composite parent) {
		parent.setSize(new Point(723, 340));
		getShell().setText("Структурный синтез оптической системы");
		
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		composite.pack();
		
		reqForm = new RequirementForm(composite, SWT.SHADOW_OUT);
		reqForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		classForm = new ClassificationForm(composite, SWT.SHADOW_OUT);
		classForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		Composite buttons = createButtons(composite);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		return composite;
	}
	
	@Override
	protected MenuManager createMenuManager() {
		MenuManager root = new MenuManager();
		
		MenuManager main_menu = new MenuManager("Меню");
		root.add(main_menu);
		main_menu.add(emptyAction);
		main_menu.add(exitAction);
		
		MenuManager help_menu = new MenuManager("Помощь");
		root.add(help_menu);
		help_menu.add(new HelpAction());
		
		return root;
	}

	private Composite createButtons(Composite parent){
		Composite buttons = new Composite(parent, SWT.NONE);
		buttons.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		ActionContributionItem aci = new ActionContributionItem(emptyAction);
		aci.fill(buttons);
		
		return buttons;
	}
}
