package com.example.simpleCalculator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
* The client-side stub for the RPC service.
*/
@RemoteServiceRelativePath("convert")
public interface ConvertService extends RemoteService {
	public String convertToBinary(String decimal) throws IllegalArgumentException;
	public String[] getHistory() throws IllegalArgumentException;
}
