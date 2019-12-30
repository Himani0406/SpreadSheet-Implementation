package com.cs635.assignment2.workbook;

import java.lang.reflect.Field;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class EditingCell extends TableCell<Columns, String> {

	private TextField textField;
	private TableColumn column;
	String oldValue;
	 
    public EditingCell(TableColumn column) {
    	this.column = column;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
        	oldValue = getText();
            super.startEdit();
            createTextField();
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
            } else {
            	String text = handleChangedValue(getString());
                setText(text);
                setGraphic(null);
            }
        }
    }

    private String handleChangedValue(String string) {
		int index = column.getTableView().getSelectionModel().getSelectedIndex();
		
		if(index == -1) {
			return string;
		}
		
		string = Context.getState().callCallback(column, index, string, oldValue);
		
		return string;
		
	}

	private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, 
                Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
