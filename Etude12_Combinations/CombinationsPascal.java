package combinations;
import java.util.*;

public class CombinationsPascal{

    public static void main(String[] args){
	int n = 10; //determines number of lines to calculate
	int k = 2; // determines number of steps across.

	//Problem can't use long to address index, therefore can't make array down to long sizes.
	long array[][] = new long[n][n];

	for (int line = 0; line <n ; line++){
	    for (int step = 0; step <= line;step++){
		if(line ==step || step == 0){
		    array[line][step] = 1;
		}else{
		    array[line][step] = array[line-1][step] + array[line-1][step-1];
		}
	    }
	}
	for( int i=0 ; i <n; i++){
	    System.err.println(Arrays.toString(array[i]));
	}

    }
    
}
