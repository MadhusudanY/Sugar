package myPackage;

public class Attribute {

  String attributeName;
  String attributeValue;

  public Attribute(String attributeName, String attributeValue) {
    super();
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  public String getAttributeValue() {
    return attributeValue;
  }

  public void setAttributeValue(String attributeValue) {
    this.attributeValue = attributeValue;
  }

}
