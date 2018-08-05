package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentEditScene {

  static Scene               compntEditScene;
  String                     key;
  int                        index;
  String                     PARAM_PRIORITY = "priority";
  String                     PARAM_ID       = "id";
  HashMap<String, TextField> ruleComponent  = null;
  HashMap<String, TextField> ruleSet        = null;
  HashMap<String, TextField> rule           = null;

  public ComponentEditScene(String key, int index) {
    super();
    this.key = key;
    this.index = index;
  }

  public void getCompEditScene(Stage window) {
    VBox vBox = new VBox();

    if (Main.ruleComponentDetails.get(key) != null) {
      ruleComponent = new HashMap<String, TextField>();
      Label lbl = new Label("Component Details : ");
      HBox hBox = new HBox();
      hBox.getChildren().add(lbl);
      hBox.setAlignment(Pos.CENTER);
      vBox.getChildren().add(hBox);
      TableView<ItemClass> table = new TableView<>();
      // Name column
      TableColumn<ItemClass, String> nameColumn = new TableColumn<ItemClass, String>("Name");
      nameColumn.setMinWidth(200);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
      // Value column
      TableColumn<ItemClass, TextField> valueColumn = new TableColumn<ItemClass, TextField>("Value");
      valueColumn.setMinWidth(400);
      valueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
      table.setItems(getItems(Main.ruleComponentDetails.get(key), ruleComponent));
      table.getColumns().addAll(nameColumn, valueColumn);
      vBox.getChildren().add(table);
    }
    if (Main.ruleSetDetails.get(key) != null) {
      ruleSet = new HashMap<String, TextField>();
      Label lbl = new Label("RuleSet Details : ");
      HBox hBox = new HBox();
      hBox.getChildren().add(lbl);
      hBox.setAlignment(Pos.CENTER);
      vBox.getChildren().add(hBox);
      TableView<ItemClass> table = new TableView<ItemClass>();
      // Name column
      TableColumn<ItemClass, String> nameColumn = new TableColumn<ItemClass, String>("Name");
      nameColumn.setMinWidth(200);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
      // Value column
      TableColumn<ItemClass, TextField> valueColumn = new TableColumn<ItemClass, TextField>("Value");
      valueColumn.setMinWidth(400);
      valueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

      table.setItems(getItems(Main.ruleSetDetails.get(key), ruleSet));
      table.getColumns().addAll(nameColumn, valueColumn);
      vBox.getChildren().add(table);
    }
    if (Main.ruleDetails.get(key) != null) {
      rule = new HashMap<String, TextField>();
      Label lbl = new Label("Rule Details : ");
      HBox hBox = new HBox();
      hBox.getChildren().add(lbl);
      hBox.setAlignment(Pos.CENTER);
      vBox.getChildren().add(hBox);
      TableView<ItemClass> table = new TableView<ItemClass>();
      // Name column
      TableColumn<ItemClass, String> nameColumn = new TableColumn<>("Name");
      nameColumn.setMinWidth(200);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
      // Value column
      TableColumn<ItemClass, String> valueColumn = new TableColumn<>("Value");
      valueColumn.setMinWidth(400);
      valueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

      table.getColumns().addAll(nameColumn, valueColumn);
      table.setItems(getItems(Main.ruleDetails.get(key), rule));
      vBox.getChildren().add(table);
    }

    HBox hBox = new HBox();
    Button done = new Button("Done");
    done.setOnAction(e -> {
      doneOnAction(window);
    });
    hBox.getChildren().add(done);
    hBox.setAlignment(Pos.BOTTOM_CENTER);
    vBox.getChildren().add(hBox);
    compntEditScene = new Scene(vBox, 700, 450);

    window.setScene(compntEditScene);
    window.show();
  }

  private void doneOnAction(Stage window) {
    //
    String pos = null;
    if (ruleComponent != null) {
      NodeInfo ni = Main.ruleComponentDetails.get(key);
      pos = update(ni, ruleComponent);
    }
    if (ruleSet != null) {
      NodeInfo ni = Main.ruleSetDetails.get(key);
      update(ni, ruleSet);
    }
    if (rule != null) {
      NodeInfo ni = Main.ruleDetails.get(key);
      update(ni, rule);
    }
    int newIndex = 0;
    if (pos != null) {
      if (pos.startsWith("+")) {
        int i = Integer.parseInt(pos.substring(1).trim());
        newIndex = index + i;
        EditScene.linkList.add(newIndex, key);
        EditScene.linkList.remove(index - 1);
      } else if (pos.startsWith("-")) {
        int i = Integer.parseInt(pos.substring(1).trim());
        newIndex = index - i - 1;
        EditScene.linkList.add(newIndex, key);
        EditScene.linkList.remove(index);
      } else {
        int i = Integer.parseInt(pos.trim());
        newIndex = i;

        if (newIndex > index) {
          EditScene.linkList.add(newIndex, key);
          EditScene.linkList.remove(index - 1);
        }
        if (newIndex < index) {
          EditScene.linkList.add(newIndex - 1, key);
          EditScene.linkList.remove(index);
        }
      }
      ArrayList<String> al = new ArrayList<String>(EditScene.linkList);
      Main.ruleSetMap.put(Main.key, al);
      EditScene editScene = new EditScene();
      editScene.getScene4(window);
    } else {
      AlertBox.display("Invalid Priority", "Please provide proper priority. E.g. +X, -X, X. Where 'X' is a + int.");
      window.setScene(compntEditScene);
      window.show();
    }

  }

  public ObservableList<ItemClass> getItems(NodeInfo ni, HashMap<String, TextField> updates) {
    FXCollections.observableArrayList().clear();
    ObservableList<ItemClass> items = FXCollections.observableArrayList();
    ArrayList<Attribute> al = ni.getAttr();
    for (Attribute attr : al) {
      String attrName = attr.getAttributeName();
      String attrValue = attr.getAttributeValue();
      TextField txtFld = new TextField(attrValue);
      if (attrName.equalsIgnoreCase(PARAM_ID)) {
        txtFld.setEditable(false);
      }
      updates.put(attrName, txtFld);
      items.add(new ItemClass(attrName, txtFld));
    }
    ArrayList<XmlElement> xl = ni.getxEle();
    for (XmlElement xEle : xl) {
      String eleName = xEle.getElementName();
      String eleValue = xEle.getElementValue();
      if (eleName.contains(PARAM_PRIORITY)) {
        eleValue = String.valueOf(index);
      }
      Label label = new Label(eleName);
      TextField txtFld = new TextField(eleValue);
      if (label.getText().equalsIgnoreCase(PARAM_ID)) {
        txtFld.setEditable(false);
      }
      updates.put(eleName, txtFld);
      items.add(new ItemClass(eleName, txtFld));
    }
    return items;
  }

  public String update(NodeInfo ni, HashMap<String, TextField> ruleComponent) {
    String index = null;
    ArrayList<Attribute> attribAl = ni.getAttr();
    for (Attribute attrib : attribAl) {
      TextField tF = ruleComponent.get(attrib.getAttributeName());
      attrib.setAttributeValue(tF.getText());
    }
    ArrayList<XmlElement> exEle = ni.getxEle();
    for (XmlElement exEl : exEle) {
      TextField tF = ruleComponent.get(exEl.getElementName());
      exEl.setElementValue(tF.getText());
      if (exEl.getElementName().contains(PARAM_PRIORITY)) {
        index = exEl.getElementValue();
        if (index.equals("") || (index.startsWith("+") && index.length() == 1)
            || (index.startsWith("-") && index.length() == 1)) {
          index = null;
        }
      }

    }
    return index;
  }
}
