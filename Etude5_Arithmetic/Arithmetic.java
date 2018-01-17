package arithmetic;

import java.util.*;

public class Arithmetic{


    public long[] numbers;
    public long total;
    public static final boolean ADD = false;
    public static final boolean MULTIPLY = true;
    public Option option = Option.NORMAL;

    public Arithmetic(long[] numbers, long total){
        this.numbers = numbers;
        this.total = total;

    }

    public String depthFirstSearch(char option){
        Stack<ArithmeticNode> myStack = new Stack<ArithmeticNode>();
        ArithmeticNode root = new ArithmeticNode(numbers[0],0);
        int maxDepth = numbers.length-1;
        System.err.println(Arrays.toString(numbers));
        if (option == 'L'){
            this.option = Option.LEFTTORIGHT;
        }
        if(total > calcMaxPossibleTotal()||total < calcMinPossibleTotal()){
            System.err.println("Max : " + calcMaxPossibleTotal());
            System.err.println( "Min : " + calcMinPossibleTotal());
            System.err.println("Impossible due to total being too big or small");
            return "impossible";
        }
        myStack.push(root);
        while (!myStack.empty()){
            ArithmeticNode currentNode = myStack.pop();
            System.err.println("Current Node : " +  currentNode);
            long currentEvaluation = currentNode.getEvaluation(this.option);
            System.err.println("Node Evaluated : " + currentEvaluation);
            if( currentEvaluation == total && currentNode.depth == maxDepth){
                return currentNode.operationsAndNumbersToString();
            }
            if (currentEvaluation <total && currentNode.depth < maxDepth){
                long[] childNumbers = Arrays.copyOf(numbers,currentNode.depth+2);
                boolean[] childAddOperations =
                    Arrays.copyOf(currentNode.operations,currentNode.operations.length+1);
                childAddOperations[currentNode.operations.length] = ADD;
                int childDepth = currentNode.depth+1;
                
                ArithmeticNode addNode =
                    new ArithmeticNode(childNumbers,childAddOperations,childDepth );
                
                boolean[] childMultiplyOperations  =
                    Arrays.copyOf(childAddOperations,childAddOperations.length);
                
                childAddOperations[currentNode.operations.length] = MULTIPLY;
                
                ArithmeticNode multiplyNode =
                    new ArithmeticNode(childNumbers,childMultiplyOperations,childDepth);
                
                myStack.push(addNode);
                myStack.push(multiplyNode);
            }
                
            System.err.println("End of while loop");
        }

        return "impossible";


    }

    public long calcMaxPossibleTotal(){
        long max = 0;
        long min = 1;
        long total=1;
        for(int i =0;i<this.numbers.length;i++){
            total *=numbers[i];
            if (numbers[i] >max){
                max = numbers[i];
            }
            if (numbers[i] < min && numbers[i] !=0){
                min = numbers[i];
            }
                
        }
        if( option == Option.NORMAL){
            return (total/max)*(max+1);
        }else{
            return (total/min)*(max+min);
        }
                      
    }
    
    public long calcMinPossibleTotal(){
        if (Arrays.binarySearch(numbers,0) != -1){
            return 0;
        }else{
            long total=0;
            for(int i =0;i<this.numbers.length;i++){
                total +=numbers[i];
            }
            return total;
        }
    }


    public long calculateLowerBound(){
        return 0;
    }

    public long calculateUpperBound(){
        return 0;
    }

    
    /**
     *  Arithmetic Node Class
     */ 
    private class ArithmeticNode{
        public long[] numbers;
        public boolean[] operations = {};
        public int depth;

        public ArithmeticNode(long[] numbers, boolean[] operations,int depth){
            this.numbers = numbers;
            this.operations = operations;
            this.depth = depth;
        }

        public ArithmeticNode(long rootValue,int depth){
            this.numbers = new long[1];;
            this.numbers[0]= rootValue;
            this.depth = depth;
        }

        public String toString(){
            return operationsAndNumbersToString();
        }

        public long getEvaluation(Option option){
            System.err.println("Option : " + option);
            if (this.operations.length == 0){
                System.err.println("Root Node Check");
                return numbers[0];
            }else if(option == Option.NORMAL ){

                ArrayList<Long> numberList = convertToLongList(numbers);
                //Do multiplication first
                System.err.println(Arrays.toString(operations));
                for(int i = operations.length-1;i >= 0;i--){
                    if (operations[i]){
                        long product = numberList.get(i)*numberList.get(i+1);
                        numberList.set(i,product);
                        numberList.remove(i+1);
                        System.err.println(numberList.toString());
                        System.err.println(i);
                    }
                }
                //Then do addition
                long sum = 0;
                for(int i = 0; i<numberList.size();i++){
                    sum += numberList.get(i);
                }
                return sum;
                
            }else{
                //Left to right evaluation
                long runningTotal = numbers[0];
                for(int i =0;i<operations.length;i++){
                    if(operations[i]){
                        runningTotal *=numbers[i+1];
                    }else{
                        runningTotal += numbers[i+1];
                    }
                }
                return runningTotal;
            }
        }

        public ArrayList<Long> convertToLongList(long[] list){
            ArrayList<Long> outputArrayList = new ArrayList<Long>();
            for(int i =0;i<list.length;i++){
                outputArrayList.add(list[i]);
            }
            return outputArrayList;
        }

        public String operationsAndNumbersToString(){
            if (this.operations.length ==0){
                return ""+numbers[0];
            }
            StringBuilder output = new StringBuilder();
            output.append(this.numbers[0]);
            System.err.println("Node operations : "+Arrays.toString(operations) + " " +operations.length);
            System.err.println("Node numbers: " + Arrays.toString(numbers));
            for(int i = 0; i<this.operations.length;i++){
                //System.err.println("index: " + i);
                if (this.operations[i]){
                    output.append("*");
                    
                }else{
                    output.append("+");
                }
                //System.err.println(this.numbers[i+1]);
                output.append(this.numbers[i+1]);
            }
            return output.toString();
        }
    }
}
