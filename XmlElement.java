package myPackage;

public class XmlElement {

  String elementName;
  String elementValue;

  public XmlElement(String elementName, String elementValue) {
    super();
    this.elementName = elementName;
    this.elementValue = elementValue;
  }

  public String getElementName() {
    return elementName;
  }

  public void setElementName(String elementName) {
    this.elementName = elementName;
  }

  public String getElementValue() {
    return elementValue;
  }

  public void setElementValue(String elementValue) {
    this.elementValue = elementValue;
  }

}
