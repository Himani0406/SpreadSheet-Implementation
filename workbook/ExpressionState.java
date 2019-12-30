package com.cs635.assignment2.workbook;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cs635.assignment2.memento.CareTaker;
import com.cs635.assignment2.memento.Originator;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ExpressionState implements State {
	static ObservableList<Columns> expressionData;
	static TableView table;
	static Label label;
	private static ExpressionState instance = new ExpressionState();
	Originator originator = new Originator();
	Callbacks callBack = new Callbacks();
	Map<String, Boolean> visited = new HashMap<>();
	Map<String, Boolean> recStack = new HashMap<>();
	
	public static ExpressionState instance() {
        return instance;
    }
	
	public void doAction(Context context) {
		ValueState state = ValueState.instance();
		state.setTableData();
		state.label.setText("Value State");
		context.setState(state);	
	}
	
	public void refreshTable() {
		table.refresh();
	}
	
	public void updateTable(TableColumn column, int index, String stateValue, String nextStateValue) {
		Columns col = expressionData.get(index);
		
		try {
			Field field = col.getClass().getDeclaredField(column.getText());
			field.setAccessible(true);
			field.set(col, stateValue);
			expressionData.set(index, col);
			setExpressionData(expressionData);
			if(nextStateValue != null) {
				ValueState.instance().updateTable(column, index, nextStateValue, null);
			}else {
				handleChange(column, index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setExpressionData(ObservableList<Columns> expressionData) {
		this.expressionData = expressionData;
	}
	
	public ObservableList<Columns> getExpressionData(){
		return expressionData;
	}
	
	public void setTableView(TableView table) {
		this.table = table;
		initializeMaps();
	}
	
	private void initializeMaps() {
		Iterator iterator = table.getColumns().iterator();
		while(iterator.hasNext()) {
			TableColumn col = (TableColumn) iterator.next();
			visited.put(col.getText(), false);
			recStack.put(col.getText(), false);
		}
	}

	public void setTableData() {
		table.setItems(expressionData);
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public String callCallback(TableColumn column, int index, String string, String oldValue) {
		if(oldValue != string && oldValue != null && !((oldValue == null || oldValue.isBlank() || oldValue.isEmpty()) && (string == null || string.isBlank() || string.isEmpty()))) {
			List<String> values = callBack.setValueData(table, ValueState.instance(), column, index, string, visited, recStack);
			if(!values.get(1).equals("Error")) {
				handleChange(column, index);
			}
			originator.setState(this, column, index, oldValue, values.get(0));
			CareTaker.getInstance().add(originator.saveStateToMemento());
		}
		return string;
	}
	

	private void handleChange(TableColumn column, int index) {
		ObservableList<Columns> data = ValueState.instance().getValueData();
		Columns expressionColumns = getExpressionData().get(index);
		
		ValueState.instance().setValueData(callBack.handleDependentValue(data, expressionColumns, column.getText(), index, visited, recStack));
		
	}
}
