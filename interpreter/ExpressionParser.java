package com.cs635.assignment2.interpreter;

import java.util.Stack;

public class ExpressionParser {

	Stack<Expression> stack = new Stack<>();
	
	public double parse(String expr) {
		String[] tokenArray = expr.split(" ");
		
		for(String symbol : tokenArray) {
			if(ExpressionUtils.isOperator(symbol)) {
				Expression rightExpression, leftExpression;
				if(ExpressionUtils.isSingleOperand(symbol)) {
					leftExpression = stack.pop();
					if(ExpressionUtils.isLogOperation(symbol)) {
						rightExpression = new Number(2);
					}else {
						rightExpression = null;
					}
				}else {
					rightExpression = stack.pop();
					leftExpression = stack.pop();
				}
				Expression operator = ExpressionUtils.getOperator(symbol, leftExpression, rightExpression);
				double result = operator.interpret();
				stack.push(new Number(result));
			}else {
				stack.push(new Number(symbol));
			}
		}
		return stack.pop().interpret();
	}
}
