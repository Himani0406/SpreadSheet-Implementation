package com.cs635.assignment2.interpreter;

public class ExpressionUtils {

	public static boolean isOperator(String s) {
		if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equalsIgnoreCase("log") || s.equalsIgnoreCase("sin")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isSingleOperand(String s) {
		if(s.equalsIgnoreCase("sin") || s.equalsIgnoreCase("log")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isLogOperation(String s) {
		if(s.equalsIgnoreCase("log")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static Expression getOperator(String s, Expression left, Expression right) {
		switch(s) {
		case "+":
			return new Add(left, right);
		case "-":
			return new Subtract(left, right);
		case "*":
			return new Product(left, right);
		case "/":
			return new Divide(left, right);
		case "log":
			return new Log(left, right);
		case "sin":
			return new Sine(left);
		}
		return null;
	}
}
