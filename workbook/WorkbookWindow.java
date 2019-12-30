package com.cs635.assignment2.workbook;

import javafx.application.Application;
import javafx.stage.Stage;

public class WorkbookWindow extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Worksheets workSheet = new Worksheets(primaryStage);
        primaryStage.setScene(workSheet.setUpScene());
        primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
}
