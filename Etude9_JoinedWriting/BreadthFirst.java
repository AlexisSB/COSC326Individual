package joined_writing;

import java.util.*;

public class BreadthFirst{

    public static ArrayList<Node> dictionary = new ArrayList<Node>();
    public static HashSet<String> wordList = new HashSet<String>();
    private enum LinkOption {SINGLY_LINKED, DOUBLY_LINKED};
    
    public static void main (String[] args){
	/* Read in dictionary*/
	/* Convert to Nodes*/
	readInDictionary();
	/* Read in first and last words */
	if(args.length != 2){
	    System.err.println("Please enter start word and end word only");
	    System.exit(0);
	}
	
	String firstWord = args[0];
	String lastWord = args[1];
	Node firstNode = findNode(firstWord, dictionary);
	Node lastNode = findNode(lastWord, dictionary);

	/* Check the words are in the dictionary*/
        if ( firstNode == null || lastNode == null){
	    System.err.println("Cannot find words in dictionary");
	    System.exit(0);
	}
	/* Start search for singly linked words*/
	LinkOption linkType = LinkOption.SINGLY_LINKED;
	boolean singlyLinkedSearch =searchBFS(firstNode, lastNode,linkType); 
	System.err.println(singlyLinkedSearch);
        
	/* When you find a node search the dictionary for its links*/
        
	if(singlyLinkedSearch){
	    System.out.println(followBreadthFirstPath(firstNode,lastNode));
	}else{
	    System.out.println("Impossible");
	}
	System.out.println(wordList.size());
	
	//Clean up
	for(Node n : dictionary){
	    n.reset();
	    wordList.clear();
	}
        
        
        /* Doubly Linked Search*/
	linkType = LinkOption.DOUBLY_LINKED;

	boolean doublyLinkedSearch = searchBFS(firstNode,lastNode,linkType);
	System.err.println(doublyLinkedSearch);
	if(doublyLinkedSearch){
	    System.out.println(followBreadthFirstPath(firstNode,lastNode));
	}else{
	    System.out.println("Impossible");
	}
        
	/* Until you find the last word*/
    }
    

   
       
    public static boolean searchBFS(Node root, Node target, LinkOption linkType){
	ArrayDeque<Node> queue = new ArrayDeque<Node>();
        root.previousNode = null;
        root.distance = 0;
        int count = 0;
        wordList.add(root.getWord());
        queue.add(root);
        int debugcount = 0;
        while(!(queue.isEmpty())){
            //System.err.println(queue+"\n");
            //System.err.print(queue.size() + "\t");

            Node currentNode = queue.poll();
            //if(!(currentNode.seen)){
            // if(!(wordList.contains(currentNode.getWord()))){
            //wordList.add(currentNode.getWord());
            if(linkType == LinkOption.SINGLY_LINKED){
                expandNode(currentNode, dictionary,LinkOption.SINGLY_LINKED);
            }else{
                expandNode(currentNode, dictionary,LinkOption.DOUBLY_LINKED);
            }
            /*
            if(debugcount < 5){
                System.err.println(currentNode.getWord()+ "\n" + currentNode.forwards);
                debugcount++;
            }else{
                System.exit(0);
            }
            */
            /*
            if (currentNode.getWord().equals("iran")){
                System.err.println(currentNode.getWord()+ "\n" + currentNode.forwards);
            }
            */
            for(Map.Entry<Node,Integer> n: currentNode.forwards.entrySet()){
                if(!(wordList.contains(n.getKey().getWord()))){
                    n.getKey().queued = true; 
                    n.getKey().previousNode = currentNode;
                    n.getKey().distance = currentNode.distance+1;
                    if(n.getKey().getWord().equals(target.getWord())){
                        return true;
                    }
                    queue.add(n.getKey());
                    wordList.add(n.getKey().getWord());
                        
                }
            }
            currentNode.seen = true;
                
                //}
        }
        return false;
    }

    public static int binarySearchForSubString(String prefix){
        int left = 0;
        int right = dictionary.size()-1;
        //System.err.println("DictSize : " +dictionary.size());
        return binarySearchForSubString(prefix,left,right);
    }
    
    public static int binarySearchForSubString(String prefix, int left, int right){
        if(left > right){
            return -1;
        }else{

            int middle = (left+right)/2;
            int oneAboveMiddle = middle-1;
            String middleWord = dictionary.get(middle).getWord();
            String wordAboveMiddle;
            if(oneAboveMiddle >=0){
                wordAboveMiddle = dictionary.get(oneAboveMiddle).getWord();
            }else{
                return middle;
            }
            String currentPrefix;
            String abovePrefix;
            if(middleWord.length() < prefix.length()){
                currentPrefix = middleWord;
            }else{
                currentPrefix = middleWord.substring(0,prefix.length());
            }
            
            if(wordAboveMiddle.length() < prefix.length()){
                abovePrefix = wordAboveMiddle;
            }else{
                abovePrefix = wordAboveMiddle.substring(0,prefix.length());
            }
            //System.err.println(middle);
            //System.err.println(prefix);
            //System.err.println(currentPrefix);
            //System.err.println(abovePrefix + "\n");
            if(currentPrefix.equals(prefix)){
               if((abovePrefix.equals(prefix))){
                   return binarySearchForSubString(prefix,left,middle-1);
               }else{
                   return middle;
               }
            }else if(currentPrefix.compareTo(prefix) > 0){
                //System.err.println(currentPrefix + " Larger than " + prefix);
                return binarySearchForSubString(prefix, left, middle-1);
            }else{
                return binarySearchForSubString(prefix, middle+1, right);
            }  
        }
    }
            
    /**
     * Method finds potential words to form a singly linked connection with.
     * Searchs through dictionary for words where the common part is
     * at least as half as long as at least one of the words.
     * @param node - Node with word to find links from.
     * @param dictionary - dictionary of words to search through.
     */
    public static void expandNode(Node node, ArrayList<Node> dictionary, LinkOption linkType){
        String word = node.getWord();
        //System.err.println(word);
        for (int i = 0; i < word.length(); i++){
            String suffix = word.substring(i);
            int start = binarySearchForSubString(suffix);
            if(start != -1){
                int count = start;
                while (count < dictionary.size()){
                    Node n = dictionary.get(count);
                    String checkWord = n.getWord();
                    if(checkWord.length() <suffix.length()){
                        //break
                        break;
                    }
                    String prefix = checkWord.substring(0,suffix.length());
                    //System.err.println("Prefix " + (count -start)+ " " + prefix);
                    if (prefix.equals(suffix)){
                        if (linkType == LinkOption.SINGLY_LINKED){
                            if((checkWord.length()<=(suffix.length()*2))
                               || (word.length() <=(suffix.length()* 2))){
                                node.addForwards(n,prefix.length());
                            }
                        }else{
			    //System.err.println(checkWord.length()/suffix.length());
                            //System.err.println(word.length()/suffix.length());
                            if((checkWord.length() <= suffix.length()*2)
                               && ((word.length()<=suffix.length()*2))){
                                node.addForwards(n,prefix.length());
                            }
                        }
                        count++;
                    }else{
                        //break
                        count= dictionary.size();
                    }
                    
                        
                }
            }
        }
    }

    public static void readInDictionary(){
	Scanner scan = new Scanner(System.in);
	while(scan.hasNextLine()){
	    String nextWord = scan.nextLine();
	    Node wordNode = new Node(nextWord);
	    dictionary.add(wordNode);
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
    
}
