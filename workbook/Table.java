package com.cs635.assignment2.workbook;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class Table{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableView createTable() {
		
		Map<String, TableColumn> tableColumns = generateColumns();
		
		Callback<TableColumn, TableCell> cellFactory =
	             new Callback<TableColumn, TableCell>() {
	                 public TableCell call(TableColumn p) {
	                    return new EditingCell(p);
	                 }
	             };
		
		for(Map.Entry<String, TableColumn> entry : tableColumns.entrySet()) {
			entry.getValue().setCellValueFactory(new PropertyValueFactory<Columns, String>(entry.getKey()));
			entry.getValue().setCellFactory(cellFactory);
			entry.getValue().setMaxWidth(200);
			entry.getValue().setMinWidth(100);
		}
		
		TableView tableView = new TableView<Columns>(generateDataInMap());
		tableView.setEditable(true);
		tableView.getColumns().setAll(tableColumns.values());
		tableView.setFixedCellSize(30);
		tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(30));
		
		return tableView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, TableColumn> generateColumns() {
		
		Map<String, TableColumn> tableColumns = new TreeMap<>();

		TableColumn columnA = new TableColumn("$A");
		columnA.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$A(t.getNewValue());
            }
        });
		
		TableColumn columnB = new TableColumn("$B");
		columnB.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$B(t.getNewValue());
            }
        });
		
		TableColumn columnC = new TableColumn("$C");
		columnC.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$C(t.getNewValue());
            }
        });
		
		TableColumn columnD = new TableColumn("$D");
		columnD.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$D(t.getNewValue());
            }
        });
		
		TableColumn columnE = new TableColumn("$E");
		columnE.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$E(t.getNewValue());
            }
        });
		
		TableColumn columnF = new TableColumn("$F");
		columnF.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$F(t.getNewValue());
            }
        });
		
		TableColumn columnG = new TableColumn("$G");
		columnG.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$G(t.getNewValue());
            }
        });
		
		TableColumn columnH = new TableColumn("$H");
		columnH.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$H(t.getNewValue());
            }
        });
		
		TableColumn columnI = new TableColumn("$I");
		columnI.setOnEditCommit( new EventHandler<CellEditEvent<Columns, String>>() {
            @Override
            public void handle(CellEditEvent<Columns, String> t) {
                ((Columns) t.getTableView().getItems().get(t.getTablePosition().getRow())).set$I(t.getNewValue());
            }
        });
		
		tableColumns.put("$A", columnA);
		tableColumns.put("$B", columnB);
		tableColumns.put("$C", columnC);
		tableColumns.put("$D", columnD);
		tableColumns.put("$E", columnE);
		tableColumns.put("$F", columnF);
		tableColumns.put("$G", columnG);
		tableColumns.put("$H", columnH);
		tableColumns.put("$I", columnI);
		
		return tableColumns;
	}

	public ObservableList<Columns> generateDataInMap() {
		ObservableList<Columns> data = FXCollections.observableArrayList(
			new Columns("", "", "", "", "", "", "", "", ""));
		return data;
	}
}
