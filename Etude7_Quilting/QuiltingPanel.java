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
@SuppressWarnings("serial")
public class QuiltingPanel extends JPanel{

    /* Size of JPanel to create*/
    private double frameSizeX;
    private double frameSizeY;
    
    Scanner scan = new Scanner(System.in);
    /*Holds information about each layer to draw*/
    ArrayList<Layer> layers = new ArrayList<Layer>();
    /* Holds information about each square that needs to be drawn*/
    ArrayList<Square> squaresToDraw = new ArrayList<Square>();
    /* A default starting size */
    double startingSize = frameSizeX/3 ;

    /** Constructor
     * Reads in input from stdin.
     * Normalises the scale of each layer
     * Adjusts panel window size so that the picture will fit on the screen
     */
    public QuiltingPanel (){

        //Read in input, create layers
        while(scan.hasNextDouble()){
            double scale = scan.nextDouble();
            int red = scan.nextInt();
            int green = scan.nextInt();
            int blue = scan.nextInt();
            Layer newLayer = new Layer(scale,red,green,blue);
            layers.add(newLayer);
        }

        normaliseLayers();
        //System.err.println("Layers : " +layers);

        //Adjust frame to fit screen
        double totalHeight = 0;
        double frameScale = 0.9;
        frameSizeX = getScreenHeight()*frameScale;
        frameSizeY = getScreenHeight()*frameScale;
        startingSize = (int) frameSizeY/4;
        
        for(Layer l : layers){
            totalHeight += l.scale;
        }
       
        startingSize = ((frameSizeY)*frameScale)/totalHeight;
        
        setPreferredSize (new Dimension((int)frameSizeX,(int)frameSizeY));
        //Start creating the scale
        createSquares();
    }

    /**
     * Gets the size of the current monitor screen.
     * @return double size of screen
     */
    public double getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getHeight();
    }

    /**
     * Normalises the scale fo each layer.
     * Picks the maximum scale. Divides all the other scales by the maximum.
     * Adjusts the scale for each layer.
     */
    public void normaliseLayers(){

        double max =0;
        for (Layer l : layers){
            if (l.getScale() > max){
                max = l.getScale();
            }
        }
        //System.err.println("Max scale : " + max );

        for( Layer l : layers){
            double currentScale = l.getScale();
            l.setScale(currentScale/max);
            //System.err.println("New Scale " + l.getScale());
        }
    }


    /** paintComponent Method Draws all shapes on panel.
     * Draws the squares layer by layer.
     * @param g - Graphics object to draw shapes on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
          
        //drawTest(g);
        int numberOfLayers = layers.size();

	for(int i = 0; i < numberOfLayers; i++){
	    for(Square s : squaresToDraw){
		//System.err.println("Square : " + s);
		if (s.getDepth() == i){
		    //System.err.println("Printing Square: " + s);
		    g.setColor(layers.get(i).getColor());
		    //System.err.println("Current Color :" + g.getColor());
		    s.drawSquare(g);
		}
	    }
	}
    }

    /**
     * Recursive starter method for creating squares to draw.
     * Sets starting layer size and then calls recursive method.
     */
    public void createSquares(){
	double startingX = frameSizeX/2; // May need double?
        double startingY = frameSizeY/2; // May need double?
		
	createSquares(0,layers.get(0),startingX,startingY);
    }

    /**
     * Recursive method for creating squares.
     * Calls itself four times to add a square at each corner.
     * @param depth - the number of layers below the current one.
     * @param layer - the current layer
     * @param centreX - the x coordinate that marks the centre of the square
     * @param centreY - the y coordinate that marks the centre of the square.
     */
    public void createSquares(int depth, Layer layer, double centreX, double centreY){
        
        //System.err.println("Recursive Case");
        double layerSize = layer.scale * startingSize;
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
        
        //System.err.println(layer);
        //System.err.println("LayerSize : " + layerSize);
        //System.err.println("X coor : " + x);
        //System.err.println("Y coor : " + y);
            
        //System.err.println("Creating a Square");
	//System.err.println( layer.getColor() + ", "+ depth +","+ x + "," + y + "," +layerSize);
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

    /**
     * Square holds details of each square to draw.
     */
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
            g.fillRect(x,y,height,width);
	}

	public String toString(){
	    return "Layer: " + depth +" "+ color + " Size : " + height + " X,Y = " + x + "," +y;
	}

    }

    /**
     * Layer holds information about each layer to draw.
     */
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
