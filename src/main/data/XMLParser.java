/*
 * XML Parsing class
 * 
 * Bugs: None known
 *
 * @author Team Atlantis
 */
package data;

import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Arrays;

public class XMLParser 
{
  private File XMLFile;
  private DocumentBuilder dBuilder;
  private Document doc;
  
  public XMLParser(File XMLFile)
  {
    this.XMLFile = XMLFile;
	try
	{
	  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  dBuilder = dbFactory.newDocumentBuilder();
    }
	catch (Exception e) 
	{
	  e.printStackTrace();
    }
  }
  
  public Marquee XMLReader() 
  {
	int segNum = 0;
    try 
    {
	  doc = dBuilder.parse(XMLFile);
	} 
    catch (Exception e) 
    {
	  e.printStackTrace();
	}
	doc.getDocumentElement().normalize();
    Marquee marquee = new Marquee();
	NodeList msgList = doc.getElementsByTagName("message");
	for (int m = 0; m < msgList.getLength(); m++) 
	{   	
	  Message message = new Message();
	  marquee.setMessage(message);
      NodeList msgChildList = msgList.item(m).getChildNodes();
      for (int i = 0; i < msgChildList.getLength(); i++) 
      {
        Node msgChildNode = msgChildList.item(i);
        if(msgChildNode.getNodeType() == Node.ELEMENT_NODE) 
        {
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
            case "marqueeFullscreen":
            {
                marquee.setFullscreen(Boolean.parseBoolean(msgChildNode.getTextContent()));
            }
            break;
            case "marqueeMaxSize":
            {
                marquee.setMaxSize(Boolean.parseBoolean(msgChildNode.getTextContent()));
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
              TextSegment segment = new TextSegment();
              NodeList segChildList = msgChildNode.getChildNodes();
              for (int j = 0; j < segChildList.getLength(); j++) 
              {
                Node segChildNode = segChildList.item(j);
                if(segChildNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                  switch(segChildNode.getNodeName()) 
                  { 
                    case "duration":
                    {
                      segment.setDuration(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "repeat":
                    {
                      segment.setRepeat(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "scrollDirection":
                    {
                      segment.setScrollDirection(ScrollDirection.valueOf(segChildNode.getTextContent()));
                    }
                    break;
                    case "effectEn":
                    {
                      segment.setEntranceEffect(EntranceTransition.valueOf(segChildNode.getTextContent()));
                    }
                    break;
                    case "effectMi":
                    {
                      segment.setMiddleEffect(MiddleEffect.valueOf(segChildNode.getTextContent()));
                    }
                   	break;
                    case "effectEx":
                    {
                      segment.setExitEffect(ExitTransition.valueOf(segChildNode.getTextContent()));
                    }
                  	break;
                    case "text":
                    {
                      segment.setText(segChildNode.getTextContent());
                    }
                  	break;
                    case "borderColors":
                    {
                      segment.setBorderColors(colorsFromString(segChildNode.getTextContent()));
                    }
                  	break;
                    case "borderEffect":
                    {
                      segment.setBorderEffect(BorderEffect.valueOf(segChildNode.getTextContent()));
                    }
                  	break;
                    case "paddingColor":
                    {
                      segment.setPaddingColor(Color.valueOf(segChildNode.getTextContent()));
                    }
                  	break;
                    case "textColors":
                    {
                      segment.setTextColors(colorsFromString(segChildNode.getTextContent()));
                    }
                  	break;
                  }
                }
              }
              message.addSegment(segNum, segment);
              segNum++;
            }           
            break;
            case "imageSegment":
            {
              ImageSegment segment = new ImageSegment();
              NodeList segChildList = msgChildNode.getChildNodes();
              for (int j = 0; j < segChildList.getLength(); j++) 
              {
                Node segChildNode = segChildList.item(j);
                if(segChildNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                  switch(segChildNode.getNodeName()) 
                  { 
                    case "duration":
                    {
                      segment.setDuration(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "repeat":
                    {
                      segment.setRepeat(Integer.parseInt(segChildNode.getTextContent()));
                    }
                    break;
                    case "scrollDirection":
                    {
                      segment.setScrollDirection(ScrollDirection.valueOf(segChildNode.getTextContent()));
                    }
                    break;
                    case "effectEn":
                    {
                      segment.setEntranceEffect(EntranceTransition.valueOf(segChildNode.getTextContent()));
                    }
                    break;
                    case "effectMi":
                    {
                      segment.setMiddleEffect(MiddleEffect.valueOf(segChildNode.getTextContent()));
                    }
                   	break;
                    case "effectEx":
                    {
                      segment.setExitEffect(ExitTransition.valueOf(segChildNode.getTextContent()));
                    }
                  	break;
                    case "source":
                    {
                      segment.setSource(segChildNode.getTextContent());
                    }
                  	break;
                  }
                }
              }
              message.addSegment(segNum, segment);
              segNum++;
            }           
            break;
          }
        }
      }
	}
	return(marquee);
  }
  
  public void XMLWriter(Marquee marquee)
  {
    Message message = marquee.getMessage();

    try 
 	{
      doc = dBuilder.newDocument();
  	  Element root = doc.createElement("message");
  	  doc.appendChild(root);

  	  Element marqueeWidth = doc.createElement("marqueeWidth");
  	  root.appendChild(marqueeWidth);
  	  marqueeWidth.appendChild(doc.createTextNode(Integer.toString(marquee.getWidth())));

  	  Element marqueeHeight = doc.createElement("marqueeHeight");
  	  root.appendChild(marqueeHeight);
  	  marqueeHeight.appendChild(doc.createTextNode(Integer.toString(marquee.getHeight())));

  	  Element marqueeLedGap = doc.createElement("marqueeLedGap");
  	  root.appendChild(marqueeLedGap);
  	  marqueeLedGap.appendChild(doc.createTextNode(Integer.toString(marquee.getLedGap())));

  	  Element marqueeFullscreen = doc.createElement("marqueeFullscreen");
  	  root.appendChild(marqueeFullscreen);
  	  marqueeFullscreen.appendChild(doc.createTextNode(Boolean.toString(marquee.isFullscreen())));

  	  Element marqueeMaxSize = doc.createElement("marqueeMaxSize");
  	  root.appendChild(marqueeMaxSize);
  	  marqueeMaxSize.appendChild(doc.createTextNode(Boolean.toString(marquee.isMaxSize())));

  	  Element messageRepeatFactor = doc.createElement("repeatFactor");
  	  root.appendChild(messageRepeatFactor);
  	  messageRepeatFactor.appendChild(doc.createTextNode(Integer.toString(message.getRepeatFactor())));

  	  Element messageDelay = doc.createElement("delay");
  	  root.appendChild(messageDelay);
  	  messageDelay.appendChild(doc.createTextNode(Integer.toString(message.getDelay())));
  
  	  Element messageComments = doc.createElement("comments");
  	  root.appendChild(messageComments);
  	  messageComments.appendChild(doc.createTextNode(message.getComments()));

      message.getContents().forEach(segment -> 
      {
    	if (segment instanceof TextSegment)
        {
      	  TextSegment textSegment = (TextSegment) segment;
    	  Element seg = doc.createElement("textSegment");
    	  root.appendChild(seg);
      	  
      	  Element duration = doc.createElement("duration");
      	  seg.appendChild(duration);
      	  duration.appendChild(doc.createTextNode(Integer.toString((int) textSegment.getDuration())));

      	  Element repeat = doc.createElement("repeat");
      	  seg.appendChild(repeat);
          repeat.appendChild(doc.createTextNode(Integer.toString(textSegment.getRepeat())));

          Element delay = doc.createElement("delay");
          seg.appendChild(delay);
          delay.appendChild(doc.createTextNode(Integer.toString(textSegment.getDelay())));

          Element subDelay = doc.createElement("subDelay");
          seg.appendChild(subDelay);
          subDelay.appendChild(doc.createTextNode(Integer.toString(textSegment.getSubDelay())));

      	  Element scrollDirection = doc.createElement("scrollDirection");
      	  seg.appendChild(scrollDirection);
      	  scrollDirection.appendChild(doc.createTextNode(textSegment.getScrollDirection().name()));

      	  Element effectEn = doc.createElement("effectEn");
      	  seg.appendChild(effectEn);
      	  effectEn.appendChild(doc.createTextNode(((EntranceTransition) textSegment.getEntranceEffect()).name()));
      	  
      	  Element effectMi = doc.createElement("effectMi");
      	  seg.appendChild(effectMi);
      	  effectMi.appendChild(doc.createTextNode(textSegment.getMiddleEffect().name()));

      	  Element effectEx = doc.createElement("effectEx");
      	  seg.appendChild(effectEx);
      	  effectEx.appendChild(doc.createTextNode(((ExitTransition) textSegment.getExitEffect()).name()));

      	  Element text = doc.createElement("text");
      	  seg.appendChild(text);
      	  text.appendChild(doc.createTextNode(textSegment.getText()));

      	  Element borderColors = doc.createElement("borderColors");
      	  seg.appendChild(borderColors);
      	  borderColors.appendChild(doc.createTextNode(Arrays.toString(textSegment.getBorderColors())));

      	  Element borderEffect = doc.createElement("borderEffect");
      	  seg.appendChild(borderEffect);
      	  borderEffect.appendChild(doc.createTextNode(textSegment.getBorderEffect().name()));

      	  Element paddingColor = doc.createElement("paddingColor");
      	  seg.appendChild(paddingColor);
      	  paddingColor.appendChild(doc.createTextNode(textSegment.getPaddingColor().toString()));

      	  Element textColors = doc.createElement("textColors");
      	  seg.appendChild(textColors);
      	  textColors.appendChild(doc.createTextNode(Arrays.toString(textSegment.getTextColors())));
        }
        else
        {
          ImageSegment imageSegment = (ImageSegment) segment;
       	  Element seg = doc.createElement("imageSegment");
          root.appendChild(seg);
      	  Element duration = doc.createElement("duration");
      	  seg.appendChild(duration);
      	  duration.appendChild(doc.createTextNode(Integer.toString((int) imageSegment.getDuration())));

      	  Element repeat = doc.createElement("repeat");
      	  seg.appendChild(repeat);
      	  repeat.appendChild(doc.createTextNode(Integer.toString(imageSegment.getRepeat())));

      	  Element delay = doc.createElement("delay");
      	  seg.appendChild(delay);
      	  delay.appendChild(doc.createTextNode(Integer.toString(imageSegment.getDelay())));

      	  Element scrollDirection = doc.createElement("scrollDirection");
      	  seg.appendChild(scrollDirection);
      	  scrollDirection.appendChild(doc.createTextNode(imageSegment.getScrollDirection().name()));

      	  Element effectEn = doc.createElement("effectEn");
      	  seg.appendChild(effectEn);
      	  effectEn.appendChild(doc.createTextNode(((EntranceTransition) imageSegment.getEntranceEffect()).name()));
      	  
      	  Element effectMi = doc.createElement("effectMi");
      	  seg.appendChild(effectMi);
      	  effectMi.appendChild(doc.createTextNode(imageSegment.getMiddleEffect().name()));

      	  Element effectEx = doc.createElement("effectEx");
      	  seg.appendChild(effectEx);
      	  effectEx.appendChild(doc.createTextNode(((ExitTransition) imageSegment.getExitEffect()).name()));
      	  
      	  Element source = doc.createElement("source");
      	  seg.appendChild(source);
      	  source.appendChild(doc.createTextNode(imageSegment.getSource()));
        }
      });
  	  // write the content into xml file
  	  TransformerFactory transformerFactory = TransformerFactory.newInstance();
  	  Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

  	  DOMSource source = new DOMSource(doc);
  	  StreamResult result = new StreamResult(XMLFile);
  	  // Output to console for testing
  	  // StreamResult result = new StreamResult(System.out);
  	  transformer.transform(source, result);
    } 
 	catch (TransformerException tfe) 
 	{
  	  tfe.printStackTrace();
  	} 
  }
  
  private Color[] colorsFromString(String string)
  {
    String[] strings = string.replace("[", "").replace("]", "").split(", ");
    Color colors[] = new Color[strings.length];
    for (int c = 0; c < colors.length; c++) 
    {
      colors[c] = Color.valueOf(strings[c]);
    }
    return(colors);
  }
}