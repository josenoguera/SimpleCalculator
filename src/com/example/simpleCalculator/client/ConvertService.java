package com.example.simpleCalculator.client;

import java.util.List;

import com.example.simpleCalculator.client.model.ConversionClient;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
* The client-side stub for the RPC service.
*/
@RemoteServiceRelativePath("convert")
public interface ConvertService extends RemoteService {
	public String convertToBinary(String decimal) throws IllegalArgumentException;
	public List<ConversionClient> getHistory() throws IllegalArgumentException;
}
