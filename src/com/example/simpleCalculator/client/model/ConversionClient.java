package com.example.simpleCalculator.client.model;

import java.io.Serializable;

public class ConversionClient implements Serializable {
	private static final long serialVersionUID = 4590568469276579898L;
	
	private String timestamp;
	private String decimal;
	private String binary;
	
	public ConversionClient() {
	    this.timestamp = "";
	  }
	
	public ConversionClient(String timestamp, String decimal, String binary) {
	    this.timestamp =timestamp;
	    this.decimal = decimal;
	    this.binary = binary;
	  }

	  public String getTimestamp() {
		  return this.timestamp;
	  }

	  public String getDecimal() {
	      return this.decimal;
	  }

	  public String getBinary() {
	      return this.binary;
	  }
	  
	  @Override
		public String toString() {
			return new String(this.getTimestamp() +  "> " + this.getDecimal() + " -> " + this.getBinary());
		}
}
