package quilting;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Quilting class support class drawing panel.
 * COSC326 Etude 7 SS 2018
 * @author Alexis Barltrop
 */
public class QuiltingPanel extends JPanel{

    private double frameSizeX = 1000;
    private double frameSizeY = 1000;
    Color currentColor;
    Scanner scan = new Scanner(System.in);
    ArrayList<Layer> layers = new ArrayList<Layer>();
    double startingSize = frameSizeX/3 ;
    
    public QuiltingPanel (){

        
        /*JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        */
        while(scan.hasNextLine()){
            //Read in input
            double scale = scan.nextDouble();
            int red = scan.nextInt();
            int green = scan.nextInt();
            int blue = scan.nextInt();
            Layer newLayer = new Layer(scale,red,green,blue);
            layers.add(newLayer);
        }
        normaliseLayers();
        double totalHeight = 0;
        frameSizeX = getScreenHeight();
        frameSizeY = getScreenHeight();
        startingSize = (int) getScreenHeight()/4;
        
        for(Layer l : layers){
            double layerHeight = l.scale *startingSize; 
            totalHeight += layerHeight;
        }
        
        startingSize = (startingSize *(frameSizeY/totalHeight));
        setPreferredSize (new Dimension((int)frameSizeX,(int)frameSizeY));
    }

    public double getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getHeight();

    }

    public void normaliseLayers(){

        double max =0;
        for (Layer l : layers){
            if (l.getScale() > max){
                max = l.getScale();
            }
        }
        System.err.println("Max scale : " + max );

        for( Layer l : layers){
            double currentScale = l.getScale();
            l.setScale(currentScale/max);
            System.err.println("New Scale " + l.getScale());
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        currentColor = new Color(0,0,255);
        
        //int startingSize = 100;
        double startingX = (frameSizeX/2); // May need double?
        double startingY = (frameSizeY/2) ; // May need double?
        double previousX = startingX;
        double previousY = startingY;
        
        //drawTest(g);
        drawSquares(g,0,layers.get(0),startingX, startingY);
    }

    public void drawTest(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(100,100,100,100);
    }
        

    public void drawSquares(Graphics g,int depth, Layer layer, double startX, double startY){
        
        //System.err.println("Recursive Case");
        g.setColor(layer.color);
        double layerSize = (int)(layer.scale * startingSize);
        double x = startX - (layerSize/2);
        double y = startY - (layerSize/2);
        double topLeftX = x;
        double topLeftY = y;
        double topRightX = topLeftX + layerSize;
        double topRightY = topLeftY;
        double bottomLeftX = topLeftX;
        double bottomLeftY = topLeftY + layerSize;
        double bottomRightX = topLeftX + layerSize;
        double bottomRightY = topRightY + layerSize;
        //System.err.println(layer);
        System.err.println("LayerSize : " + layerSize);
        //System.err.println("X coor : " + x);
        //System.err.println("Y coor : " + y);
            
        //System.err.println("Drawing A Rectangle");
        g.fillRect((int)x,(int)y,(int)layerSize,(int)layerSize);

        if (depth == layers.size()-1){
                
            return;
        }else{
            drawSquares(g,depth+1,layers.get(depth+1),topLeftX,topLeftY);
            drawSquares(g,depth+1,layers.get(depth+1),topRightX, topRightY);
            drawSquares(g,depth+1,layers.get(depth+1),bottomLeftX, bottomRightY);
            drawSquares(g,depth+1,layers.get(depth+1),bottomRightX, bottomRightY);
            
        }
    }
  



    private class Layer{

        private double scale;
        private Color color;
        
        public Layer(){
            this(0.0,0,0,0);
        }
        public Layer(double scale,int r, int g, int b ){
            this.color = new Color(r,g,b);
            this.scale = scale;
        }

        public String toString(){
            return "Layer : " + scale + " " + color;
        }

        public Color getColor(){
            return this.color;
        }

        public double getScale(){
            return this.scale;
        }
        public void setScale(double scale){
            this.scale = scale;
        }
    }
}
