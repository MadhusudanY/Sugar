package myPackage;

import java.util.ArrayList;

public class NodeInfo {

  ArrayList<Attribute>  attr;
  ArrayList<XmlElement> xEle;

  public NodeInfo(ArrayList<Attribute> attr, ArrayList<XmlElement> xEle) {
    super();
    this.attr = attr;
    this.xEle = xEle;
  }

  public ArrayList<Attribute> getAttr() {
    return attr;
  }

  public void setAttr(ArrayList<Attribute> attr) {
    this.attr = attr;
  }

  public ArrayList<XmlElement> getxEle() {
    return xEle;
  }

  public void setxEle(ArrayList<XmlElement> xEle) {
    this.xEle = xEle;
  }

}
