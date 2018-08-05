package myPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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

public class QuickScene {

  static String find, findField, in, inField;
  static Scene  quickScene;

  public QuickScene(String find, String findField, String in, String inField) {
    super();
    QuickScene.find = find;
    QuickScene.findField = findField;
    QuickScene.in = in;
    QuickScene.inField = inField;
  }

  public void getScene2(Stage window) {
    Main.buttonAction(find, findField, in, inField);
    HashMap<Integer, ArrayList<String>> quickView = getQuickView();
    HashMap<String, TreeItem<String>> treeItems = new HashMap<String, TreeItem<String>>();

    TreeView<String> tree;
    TreeItem<String> root = null;
    for (int a : quickView.keySet()) {
      ArrayList<String> al = quickView.get(a);
      for (String item : al) {
        String prevItem = null;
        if (!(al.indexOf(item) - 1 < 0)) {
          prevItem = al.get(al.indexOf(item) - 1);
        }
        if (!treeItems.containsKey(item)) {
          TreeItem<String> it = new TreeItem<>(item);
          treeItems.put(item, it);
          if (prevItem != null) {
            TreeItem<String> parent = treeItems.get(prevItem);
            parent.getChildren().add(it);
          } else {
            root = it;
          }
        }
      }
    }
    tree = new TreeView<>(root);
    // Back
    Button back = new Button("Back");
    back.setOnAction(e -> {
      window.setScene(FirstScene.firstScene);
    });

    // Trace Button
    Button trace = new Button();
    trace.setText("Trace");
    trace.setOnAction(e -> {
      window.setScene(TraceScene.traceScene);
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
    // hBox.getChildren().addAll(back, trace, edit);
    hBox.getChildren().addAll(back);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(tree, hBox);
    // Layout
    StackPane layout = new StackPane();
    layout.getChildren().add(vBox);
    layout.autosize();
    quickScene = new Scene(layout, 700, 450);
    window.setScene(quickScene);
    window.show();
  }

  private static HashMap<Integer, ArrayList<String>> getQuickView() {
    HashMap<Integer, ArrayList<String>> firsStagetMap = new HashMap<Integer, ArrayList<String>>();
    HashMap<Integer, ArrayList<String>> secondStageMap = new HashMap<Integer, ArrayList<String>>();
    ArrayList<String> objectList = new ArrayList<String>();
    Main.setObjectList(objectList);
    int i = 1;
    for (String key : Main.viewMap.keySet()) {
      if (!objectList.contains(key)) {
        ArrayList<String> trace = new ArrayList<String>();
        trace.add(key);
        firsStagetMap.put(i++, trace);
      }
    }
    for (i = 1; i <= firsStagetMap.size(); i++) {
      String comp = firsStagetMap.get(i).get(0);
      setQuickRecursive(comp, firsStagetMap.get(i));
    }
    int c = 0;
    for (int item : firsStagetMap.keySet()) {
      ArrayList<String> al = firsStagetMap.get(item);
      Iterator<String> it = al.listIterator();
      HashSet<Integer> indexSet = new HashSet<Integer>();
      int index = 0;
      while (it.hasNext()) {
        if (it.next().equals(Main.key)) {
          indexSet.add(index);
        }
        index++;
      }
      for (int a : indexSet) {
        ArrayList<String> xl = new ArrayList<String>();
        int skipCounter = 0;
        for (int b = a; b >= 0; b--) {
          if (al.get(b).equals(Main.key) && (skipCounter == 0)) {
            xl.add(al.get(b));
          } else if (al.get(b).equals("[") && (skipCounter == 0)) {
            // xl.add(al.get(b));
          } else if (al.get(b).equals("]")) {
            skipCounter += 2;
          } else if (skipCounter > 0) {
            skipCounter -= 1;
          } else if (skipCounter == 0) {
            xl.add(al.get(b));
          }
        }
        secondStageMap.put(c++, xl);
      }
    }
    return secondStageMap;
  }

  private static void setQuickRecursive(String comp, ArrayList<String> al) {
    ArrayList<String> bl = Main.viewMap.get(comp);
    for (String item : bl) {
      if (item.equals(Main.key)) {
        al.add("[");
        al.add(item);
        al.add("]");
      } else if (Main.viewMap.containsKey(item)) {
        al.add("[");
        al.add(item);
        setQuickRecursive(item, al);
        al.add("]");
      }
    }
  }

}