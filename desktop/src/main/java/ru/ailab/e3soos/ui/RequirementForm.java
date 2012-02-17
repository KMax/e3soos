package ru.ailab.e3soos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import models.Requirements;

public class RequirementForm extends Group {

	private Spinner spinner_0; //Светосила
	private Spinner spinner_1; //Угол поля зрения
	private Spinner spinner_2; //Фокусное расстояние
	private Spinner spinner_3; //Видимый хроматический диапазон
	private Spinner spinner_4; //Задний фокальный отрезок
	private Combo combo_0; //Качество изображения
	private Combo combo_1; //Положение входного зрачка
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RequirementForm(Composite parent, int style) {
		super(parent, style);
		
		setText("Технические требования");
		setLayout(new GridLayout(2, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Светосила");
		
		spinner_0 = new Spinner(this, SWT.BORDER);
		spinner_0.setMaximum(1000);
		spinner_0.setDigits(2);
		spinner_0.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("Угол поля зрения");
		
		spinner_1 = new Spinner(this, SWT.BORDER);
		spinner_1.setMaximum(360);
		spinner_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setText("Фокусное расстояние");
		
		spinner_2 = new Spinner(this, SWT.BORDER);
		spinner_2.setMaximum(10000);
		spinner_2.setDigits(2);
		spinner_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("Видимый хроматический диапазон");
		
		spinner_3 = new Spinner(this, SWT.BORDER);
		spinner_3.setMaximum(100000);
		spinner_3.setDigits(2);
		spinner_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("Качество изображения");
		
		combo_0 = new Combo(this, SWT.NONE);
		combo_0.setItems(new String[] {"", "", ""});
		combo_0.setItem(Requirements.ImageQuality.GEOMETRICALLY_LIMITED, "Геометрически-ограниченное");
		combo_0.setItem(Requirements.ImageQuality.DIFFRACTION, "Дифракционное");
		combo_0.setItem(Requirements.ImageQuality.INTERSTITIAL, "Промежуточное");
		combo_0.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setText("Задний фокальный отрезок");
		
		spinner_4 = new Spinner(this, SWT.BORDER);
		spinner_4.setMaximum(1000);
		spinner_4.setDigits(2);
		spinner_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setText("Положение входного зрачка");
		
		combo_1 = new Combo(this, SWT.NONE);
		combo_1.setItems(new String[]{"","",""});
		combo_1.setItem(Requirements.EntrancePupilPosition.INSIDE, "Внутри системы");
		combo_1.setItem(Requirements.EntrancePupilPosition.BACKWARD, "Вынесен назад");
		combo_1.setItem(Requirements.EntrancePupilPosition.FORWARD, "Вынесен вперед");
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public Requirements getRequirements(){
		Requirements req = new Requirements();
		req.setAperture((float) (spinner_0.getSelection() / 
				Math.pow(10, spinner_0.getDigits())));
		req.setArc((float) (spinner_1.getSelection() / 
				Math.pow(10, spinner_1.getDigits())));
		req.setFocalDistance((float) (spinner_2.getSelection() / 
				Math.pow(10, spinner_2.getDigits())));
		req.setChromaticInterval((float) (spinner_3.getSelection() / 
				Math.pow(10, spinner_3.getDigits())));
		req.setBackFocalSegment((float) (spinner_4.getSelection() / 
				Math.pow(10, spinner_4.getDigits())));
		req.setImageQuality(combo_0.getSelectionIndex());
		req.setPupilPosition(combo_1.getSelectionIndex());
		return req;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
