package combinations;
import java.util.*;

public class CombinationsPascal{

    public static void main(String[] args){
	long n = Long.parseLong(args[0]); //determines number of lines to calculate
	long k = Long.parseLong(args[1]); // determines number of steps across.

	//Problem can't use long to address index, therefore can't make array down to long sizes.
        //stepMax should never be larger than max int???
        // May want to test this .
        long stepMax;
        if(k < n-k){
            stepMax = k;
        }else{
            stepMax = n-k;
        }
        //Add one because triangle starts with choose 0.
        stepMax++;

        if (stepMax > Integer.MAX_VALUE){
            System.err.println("stepMax too big");
        }
        
        long[] previousLine = new long[(int)stepMax];

        long currentDepth = 0; // 1 2 1
        //previousLine.add(1);
        //previousLine.add(2);
        //previousLine.add(1);
        long[] currentLine = new long[(int)stepMax];
        currentLine[0]= 1;;
        
        
        long stepLimit;
        for (long line = 0; line <n+1;line++){
            
            if(currentDepth<= stepMax){
                stepLimit = currentDepth;
            }else{
                stepLimit = stepMax;
            }
            for (int step = 1; step < stepLimit ;step++){
                try{
                currentLine[step] = Math.addExact(previousLine[step],previousLine[step-1]);
                }catch(ArithmeticException e){
                    System.err.println( e);
                    System.exit(0);
                }
            }
                
                
            if(currentDepth < stepMax){
                currentLine[(int)currentDepth] = 1;
            }
            currentDepth++;
            System.err.println(Arrays.toString(currentLine));
            previousLine = currentLine;
            currentLine = new long[(int)stepMax];
            currentLine[0]=1;
        }

        System.out.println("Answer : " + previousLine[(int)stepMax-1]);




        
        /*Working with integers
        ArrayList<Integer> previousLine = new ArrayList<Integer>();

        int currentDepth = 0; // 1 2 1
        //previousLine.add(1);
        //previousLine.add(2);
        //previousLine.add(1);
        ArrayList<Integer> currentLine = new  ArrayList<Integer>();
        currentLine.add(1);
        int stepMax;
        if(k < n-k){
            stepMax = k;
        }else{
            stepMax = n-k;
        }
        
        for (int line = 0; line <n;line++){
            for (int step = 1; step < previousLine.size();step++){
                currentLine.add(previousLine.get(step)+previousLine.get(step-1));
            }
            if(currentDepth < stepMax){
                currentLine.add(1);
            }
            currentDepth++;
            System.err.println(currentLine);
            previousLine = currentLine;
            currentLine = new ArrayList<Integer>();
            currentLine.add(1);
        }

        System.out.println("Answer : " + previousLine.get(stepMax));
        */
        //printPascalsTriangle(n);

    }

    public static void printPascalsTriangle(int depth){
        long array[][] = new long[depth][depth];

	for (int line = 0; line <depth ; line++){
	    for (int step = 0; step <= line;step++){
		if(line ==step || step == 0){
		    array[line][step] = 1;
		}else{
		    array[line][step] = array[line-1][step] + array[line-1][step-1];
		}
	    }
	}
	for( int i=0 ; i <depth; i++){
	    System.err.println(Arrays.toString(array[i]));
	}
    }

        
}
