package com.cs635.assignment2.workbook;

import com.cs635.assignment2.memento.CareTaker;
import com.cs635.assignment2.memento.Memento;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Worksheets extends BorderPane {

	Context context;
	Label label;
	Table worksheet;
	TableView table;
	
	public Worksheets(final Stage primaryStage){
		primaryStage.setTitle("Table View Sample");
		primaryStage.setWidth(945);
		primaryStage.setHeight(200);
	}
	
	public Scene setUpScene() {
		Scene scene = new Scene(new Group());
		worksheet = new Table();
		
		Button switchButton = new Button("Switch View");
		switchButton.setOnAction(e -> handleViewChange(e));
		Button undoButton = new Button("Undo");
		undoButton.setOnAction(e -> handleUndoEvent(e));
		table =  worksheet.createTable();
		label = new Label("Value View");
		label.setFont(new Font("Arial", 20));
		
		setStateData();
		
		context = new Context();
		context.doAction();

		final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, switchButton, undoButton);
        ((Group)scene.getRoot()).getChildren().add(vbox);
        return scene;
	}
	
	private void handleUndoEvent(ActionEvent e) {
		Memento memento = CareTaker.getInstance().get();
		if(memento != null) {
			State state = memento.getState();
			state.updateTable(memento.getTableColumn(), memento.getIndex(), memento.getStateValue(), memento.getnextStateValue());
			Context.getState().refreshTable();
		}
	}

	private void setStateData() {

		ObservableList<Columns> valueData = worksheet.generateDataInMap();
		ObservableList<Columns> expressionData = worksheet.generateDataInMap();
		
		ValueState valueState = ValueState.instance();
		valueState.setTableView(table);
		valueState.setValueData(valueData);
		valueState.setLabel(label);
		
		ExpressionState expressionState = ExpressionState.instance();
		expressionState.setTableView(table);
		expressionState.setExpressionData(expressionData);
		expressionState.setLabel(label);
		
	}

	private void handleViewChange(ActionEvent e) {
		context.doAction();
	}

}
