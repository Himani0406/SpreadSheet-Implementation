package com.cs635.assignment2.interpreter;

public class TestInterpreter {

	public static void main(String[] args) {
		String token = "1967 21 + 3 sin *";
		ExpressionParser ex = new ExpressionParser();
		System.out.println(ex.parse(token));
	}
}
