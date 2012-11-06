import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ColladaToCG1
{
  /*
    Blender-Export:
      - Edit-Mode
      - mark all faces (in face selection mode)
      - Strg + T to triangulate
      - extrude individual (no movement)
      - duplicate (no movement)
      - select inverse
      - remove selected faces
  */
  public static void main(String[] args)
  {
    try 
    {
        String in = "/home/user/Arbeitsfläche/tri2.dae";
        String out = "/home/user/Arbeitsfläche/tri2.txt";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(in));
        String points = xPathToString(document, "//float_array/.");
        points = transformPoints(points);
        FileUtils.writeStringToFile(new File(out), points);
        System.out.println("fertig");
    } 
    catch (Exception e){e.printStackTrace();}
  }
  
  private static String transformPoints(String points)
  {
    String[] split = points.split(" ");
    StringBuilder result = new StringBuilder("[");
    for (int i = 0 ; i < split.length ; i = i + 3)
    {
      result.append(split[i] + "," + split[i + 1] + "," + split[i + 2] + "],\n[");
    }
    return result.substring(0,result.length() - 3);
  }
  
  private static String xPathToString(Node node, String expression)
  {
      try 
      {
          XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);
          return (String)expr.evaluate(node, XPathConstants.STRING);
      } 
      catch (Exception e) {return null;}
  }

}
