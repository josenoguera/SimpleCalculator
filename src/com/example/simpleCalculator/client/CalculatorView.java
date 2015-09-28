package com.example.simpleCalculator.client;

import java.util.List;

import com.example.simpleCalculator.client.model.ConversionClient;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

public class CalculatorView {
	
	private final String kSIZE = "40";
	
	private TextField mDisplay, mDisplayBinary;
	private Calculator mCalculator = new Calculator();
	private boolean mClearScreen = true;
	private boolean mClickNumber = false;
	private boolean mCanOperate = false;
	
	FramedPanel mHistoryFrame = null;
	
	/**
	 * Create a remote service proxy to talk to the server-side Convert service.
	 */
	private final ConvertServiceAsync mConvertService = GWT.create(ConvertService.class);
	
	public Widget CreateView() {

		VerticalPanel vertical = new VerticalPanel();
		
		mDisplay = new TextField();
		mDisplay.setEnabled(false);
		mDisplay.setSize("200", kSIZE);
		mDisplay.setValue("0");
		vertical.add(mDisplay);
				
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
		
		horizontal = new HorizontalPanel();
		TextButton binaryButton = new TextButton("Convert to binary", mConvertHandler);
		binaryButton.setSize("100", kSIZE);
		horizontal.add(binaryButton);
		mDisplayBinary = new TextField();
		mDisplayBinary.setEnabled(false);
		mDisplayBinary.setSize("100", kSIZE);
		mDisplayBinary.setValue("");
		horizontal.add(mDisplayBinary);
		vertical.add(horizontal);
		
		horizontal = new HorizontalPanel();
		TextButton historyButton = new TextButton("Show conversion history", mHistoryHandler);
		historyButton.setSize("200", kSIZE);
		horizontal.add(historyButton);
		vertical.add(horizontal);
		
		FramedPanel frame = new FramedPanel();
		frame.setButtonAlign(BoxLayoutPack.CENTER); // Center
		frame.setHeadingText("Simple Calculator");
		frame.setPixelSize(220, 330);
		frame.addStyleName("white-bg");
		frame.add(new HTML());
		frame.getElement().setMargins(new Margins(5));
		frame.setWidget(vertical);
		
		FlowLayoutContainer container = new FlowLayoutContainer();
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
			
			String displayText = mDisplay.getValue();

			if(isNumber) {
				if(mClearScreen) {
					mDisplay.setValue(buttonText);
				}
				else {
					mDisplay.setValue(displayText + buttonText);
				}
				mClearScreen = false;
				mClickNumber = true;
				mCanOperate = true;
			}
			else if(buttonText.equals(".")) {
				mDisplay.setValue(displayText + buttonText);
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
					mCanOperate = true;
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
					mDisplay.setValue(String.valueOf((int)result));
				}
				else {
					mDisplay.setValue(String.valueOf(result));
				}
			}
			
		}
	};
	
	private SelectHandler mConvertHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			
			mConvertService.convertToBinary(mDisplay.getValue(), new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					mDisplayBinary.setValue("Remote Procedure Call - Failure");
				}

				public void onSuccess(String result) {
					mDisplayBinary.setValue(result);
				}
			});
		}
	};
	
	private SelectHandler mHistoryHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			
			if(mHistoryFrame != null) {
				mHistoryFrame.removeFromParent();
				mHistoryFrame = null;
			}
			
			mHistoryFrame = new FramedPanel();
			mHistoryFrame.setButtonAlign(BoxLayoutPack.CENTER); // Center
			mHistoryFrame.setHeadingText("Conversion history");
			mHistoryFrame.setPixelSize(300, 330);
			mHistoryFrame.addStyleName("white-bg");
			mHistoryFrame.add(new HTML());
			mHistoryFrame.getElement().setMargins(new Margins(5));
			
			mConvertService.getHistory(new AsyncCallback<List<ConversionClient>>() {
				public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					TextArea text = new TextArea();
					text.setText("Remote Procedure Call - Failure");
					text.setPixelSize(280, 20);
					text.setAllowTextSelection(false);
					text.setPreventScrollbars(true);
					mHistoryFrame.setWidget(text);
					
					FlowLayoutContainer container = new FlowLayoutContainer();
					container.add(mHistoryFrame);
					
					// Add the widgets to the root panel.
					RootPanel.get().add(container);
				}

				public void onSuccess(List<ConversionClient> result) {
					
					VerticalPanel vertical = new VerticalPanel();
					
					for(ConversionClient conv : result) {
						TextArea text = new TextArea();
						text.setText(conv.toString());
						text.setPixelSize(280, 20);
						text.setAllowTextSelection(false);
						text.setPreventScrollbars(true);
						vertical.add(text);
					}

					//create scrollpanel with content
					ScrollPanel scrollPanel = new ScrollPanel();
					scrollPanel.setSize("800px", "330px");
					scrollPanel.add(vertical);

					mHistoryFrame.setWidget(scrollPanel);
					
					FlowLayoutContainer container = new FlowLayoutContainer();
					container.add(mHistoryFrame);
					
					// Add the widgets to the root panel.
					RootPanel.get().add(container);
				}
			});
		}
	};
	
	private class CustomButton extends TextButton {
		
		public CustomButton(String title, SelectHandler handler) {
			super(title, handler);
			
			this.setSize(kSIZE, kSIZE);
		}
	}
}
