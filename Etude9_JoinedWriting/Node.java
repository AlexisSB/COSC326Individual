package joined_writing;

import java.util.*;
public class Node{
        
    String word;
    HashMap<Node,Integer> forwards = new  HashMap<Node,Integer>();
    HashMap<Node,Integer> backwards = new HashMap<Node,Integer>();
    Node previousNode;
    boolean seen = false;
        
    public Node(String word){
        this.word = word;
    }
        
    public String getWord(){
        return word;
    }

    public String toString(){
        return word;
    }
        
    public void addForwards(Node node, Integer commonLetters){
        this.forwards.put(node,commonLetters);
    }
        
    public void addBackwards(Node node, Integer commonLetters){
        this.backwards.put(node,commonLetters);
    }
}
    
