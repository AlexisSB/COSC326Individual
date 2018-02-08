package joined_writing;

import java.util.*;
public class Node implements Comparable<Node>{
        
    String word;
    HashMap<Node,Integer> forwards = new  HashMap<Node,Integer>();
    HashMap<Node,Integer> backwards = new HashMap<Node,Integer>();
    Node previousNode;
    boolean seen = false;
    int distance = Integer.MAX_VALUE;
    boolean queued = false;
        
    public Node(String word){
        this.word = word;
    }
        
    public String getWord(){
        return word;
    }

    public String toString(){
        return word + " Distance : " + distance;
    }
        
    public void addForwards(Node node, Integer commonLetters){
        this.forwards.put(node,commonLetters);
    }
        
    public void addBackwards(Node node, Integer commonLetters){
        this.backwards.put(node,commonLetters);
    }

    public int compareTo(Node n){
        return this.word.compareTo(n.word);
    }

    public void reset(){
        forwards.clear();
        previousNode = null;
        seen = false;
        distance = Integer.MAX_VALUE;
        queued = false;
    }
}
    
