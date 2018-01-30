package combinations;
import java.util.*;

public class Combinations{


    public static void main (String[] args){
        long n = Long.parseLong(args[0]);
        long k = Long.parseLong(args[1]);
        System.err.println("n : " +n + ", k : " + k);

        long bottom = n -k;
        long[] bottomArray;
	long[] topArray;

	//Generate Arrays
        if (k < bottom){
	    // k less than n-k
	   
	    bottomArray = new long[(int)k];//Possible problem with int cast, maybe try araylist.
	    int bottomArrayCounter = 0;
	    topArray = new long[(int)k];
	    int topArrayCounter = 0;
	    for (long i = n; i >bottom;i--){
		topArray[topArrayCounter] = i;
		topArrayCounter++;
		//System.err.print(i+ ", ");
            }
	    System.err.println(Arrays.toString(topArray));
            //System.err.println();
            for( long i= k; i >0; i--){
		bottomArray[bottomArrayCounter] = i;
		bottomArrayCounter++;
                //System.err.print(i+ ", ");
            }
	    System.err.println(Arrays.toString(bottomArray));
            
        }else{
	    // k larger than n-k
	    bottomArray = new long[(int)bottom];//Possible problem with int cast, maybe try araylist.
	    int bottomArrayCounter = 0;
	    topArray = new long[(int)bottom];
	    int topArrayCounter = 0;
            for (long i = n; i >k;i--){
		topArray[topArrayCounter] = i;
		topArrayCounter++;
                //System.err.print(i+ ", ");
            }
	    System.err.println(Arrays.toString(topArray));
            //System.err.println();
            for( long i = bottom; i>0;i--){
		bottomArray[bottomArrayCounter] = i;
		bottomArrayCounter++;
                //System.err.print(i+ ", ");
            }
	    System.err.println(Arrays.toString(bottomArray));
	}

	//End of array generation

	//Start reduction

	for (int i = 0; i< topArray.length; i++){
	    
	    for(int j = 0; j <bottomArray.length; j++){
		if (bottomArray[j] != 1){
		    if (topArray[i]% bottomArray[j] ==0){
			topArray[i] /= bottomArray[j];
			bottomArray[j] = 1; 
		    }
		}
	    }
	}
	System.err.println(Arrays.toString(topArray));

	//End of reduction


	//Final Multiplication

	long output = topArray[0];
	for( int i = 1; i <topArray.length;i++){
	    output *= topArray[i];
	}

	System.out.println("Answer : " + output);

    }

     

}
