package com.cs635.assignment2.interpreter;

public class Product implements Expression {

	private final Expression leftExpression;
	private final Expression rightExpression;
	
	public Product(Expression leftExpression, Expression rightExpression)
	{
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public double interpret() {
		return this.leftExpression.interpret() * this.rightExpression.interpret();
	}

}
