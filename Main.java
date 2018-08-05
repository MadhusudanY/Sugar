package myPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
  static HashMap<String, ArrayList<String>> ruleSetMap           = new HashMap<String, ArrayList<String>>();
  static HashMap<String, ArrayList<String>> viewMap              = new HashMap<String, ArrayList<String>>();

  static HashMap<String, NodeInfo>          ruleSetDetails       = new HashMap<String, NodeInfo>();
  static HashMap<String, NodeInfo>          ruleComponentDetails = new HashMap<String, NodeInfo>();
  static HashMap<String, NodeInfo>          ruleDetails          = new HashMap<String, NodeInfo>();
  static ArrayList<String>                  keyList              = new ArrayList<String>();
  static String                             key;                                                            // Input
  // Variable
  static String                             ruleSetXMLPath;                                                 // Input
                                                                                                            // Variable
  static boolean                            isRuleSetMapSet      = false;

  public static boolean isRuleSetMapSet() {
    return isRuleSetMapSet;
  }

  private static void setRuleSetMapSet(boolean val) {
    isRuleSetMapSet = val;
  }

  public static void main(String[] args) throws XPathExpressionException {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Stage window;
    window = primaryStage;
    window.setTitle("Sugar_v0");
    primaryStage.getIcons().add(new Image("file:resources/sumtotal_0.png"));
    FirstScene scene1 = new FirstScene();
    scene1.getScene1(window);
  }

  static void setRuleSetMap() {
    if (isRuleSetMapSet) {
      return;
    }
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder;
    Document doc = null;
    try {
      if (!ruleSetXMLPath.isEmpty()) {
        builder = factory.newDocumentBuilder();
        doc = builder.parse(ruleSetXMLPath);
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();
        // Create XPath object
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile("//ruleset");
        NodeList nodes_RuleSet = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes_RuleSet.getLength(); i++) {
          ArrayList<String> ruleSetComponentList = new ArrayList<String>(); // TODO which one is better perf. 'new'
                                                                            // within
                                                                            // for loop OR object.clear() OR garbage
                                                                            // collection point of view.
          Node node = nodes_RuleSet.item(i);
          setRuleSetDetails(node);
          String id = node.getAttributes().getNamedItem("id").getTextContent();
          expr = xpath.compile("//ruleset[@id='" + id + "']/ruleset-component");
          NodeList nodes_RuleComps = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
          for (int j = 0; j < nodes_RuleComps.getLength(); j++) {
            Node childNode = nodes_RuleComps.item(j);
            String comp = childNode.getAttributes().getNamedItem("id").getTextContent();
            setRuleComponentDetails(childNode);
            ruleSetComponentList.add(comp);
          }
          ruleSetMap.put(id, ruleSetComponentList);
        }

        expr = xpath.compile("//rule");
        NodeList node_Rule = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int k = 0; k < node_Rule.getLength(); k++) {
          Node node = node_Rule.item(k);
          setRuleDetails(node);
        }
      }
      setRuleSetMapSet(true);
    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
      e.printStackTrace();
    }

  }

  private static void setRuleDetails(Node node) {
    String id = node.getAttributes().getNamedItem("id").getTextContent();
    if (ruleDetails.get(id) == null) {
      ArrayList<Attribute> al_Attrib = new ArrayList<Attribute>();
      ArrayList<XmlElement> al_Ele = new ArrayList<XmlElement>();
      NamedNodeMap nl = node.getAttributes();
      for (int i = 0; i < nl.getLength(); i++) {
        Attr attr = (Attr) nl.item(i);
        String name = attr.getName();
        String value = attr.getValue();
        Attribute attrib = new Attribute(name, value);
        al_Attrib.add(attrib);
      }
      NodeList nodeList = node.getChildNodes();
      for (int j = 0; j < nodeList.getLength(); j++) {
        Node ele = nodeList.item(j);
        if ((node.getNodeType() == Node.ELEMENT_NODE) && (ele.getLocalName() != null) && (!ele.hasAttributes())) {
          XmlElement xEle = new XmlElement(ele.getLocalName(), ele.getTextContent());
          al_Ele.add(xEle);
        }
      }
      NodeInfo ni = new NodeInfo(al_Attrib, al_Ele);
      ruleDetails.put(id, ni);
    }
  }

  private static void setRuleComponentDetails(Node node) {
    String id = node.getAttributes().getNamedItem("id").getTextContent();
    if (ruleComponentDetails.get(id) == null) {
      ArrayList<Attribute> al_Attrib = new ArrayList<Attribute>();
      ArrayList<XmlElement> al_Ele = new ArrayList<XmlElement>();
      NamedNodeMap nl = node.getAttributes();
      for (int i = 0; i < nl.getLength(); i++) {
        Attr attr = (Attr) nl.item(i);
        String name = attr.getName();
        String value = attr.getValue();
        Attribute attrib = new Attribute(name, value);
        al_Attrib.add(attrib);
      }
      NodeList nodeList = node.getChildNodes();
      for (int j = 0; j < nodeList.getLength(); j++) {
        Node ele = nodeList.item(j);
        if ((node.getNodeType() == Node.ELEMENT_NODE) && (ele.getLocalName() != null) && (!ele.hasAttributes())) {
          XmlElement xEle = new XmlElement(ele.getLocalName(), ele.getTextContent());
          al_Ele.add(xEle);
        }
      }
      NodeInfo ni = new NodeInfo(al_Attrib, al_Ele);
      ruleComponentDetails.put(id, ni);
    }
  }

  private static void setRuleSetDetails(Node node) {
    String id = node.getAttributes().getNamedItem("id").getTextContent();
    if (ruleSetDetails.get(id) == null) {
      ArrayList<Attribute> al_Attrib = new ArrayList<Attribute>();
      ArrayList<XmlElement> al_Ele = new ArrayList<XmlElement>();
      NamedNodeMap nl = node.getAttributes();
      for (int i = 0; i < nl.getLength(); i++) {
        Attr attr = (Attr) nl.item(i);
        String name = attr.getName();
        String value = attr.getValue();
        Attribute attrib = new Attribute(name, value);
        al_Attrib.add(attrib);
      }
      NodeList nodeList = node.getChildNodes();
      for (int j = 0; j < nodeList.getLength(); j++) {
        Node ele = nodeList.item(j);
        if ((node.getNodeType() == Node.ELEMENT_NODE) && (ele.getLocalName() != null) && (!ele.hasAttributes())) {
          XmlElement xEle = new XmlElement(ele.getLocalName(), ele.getTextContent());
          al_Ele.add(xEle);
        }
      }
      NodeInfo ni = new NodeInfo(al_Attrib, al_Ele);
      ruleSetDetails.put(id, ni);
    }
  }

  public static void buttonAction(String find, String findField, String in, String inField) {
    ArrayList<String> al = new ArrayList<String>();
    al.add(find + "#" + findField);
    Main.key = findField;
    al.add(in + "#" + inField);
    Main.ruleSetXMLPath = inField;
    Stage alert = AlertBox.pleasewait();
    Main.setInput(al);
    Main.setRuleSetMap();

    Main.keyList.add(Main.key);
    while (Main.keyList.size() > 0) {
      String pin = Main.keyList.get(0);
      for (String ruleSet : Main.ruleSetMap.keySet()) {
        ArrayList<String> ruleCompList = Main.ruleSetMap.get(ruleSet);
        if ((ruleCompList.contains(pin)) && (Main.viewMap.get(ruleSet) == null)) {
          Main.viewMap.put(ruleSet, ruleCompList);
          Main.keyList.add(ruleSet);
        }
      }
      Main.keyList.remove(pin);
    }

    AlertBox.dismiss(alert);
  }

  public static void setObjectList(ArrayList<String> objectList) {
    for (String key : Main.viewMap.keySet()) {
      objectList.addAll(Main.viewMap.get(key));
    }
  }

  public static boolean setInput(ArrayList<String> al) {
    File f = new File("resources/input.txt");
    FileWriter fw = null;
    BufferedWriter bw = null;
    try {
      fw = new FileWriter(f);
      bw = new BufferedWriter(fw);
      for (String item : al) {
        bw.write(item);
        bw.write("\n");
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
        if (fw != null)
          fw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return false;
  }

  public static String getInput(String keyword) {
    try {
      File f = new File("resources/input.txt");
      BufferedReader b = new BufferedReader(new FileReader(f));
      String readLine;
      while ((readLine = b.readLine()) != null) {
        if (readLine.startsWith(keyword)) {
          return readLine.substring(keyword.length() + 1);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
