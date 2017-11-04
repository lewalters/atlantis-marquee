/**
 * XML Parsing class
 * 
 * Bugs: None known
 *
 * @author Team Atlantis
 */
package data;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;

public class XMLParser 
{
  private File XMLFile;
  private DocumentBuilderFactory dbFactory;
  private DocumentBuilder dBuilder;
  private Document doc;
  
  public XMLParser(File XMLFile)
  {
    this.XMLFile = XMLFile;
	try
	{
	  dbFactory = DocumentBuilderFactory.newInstance();
	  dBuilder = dbFactory.newDocumentBuilder();
	  doc = dBuilder.parse(XMLFile);
    }
	catch (Exception e) 
	{
	  e.printStackTrace();
    }
  }
  
  public void XMLReader() 
  {
	doc.getDocumentElement().normalize();
    Marquee marquee = new Marquee(0, 0, 0);
//  Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};

	NodeList msgList = doc.getElementsByTagName("message");
	for (int m = 0; m < msgList.getLength(); m++) 
	{   	
	  Message message = new Message("", 0, 0,"");
	  marquee.setMessage(message);
      NodeList msgChildList = msgList.item(m).getChildNodes();
      for (int i = 0; i < msgChildList.getLength(); i++) 
      {
        Node msgChildNode = msgChildList.item(i);
        if(msgChildNode.getNodeType() == Node.ELEMENT_NODE) 
        {
          System.out.println(msgChildNode.getNodeName() + ":" + msgChildNode.getTextContent().trim());
          switch(msgChildNode.getNodeName()) 
          {
            case "marqueeWidth":
            {
            	marquee.setWidth(Integer.parseInt(msgChildNode.getTextContent()));
            }
            break;
            case "marqueeHeight":
            {
            	marquee.setHeight(Integer.parseInt(msgChildNode.getTextContent()));
            }
            break;
            case "marqueeLedGap":
            {
            	marquee.setLedGap(Integer.parseInt(msgChildNode.getTextContent()));
            }
            break;
            case "name":
            {
            	message.setName(msgChildNode.getTextContent());
            }
            break;
            case "repeatFactor":
            {
            	message.setRepeatFactor(Integer.parseInt(msgChildNode.getTextContent()));
            }
            break;
            case "delay":
            {
            	message.setDelay(Integer.parseInt(msgChildNode.getTextContent()));
            }
            break;
            case "comments":
            {
            	message.setComments(msgChildNode.getTextContent());
            }
            break;
            case "textSegment":
            {
              System.out.println("Child: found textSegment");
              NodeList segChildList = msgChildNode.getChildNodes();
              for (int j = 0; j < segChildList.getLength(); j++) 
              {
                Node segChildNode = segChildList.item(j);
                if(segChildNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                  System.out.println(segChildNode.getNodeName() + ":" + segChildNode.getTextContent().trim());
                  switch(segChildNode.getNodeName()) 
                  { 
                    case "duration":
                    {
//                      segment.setDuration(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "speed":
                    {
//                      segment.setSpeed(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "scrollDirection":
                    {
//                      segment.setScrollDirection(ScrollDirection.valueOf(segChildNode.getTextContent()));
                    }
                    break;
// Color[] borderColors, BorderEffect borderEffect, 
// Color paddingColor, MarqueeEffect effectEn, StaticEffect effectMi, 
// MarqueeEffect effectEx, String textColor, String text)

                    case "effectEn":
                    break;
                    case "effectMi":
                    break;
                    case "effectEx":
                    break;
                    case "hLength":
                    break;
                    case "vLength":
                    break;
                    case "size":
                    break;
                  }
                }
              }
//            Segment segment1 = new TextSegment(5, 10, ScrollDirection.STATIC, colorList, BorderEffect.NONE, Color.WHITE, ScrollDirection.LEFT, StaticEffect.NONE, TransitionEffect.RANDOM_LIGHT, "DA70D6", "abcdef");
//            Segment segment2 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect.FADE, StaticEffect.BLINK, TransitionEffect.FADE, "gbf.png");
//            message.addSegment(0, segment1);
//            message.addSegment(1, segment2);
            }           
            break;
          }
        }
      }
	}
  }
  
  public void XMLWriter()
  {
 	try 
 	{
  	  Element rootElement = doc.createElement("message");
  	  doc.appendChild(rootElement);

  	  // staff elements
  	  Element staff = doc.createElement("Staff");
  	  rootElement.appendChild(staff);

  	  // set attribute to staff element
  	  Attr attr = doc.createAttribute("id");
  	  attr.setValue("1");
  	  staff.setAttributeNode(attr);

  	  // shorten way
  	  // staff.setAttribute("id", "1");

  	  // firstname elements
  	  Element firstname = doc.createElement("firstname");
  	  firstname.appendChild(doc.createTextNode("yong"));
  	  staff.appendChild(firstname);

  	  // lastname elements
  	  Element lastname = doc.createElement("lastname");
  	  lastname.appendChild(doc.createTextNode("mook kim"));
  	  staff.appendChild(lastname);

  	  // nickname elements
  	  Element nickname = doc.createElement("nickname");
  	  nickname.appendChild(doc.createTextNode("mkyong"));
  	  staff.appendChild(nickname);
 	  // salary elements
 	  Element salary = doc.createElement("salary");
 	  salary.appendChild(doc.createTextNode("100000"));
 	  staff.appendChild(salary);

  	  // write the content into xml file
  	  TransformerFactory transformerFactory = TransformerFactory.newInstance();
  	  Transformer transformer = transformerFactory.newTransformer();
  	  DOMSource source = new DOMSource(doc);
  	  StreamResult result = new StreamResult(XMLFile);

  	  // Output to console for testing
  	  // StreamResult result = new StreamResult(System.out);

  	  transformer.transform(source, result);

  	  System.out.println("File saved!");
    } 
 	catch (TransformerException tfe) 
 	{
  	  tfe.printStackTrace();
  	}
  }
}