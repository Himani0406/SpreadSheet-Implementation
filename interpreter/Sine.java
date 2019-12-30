package com.cs635.assignment2.interpreter;

public class Sine implements Expression {

	private final Expression expression;
	
	public Sine(Expression expression)
	{
		this.expression = expression;
	}
	
	@Override
	public double interpret() {
		return Math.sin(this.expression.interpret());
	}

}
