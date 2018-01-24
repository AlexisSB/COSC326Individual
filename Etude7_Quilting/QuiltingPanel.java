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
    ArrayList<Square> squaresToDraw = new ArrayList<Square>();
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
        //System.err.println("Layers : " +layers);
        double totalHeight = 0;
        frameSizeX = getScreenHeight()*0.9;
        frameSizeY = getScreenHeight()*0.9;
        startingSize = (int) frameSizeY/4;
        
        for(Layer l : layers){
            double layerHeight = l.scale *startingSize; 
            totalHeight += layerHeight;
        }
       
        startingSize = (startingSize *(frameSizeY/totalHeight))*0.9;
        setPreferredSize (new Dimension((int)frameSizeX,(int)frameSizeY));
        createSquares();
       
      
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
  
        //drawTest(g);
        int numberOfLayers = layers.size();

	for(int i = 0; i < numberOfLayers; i++){
	    for(Square s : squaresToDraw){
		System.err.println("Square : " + s);
		if (s.getDepth() == i){
		    System.err.println("Printing Square: " + s);
		    g.setColor(layers.get(i).getColor());
		    System.err.println("Current Color :" + g.getColor());
		    s.drawSquare(g);
		}
	    }
	}
	
    }

    public void drawTest(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(100,100,100,100);
    }
  
    public void createSquares(){
	double startingX = frameSizeX/2; // May need double?
        double startingY = frameSizeY/2; // May need double?
		
	createSquares(0,layers.get(0),startingX,startingY);
    }

    public void createSquares(int depth, Layer layer, double centreX, double centreY){
        
        //System.err.println("Recursive Case");
        double layerSize = (int)(layer.scale * startingSize);
        double x = centreX - (layerSize/2);
        double y = centreY - (layerSize/2);
        double topLeftX = x;
        double topLeftY = y;
        double topRightX = topLeftX + layerSize;
        double topRightY = topLeftY;
        double bottomLeftX = topLeftX;
        double bottomLeftY = topLeftY + layerSize;
        double bottomRightX = topLeftX + layerSize;
        double bottomRightY = topRightY + layerSize;
        
        System.err.println(layer);
        System.err.println("LayerSize : " + layerSize);
        System.err.println("X coor : " + x);
        System.err.println("Y coor : " + y);
            
        System.err.println("Creating a Square");
	System.err.println( layer.getColor() + ", "+ depth +","+ x + "," + y + "," +layerSize);
	Square thisSquare = new Square(layer.getColor(),depth,(int)x,(int)y,(int)layerSize,(int)layerSize);
	squaresToDraw.add(thisSquare);
        //g.fillRect(x,y,layerSize,layerSize);


        if (depth == layers.size()-1){
                
            return;
        }else{
            createSquares(depth+1,layers.get(depth+1),topLeftX,topLeftY);
            createSquares(depth+1,layers.get(depth+1),topRightX, topRightY);
            createSquares(depth+1,layers.get(depth+1),bottomLeftX, bottomRightY);
            createSquares(depth+1,layers.get(depth+1),bottomRightX, bottomRightY);
            
        }
    }

    //add shpaes to an array of arrays instead of drawing them recursively.
  

    private class Square{
	private Color color;
	private int depth;
	private int x;
	private int y;
	private int height;
	private int width;
	
	public Square(Color color,int depth, int x,int y, int height, int width){
	    this.color = color;
	    this.depth = depth;
	    this.x = x;
	    this.y = y;
	    this.height = height;
	    this.width = width;

	}

	public int getDepth(){
	    return depth;
	}

	public void drawSquare(Graphics g){
	    g.setColor(this.color);
	    // g.fillRect(100,100,100,100);
	    System.err.println( x + "," + y + "," +height + "," +width);
	    g.fillRect(x,y,height,width);
	}

	public String toString(){
	    return "Layer: " + depth +" "+ color + " Size : " + height + " X,Y = " + x + "," +y;
	}

    }

    private class Layer{
	//private int depth;
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
