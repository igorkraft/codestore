import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/*
 * Debuggen eines Applets
 * ControlPanel in der Konsole aufrufen
 * -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
 */
public class TorusApplet extends Applet implements Runnable
{
  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  private static final long serialVersionUID = 1L;
  private List<List<double[]>> object = null;
  private BufferedImage img = null;
  private boolean rotate = true;

  private double[] rotateAroundXAxis(double[] point, double angle)
  {
    double[] result = new double[3];
    result[X] = point[X];
    result[Y] = (Math.cos(angle) * point[Y] - Math.sin(angle) * point[Z]);
    result[Z] = (Math.sin(angle) * point[Y] + Math.cos(angle) * point[Z]);
    return result;
  }
  
  private double[] rotateAroundYAxis(double[] point, double angle)
  {
    double[] result = new double[3];
    result[X] = (Math.cos(angle) * point[X] + Math.sin(angle) * point[Z]);
    result[Y] = point[Y];
    result[Z] = (-Math.sin(angle) * point[X] + Math.cos(angle) * point[Z]);
    return result;
  }
  
  private double[] rotateAroundZAxis(double[] point, double angle)
  {
    double[] result = new double[3];
    result[X] = (Math.cos(angle) * point[X] - Math.sin(angle) * point[Y]);
    result[Y] = (Math.sin(angle) * point[X] + Math.cos(angle) * point[Y]);
    result[Z] = point[Z];
    return result;
  }
  
  private double[] translateAlongXAxis(double[] point, double scalar)
  {
    double[] result = new double[3];
    result[X] = point[X] + scalar;
    result[Y] = point[Y];
    result[Z] = point[Z];
    return result;
  }
  
  private double[] translateAlongYAxis(double[] point, double scalar)
  {
    double[] result = new double[3];
    result[X] = point[X];
    result[Y] = point[Y] + scalar;
    result[Z] = point[Z];
    return result;
  }
  
  private double[] translateAlongZAxis(double[] point, double scalar)
  {
    double[] result = new double[3];
    result[X] = point[X];
    result[Y] = point[Y];
    result[Z] = point[Z] + scalar;
    return result;
  }
  
  private String triangleToString(List<double[]> triangle)
  {
    DecimalFormat formater = new DecimalFormat( "0.0000" );
    String result = "T{p[";
    result = result + formater.format(triangle.get(0)[X]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(0)[Y]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(0)[Z]).replace(',', '.') + ",";
    result = result + formater.format(triangle.get(1)[X]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(1)[Y]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(1)[Z]).replace(',', '.') + ",";
    result = result + formater.format(triangle.get(2)[X]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(2)[Y]).replace(',', '.') + " ";
    result = result + formater.format(triangle.get(2)[Z]).replace(',', '.') + "]}\n";
    return result;
  }
  
  private List<double[]> createStartSlice(double radiusSlice,int pointCountSlice,double radiusTorus)
  {
    List<double[]> points = new ArrayList<double[]>(pointCountSlice);
    points.add(new double[]{0,radiusSlice,0});
    double angle = 2 * Math.PI/((double)pointCountSlice);
    for(int i = 0 ; i < pointCountSlice - 1 ; i++)
    {
      points.add(this.rotateAroundXAxis(points.get(i), angle));
      points.set(i,this.translateAlongYAxis(points.get(i), radiusTorus));
      points.set(i,this.translateAlongZAxis(points.get(i), 2));
    }
    points.set(pointCountSlice - 1,this.translateAlongYAxis(points.get(pointCountSlice - 1), radiusTorus));
    points.set(pointCountSlice - 1,this.translateAlongZAxis(points.get(pointCountSlice - 1), 2));
    return points;
  }
  
  private List<List<double[]>> generateObject(double radiusSlice,int pointCountSlice,double radiusTorus,int sliceCount)
  {
    List<List<double[]>> triangles = new ArrayList<List<double[]>>();
    List<double[]> slice = this.createStartSlice(radiusSlice,pointCountSlice,radiusTorus);
    double angle = 2 * Math.PI/((double)sliceCount);
    for (int curSlice = 0 ; curSlice < sliceCount * 1.75 ; curSlice++)
    {
      List<double[]> newSlice = new ArrayList<double[]>();
      for (int curPoint = 0 ; curPoint < pointCountSlice ; curPoint++)
      {
        int nextPoint = (curPoint + 1) % pointCountSlice;
        List<double[]> triangleA = new ArrayList<double[]>();
        List<double[]> triangleB = new ArrayList<double[]>();
        newSlice.add(curPoint,this.translateAlongZAxis(this.rotateAroundZAxis(slice.get(curPoint), angle),-0.14));
        if (nextPoint != 0) 
          newSlice.add(nextPoint,this.translateAlongZAxis(this.rotateAroundZAxis(slice.get(nextPoint), angle),-0.14));
        triangleA.add(slice.get(curPoint));
        triangleA.add(newSlice.get(curPoint));
        triangleA.add(newSlice.get(nextPoint));
        triangleB.add(slice.get(curPoint));
        triangleB.add(newSlice.get(nextPoint));
        triangleB.add(slice.get(nextPoint));
        triangles.add(triangleA);
        triangles.add(triangleB);
      }
      slice = newSlice;
    }
    return triangles;
  }
  
  public String createVrmlScene()
  {
    String scene = "\n"+
    "#VRML V2.0 utf8\n"+
    "\n"+
    "Background\n"+
    "{\n"+
    " skyColor 0.3 0.3 0.8 \n"+
    "}\n"+
    "\n"+
    "PROTO T\n"+
    "[\n"+
    " exposedField MFVec3f p  []\n"+
    " exposedField SFColor c  1 0 0\n"+
    "]\n"+
    "{\n"+ 
    " Transform\n"+
    " {\n"+
    "  children\n"+
    "  [\n"+
    "   Shape\n"+
    "   {\n"+
    "    appearance Appearance\n"+
    "    {\n"+
    "     material Material\n"+
    "     {\n"+
    "      diffuseColor IS c\n"+
    "     }\n"+
    "    }\n"+
    "    geometry IndexedFaceSet\n"+
    "    {\n"+
    "     coord Coordinate\n"+
    "     {\n"+
    "      point IS p\n"+
    "     }\n"+
    "     coordIndex [0 1 2]\n"+
    "    }\n"+
    "   }\n"+
    "## Hier einkommentieren, um Innenseite darzustellen\n"+
    "## (Vorsicht: doppelt so viele Dreiecke!)\n"+
    "#   Shape\n"+
    "#   {\n"+
    "#    appearance Appearance\n"+
    "#    {\n"+
    "#     material Material\n"+
    "#     {\n"+
    "#      diffuseColor 1 1 0\n"+
    "#     }\n"+
    "#    }\n"+
    "#    geometry IndexedFaceSet\n"+
    "#    {\n"+
    "#     coord Coordinate\n"+
    "#     {\n"+
    "#      point IS p\n"+
    "#     }\n"+
    "#     coordIndex [2 1 0]\n"+
    "#    }\n"+
    "#   }\n"+ 
    "  ]\n"+
    " }\n"+
    "}\n"+
    "\n"+
    "########################################################################\n"+
    "\n";
    String result = "";
    for (List<double[]> triangle : this.object)
    {
      result = result + this.triangleToString(triangle);
    }
    return scene + result;
  }
  
  public void refreshObject(String inRadiusSlice,String inPointCountSlice,String inRadiusTorus,String inSliceCount)
  {
    double radiusSlice     = Double.parseDouble(inRadiusSlice);
    int    pointCountSlice = Integer.parseInt(inPointCountSlice);
    double radiusTorus     = Double.parseDouble(inRadiusTorus);
    int    sliceCount      = Integer.parseInt(inSliceCount);
    this.object = this.generateObject(radiusSlice, pointCountSlice, radiusTorus, sliceCount);
    this.drawObject();
    this.repaint();
  }
  
  private void drawTriangle(Graphics g, int pAX,int pBX,int pCX, int pAY,int pBY,int pCY)
  {
    g.drawLine(pAX,pAY,pBX,pBY);
    g.drawLine(pAX,pAY,pCX,pCY);
    g.drawLine(pBX,pBY,pCX,pCY);
  }
  
  private void drawFilledTriangle(Graphics g, int pAX,int pBX,int pCX, int pAY,int pBY,int pCY)
  {
    g.fillPolygon(new int[]{pAX,pBX,pCX},new int[]{pAY,pBY,pCY}, 3);
  }
  
  @Override
  public void init()
  {
    super.init();
    this.setBackground(Color.black);
    this.object = this.generateObject(0.5, 16, 2, 16);
    this.img = new BufferedImage(this.getSize().width,this.getSize().height,BufferedImage.TYPE_INT_BGR);
    (new Thread(this)).start();
  }
  
  @Override
  public void paint(Graphics g)
  {
    g.drawImage(img,0,0,null);
  }
  
  private int isTrianglePoint(int x, int y, int pAX, int pAY, int pBX, int pBY, int pCX, int pCY)
  {
    int dXA = pAX - x;
    int dYA = pAY - y;
    int dXB = pBX - x;
    int dYB = pBY - y;
    int dXC = pCX - x;
    int dYC = pCY - y;
    int aKb = dXA*dYB-dXB*dYA;
    int bKc = dXB*dYC-dXC*dYB;
    int cKa = dXC*dYA-dXA*dYC;
    if (aKb >= 0 && bKc >= 0 && cKa >= 0)
    {
      return 8;
    }
    if (aKb <= 0 && bKc <= 0 && cKa <= 0)
    {
      return 16;
    }
    return 0;
  }

  private int[] detectBorders(int a, int b, int c)
  {
    int[] result = new int[2];
    if (a < b)
    {
      if (b < c)
      {
        result[0] = a;
        result[1] = c;
      }
      else
      {
        result[1] = b;
        if (a < c)
        {
          result[0] = a;
        }
        else
        {
          result[0] = c;
        }
      }
    }
    else
    {
      if (b < c)
      {
        result[0] = b;
        if (a < c)
        {
          result[1] = c;
        }
        else
        {
          result[1] = a;
        }
      }
      else
      {
        result[0] = c;
        result[1] = a;
      }
    }
    return result;
  }
  
  private int getColor(double[] point, int shift)
  {
    double difference = point[X]*point[X]+point[Y]*point[Y]+point[Z]*point[Z];
    double maxDiff = 15; // gibt an, wie stark sich das Licht ausbreitet
    int intensity = 255 - (int) (Math.round((difference / maxDiff) * 255));
    if (intensity > 255)
    {
      intensity = 255;
    }
    if (intensity < 0)
    {
      intensity = 0;
    }
    //return (r << 16)+(g << 8)+b;
    return (intensity << shift);
  }
  
  private void drawObject()
  {
    int diff = this.getSize().width / 2;
    double factor = this.getSize().width / 6;
    double[][] zBuffer = new double[this.getSize().width][this.getSize().height];
    for (int y = 0 ; y < this.getSize().height ; y++)
      for (int x = 0 ; x < this.getSize().width ; x++)
      {
        zBuffer[x][y] = -Double.MAX_VALUE;
        this.img.setRGB(x, y, 0xFF000000);
      }
    for (List<double[]> triangle : this.object)
    {
      int pAX = (int)(triangle.get(0)[X]*factor) + diff;
      int pBX = (int)(triangle.get(1)[X]*factor) + diff;
      int pCX = (int)(triangle.get(2)[X]*factor) + diff;
      int pAY = (int)(triangle.get(0)[Y]*factor) + diff;
      int pBY = (int)(triangle.get(1)[Y]*factor) + diff;
      int pCY = (int)(triangle.get(2)[Y]*factor) + diff;
      double depth = triangle.get(0)[Z];
      int[] bX = this.detectBorders(pAX, pBX, pCX);
      int[] bY = this.detectBorders(pAY, pBY, pCY);
      for (int y = bY[0] ; y <= bY[1] ; y++)
        for (int x = bX[0] ; x <= bX[1] ; x++)
        {
          if (x < 0 || x >= this.getSize().width || y < 0 || y >= this.getSize().height)
          {
            continue;
          }
          if (zBuffer[x][y] < depth)
          {
            int shift = this.isTrianglePoint(x,y,pAX,pAY,pBX,pBY,pCX,pCY);
            if (shift != 0)
            {
              zBuffer[x][y] = depth;
              img.setRGB(x, y,this.getColor(triangle.get(0), shift));
            }
          }
        }
    }
  }
  
  @Override
  public void run()
  {
    while (this.rotate)
    {
      double angle = 2 * Math.PI / 180;
      for (List<double[]> triangle : this.object)
      {
        triangle.set(0,this.rotateAroundZAxis(triangle.get(0), angle));
        triangle.set(1,this.rotateAroundZAxis(triangle.get(1), angle));
        triangle.set(2,this.rotateAroundZAxis(triangle.get(2), angle));
        triangle.set(0,this.rotateAroundYAxis(triangle.get(0), angle));
        triangle.set(1,this.rotateAroundYAxis(triangle.get(1), angle));
        triangle.set(2,this.rotateAroundYAxis(triangle.get(2), angle));
      }
      this.drawObject();
      //########## draw ###################
      try{Thread.currentThread().sleep(10);}catch(Exception e){}
      this.repaint();
    }
  }
  
  public void startRotation()
  {
    this.rotate = true;
    (new Thread(this)).start();
  }
  
  public void stopRotation()
  {
    this.rotate = false;
  }
}
