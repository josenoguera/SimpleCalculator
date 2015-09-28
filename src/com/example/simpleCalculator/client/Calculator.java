package com.example.simpleCalculator.client;


public class Calculator {
	
	private enum Operation {
		NONE, ADD, SUB, MUL, DIV
	};
	
	private Double mValue = null;
	private Operation mOperation = Operation.NONE;
		
	public double equals(double v) {
		mValue = operate(mValue, v);
		mOperation = Operation.NONE;
	
		return mValue;
	}
	
	public double add(double v) {
		prevOperation(v);
		mOperation = Operation.ADD;
		return mValue;
	}
	
	public double sub(double v) {
		prevOperation(v);
		mOperation = Operation.SUB;
		return mValue;
	}
	
	public double mul(double v) {
		prevOperation(v);
		mOperation = Operation.MUL;
		return mValue;
	}
	
	public double div(double v) {
		prevOperation(v);
		mOperation = Operation.DIV;
		return mValue;
	}
	
	public double percent(double v) {
		double ret = v/mValue;
		return ret;
	}
	
	public double clear() {
		mValue = null;
		mOperation = Operation.NONE;
		return 0;
	}
	
	private void prevOperation(double v) {
		if(mValue != null) {
			mValue = operate(mValue, v);
		}
		else {
			mValue = v;
		}
	}
	
	private Double operate(Double a, Double b) {
		Double ret = 0.0;
		
		switch(mOperation) {
		case NONE:
			ret = b;
			break;
		case ADD:
			ret = a+b;
			break;
		case SUB:
			ret = a-b;
			break;
		case MUL:
			ret = a*b;
			break;
		case DIV:
			ret = a/b;
			break;
		}
	
		return ret;
	}
}
