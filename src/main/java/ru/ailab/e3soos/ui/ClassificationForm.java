package ru.ailab.e3soos.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import ru.ailab.e3soos.model.Classification;

public class ClassificationForm extends Group {

	private Label label_J;
	private Label label_W;
	private Label label_F;
	private Label label_L;
	private Label label_Q;
	private Label label_S;
	private Label label_D;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ClassificationForm(Composite parent, int style) {
		super(parent, style);
		
		setText("Классификация ОС");
		setLayout(new GridLayout(2, false));
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		label_1.setText("по оптическим характеристикам");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("J");
		
		label_J = new Label(this, SWT.NONE);
		label_J.setText("0");
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setText("W");
		
		label_W = new Label(this, SWT.NONE);
		label_W.setText("0");
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setText("F");
		
		label_F = new Label(this, SWT.NONE);
		label_F.setText("0");
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label_5.setText("по назначению");
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setText("L");
		
		label_L = new Label(this, SWT.NONE);
		label_L.setText("0");
		
		Label label_7 = new Label(this, SWT.NONE);
		label_7.setText("Q");
		
		label_Q = new Label(this, SWT.NONE);
		label_Q.setText("0");
		
		Label label_8 = new Label(this, SWT.NONE);
		label_8.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label_8.setText("по конструктивным особенностям");
		
		Label label_9 = new Label(this, SWT.NONE);
		label_9.setText("S");
		
		label_S = new Label(this, SWT.NONE);
		label_S.setText("0");
		
		Label label_10 = new Label(this, SWT.NONE);
		label_10.setText("D");
		
		label_D = new Label(this, SWT.NONE);
		label_D.setText("0");
	}

	public void setData(Classification c){
		label_J.setText(String.valueOf(c.getJ()));
		label_W.setText(String.valueOf(c.getW()));
		label_F.setText(String.valueOf(c.getF()));
		label_L.setText(String.valueOf(c.getL()));
		label_Q.setText(String.valueOf(c.getQ()));
		label_S.setText(String.valueOf(c.getS()));
		label_D.setText(String.valueOf(c.getD()));
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
