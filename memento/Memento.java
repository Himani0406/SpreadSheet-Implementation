package com.cs635.assignment2.memento;

import com.cs635.assignment2.workbook.State;

import javafx.scene.control.TableColumn;

public class Memento {
	private State state;
	private TableColumn column;
	private int index;
	private String stateValue;
	private String nextStateValue;

	   public Memento(State state, TableColumn column, int index, String stateValue, String nextStateValue){
		   this.state = state;
		   this.column = column;
		   this.index = index;
		   this.stateValue = stateValue;
		   this.nextStateValue = nextStateValue;
	   }

	   public State getState(){
		      return state;
	   }
		   
	   public TableColumn getTableColumn() {
			   return column;
	   }
	   
	   public int getIndex() {
		   return index;
	   }
	   
	   public String getStateValue() {
		   return stateValue;
	   }
	   
	   public String getnextStateValue() {
		   return nextStateValue;
	   }
}
