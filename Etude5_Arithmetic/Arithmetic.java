package arithmetic;

import java.util.*;

public class Arithmetic{


    public long[] numbers;
    public long total;
    public static final boolean addition = false;
    public static final boolean multiplication = true;
    public Option option = Option.NORMAL;

    public Arithmetic(long[] numbers, long total){
        this.numbers = numbers;
        this.total = total;

    }

    public boolean[] depthFirstSearch(char option){
        Stack<ArithmeticNode> myStack = new Stack<ArithmeticNode>();
        ArithmeticNode root = new ArithmeticNode(numbers[0]);
        if (option == 'L'){
            this.option = Option.LEFTTORIGHT;
        }

        myStack.push(root);
        while (!myStack.empty()){
            ArithmeticNode currentNode = myStack.pop();
            long currentEvaluation = currentNode.getEvaluation(this.option);
            

        }

        return null;


    }

    private class ArithmeticNode{
        public long[] numbers;
        public boolean[] operations;

        public ArithmeticNode(long[] numbers, boolean[] operations){
            this.numbers = numbers;
            this.operations = operations;
        }

        public ArithmeticNode(long rootValue){
            this.numbers = new long[1];;
            this.numbers[0]= rootValue;
            this.operations = null;
        }

        public long getEvaluation(Option option){
            if (this.operations == null){
                return numbers[0];
            }else if(option == Option.NORMAL ){

                ArrayList<Long> numberList = convertToLongList(numbers);
                //Do multiplication first
                for(int i = 0;i<numberList.size();i++){
                    if (operations[i]){
                        long product = numberList.get(i)*numberList.get(i+1);
                        numberList.set(i,product);
                        numberList.remove(i+1);
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

    }
}
