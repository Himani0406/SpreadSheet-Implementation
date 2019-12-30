package com.cs635.assignment2.workbook;

import javafx.scene.control.TableColumn;

public interface State {
	public void doAction(Context context);
	public void updateTable(TableColumn column, int index, String stateValue, String nextStateValue);
	public void refreshTable();
	public String callCallback(TableColumn column, int index, String string, String oldValue);
}
