package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateScene {

  public static Scene        createScene;
  HashMap<String, TextField> ruleComponent = new HashMap<String, TextField>();
  HashMap<String, TextField> ruleSet       = new HashMap<String, TextField>();
  HashMap<String, TextField> rule          = new HashMap<String, TextField>();

  public void createRuleset(Stage window) {
    String key1 = Main.ruleComponentDetails.keySet().iterator().next();
    String key2 = Main.ruleSetDetails.keySet().iterator().next();
    NodeInfo niComp = Main.ruleComponentDetails.get(key1);
    NodeInfo niRuleSet = Main.ruleSetDetails.get(key2);
    GridPane grid1 = new GridPane();
    grid1.setPadding(new Insets(15, 15, 15, 15));
    grid1.setVgap(10);
    grid1.setHgap(12);

    int row = 0;

    Label label1 = new Label("Component Details : ");
    grid1.getChildren().add(label1);
    GridPane.setConstraints(label1, 1, row++);
    row = getGridPane(grid1, niComp, row++, ruleComponent);
    row++;

    Label label2 = new Label("RuleSet Details : ");
    grid1.getChildren().add(label2);
    GridPane.setConstraints(label2, 1, row++);
    row = getGridPane(grid1, niRuleSet, row++, ruleSet);
    row++;
    // rule = new HashMap<String, TextField>();
    // Label lbl = new Label("Rule Details : ");
    // grid1.getChildren().add(lbl);
    // GridPane.setConstraints(lbl, 1, row++);
    // getGridPane(grid1, Main.ruleDetails, row++, rule);
    HBox hBox = new HBox();
    Button create = new Button("Create");
    create.setOnAction(e -> {
      saveRuleSet(window, niComp, niRuleSet);
    });
    hBox.getChildren().add(create);
    hBox.setAlignment(Pos.BOTTOM_CENTER);
    VBox vBox = new VBox();
    vBox.getChildren().addAll(grid1, hBox);
    createScene = new Scene(vBox, 650, 550, true);
    window.setScene(createScene);
    window.show();
  }

  private void saveRuleSet(Stage window, NodeInfo niComp, NodeInfo niRuleSet) {
    String compID = null;
    String ruleSetID = null;
    if (Main.ruleComponentDetails.get("id") == null) {
      compID = ruleComponent.get("id").getText().trim();
    }
    if (Main.ruleSetDetails.get("id") == null) {
      ruleSetID = ruleSet.get("id").getText().trim();
    }
    if (compID == null || ruleSetID == null || (compID.length() < 1) || (ruleSetID.length() < 1)
        || !(compID.equals(ruleSetID))) {
      AlertBox.display("Ruleset Creation", "Id exists or is invalid.Please update.");
      window.setScene(createScene);
    } else {
      if (ruleComponent.size() != 0) {
        ArrayList<Attribute> newAl = new ArrayList<Attribute>();
        ArrayList<XmlElement> newXEle = new ArrayList<XmlElement>();
        ArrayList<Attribute> al = niComp.getAttr();
        ArrayList<XmlElement> xe = niComp.getxEle();
        for (Attribute attr : al) {
          String attrName = attr.getAttributeName();
          String attrValue = ruleComponent.get(attrName).getText();

          Attribute attribute = new Attribute(attrName, attrValue);
          newAl.add(attribute);
        }
        for (XmlElement xEle : xe) {
          String eleName = xEle.getElementName();
          String eleValue = ruleComponent.get(eleName).getText();

          XmlElement xmlElement = new XmlElement(eleName, eleValue);
          newXEle.add(xmlElement);
        }
        NodeInfo newNi = new NodeInfo(newAl, newXEle);
        Main.ruleComponentDetails.put(compID, newNi);
      }

      if (ruleSet.size() != 0) {
        ArrayList<Attribute> newAl = new ArrayList<Attribute>();
        ArrayList<XmlElement> newXEle = new ArrayList<XmlElement>();
        ArrayList<Attribute> al = niRuleSet.getAttr();
        ArrayList<XmlElement> xe = niRuleSet.getxEle();
        for (Attribute attr : al) {
          String attrName = attr.getAttributeName();
          String attrValue = ruleSet.get(attrName).getText();

          Attribute attribute = new Attribute(attrName, attrValue);
          newAl.add(attribute);
        }
        for (XmlElement xEle : xe) {
          String eleName = xEle.getElementName();
          String eleValue = ruleSet.get(eleName).getText();

          XmlElement xmlElement = new XmlElement(eleName, eleValue);
          newXEle.add(xmlElement);
        }
        NodeInfo newNi = new NodeInfo(newAl, newXEle);
        Main.ruleSetDetails.put(compID, newNi);
      }
      if (Main.ruleSetMap.get(ruleSetID) == null) {
        Main.ruleSetMap.put(ruleSetID, new ArrayList<String>());
      } else {
        AlertBox.display("Ruleset Creation", "Id exists or is invalid.Please update.");
        window.setScene(createScene);
      }
    }
    Main.key = ruleSetID;
    EditScene editScene = new EditScene();
    editScene.getScene4(window);
    window.show();
  }

  public void createRule(Stage window) {
    String key1 = Main.ruleComponentDetails.keySet().iterator().next();
    String key2 = Main.ruleDetails.keySet().iterator().next();
    NodeInfo niComp = Main.ruleComponentDetails.get(key1);
    NodeInfo niRule = Main.ruleDetails.get(key2);
    GridPane grid1 = new GridPane();
    grid1.setPadding(new Insets(15, 15, 15, 15));
    grid1.setVgap(10);
    grid1.setHgap(12);

    int row = 0;

    Label label1 = new Label("Component Details : ");
    grid1.getChildren().add(label1);
    GridPane.setConstraints(label1, 1, row++);
    row = getGridPane(grid1, niComp, row++, ruleComponent);
    row++;

    Label label2 = new Label("Rule Details : ");
    grid1.getChildren().add(label2);
    GridPane.setConstraints(label2, 1, row++);
    row = getGridPane(grid1, niRule, row++, rule);
    row++;
    // rule = new HashMap<String, TextField>();
    // Label lbl = new Label("Rule Details : ");
    // grid1.getChildren().add(lbl);
    // GridPane.setConstraints(lbl, 1, row++);
    // getGridPane(grid1, Main.ruleDetails, row++, rule);
    HBox hBox = new HBox();
    Button create = new Button("Create");
    create.setOnAction(e -> {
      saveRule(window, niComp, niRule);
    });
    hBox.getChildren().add(create);
    hBox.setAlignment(Pos.BOTTOM_CENTER);
    VBox vBox = new VBox();
    vBox.getChildren().addAll(grid1, hBox);
    createScene = new Scene(vBox, 650, 550, true);
    window.setScene(createScene);
    window.show();
  }

  private void saveRule(Stage window, NodeInfo niComp, NodeInfo niRule) {
    String compID = null;
    String ruleID = null;
    if (Main.ruleComponentDetails.get("id") == null) {
      compID = ruleComponent.get("id").getText().trim();
    }
    if (Main.ruleDetails.get("id") == null) {
      ruleID = rule.get("id").getText().trim();
    }
    if (compID == null || ruleID == null || (compID.length() < 1) || (ruleID.length() < 1)
        || !(compID.equals(ruleID))) {
      AlertBox.display("Rule Creation", "Id exists or is invalid.Please update.");
      window.setScene(createScene);
    } else {
      if (ruleComponent.size() != 0) {
        ArrayList<Attribute> newAl = new ArrayList<Attribute>();
        ArrayList<XmlElement> newXEle = new ArrayList<XmlElement>();
        ArrayList<Attribute> al = niComp.getAttr();
        ArrayList<XmlElement> xe = niComp.getxEle();
        for (Attribute attr : al) {
          String attrName = attr.getAttributeName();
          String attrValue = ruleComponent.get(attrName).getText();

          Attribute attribute = new Attribute(attrName, attrValue);
          newAl.add(attribute);
        }
        for (XmlElement xEle : xe) {
          String eleName = xEle.getElementName();
          String eleValue = ruleComponent.get(eleName).getText();

          XmlElement xmlElement = new XmlElement(eleName, eleValue);
          newXEle.add(xmlElement);
        }
        NodeInfo newNi = new NodeInfo(newAl, newXEle);
        Main.ruleComponentDetails.put(compID, newNi);
      }

      if (rule.size() != 0) {
        ArrayList<Attribute> newAl = new ArrayList<Attribute>();
        ArrayList<XmlElement> newXEle = new ArrayList<XmlElement>();
        ArrayList<Attribute> al = niRule.getAttr();
        ArrayList<XmlElement> xe = niRule.getxEle();
        for (Attribute attr : al) {
          String attrName = attr.getAttributeName();
          String attrValue = rule.get(attrName).getText();

          Attribute attribute = new Attribute(attrName, attrValue);
          newAl.add(attribute);
        }
        for (XmlElement xEle : xe) {
          String eleName = xEle.getElementName();
          String eleValue = rule.get(eleName).getText();

          XmlElement xmlElement = new XmlElement(eleName, eleValue);
          newXEle.add(xmlElement);
        }
        NodeInfo newNi = new NodeInfo(newAl, newXEle);
        Main.ruleDetails.put(compID, newNi);
      }
    }

    window.setScene(EditScene.editScene);
    window.show();

  }

  private int getGridPane(GridPane grid1, NodeInfo ni, int row, HashMap<String, TextField> updates) {
    ArrayList<Attribute> al = ni.getAttr();
    for (Attribute attr : al) {
      String attrName = attr.getAttributeName();
      Label label = new Label(attrName);
      TextField txtFld = new TextField();
      txtFld.setPrefWidth(500);

      updates.put(attrName, txtFld);
      int col = 0;
      GridPane.setConstraints(label, col, row);
      grid1.getChildren().add(label);
      col = 1;
      GridPane.setConstraints(txtFld, col, row);
      txtFld.setPrefWidth(500);
      grid1.getChildren().add(txtFld);
      row++;
    }
    ArrayList<XmlElement> xl = ni.getxEle();
    for (XmlElement xEle : xl) {
      String eleName = xEle.getElementName();
      Label label = new Label(eleName);
      TextField txtFld = new TextField();
      updates.put(eleName, txtFld);
      int col = 0;
      GridPane.setConstraints(label, col, row);
      grid1.getChildren().add(label);
      col = 1;
      GridPane.setConstraints(txtFld, col, row);
      grid1.getChildren().add(txtFld);
      row++;
    }
    return row - 1;
  }
}
