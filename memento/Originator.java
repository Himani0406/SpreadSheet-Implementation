package com.cs635.assignment2.memento;

import com.cs635.assignment2.workbook.State;

import javafx.scene.control.TableColumn;

public class Originator {
	private State state;
	private TableColumn column;
	private int index;
	private String stateValue;
	private String nextStateValue;

	   public void setState(State state, TableColumn column, int index, String stateValue, String nextStateValue){
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
	   
	   public Memento saveStateToMemento(){
	      return new Memento(state, column, index, stateValue, nextStateValue);
	   }

	   public void getStateFromMemento(Memento memento){
	      state = memento.getState();
	   }
	   
	   public void getTableColumnFromMemento(Memento memento){
		   column = memento.getTableColumn();
	   }
	   
	   public void getIndexFromMemento(Memento memento) {
		   index = memento.getIndex();
	   }
	   
	   public void getStateValueFromMemento(Memento memento) {
		   stateValue = memento.getStateValue();
	   }
	   
	   public void getnextStateValueFromMemento(Memento memento) {
		   nextStateValue = memento.getnextStateValue();
	   }
}
