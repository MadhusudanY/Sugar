package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TraceScene {
  static String find, findField, in, inField;
  static Scene  traceScene;

  public TraceScene(String find, String findField, String in, String inField) {
    super();
    TraceScene.find = find;
    TraceScene.findField = findField;
    TraceScene.in = in;
    TraceScene.inField = inField;
  }

  public void getScene3(Stage window) {
    Main.buttonAction(find, findField, in, inField);
    HashMap<Integer, ArrayList<String>> firsStageMap = new HashMap<Integer, ArrayList<String>>();
    ArrayList<String> objectList = new ArrayList<String>();
    Main.setObjectList(objectList);
    int i = 1;
    for (String key : Main.viewMap.keySet()) {
      if (!objectList.contains(key)) {
        ArrayList<String> trace = new ArrayList<String>();
        trace.add(key);
        firsStageMap.put(i++, trace);
      }
    }
    for (i = 1; i <= firsStageMap.size(); i++) {
      String comp = firsStageMap.get(i).get(0);
      setTraceRecursive(comp, firsStageMap.get(i));
    }
    normalizeMap(Main.key, firsStageMap);
    TreeView<String> tree;
    TreeItem<String> parent = null;
    TreeItem<String> root = new TreeItem<String>();
    for (int a : firsStageMap.keySet()) {
      ArrayList<String> al = firsStageMap.get(a);
      TreeItem<String> it = new TreeItem<String>(al.get(0));
      parent = it;
      for (int x = 1; x < al.size(); x++) {
        it = new TreeItem<>(al.get(x));
        parent.getChildren().add(it);
      }
      root.getChildren().add(parent);
    }

    tree = new TreeView<String>(root);
    tree.setShowRoot(false);
    // Back
    Button back = new Button("Back");
    back.setOnAction(e -> {
      window.setScene(FirstScene.firstScene);
    });

    // QuickLook Button
    Button quick = new Button();
    quick.setText("Trace");
    quick.setOnAction(e -> {
      window.setScene(QuickScene.quickScene);
    });

    // Edit Button
    Button edit = new Button();
    edit.setText("Edit");
    edit.setOnAction(e -> {
      window.setScene(EditScene.editScene);
    });

    HBox hBox = new HBox();
    hBox.setAlignment(Pos.BOTTOM_CENTER);
    hBox.setPadding(new Insets(10, 10, 10, 10));
    hBox.setSpacing(100);
    // hBox.getChildren().addAll(back, quick, edit);
    hBox.getChildren().addAll(back);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(tree, hBox);
    // Layout
    StackPane layout = new StackPane();
    layout.getChildren().add(vBox);
    layout.autosize();
    traceScene = new Scene(layout, 700, 450);
    window.setScene(traceScene);
    window.show();

  }

  private void normalizeMap(String key, HashMap<Integer, ArrayList<String>> firstStageMap) {

    for (int i : firstStageMap.keySet()) {
      int lastIndex = 0;
      ArrayList<String> al = firstStageMap.get(i);
      for (int j = 0; j < al.size(); j++) {
        if (al.get(j).equals(Main.key)) {
          lastIndex = j;
        }
      }
      al.subList(lastIndex + 1, al.size()).clear();
    }

  }

  private static void setTraceRecursive(String comp, ArrayList<String> al) {
    ArrayList<String> bl = Main.ruleSetMap.get(comp);
    for (String item : bl) {
      if (item.equals(Main.key)) {
        al.add(item);
      } else if (Main.ruleSetMap.containsKey(item)) {
        setTraceRecursive(item, al);
      } else {
        al.add(item);
      }
    }
  }
}
