package arithmetic;

import java.util.*;

public class ArithmeticApp{

    private static long[] numbers;
    private  static Operation[] operations;
    private static Option option;
    private  static long target;
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args){
        while(scan.hasNextLine()){
            readInput();
            if (option == Option.LEFTTORIGHT){
                System.out.print("L ");
            }else{
                System.out.print("N ");
            }
            
            if(depthFirstSearch()){
                //System.out.println("Found");
                //System.out.println(Arrays.toString(operations));
                System.out.println(formattedNumbersAndOperations());
            }else{
                System.out.println("impossible");
            }
        }
    }

    public static String formattedNumbersAndOperations(){
        StringBuilder output = new StringBuilder();
        output.append(numbers[0]);
        for(int i= 0; i<operations.length;i++){
            if( operations[i] == Operation.ADD){
                output.append(" +");
            }else if( operations[i] == Operation.MULTIPLY){
                output.append(" *");
            }else{
                System.err.println("Error NONE in output");
                return "Error NONE in output";
            }
             
            output.append(" " + numbers[i+1]);
        }
        return output.toString();
    }
            

    public static boolean depthFirstSearch(){

        //Create Root Node
        int numberOfOperations = numbers.length-1;
        Long firstNumber = new Long(numbers[0]);
        Node rootNode = new Node(firstNumber,numberOfOperations);
        
        //Create Stack
        ArrayDeque<Node> myStack = new ArrayDeque<Node>();
        //Add root node to stack
        myStack.push(rootNode);

        //Start search
        while (!myStack.isEmpty()){
            
            System.err.println("Stack: " + myStack);
	    //Get Node off the stack
            Node currentNode = myStack.pop();
            //Create shared variables
            long nodeValue = evaluateNode(currentNode);
	    System.out.println("Current Node : " + currentNode + "Node Value : " + nodeValue);
            int depth = currentNode.getDepth();
            
            ArrayList<Long> tempList = new ArrayList<Long>();

            //Check to see if node is a valid solution.
	    if (nodeValue == target && depth == numbers.length-1){
                System.err.println("Found a value");
                System.err.println(currentNode);
                operations = currentNode.getOperations();
                return true;
            }
            //Check for long overflow
            if(nodeValue <0){
                System.out.println("Overflow of long type. Target too large");

            }else if (nodeValue <= target && depth < numbers.length-1){
                //Check if the node is an internal node that can be expanded.
                //Prune if node value is too big.
                //Shared Varialbles.
                ArrayList<Long> currentNodeValues = currentNode.getPendingValue();
                long nextNumber = numbers[depth+1];
                //Create Add node
		Node addNode = new Node();
                addNode.setDepth(depth+1);
                /*
                Operation[] tempOperations = new Operation[numbers.length-1];        
               
                */
                Operation[] addNodeOperations = new Operation[numberOfOperations];
                for(int i = 0;i< numberOfOperations;i++){
                    addNodeOperations[i] = currentNode.getOperations()[i];
                }
                addNodeOperations[depth] = Operation.ADD;
                addNode.setOperations(addNodeOperations);
		//System.err.println("Add Node Operations : " + Arrays.toString(addNodeOperations));


                //Addition
                
                ArrayList<Long> addNodeValues = new ArrayList<Long>();

                if (option == Option.LEFTTORIGHT){
		    //LTR Addition
		    		    
		    addNodeValues.add(currentNode.getPendingValue().get(0) + nextNumber);
                    addNode.setPendingValue(addNodeValues);
                    

		}else{
		    //Normal Addition

		    //Check previous and current operations.
		    //tempList = currentNode.getPendingValue();
		    Operation currentOperation = Operation.ADD;
		    Operation previousOperation;
		    Operation oneBeforeLastOperation;

                    if (depth == 0){
			//System.err.println("Set to none");
			previousOperation = Operation.NONE;
			oneBeforeLastOperation = Operation.NONE;
		    }else if (depth ==1){
			previousOperation =currentNode.getOperations()[depth-1];
			oneBeforeLastOperation = Operation.NONE;
		    }else{
			//System.err.println(Arrays.toString(currentNode.getOperations()));
			previousOperation = currentNode.getOperations()[depth-1];
			oneBeforeLastOperation = currentNode.getOperations()[depth-2];
		    }
		    System.err.println("Current Operation : " + currentOperation);
		    System.err.println("Previous operation : " + previousOperation);
		    System.err.println("One before last operation: " + oneBeforeLastOperation);
				       
		    //New node action depends on previosu operation.
		    
                    //Create multiply Node arraylist and copy values from current
                    /*
                    for(int i = 0;i< 2;i++){
		    multiplyNodeValues.set(i, new Long(currentNode.getPendingValue.get(i)));
                    }
                    */

		    if(oneBeforeLastOperation == Operation.NONE){
			if (previousOperation == Operation.ADD){
			    Long newFirstValue = currentNodeValues.get(0) + currentNodeValues.get(1);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
			}else if (previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
			}else{
			    //Root Node Case, both NONE
			    addNodeValues.add(currentNodeValues.get(0));
			    addNodeValues.add(new Long(nextNumber));
			}
		    }
		
		    if (oneBeforeLastOperation == Operation.ADD){	
			if (previousOperation == Operation.ADD){
			    Long newFirstValue = currentNodeValues.get(0)
				+ currentNodeValues.get(1);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
			}else if(previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0)+ currentNodeValues.get(1);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
                        }else{
			    System.err.println("Missed NONE in case before last");
			}
		    }

		    if(oneBeforeLastOperation == Operation.MULTIPLY){
			if (previousOperation == Operation.ADD){
			    Long newFirstValue = currentNodeValues.get(0)
				+ currentNodeValues.get(1);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
			}else if(previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0);
			    addNodeValues.add(newFirstValue);
			    addNodeValues.add(new Long(nextNumber));
                        }else{
			    System.err.println("Missed NONE in case before last");
			}
		    }
		    addNode.setPendingValue(addNodeValues);
		}
                
                System.err.println("Add Node Value : " + addNodeValues);
                System.err.println("Add Node Depth : " + addNode.getDepth());
                System.err.println("addNode " + addNode);
                System.err.println("addNode " + Arrays.toString(addNode.getOperations()));

                //Multiplication
		
                //Create Multiply Node
                //Create Add node
		Node multiplyNode = new Node();
                multiplyNode.setDepth(depth+1);
                Operation[] multiplyNodeOperations = new Operation[numberOfOperations];
                for(int i = 0;i< numberOfOperations;i++){
                    multiplyNodeOperations[i] = currentNode.getOperations()[i];
                }
                multiplyNodeOperations[depth] = Operation.MULTIPLY;
                multiplyNode.setOperations(multiplyNodeOperations);
		//System.err.println(Arrays.toString(multiplyNodeOperations));

                ArrayList<Long> multiplyNodeValues = new ArrayList<Long>();
                
                if (option == Option.LEFTTORIGHT){
                    //LTR Multiplication
                    multiplyNodeValues.add(currentNode.getPendingValue().get(0) * nextNumber);
                    multiplyNode.setPendingValue(multiplyNodeValues);
                   
                }else{
                    
                    //Normal Multiplication
                    Operation currentOperation = Operation.MULTIPLY;
		    Operation previousOperation;
		    Operation oneBeforeLastOperation;

                    if (depth == 0){
			//System.err.println("Set to none");
			previousOperation = Operation.NONE;
			oneBeforeLastOperation = Operation.NONE;
		    }else if (depth ==1){
			previousOperation =currentNode.getOperations()[depth-1];
			oneBeforeLastOperation = Operation.NONE;
		    }else{
			//System.err.println(Arrays.toString(currentNode.getOperations()));
			previousOperation = currentNode.getOperations()[depth-1];
			oneBeforeLastOperation = currentNode.getOperations()[depth-2];
		    }
		    System.err.println("Current Operation : " + currentOperation);
		    System.err.println("Previous operation : " + previousOperation);
		    System.err.println("One before last operation: " + oneBeforeLastOperation);
		    //End of checking operations

		     if(oneBeforeLastOperation == Operation.NONE){
			if (previousOperation == Operation.ADD){
			    multiplyNodeValues.add(currentNodeValues.get(0));
			    multiplyNodeValues.add(currentNodeValues.get(1)*nextNumber);
			}else if (previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0) * nextNumber;
			    multiplyNodeValues.add(newFirstValue);
			}else{
			    //Root Node Case, both NONE
			    multiplyNodeValues.add(currentNodeValues.get(0)*nextNumber);
			}
		    }
		     
		    if (oneBeforeLastOperation == Operation.ADD){	
			if (previousOperation == Operation.ADD){
			    Long newFirstValue = currentNodeValues.get(0);
			    Long newSecondValue = currentNodeValues.get(1) *nextNumber;
			    multiplyNodeValues.add(newFirstValue);
			    multiplyNodeValues.add(newSecondValue);
			}else if(previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0);
			    multiplyNodeValues.add(newFirstValue);
			    multiplyNodeValues.add(currentNodeValues.get(1)*nextNumber);
                        }else{
			    System.err.println("Missed NONE in case before last");
			}
		    }
		    
		    if(oneBeforeLastOperation == Operation.MULTIPLY){
			if (previousOperation == Operation.ADD){
			    Long newFirstValue = currentNodeValues.get(0);
			    Long newSecondValue = currentNodeValues.get(1)*nextNumber;
			    multiplyNodeValues.add(newFirstValue);
			    multiplyNodeValues.add(newSecondValue);
			}else if(previousOperation == Operation.MULTIPLY){
			    Long newFirstValue = currentNodeValues.get(0)*nextNumber;
			    multiplyNodeValues.add(newFirstValue);

                        }else{
			    System.err.println("Missed NONE in case before last");
			}

			

		    }
                    multiplyNode.setPendingValue(multiplyNodeValues);
                    
                }
                System.err.println("Multiply Node Value : " + multiplyNodeValues);
                System.err.println("Multiply Node Depth : " + multiplyNode.getDepth());
                System.err.println("multiplyNode : " + multiplyNode);
                System.err.println("multiplyNode : " + Arrays.toString(multiplyNode.getOperations()));
		
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
            //TODO for normal
            //If there are two values then the previous operation is aaddition, just add them up?
            //Case where ends in +1*1?
            //Will evaluate to total even though not at right depth.
            //As long as check is <= total should be ok.
            ArrayList<Long> values = node.getPendingValue();
            
            if (values.size()==1){
                return values.get(0);
            }else{
                return values.get(0) + values.get(1);
            }
        }
    }
    
    public static  void readInput(){
        

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
