package myPackage;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.geometry.*;

public class AlertBox {

  public static void display(String title, String message) {
    Stage window = new Stage();
    // Block events to other windows
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    window.setMinHeight(100);
    window.getIcons().add(new Image("file:resources/sumtotal_0.png"));

    Label label = new Label();
    label.setText(message);
    Button closeButton = new Button("OK");
    closeButton.setOnAction(e -> window.close());

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, closeButton);
    layout.setAlignment(Pos.CENTER);

    // Display window and wait for it to be closed before returning
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  public static void dismiss(Stage window) {
    window.close();
  };

  public static Stage pleasewait() {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Reading Ruleset XML. Please wait...");
    window.setMinWidth(500);
    window.setMinHeight(200);
    window.getIcons().add(new Image("file:resources/sumtotal_0.png"));
    Label label = new Label();
    label.setText("Reading Ruleset XML. Please wait...");
    Button closeButton = new Button("OK");
    // closeButton.setOnAction();

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, closeButton);
    layout.setAlignment(Pos.CENTER);
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.show();
    // window.showAndWait();
    return window;
  }

}
