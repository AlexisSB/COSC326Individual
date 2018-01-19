package arithmetic;

import java.util.ArrayList;

public class Node{

    ArrayList<Long> pendingValue;
    Operation[] operations;

    public Node(){
        this(null,new ArrayList<Long>());
    }

    public Node (Operation[] operations, ArrayList<Long> pendingValue){
        this.pendingValue = pendingValue;
        this.operations = operations;
    }
    
    public Operation[] getOperations(){
        return this.operations;
    }

    public void setOperations(Operation[] operations){
        this.operations = operations;
    }

    public void setPendingValue(ArrayList<Long> pendingValue){
        this.pendingValue = pendingValue;
    }

    public ArrayList<Long> getPendingValue(){
        return this.pendingValue;
    }

    public String toString(){
        StringBuilder operationsOutput = new StringBuilder();
        StringBuilder valueOutput = new StringBuilder();

        operationsOutput.append(" Operations : ");
        for( int i= 0;i<operations.length;i++){
            if (operations[i] == Operation.ADD){
                operationsOutput.append("+, ");
            }else if (operations[i] == Operation.MULTIPLY){
                operationsOutput.append("*, ");
            }
        }

        valueOutput.append(" Value : ");
        for (int i=0; i<pendingValue.size(); i++){
            valueOutput.append(pendingValue.get(i));

        }

        StringBuilder output = new StringBuilder();

        output.append(valueOutput.toString());
        output.append(operationsOutput.toString());

        return output.toString();
                      


    }
}
    
    
    
