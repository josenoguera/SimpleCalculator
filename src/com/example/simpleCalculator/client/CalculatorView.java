package com.example.simpleCalculator.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;

public class CalculatorView {
	
	private final String kSIZE = "40";
	
	private TextField mScreen;
	private Calculator mCalculator = new Calculator();
	private boolean mClearScreen = true;
	private boolean mClickNumber = false;
	private boolean mCanOperate = false;
	
	public Widget CreateView() {

		VerticalPanel vertical = new VerticalPanel();
		
		mScreen = new TextField();
		mScreen.setEnabled(false);
		mScreen.setSize("200", kSIZE);
		mScreen.setValue("0");
		vertical.add(mScreen);
				
		HorizontalPanel horizontal = new HorizontalPanel();
		horizontal.add(new CustomButton("7", mSelectHandler));
		horizontal.add(new CustomButton("8", mSelectHandler));
		horizontal.add(new CustomButton("9", mSelectHandler));
		horizontal.add(new CustomButton("+/-", mSelectHandler));
		horizontal.add(new CustomButton("%", mSelectHandler));
		vertical.add(horizontal);
		
		
		horizontal = new HorizontalPanel();
		horizontal.add(new CustomButton("4", mSelectHandler));
		horizontal.add(new CustomButton("5", mSelectHandler));
		horizontal.add(new CustomButton("6", mSelectHandler));
		horizontal.add(new CustomButton("+", mSelectHandler));
		horizontal.add(new CustomButton("-", mSelectHandler));
		vertical.add(horizontal);
		
		horizontal = new HorizontalPanel();
		horizontal.add(new CustomButton("1", mSelectHandler));
		horizontal.add(new CustomButton("2", mSelectHandler));
		horizontal.add(new CustomButton("3", mSelectHandler));
		horizontal.add(new CustomButton("*", mSelectHandler));
		horizontal.add(new CustomButton("/", mSelectHandler));
		vertical.add(horizontal);
		
		horizontal = new HorizontalPanel();
		horizontal.add(new CustomButton("0", mSelectHandler));
		horizontal.add(new CustomButton(".", mSelectHandler));
		horizontal.add(new CustomButton("=", mSelectHandler));
		horizontal.add(new CustomButton("C", mSelectHandler));
		horizontal.add(new CustomButton("CE", mSelectHandler));
		vertical.add(horizontal);
		
		FramedPanel frame = new FramedPanel();
		frame.setButtonAlign(BoxLayoutPack.CENTER); // Center
		frame.setHeadingText("Simple Calculator");
		frame.setPixelSize(220, 250);
		frame.addStyleName("white-bg");
		frame.add(new HTML());
		frame.getElement().setMargins(new Margins(5));
		frame.setWidget(vertical);
		
		CenterLayoutContainer container = new CenterLayoutContainer();
		container.add(frame);
		
		return container;
	}
	
	private SelectHandler mSelectHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			String buttonText = ((TextButton)event.getSource()).getText();
			
			boolean isNumber = true;
			try {
				Double.valueOf(buttonText);
			}
			catch(NumberFormatException e) {
				isNumber = false;
			}
			
			String displayText = mScreen.getValue();

			if(isNumber) {
				if(mClearScreen) {
					mScreen.setValue(buttonText);
				}
				else {
					mScreen.setValue(displayText + buttonText);
				}
				mClearScreen = false;
				mClickNumber = true;
				mCanOperate = true;
			}
			else if(buttonText.equals(".")) {
				mScreen.setValue(displayText + buttonText);
				mClearScreen = false;
				mCanOperate = true;
			}
			else {
				double result = 0;
				mClearScreen = true;
				
				double displayValue = 0;
				try {
					displayValue = Double.valueOf(displayText);
				}
				catch(NumberFormatException e) {
				}
				
				if(buttonText.equals("%")) {
					result = mCalculator.percent(displayValue);
					mClickNumber = false;
					mCanOperate = true;
				}
				else if(buttonText.equals("C")) {
					result = mCalculator.clear();
					mCanOperate = false;
					mClickNumber = false;
				}
				else if(buttonText.equals("CE")) {
					result = 0;
				}
				else if(buttonText.equals("=")) {
					result = mCalculator.equals(displayValue);
					mCanOperate = false;
					mClickNumber = false;
				}
				else if(buttonText.equals("+/-")) {
					if(displayValue != 0) {
						result = -displayValue;
					}
					if(mClickNumber) {
						mClearScreen = false;
					}
					else {
						mClearScreen = true;
					}
					mCanOperate = true;
				}
				else {
					mClickNumber = false;
					
					if(mCanOperate) {
						mCanOperate = false;
					
						if(buttonText.equals("+")) {
							result = mCalculator.add(displayValue);
						}
						else if(buttonText.equals("-")) {
							result = mCalculator.sub(displayValue);
						}
						else if(buttonText.equals("*")) {
							result = mCalculator.mul(displayValue);
						}
						else if(buttonText.equals("/")) {
							result = mCalculator.div(displayValue);
						}
					}
					else {
						result = displayValue;
					}
				}
				
				if((result - (int)result) == 0.0) {
					mScreen.setValue(String.valueOf((int)result));
				}
				else {
					mScreen.setValue(String.valueOf(result));
				}
			}
			
		}
	};
	
	private class CustomButton extends TextButton {
		
		public CustomButton(String title, SelectHandler handler) {
			super(title, handler);
			
			this.setSize(kSIZE, kSIZE);
		}
	}
}
