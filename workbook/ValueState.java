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

public class ValueState implements State {
	
	static ObservableList<Columns> valueData = null;
	static TableView table = null;
	static Label label;
	private static ValueState instance = new ValueState();
	Originator originator = new Originator();
	Callbacks callBack = new Callbacks();
	Map<String, Boolean> visited = new HashMap<>();
	Map<String, Boolean> recStack = new HashMap<>();
	
	public static ValueState instance() {
        return instance;
    }
	
	public void doAction(Context context) {
		ExpressionState state = ExpressionState.instance();
		state.setTableData();
		state.label.setText("Expression State");
	    context.setState(state);
	}
	
	public void updateTable(TableColumn column, int index, String stateValue, String nextStateValue) {
		Columns col = valueData.get(index);
		
		try {
			Field field = col.getClass().getDeclaredField(column.getText());
			field.setAccessible(true);
			field.set(col, stateValue);
			valueData.set(index, col);
			setValueData(valueData);
			if(nextStateValue != null) {
				ExpressionState.instance().updateTable(column, index, nextStateValue, null);
			}else {
				handleChange(column, index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		table.refresh();
	}
	
	public void setValueData(ObservableList<Columns> valueData) {
		this.valueData = valueData;
	}
	
	public ObservableList<Columns> getValueData(){
		return valueData;
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
		table.setItems(valueData);
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public String callCallback(TableColumn column, int index, String string, String oldValue) {
		List<String> values = callBack.setExpressionData(table, ExpressionState.instance(), column, index, string);
		if(oldValue != string && oldValue != null && !((oldValue == null || oldValue.isBlank() || oldValue.isEmpty()) && (string == null || string.isBlank() || string.isEmpty()))) {
			if(!values.get(1).equals("Error")) {
				handleChange(column, index);
				table.setItems(null);
				table.setItems(getValueData());
			}
			originator.setState(this, column, index, oldValue, values.get(0));
			CareTaker.getInstance().add(originator.saveStateToMemento());
			return values.get(1);
		}
		return string;
	}
	
	private void handleChange(TableColumn column, int index) {
		ObservableList<Columns> data = getValueData();
		Columns expressionColumns = ExpressionState.instance().getExpressionData().get(index);
		
		setValueData(callBack.handleDependentValue(data, expressionColumns, column.getText(), index, visited, recStack));
	}
}
