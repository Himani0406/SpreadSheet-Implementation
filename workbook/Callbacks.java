package com.cs635.assignment2.workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cs635.assignment2.interpreter.ExpressionParser;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Callbacks {

	public static ExpressionParser ex = new ExpressionParser();
	
	@SuppressWarnings("rawtypes")
	public List<String> setExpressionData(TableView table, ExpressionState expressionState, TableColumn column, int index, String string) {
		
		return handleChangedValue(table, string, index, expressionState, column);
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> setValueData(TableView table, ValueState valueState, TableColumn column, int index, String string, Map<String, Boolean> visited,
			Map<String, Boolean> recStack) {
		
		return handleChangedExpression(string, index, valueState, column, visited, recStack);
	}
	
	private List<String> handleChangedExpression(String string, int index, ValueState valueState, TableColumn column, Map<String, Boolean> visited,
			Map<String, Boolean> recStack) {
		List<String> values = new ArrayList<>();
		ObservableList<Columns> data = valueState.getValueData();
		Columns col = data.get(index);
		
		String value = "", oldValue = "";
		
		if(!(string.isBlank() || string.isEmpty())) {
			value = handleColumnNames(string, col, index, visited, recStack).replace("[", "").replace("]", "").replaceAll(",", "");
			if(!(value.isBlank() || value.isEmpty() || value.equals("Error"))) {
				value = String.valueOf(ex.parse(value));
			}
		}
		
		Field field;
		try {
			
			field = col.getClass().getDeclaredField(column.getText());
			field.setAccessible(true);
			values.add(field.get(col).toString());
			field.set(col, value);

			if(value.equals("Error")) {
				for(Map.Entry<String, Boolean> rec : recStack.entrySet()) {
					if(rec.getValue()) {
						field = col.getClass().getDeclaredField(rec.getKey());
						field.setAccessible(true);
						field.set(col, "Error");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.set(index, col);
		valueState.setValueData(data);
		
		values.add(value);
		return values;
	}
	
	private List<String> handleChangedValue(TableView table, String string, int index, ExpressionState expressionState, TableColumn column) {
		List<String> values = new ArrayList<>();
		if(!string.matches("-?\\d+(\\.\\d+)?") && !string.equals("Error")) {
			string = "";
		}
		ObservableList<Columns> data = expressionState.getExpressionData();
		Columns col = data.get(index);
		Field field;
		try {
			field = col.getClass().getDeclaredField(column.getText());
			field.setAccessible(true);
			values.add(field.get(col).toString());
			if(!string.equals("Error")) {
				field.set(col, string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.set(index, col);
		expressionState.setExpressionData(data);
		
		values.add(string);
		
		return values;
	}
	
	private String handleColumnNames(String string, Columns col, int index, Map<String, Boolean> visited,
			Map<String, Boolean> recStack) {
		if(!string.matches("-?\\d+(\\.\\d+)?")) {
			String[] numbers = string.split(" ");
			
			for(int i = 0; i < numbers.length; i++) {
				if(numbers[i].contains("$")) {
					try {
						resetMaps(visited, recStack);
						if(checkCyclic(numbers[i], visited, recStack)) {
							return "Error";
						}
						Field field = col.getClass().getDeclaredField(numbers[i]);
						field.setAccessible(true);
						numbers[i] = field.get(col).toString();
						if(numbers[i] == "") {
							numbers[i] = "0";
						}
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			return Arrays.toString(numbers);
		}else {
			return string;
		}
	}

	private void resetMaps(Map<String, Boolean> recStack, Map<String, Boolean> visited) {
		for(Map.Entry<String, Boolean> rec : recStack.entrySet()) {
			rec.setValue(false);
		}
		
		for(Map.Entry<String, Boolean> rec : visited.entrySet()) {
			rec.setValue(false);
		}
	}

	private Boolean checkCyclic(String column, Map<String, Boolean> visited, Map<String, Boolean> recStack) {
		
		if(recStack.get(column)) {
			return true;
		}
		
		if(visited.get(column)) {
			return false;
		}
		
		visited.replace(column, true);
		recStack.replace(column, true);
		
		Columns data = ExpressionState.expressionData.get(0);
		Field[] field = data.getClass().getDeclaredFields();
		
		Field colField;
		try {
			colField = data.getClass().getDeclaredField(column);
			colField.setAccessible(true);
			String fieldValue = colField.get(data).toString();
			if(fieldValue.contains("$")) {
				for(Field f : field) {
					if(fieldValue.contains(f.getName())) {
						if(checkCyclic(f.getName(), visited, recStack)) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		recStack.replace(column, false);
		return false;
	}

	public ObservableList<Columns> handleDependentValue(ObservableList<Columns> data, Columns expressionColumns, String column, int index, Map<String, Boolean> visited,
			Map<String, Boolean> recStack) {
		
		Field[] fields = expressionColumns.getClass().getDeclaredFields();
		
		Columns valueCol = data.get(index);
		
		for(Field f : fields) {
			f.setAccessible(true);
			String fieldValue;
			try {
				fieldValue = f.get(expressionColumns).toString();
				if(f.getName() != column && fieldValue.contains(column)) {
					String value = handleColumnNames(fieldValue, valueCol, index, visited, recStack).replace("[", "").replace("]", "").replaceAll(",", "");
					if(!(value.isBlank() || value.isEmpty() || value.contains("Error"))) {
						value = String.valueOf(ex.parse(value));
					}
					Field field = valueCol.getClass().getDeclaredField(f.getName());
					field.setAccessible(true);
					field.set(valueCol, value);
					data = handleDependentValue(data, expressionColumns, f.getName(), index, visited, recStack);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		data.set(index, valueCol);
		return data;
	}
}
