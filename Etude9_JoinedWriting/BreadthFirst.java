package joined_writing;

import java.util.*;

public class BreadthFirst{

    public static ArrayList<Node> dictionary = new ArrayList<Node>();

    public static void main (String[] args){
	/* Read in dictionary*/
	/* Convert to Nodes*/
	readInDictionary();
	/* Read in first and last words */
	if(args.length>0){
	    String firstWord = args[0];
            String lastWord = args[1];
            Node firstNode = findNode(firstWord, dictionary);
            Node lastNode = findNode(lastWord, dictionary);
	    /* Djikstra from the first word*/
	    /* When you find a node search the dictionary for its links*/
	    /* Until you find the last word*/
	}
    }

    public static boolean searchBFS(Node root, Node target){
	
    }

     /**
     * Method finds potential words to form a singly linked connection with.
     * Searchs through dictionary for words where the common part is
     * at least as half as long as at least one of the words.
     * @param node - Node with word to find links from.
     * @param dictionary - dictionary of words to search through.
     */
    public static void expandNodeSingle(Node node, ArrayList<Node> dictionary){
        String word = node.getWord();
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
    
}
