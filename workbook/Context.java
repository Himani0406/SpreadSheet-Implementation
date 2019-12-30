package com.cs635.assignment2.workbook;

public class Context {

	private static State state;

	   public Context(){ 
	      state = ValueState.instance();
	   }

	   public void setState(State state){
	      Context.state = state;		
	   }

	   public static State getState(){
	      return state;
	   }
	   
	   public void doAction() {
		   state.doAction(this);
	   }
}
