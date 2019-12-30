package com.cs635.assignment2.memento;

import java.util.Stack;

public class CareTaker {
	private Stack<Memento> mementoList = new Stack<Memento>();
	private static CareTaker instance = new CareTaker();
	
	public static CareTaker getInstance() {
		return instance;
	}

	   public void add(Memento state){
	      mementoList.add(state);
	   }

	   public Memento get(){
		   if(mementoList.size() > 0) {
			   return mementoList.pop();
		   }else {
			   return null;
		   }
	      
	   }
}
