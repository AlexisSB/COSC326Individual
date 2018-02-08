package joined_writing;

import java.util.*;

/**
 * COSC326 SS 2018 Etude 9 Joined Up Writing
 * Etude description at:
 * http://www.cs.otago.ac.nz/cosc326/etudes2018ss/09.pdf
 * Program finds links between two words in a dictionary.
 * Links consist of shared letters between words where 
 * the suffix of one is the prefix of the other.
 * A singly joined link has the common part at least 
 * half as long as one of the words.
 * A doubly joined link has common part at least half as long as both words.
 * @author Alexis Barltrop
 */
public class BreadthFirst{

    /* List of words read in from dictionary text file */
    public static ArrayList<Node> dictionary = new ArrayList<Node>();
    /* List of words already seen in the search so far */
    public static HashSet<String> wordList = new HashSet<String>();
    /* Enum for switching linking type */
    private enum LinkOption {SINGLY_LINKED, DOUBLY_LINKED};
    
    public static void main (String[] args){
	/* Read in dictionary*/
	readInDictionary();

	/* Check right number of arguements */
	if(args.length != 2){
	    System.err.println("Please enter start word and end word only");
	    System.exit(0);
	}

	/* Read in first and last words */
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

	if(singlyLinkedSearch){
	    System.out.println(generateOutputString(firstNode,lastNode));
	}else{
	    System.out.println("0");
	}
	//System.out.println(wordList.size());
	
	/*Clean up*/
	for(Node n : dictionary){
	    n.reset();
	    wordList.clear();
	}
        
        
        /* Doubly Linked Search*/
	linkType = LinkOption.DOUBLY_LINKED;
	boolean doublyLinkedSearch = searchBFS(firstNode,lastNode,linkType);

	if(doublyLinkedSearch){
	    System.out.println(generateOutputString(firstNode,lastNode));
	}else{
	    System.out.println("0");
	}
    }
    

   
    /**
     * Breadth first search of dictionary graph.
     * Explores the edges of each node when it is removed from the queue.
     * As each word is added to the queue it is added to the list of words that have been seen.
     * @param root - source node for the search.
     * @param target - target node to find.
     * @param linkType - type of link between words to use in search, single or double.
     * @return true if link between source and target can be found, false otherwise.
     */
    public static boolean searchBFS(Node root, Node target, LinkOption linkType){
	ArrayDeque<Node> queue = new ArrayDeque<Node>();
        root.previousNode = null;
        root.distance = 0;
        int count = 0;
	queue.add(root);
	wordList.add(root.getWord());
	while(!(queue.isEmpty())){
	    Node currentNode = queue.poll();

	    if(linkType == LinkOption.SINGLY_LINKED){
                expandNode(currentNode, dictionary,LinkOption.SINGLY_LINKED);
            }else{
                expandNode(currentNode, dictionary,LinkOption.DOUBLY_LINKED);
            }
	    
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
	}
        return false;
    }

            
    /**
     * Method finds possible links from a word in given dictionary.
     * For each possible suffix from the word it does a binary search
     * to find the start of words beginning with that suffix.
     * @param node - Node with word to find links from.
     * @param dictionary - dictionary of words to search through.
     * @param linkOption - type of link between words to look for in dictionary.
     */
    public static void expandNode(Node node, ArrayList<Node> dictionary, LinkOption linkType){
	String word = node.getWord();

	for (int i = 0; i < word.length(); i++){

	    String suffix = word.substring(i);
	    /* Get start position in dictionary for a given suffix */
            int start = binarySearchForSubString(suffix);

	    /* If no links then quit */
            if(start == -1){
		return;
	    }
	    
	    int count = start;
	    
	    while (count < dictionary.size()){
		Node n = dictionary.get(count);
		String checkWord = n.getWord();
		if(checkWord.length() <suffix.length()){
		    break;
		}
		String prefix = checkWord.substring(0,suffix.length());
		if (prefix.equals(suffix)){
		    if (linkType == LinkOption.SINGLY_LINKED){
			if((checkWord.length()<=(suffix.length()*2))
			   || (word.length() <=(suffix.length()* 2))){
			    node.addForwards(n,prefix.length());
			}
		    }else{
			if((checkWord.length() <= suffix.length()*2)
			   && ((word.length()<=suffix.length()*2))){
			    node.addForwards(n,prefix.length());
			}
		    }
		    count++;
		}else{
		    break;
		}
	    }
        }
    }
    
    /**
     * Binary search of dictionary.
     * Searchs for words starting with the prefix.
     * @param prefix - starting letters to find in the dictionary.
     * @return location of first word in dictionary starting with prefix.
     */
    public static int binarySearchForSubString(String prefix){
        int left = 0;
        int right = dictionary.size()-1;
	return binarySearchForSubString(prefix,left,right);
    }
    /** Recursive helper method for binary search
     * Continues search until it finds a word that matches the prefix, 
     * and the word above it does not.
     * @param prefix - starting letters to find in the dictionary 
     * @param left - starting index for search
     * @param right - ending index for search
     * @return location of first word in dictionary starting with prefix.
     */
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
            
            if(currentPrefix.equals(prefix)){
               if((abovePrefix.equals(prefix))){
                   return binarySearchForSubString(prefix,left,middle-1);
               }else{
                   return middle;
               }
            }else if(currentPrefix.compareTo(prefix) > 0){
		return binarySearchForSubString(prefix, left, middle-1);
            }else{
                return binarySearchForSubString(prefix, middle+1, right);
            }  
        }
    }

    /**
     * Reads in dictionary of words from stdin.
     */
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

    /**
     * Generates the chain of words in string for output.
     * Follows the links to the previous node starting from the target node.
     * Output format has number of links followed by the list of words.
     * @param root - starting source node.
     * @param target - goal node.
     * @return output string in required format.
     */
    public static String generateOutputString(Node root, Node target){
	StringBuilder output = new StringBuilder();
	Node currentNode = target;
	int count = 1;
	output.append(currentNode.getWord());
	currentNode = currentNode.previousNode;

	while (currentNode.previousNode != null){
	    output.append(" " +currentNode.getWord());
	    currentNode = currentNode.previousNode;
	    count++;
	}
	
	return count + " " +output.toString();
    }
}
