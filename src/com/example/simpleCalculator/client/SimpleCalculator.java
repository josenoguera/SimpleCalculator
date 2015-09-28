package com.example.simpleCalculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleCalculator implements EntryPoint {

	private CalculatorView mCalculatorView;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	    mCalculatorView = new CalculatorView();
	    RootPanel.get().add(mCalculatorView.CreateView());
	}
	
	
	
	
}
