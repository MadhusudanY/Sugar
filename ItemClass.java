package myPackage;

import javafx.scene.control.TextField;

public class ItemClass {

  private String    itemName;
  private TextField itemValue;

  public ItemClass(String itemName, TextField itemValue) {
    this.itemName = itemName;
    this.itemValue = itemValue;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public TextField getItemValue() {
    return itemValue;
  }

  public void setItemValue(String itemValue) {
    this.itemValue.setText(itemValue);
  }

}
