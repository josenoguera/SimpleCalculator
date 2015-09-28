package com.example.simpleCalculator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ConvertService</code>.
 */
public interface ConvertServiceAsync {
	public void convertToBinary(String decimal, AsyncCallback<String> callback) throws IllegalArgumentException;
	public void getHistory(AsyncCallback<String[]> callback) throws IllegalArgumentException;
}