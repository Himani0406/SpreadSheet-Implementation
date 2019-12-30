package com.cs635.assignment2.interpreter;

public class Number implements Expression {

	private final double number;
	
	public Number(double number) {
		this.number = number;
	}
	
	public Number(String number) {
		this.number = Double.parseDouble(number);
	}
	
	@Override
	public double interpret() {
		return this.number;
	}

}
