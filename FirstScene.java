package myPackage;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FirstScene {
  public static Scene firstScene;

  public Scene getScene1(Stage window) {

    // GridPane with 10px padding around edge
    GridPane grid1 = new GridPane();
    grid1.setPadding(new Insets(25, 25, 25, 25));
    grid1.setVgap(10);
    grid1.setHgap(12);

    Label find = new Label("Find :");
    GridPane.setConstraints(find, 1, 0, 1, 1);
    // findField
    TextField findField = new TextField(Main.getInput(find.getText()));
    findField.setPromptText("Enter Ruleset Component name");
    findField.setPrefWidth(300);
    GridPane.setConstraints(findField, 2, 0, 3, 1);

    Label in = new Label("In :");
    GridPane.setConstraints(in, 1, 1, 1, 1);
    // inField
    TextField inField = new TextField(Main.getInput(in.getText()));
    inField.setPromptText("Enter RulesetXML path");
    inField.setPrefWidth(300);
    GridPane.setConstraints(inField, 2, 1, 3, 1);

    // Browse Button
    Button pick = new Button();
    pick.setText("Pick");
    pick.setOnAction(e -> {
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Select RulesetXML File");
      File defaultFile = (inField.getText() != null) ? new File(inField.getText()) : new File("/");
      chooser.setInitialDirectory(defaultFile.getParentFile());
      File selectedFile = chooser.showOpenDialog(null);
      if (selectedFile != null && validateWhere(selectedFile.getAbsolutePath()))
        inField.setText(selectedFile.getAbsolutePath());
    });
    GridPane.setConstraints(pick, 5, 1, 1, 1);

    // QuickLook Button
    Button quickLookButton = new Button();
    GridPane.setConstraints(quickLookButton, 2, 2, 1, 1);
    quickLookButton.setText("QuickLook");
    quickLookButton.setOnAction(e -> {
      QuickScene scene2 = new QuickScene(find.getText(), findField.getText(), in.getText(), inField.getText());
      scene2.getScene2(window);
    });
    // Trace Button
    Button trace = new Button();
    GridPane.setConstraints(trace, 3, 2, 1, 1);
    trace.setText("Trace");
    trace.setOnAction(e -> {
      TraceScene scene3 = new TraceScene(find.getText(), findField.getText(), in.getText(), inField.getText());
      scene3.getScene3(window);
    });

    // Edit Button
    Button edit = new Button();
    GridPane.setConstraints(edit, 4, 2, 1, 1);
    edit.setText("Edit");
    edit.setOnAction(e -> {
      EditScene scene4 = new EditScene(find.getText(), findField.getText(), in.getText(), inField.getText());
      scene4.getScene4(window);
    });
    // Add everything to grid
    grid1.getChildren().addAll(find, findField, in, inField, pick, quickLookButton, trace, edit);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(grid1);
    firstScene = new Scene(vBox, 480, 150, true);
    window.setScene(firstScene);
    window.show();
    return firstScene;
  }

  private boolean validateWhere(String text) {
    File selectedFile = new File(text);
    if (selectedFile.isFile() && selectedFile.getAbsolutePath().endsWith(".xml")) {
      Main.ruleSetXMLPath = text;
      return true;
    } else
      AlertBox.display("Sugar_v0", "Please re-Check XML path !");
    return false;

  }
}
