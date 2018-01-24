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

    private int frameSizeX = 1000;
    private int frameSizeY = 1000;
    Color currentColor;
    Scanner scan = new Scanner(System.in);
    ArrayList<Layer> layers = new ArrayList<Layer>();
    int startingSize = frameSizeX/3 ;
    
    public QuiltingPanel (){

        setPreferredSize (new Dimension(frameSizeX,frameSizeY));
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
        
        int startingSize = 100;
        int startingX = (frameSizeX/2); // May need double?
        int startingY = (frameSizeY/2) ; // May need double?
        int previousX = startingX;
        int previousY = startingY;
        
        //drawTest(g);
        drawSquares(g,0,layers.get(0),startingX, startingY);
    }

    public void drawTest(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(100,100,100,100);
    }
        

    public void drawSquares(Graphics g,int depth, Layer layer, int startX, int startY){
        
        //System.err.println("Recursive Case");
        g.setColor(layer.color);
        int layerSize = (int)(layer.scale * startingSize);
        int x = startX - (layerSize/2);
        int y = startY - (layerSize/2);
        int topLeftX = x;
        int topLeftY = y;
        int topRightX = topLeftX + layerSize;
        int topRightY = topLeftY;
        int bottomLeftX = topLeftX;
        int bottomLeftY = topLeftY + layerSize;
        int bottomRightX = topLeftX + layerSize;
        int bottomRightY = topRightY + layerSize;
        //System.err.println(layer);
        System.err.println("LayerSize : " + layerSize);
        //System.err.println("X coor : " + x);
        //System.err.println("Y coor : " + y);
            
        //System.err.println("Drawing A Rectangle");
        g.fillRect(x,y,layerSize,layerSize);

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
