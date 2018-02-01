package joined_writing;

import java.util.*;


/**
 * COSC326 Etude 9 JoinedUpWriting.
 * Program finds a series of words where each word shares letters with the
 * next and previous word. Finds a path through the dictionary for
 * two words given as arguments. Dictionary taken from stdin.
 * Uses iterative deepening depth first search to search through possible
 * paths.
 * @author Alexis Barltrop
 */
public class JoinedWordsApp{

    /*Holds list of nodes to solution for singly linked words*/
    public static ArrayList<Node> singleOutput = new ArrayList<Node>();
    /*Holds list of nodes to solution for double linked words*/
    public static ArrayList<Node> doubleOutput = new ArrayList<Node>();
    /*Holds the entire dictionary as list of nodes*/
    public static ArrayList<Node> dictionary = new ArrayList<Node>();


    /**
     * Main method.
     * Processes words in dictionary into nodes.
     * Reads in the start and end word
     * Searchs through for singly linked path first.
     * Then searches for doubly linked path.
     * @param args - two arguments should be start and end words.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        //Read in dictionary from stdin.
        //Process to nodes
        while(scan.hasNextLine()){
            String nextWord = scan.nextLine();
            Node wordNode = new Node(nextWord);
            dictionary.add(wordNode);
        }
        
        /*
        // Used to process the entire dictionary.
        for(Node n: dictionary){
            
            findForwards(n,dictionary);
            //findBackwards(n,dictionary);
        }
        */
        
        /*
        // Prints the entire dictionary and connections
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
	    if( firstNode== null || lastNode == null){
		System.err.println("Word cannot be found in dictionary");
		System.exit(0);
	    }
            //Singly Linked Search

	   
	    /*
            System.out.println(searchSingle(firstNode, lastNode));
            System.out.println("Search Single");
            System.out.println(singleOutput);

            //Doubly Linked Search
	    */
	    searchDoubleBreadthFirst(firstNode,lastNode);
	    doubleOutput = followBreadthFirstPath(firstNode, lastNode);
	   
	    // System.out.println(searchDouble(firstNode,lastNode));
            System.out.println("Search Double");
            System.out.println(doubleOutput);
            
	    
        }else{
            System.err.println("No arguments entered. Please give first and last word");
            System.exit(0);
        }
    }
    
    /**
     * Recursive Method Start for singly linked search.
     * Iterative Deepening Depth First Search.
     * Only searches down to a depth limit (one tenth of dictionary size).
     * @param root - starting node for search
     * @param target - goal node.
     * @return true if target found, false if not.
     */
    public static boolean searchSingle(Node root, Node target){
        int depthLimit = dictionary.size()/10;
        singleOutput.add(root);
        for (int depth = 0 ; depth < depthLimit;depth++){
            boolean found = DLSSingle(root,target,depth);
            if(found){
                return found;
            }
        }
        return false;
    }

    /**
     * Recursive helper method for singly linked search.
     * Depth First Search to fixed depth.
     * @param node - parent node to search from
     * @param target - goal node
     * @param depth - maximum depth of search.
     * @return true if target found, false if not.
     */
    public static boolean DLSSingle(Node node, Node target, int depth){
        if(depth == 0 && node.getWord().equals(target.getWord())){
            return true;
        }
        if (depth > 0){
            //findForwards(node,dictionary);
            findSinglyLinkedForwards(node,dictionary);
            for(Map.Entry<Node,Integer>  n : node.forwards.entrySet()){
                singleOutput.add(n.getKey());
                //This line prints the current state of the search.
                //System.err.println(singleOutput);
                boolean found = DLSSingle(n.getKey(),target,depth-1);
                if(found){
                    return found;
                }
                singleOutput.remove(n.getKey());
            }
        }
        return false;
    }

    /**
     * Method finds potential words to form a singly linked connection with.
     * Searchs through dictionary for words where the common part is
     * at least as half as long as at least one of the words.
     * @param node - Node with word to find links from.
     * @param dictionary - dictionary of words to search through.
     */
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
                                //System.err.println(node.forwards);
                            }
                        }
                    }
                }
            }

        }
    }
    ///////////////////////////////////////////////////////


    public static ArrayList<Node> followBreadthFirstPath(Node root, Node target){
	ArrayList<Node> output = new ArrayList<Node>();
	Node currentNode = target;
	System.err.println("Generating output arraylist");
	while (currentNode.previousNode != root){
	    System.err.println("Adding " + currentNode.previousNode );
	    output.add(0,currentNode.previousNode);
	    currentNode = currentNode.previousNode;
	}
	
	output.add(target);
	return output;
    }
    
    public static boolean searchDoubleBreadthFirst(Node root, Node target){
	ArrayDeque<Node> queue = new ArrayDeque<Node>();
	doubleOutput.add(root);
	queue.add(root);
	while (!(queue.isEmpty())){
	    System.err.println(queue);
	    //System.out.println(queue.size());
	    Node currentNode = queue.remove();
	    //System.err.println(currentNode);
	    //Add node to output
	    //Check if current node is goal
	    if(currentNode.getWord().equals(target.getWord())){
		System.out.println("Found target");
		return true;
	    }else{
		//Explore the node.
		//System.err.println("Exploring tree");
		findDoublyLinkedForwards(currentNode, dictionary);
		
		//If the node hasn't been visited then add to queue
		for(Map.Entry<Node,Integer>  n : currentNode.forwards.entrySet()){
		    //if (n.getKey().previousNode == null){
		    //System.err.println("Adding nodes to queue");
		    if(!(n.getKey().seen)){
			n.getKey().previousNode = currentNode;
			n.getKey().seen = true;
			queue.add(n.getKey());
		    }
		}
	    }
	    
	}
	return false;
	

    }

    /////////////////////////////////////////////////////
    
    /**
     * Recursive Method Start for doubly linked search.
     * Iterative Deepening Depth First Search.
     * Only searches down to a depth limit (one tenth of dictionary size).
     * @param root - starting node for search
     * @param target - goal node.
     * @return true if target found, false if not.
     */
    public static boolean searchDouble(Node root, Node target){
        doubleOutput.add(root);
        for (int depth = 0 ; depth < dictionary.size()/10;depth++){
            boolean found = DLSDouble(root,target,depth);
            if(found){
                return found;
            }
        }
        return false;
    }

    /**
     * Recursive helper method for doubly linked search.
     * Depth First Search to fixed depth.
     * @param node - parent node to search from
     * @param target - goal node
     * @param depth - maximum depth of search.
     * @return true if target found, false if not.
     */
    public static boolean DLSDouble(Node node, Node target, int depth){
        
        if(depth == 0 && node.getWord().equals(target.getWord())){
            return true;
        }
        if (depth > 0){
            //findForwards(node,dictionary);
            findDoublyLinkedForwards(node,dictionary);
            for(Map.Entry<Node,Integer>  n : node.forwards.entrySet()){
		if(!(doubleOutput.contains(n.getKey()))){
		    doubleOutput.add(n.getKey());
		}else{
		    return false;
		}
                
                //This line prints the current state of the search.
                //System.err.println(doubleOutput);
                
                boolean found = DLSDouble(n.getKey(),target,depth-1);
                if(found){
                    return found;
                }
                doubleOutput.remove(n.getKey());
            }
        }
        return false;
    }

    /**
     * Method finds potential words to form a doubly linked connection with.
     * Searchs through dictionary for words where the common part is
     * at least as half as long as both words.
     * @param node - Node with word to find links from.
     * @param dictionary - dictionary of words to search through.
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
                                //System.err.println(node.forwards);
                            }
                        }
                    }
                }
            }
        }
    }
    

    /**
     * Searches through the dictionary for a node matching the given string.
     * @param word - String to look for in node dictionary
     * @param dictionary - dictionary of word nodes.
     * @return the node that matches the given word else null if not found.
     */
    public static Node findNode(String word, ArrayList<Node> dictionary){
        for(Node n : dictionary){
            if(n.getWord().equals(word)){
                return n;
            }
        }
        return null;

    }

    /**
     * Searches through the dictionary for words that join up.
     * That is the proper suffix of one is the proper prefix of the other.
     * Adds results to the node data fields.
     * @param node - node to find links from.
     * @param dictionary - dictionary of node words to look through.
     */
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
                        //System.err.println(node.forwards);
                    }
                }
            }
        }
    }
}
