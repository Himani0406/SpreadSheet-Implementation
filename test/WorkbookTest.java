package com.cs635.assignment2.test;

import static org.junit.Assert.assertThat;
import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;
import java.util.Objects;
import com.cs635.assignment2.workbook.Worksheets;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.framework.junit.TestFXRule;
import org.testfx.util.WaitForAsyncUtils;

import com.cs635.assignment2.workbook.Columns;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkbookTest extends ApplicationTest {

	@Rule
	public TestRule rule = RuleChain.outerRule(new TestFXRule()).around(exception = ExpectedException.none());
    public ExpectedException exception;
    
    TableView<Columns> tableView;
    Label label;
    Button switchButton;
    Button undoButton;
    private Scene scene;
    FxRobot robot = new FxRobot();
    
    @Override
    public void start(Stage primaryStage) {
    	Worksheets workSheet = new Worksheets(primaryStage);
    	scene = workSheet.setUpScene();
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();
    }
    
    @Before
    public void setUp () throws Exception {
    	VBox vbox = (VBox) scene.getRoot().getChildrenUnmodifiable().get(0);
    	label = (Label) vbox.getChildren().get(0);
    	tableView = (TableView<Columns>) vbox.getChildren().get(1);
    	switchButton = (Button) vbox.getChildren().get(2);
    	undoButton = (Button) vbox.getChildren().get(3);
    }
    
	@Test
    public void testButtonClick () {
    	robot.clickOn(switchButton);
    	assertThat(label, hasText("Value State"));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    public void hasTableCell_updatedFromExpression() {
		robot.clickOn(tableView);
        Columns col = tableView.getItems().get(0);
        col.set$A("5");
        col.set$B("6");
        col.set$C("$A $B +");
        assertThat(tableView, hasTableCell("5"));
        assertThat(tableView, hasTableCell("6"));
        assertThat(tableView, hasTableCell("$A $B +"));
        robot.clickOn(tableView);
        robot.clickOn(switchButton);
        WaitForAsyncUtils.waitForFxEvents();
        System.out.println(tableView.getItems().get(0));
        robot.clickOn(tableView);
        assertThat(tableView, hasTableCell("5.0"));
        assertThat(tableView, hasTableCell("6.0"));
        assertThat(tableView, hasTableCell("11.0"));
    }
	

	@SuppressWarnings("unchecked")
	@Test
    public void hasTableCell_updatedFromValue() {
		robot.clickOn(switchButton);
		robot.clickOn(tableView);
        Columns col = tableView.getItems().get(0);
        col.set$A("5");
        tableView.refresh();
        assertThat(tableView, hasTableCell("5"));
        robot.clickOn(switchButton);
        WaitForAsyncUtils.waitForFxEvents();
        tableView.refresh();
        assertThat(tableView, hasTableCell("5"));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    public void hasTableCell_undoFromValue() {
		robot.clickOn(switchButton);
		robot.clickOn(tableView);
        Columns col = tableView.getItems().get(0);
        col.set$A("5");
        tableView.refresh();
        assertThat(tableView, hasTableCell("5"));
        robot.clickOn(undoButton);
        WaitForAsyncUtils.waitForFxEvents();
        exception.expect(AssertionError.class);
        assertThat(tableView, hasTableCell("5"));
        
    }
	
	@SuppressWarnings("unchecked")
	@Test
    public void hasTableCell_undoFromExpression() {
		robot.clickOn(tableView);
        Columns col = tableView.getItems().get(0);
        col.set$A("5");
        col.set$B("6");
        col.set$C("$A $B +");
        tableView.refresh();
        assertThat(tableView, hasTableCell("5"));
        assertThat(tableView, hasTableCell("6"));
        assertThat(tableView, hasTableCell("$A $B +"));
        robot.clickOn(undoButton);
        WaitForAsyncUtils.waitForFxEvents();
        exception.expect(AssertionError.class);
        assertThat(tableView, hasTableCell("$A $B +"));
        robot.clickOn(switchButton);
        exception.expect(AssertionError.class);
        assertThat(tableView, hasTableCell("11.0"));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    public void hasTableCell_hasCyclic() {
		robot.clickOn(tableView);
        Columns col = tableView.getItems().get(0);
        col.set$A("5");
        col.set$B("$C +");
        col.set$C("$B +");
        tableView.refresh();
        assertThat(tableView, hasTableCell("5"));
        robot.clickOn(switchButton);
        assertThat(tableView, hasTableCell("Error"));
    }

	public static Matcher<TableView> hasTableCell(String value) {
        String descriptionText = "has table cell \"" + value + "\"";
        return typeSafeMatcher(TableView.class, descriptionText,
            tableView -> "\nwhich does not contain a cell with the given value",
            node -> hasTableCell(node, value));
    }
	
	private static boolean hasTableCell(TableView view, String value) {
		for(int i=0; i < view.getItems().size(); i++) {
			for(int j=0; j < view.getColumns().size(); j++) {
				TableColumn col = (TableColumn) view.getColumns().get(j);
				Object r = view.getItems().get(i);
				if(col.getCellObservableValue(r).getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Matcher<Labeled> hasText(String text) {
        String descriptionText = "has text \"" + text + "\"";
        return typeSafeMatcher(Labeled.class, descriptionText,
        		labeled -> "\"" + labeled.getText() + "\"",
                node -> hasText(node, text));
    }
	
    private static boolean hasText(Labeled labeled, String string) {
    	return Objects.equals(string, labeled.getText());
	}
    
    @After
    public void tearDown () throws Exception {
    	FxToolkit.hideStage();
    }
}
