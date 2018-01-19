package arithmetic;

import java.util.*;

public class ArithmeticApp{

    private static long[] numbers;
    private  static Operation[] operations;
    private static Option option;
    private  static long target;

    public static void main(String[] args){

        readInput();
        if (option == Option.LEFTTORIGHT){
            System.out.print("L ");
        }else{
            System.out.print("N ");
        }
        
        if(depthFirstSearch()){
            System.out.println("Found");
        }else{
            System.out.println("impossible");
        }
        
    }

    public static boolean depthFirstSearch(){

        //Create Root Node
        Node rootNode = new Node();
        Operation[] emptyOperations = new Operation[numbers.length-1];
        for (int i=0; i<emptyOperations.length;i++){
            emptyOperations[i] = Operation.NONE;
        }
        rootNode.setOperations(emptyOperations);
        ArrayList<Long> tempList = rootNode.getPendingValue();
        tempList.add(new Long(numbers[0]));

        //Create Stack
        ArrayDeque<Node> myStack = new ArrayDeque<Node>();
        //Add root node to stack
        myStack.push(rootNode);

        //Start search
        while (!myStack.isEmpty()){
            System.err.println("Stack: " + myStack);
            Node currentNode = myStack.pop();
           

            long nodeValue = evaluateNode(currentNode);
            int depth = myStack.size();
            if (nodeValue == target && depth == numbers.length-1){
                operations = currentNode.getOperations();
                return true;
            }
            if (nodeValue < target && depth < numbers.length-1){

                //Create Add node 
                Operation[] tempOperations = new Operation[numbers.length-1];        
                for(int i = 0;i< tempOperations.length;i++){
                    tempOperations[i] = currentNode.getOperations()[i];
                }
                tempOperations[depth] = Operation.ADD;

                //LTR Addition
                long tempValue = currentNode.getPendingValue().get(0);
                tempList = new ArrayList<Long>();
                System.err.println("\nTemp Value : " + tempValue);
                System.err.println("Depth : " + depth);
                tempList.add(tempValue + numbers[depth+1]);
                Node addNode = new Node(tempOperations,tempList);
                System.err.println("addNode " + addNode);
                                
                //Create Multiply Node

                //LTR Multiplication
                tempOperations[depth] = Operation.MULTIPLY;
                tempList = new ArrayList<Long>();
                tempList.add(tempValue * numbers[depth+1]);
                Node multiplyNode = new Node(tempOperations, tempList);
                System.err.println("multiplyNode : " + multiplyNode);

                myStack.push(addNode);
                myStack.push(multiplyNode);
            }
        }
        
        return false;
    }

    public static long evaluateNode(Node node){
        if (option == Option.LEFTTORIGHT){
            return node.getPendingValue().get(0);
        }else{
            return 1;
        }
    }

    public static  void readInput(){
        Scanner scan = new Scanner(System.in);

        //Creates a long array of the input numbers
        String numberInput = scan.nextLine();
        String[] numbersString = numberInput.split(" ");
        long[] numbersLong = new long[numbersString.length];
        for (int i = 0; i < numbersLong.length;i++){
            numbersLong[i] = Long.parseLong(numbersString[i]);
        }
        numbers = numbersLong;

        //Sets total
        long total = scan.nextLong();
        target = total;

        //Sets option
        String optionInput = scan.nextLine().trim();
        char optionChar = optionInput.charAt(0);
        if(optionChar == 'N'){
            option = Option.NORMAL;
        } else if (optionChar =='L'){
            option = Option.LEFTTORIGHT;
        }else{
            System.err.println("Evaluation option not valid. Please check input");
            System.exit(0);
        }

        System.err.println("Numbers : " + Arrays.toString(numbers));
        System.err.println("Target : " + target);
        System.err.println("Option : " + option);
    }
}
