package joined_writing;

import java.util.*;

public class JoinedWordsApp{

    public static ArrayList<Node> output = new ArrayList<Node>();
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

        for(Node n: dictionary){
            
            findForwards(n,dictionary);
            //findBackwards(n,dictionary);
        }
        /*
        for (Node n: dictionary){
            System.err.println(n);
            System.err.println("\t" + n.forwards);
        }
        */
        if(args.length>0){
            String firstWord = args[0];
            String lastWord = args[1];
            Node firstNode = findNode(firstWord, dictionary);
            Node lastNode = findNode(lastWord, dictionary);
            System.err.println(search(firstNode, lastNode));
            System.err.println(output);
        }
        //This is where we do the search
        

        
        //For each node find the words that are linked going forward, so words that start with the end of the word.
        //Find words that end with the start of the word.
        // Can create double pointers for each one one i.e when one points forward it also points back from another node.
        

    }

    public static boolean search(Node root, Node target){
        //ArrayList<Node> outputHolder = new ArrayList<Node>();
        for (int depth = 0 ; depth < dictionary.size()/10;depth++){
            boolean found = DLS(root,target,depth);
            if(found){
                return found;
            }
        }
        return false;
    }

    public static boolean DLS(Node node, Node target, int depth){
        if(depth == 0 && node.getWord().equals(target.getWord())){
            return true;
        }
        if (depth > 0){
            for(Map.Entry<Node,Integer>  n : node.forwards.entrySet()){
                output.add(n.getKey());
                System.err.println(output);
                boolean found = DLS(n.getKey(),target,depth-1);
                
                if(found){
                    return found;
                }
                output.remove(n.getKey());
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

    public static void findForwards(Node node, ArrayList<Node> dictionary){
        String word = node.getWord();
        for(Node n: dictionary){
            String otherWord = n.getWord();
            for (int i = 0; i < word.length(); i++){
                if(otherWord.length() >= word.length()-i){
                    if (word.substring(i)
                        .equals(otherWord.substring(0,word.length()-i))){
                        node.addForwards(n,word.length()-i);
                        n.addBackwards(node, word.length()-i);
                    }
                }
            }
        }
    }
}
