package myPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditScene {
  static String             find, findField, in, inField;
  static Scene              editScene;
  static LinkedList<String> linkList;

  public EditScene(String find, String findField, String in, String inField) {
    super();
    EditScene.find = find;
    EditScene.findField = findField;
    EditScene.in = in;
    EditScene.inField = inField;
  }

  public EditScene() {
  }

  public void getScene4(Stage window) {
    Main.buttonAction(find, findField, in, inField);
    ArrayList<String> al = Main.ruleSetMap.get(Main.key);
    if (al == null) {
      AlertBox.display("Sugar_v0", "Sorry ! could not find the component.\n Work In Progress");
      window.setScene(FirstScene.firstScene);
    } else {
      linkList = new LinkedList<String>(al);
      getEditLayout(window);
    }
  }

  public void getEditLayout(Stage window) {
    TreeItem<String> root = new TreeItem<String>(Main.key);
    int i = 1;
    for (String ele : linkList) {
      TreeItem<String> it = new TreeItem<String>(i++ + ". " + ele);
      root.getChildren().add(it);
    }
    root.setExpanded(true);
    TreeView<String> treeView = new TreeView<String>(root);
    treeView.setCellFactory(e -> new CustomCell(window));

    // Back
    Button back = new Button("Back");
    back.setOnAction(e -> {
      window.setScene(FirstScene.firstScene);
    });

    Button createRuleset = new Button("New Ruleset");
    createRuleset.setOnAction(e -> {
      CreateScene createScene = new CreateScene();
      createScene.createRuleset(window);
    });

    Button createRule = new Button("New Rule");
    createRule.setOnAction(e -> {
      CreateScene createScene = new CreateScene();
      createScene.createRule(window);
    });

    Button save = new Button("Save");
    save.setOnAction(e -> {
    });
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.BOTTOM_CENTER);
    hBox.setPadding(new Insets(10, 10, 10, 10));
    hBox.setSpacing(100);
    hBox.getChildren().addAll(back, createRule, createRuleset, save);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(treeView, hBox);
    // Layout
    StackPane layout = new StackPane();
    layout.getChildren().add(vBox);
    layout.autosize();
    editScene = new Scene(layout, 700, 450);
    window.setScene(editScene);
    window.show();

  }

  class CustomCell extends TreeCell<String> {
    Stage window;
    int   index = 0;

    public CustomCell(Stage window) {
      super();
      this.window = window;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);

      // If the cell is empty we don't show anything.
      if (isEmpty()) {
        setGraphic(null);
        setText(null);
      } else {
        // We only show the custom cell if it is a leaf, meaning it has
        // no children.
        if (this.getTreeItem().isLeaf()) {
          HBox cellBox = new HBox(10);

          Label label = new Label(item);
          Button add = new Button("Add");
          add.setOnAction(e -> {
            String str = this.getTreeItem().getValue();

            if (str.contains(".")) {
              index = Integer.parseInt(str.substring(0, str.indexOf('.')).trim());
            }

            Stage addStage = new Stage();
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.setTitle(window.getTitle());
            addStage.setMinWidth(200);
            addStage.setMinHeight(150);
            Label ruleCompLabel = new Label("RuleComponentID @" + (index + 1) + ": ");
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            ArrayList<String> choiceList = new ArrayList<String>();
            choiceList.addAll(Main.ruleComponentDetails.keySet());
            choiceList.addAll(Main.ruleDetails.keySet());
            Collections.sort(choiceList, ALPHABETICAL_ORDER);
            String temp = ""; // this variable ensures that duplicate items are not displayed, since
                              // Main.ruleComponentDetails & Main.ruleDetails will create duplicate entries.
            for (String choice : choiceList) {
              if (!temp.equals(choice)) {
                choiceBox.getItems().add(choice);
                temp = choice;
              }
            }

            Button ok = new Button("Done");
            ok.setOnAction(f -> {
              linkList.add(index, choiceBox.getValue());
              ArrayList<String> al = new ArrayList<String>(linkList);
              Main.ruleSetMap.put(Main.key, al);
              addStage.close();
              getEditLayout(window);
            });

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBox.setSpacing(20);
            vBox.getChildren().addAll(ruleCompLabel, choiceBox, ok);

            // StackPane layout = new StackPane();
            // layout.getChildren().add(vBox);
            // layout.autosize();

            Scene addScene = new Scene(vBox, 480, 150, true);
            addStage.setScene(addScene);
            addStage.showAndWait();

            getEditLayout(window);
          });

          Button edit = new Button("Edit");
          edit.setOnAction(e -> {
            String str = this.getTreeItem().getValue();
            String ele = str.substring(str.indexOf('.') + 1, str.length()).trim();
            index = Integer.parseInt(str.substring(0, str.indexOf('.')).trim());
            ComponentEditScene compEditScene = new ComponentEditScene(ele, index);
            compEditScene.getCompEditScene(window);
          });

          Button remove = new Button("Remove");
          remove.setOnAction(e -> {
            String str = this.getTreeItem().getValue();
            index = Integer.parseInt(str.substring(0, str.indexOf('.')).trim());
            linkList.remove(index - 1);
            ArrayList<String> al = new ArrayList<String>(linkList);
            Main.ruleSetMap.put(Main.key, al);
            getEditLayout(window);
          });
          // Here we bind the pref height of the label to the height of the checkbox. This way the label and the
          // checkbox will have the same size.
          label.prefHeightProperty().bind(add.heightProperty());

          cellBox.getChildren().addAll(add, edit, remove, label);

          // We set the cellBox as the graphic of the cell.
          setGraphic(cellBox);
          setText(null);
        } else {
          // If this is the root we just display the text.
          setGraphic(null);
          setText(item);
        }
      }
    }
  }

  private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
    public int compare(String str1, String str2) {
      int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
      if (res == 0) {
        res = str1.compareTo(str2);
      }
      return res;
    }
  };

}
