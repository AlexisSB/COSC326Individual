package joined_writing;

import java.util.*;

public class JoinedWordsApp{

    public static ArrayList<Node> singleOutput = new ArrayList<Node>();
    public static ArrayList<Node> doubleOutput = new ArrayList<Node>();
    public static ArrayList<Node> dictionary = new ArrayList<Node>();

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
       
        //read input from args

        //Read in dictionary.

        //For each word in dictionary create a node
        while(scan.hasNextLine()){
            String nextWord = scan.nextLine();
            Node wordNode = new Node(nextWord);
            dictionary.add(wordNode);
        }
        /*
        for(Node n: dictionary){
            
            findForwards(n,dictionary);
            //findBackwards(n,dictionary);
        }
        */
        /*
        for (Node n: dictionary){
            System.err.println(n);
            System.err.println("\t" + n.forwards);
        }
        */
        //This is where we do the search
        if(args.length>0){
            String firstWord = args[0];
            String lastWord = args[1];
            Node firstNode = findNode(firstWord, dictionary);
            Node lastNode = findNode(lastWord, dictionary);
            
            System.out.println(searchSingle(firstNode, lastNode));
            System.out.println("Search Single");
            System.out.println(singleOutput);
            //output = new ArrayList<Node>();
            
            System.out.println(searchDouble(firstNode,lastNode));
            System.out.println("Search Double");
            System.out.println(doubleOutput);
        }
        /* 
        for (Node n: dictionary){
            System.err.println(n);
            System.err.println("\t" + n.forwards);
        }
        */
        
        //For each node find the words that are linked going forward, so words that start with the end of the word.
        //Find words that end with the start of the word.
        // Can create double pointers for each one one i.e when one points forward it also points back from another node.
        

    }

    public static boolean searchSingle(Node root, Node target){
        //ArrayList<Node> outputHolder = new ArrayList<Node>();
        singleOutput.add(root);
        for (int depth = 0 ; depth < dictionary.size()/10;depth++){
            boolean found = DLSSingle(root,target,depth);
            if(found){
                return found;
            }
        }
        return false;
    }

    public static boolean DLSSingle(Node node, Node target, int depth){
        
        if(depth == 0 && node.getWord().equals(target.getWord())){
            return true;
        }
        if (depth > 0){
            //findForwards(node,dictionary);
            findSinglyLinkedForwards(node,dictionary);
            //findDoublyLinkedForwards(node,dictionary);
            for(Map.Entry<Node,Integer>  n : node.forwards.entrySet()){
                singleOutput.add(n.getKey());
                //This line prints the current state of the search.
                System.err.println(singleOutput);
                boolean found = DLSSingle(n.getKey(),target,depth-1);
                
                if(found){
                    return found;
                }
                singleOutput.remove(n.getKey());
            }
        }
        return false;
    }


    public static boolean searchDouble(Node root, Node target){
        //ArrayList<Node> outputHolder = new ArrayList<Node>();
        doubleOutput.add(root);
        for (int depth = 0 ; depth < dictionary.size()/10;depth++){
            boolean found = DLSDouble(root,target,depth);
            if(found){
                return found;
            }
        }
        return false;
    }

    public static boolean DLSDouble(Node node, Node target, int depth){
        
        if(depth == 0 && node.getWord().equals(target.getWord())){
            return true;
        }
        if (depth > 0){
            //findForwards(node,dictionary);
            findDoublyLinkedForwards(node,dictionary);
            for(Map.Entry<Node,Integer>  n : node.forwards.entrySet()){
                doubleOutput.add(n.getKey());
                //This line prints the current state of the search.
                System.err.println(doubleOutput);
                boolean found = DLSDouble(n.getKey(),target,depth-1);
                
                if(found){
                    return found;
                }
                doubleOutput.remove(n.getKey());
            }
        }
        return false;
    }

   
    public static Node findNode(String word, ArrayList<Node> dictionary){
        for(Node n : dictionary){
            if(n.getWord().equals(word)){
                return n;
            }
        }
        return null;

    }

    public static void findSinglyLinkedForwards(Node node, ArrayList<Node> dictionary){
        String word = node.getWord();
        node.forwards.clear();
        for(Node n : dictionary){
            String otherWord = n.getWord();
            if(!(word.equals(otherWord))){
                for (int i = 0; i < word.length(); i++){
                    if(otherWord.length() >=word.length()-i){ // Checks that other word is long enough
                        if(word.substring(i)
                           .equals(otherWord.substring(0,word.length()-i))){
                            if(word.substring(i).length() >= (int)Math.ceil(word.length() /2.0)
                               || word.substring(i).length() >= (int) Math.ceil(otherWord.length()/2.0)){
                                //System.err.println("Check in double linked loop");
                                node.addForwards(n,word.length()-i);
                                System.err.println(node.forwards);
                            }
                        }
                    }
                }
            }

        }
    }


    /*
    public static void findDoublyLinkedForwards(Node node, ArrayList<Node> dictionary){
        if (!(node.forwards.isEmpty())){
            System.err.println("Done");
        }else{
            findForwards(node, dictionary);
        }
            

    }
    */
    
    public static void findDoublyLinkedForwards(Node node, ArrayList<Node> dictionary){
        String word = node.getWord();
        node.forwards.clear();
        for(Node n : dictionary){
            String otherWord = n.getWord();
            if(!(word.equals(otherWord))){
                for (int i = 0; i < word.length(); i++){
                    if(otherWord.length() >=word.length()-i){ // Checks that other word is long enough
                        if(word.substring(i)
                           .equals(otherWord.substring(0,word.length()-i))){
                            if(word.substring(i).length() >= (int)Math.ceil(word.length() /2.0)
                               && word.substring(i).length() >= (int) Math.ceil(otherWord.length()/2.0)){
                                //System.err.println("Check in double linked loop");
                                node.addForwards(n,word.length()-i);
                                System.err.println(node.forwards);
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    public static void findForwards(Node node, ArrayList<Node> dictionary){
        String word = node.getWord();
        for(Node n: dictionary){
            String otherWord = n.getWord();
            for (int i = 0; i < word.length(); i++){
                if(otherWord.length() >= word.length()-i){
                    if (word.substring(i)
                        .equals(otherWord.substring(0,word.length()-i))){
                        node.addForwards(n,word.length()-i);
                        //n.addBackwards(node, word.length()-i);
                        System.err.println(node.forwards);
                    }
                }
            }
        }
    }
}
