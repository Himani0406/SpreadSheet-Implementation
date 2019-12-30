package com.cs635.assignment2.interpreter;

public class Log implements Expression {

	private final Expression leftExpression;
	private final Expression rightExpression;
	
	public Log(Expression leftExpression, Expression rightExpression)
	{
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public double interpret() {
		return Math.log(this.leftExpression.interpret()) / Math.log(this.rightExpression.interpret());
	}

}
