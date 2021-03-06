package com.example.simpleCalculator.server.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Conversion {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Date timestamp;
	@Persistent
	private String decimal;
	@Persistent
	private String binary;

	public Conversion(String decimal) {
		this.timestamp = new Date();
		this.decimal = decimal;
		this.binary = convertToBinary(decimal);
	}

	public Long getId() {
		return this.id;
	}

	public String getDecimal() {
		return this.decimal;
	}

	public String getBinary() {
		return this.binary;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}
	 
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS");
		String dateString = format.format(this.timestamp);
		
		return new String("\"" + dateString + "\",\"" + this.getDecimal() + "\",\"" + this.getBinary() + "\"");
	}
	
	private String convertToBinary(String decimal) {
		String ret = "";
		try {
			int dec = Double.valueOf(decimal).intValue();
			ret = String.format("%8s", Integer.toBinaryString(dec)).replace(' ', '0');
		}
		catch(NumberFormatException e) {
		}
		
		return ret;
	}
}
