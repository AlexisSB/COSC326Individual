package combinations;
import java.util.*;


/**
 * COSC326 SS 2018 Etude 12 Counting It Up
 * Class calculates the combination formula using 64 bit longs.
 * Does reduction of numbers before multiplication to prevent overflow.
 * @author Alexis Barltrop
 */
public class Combinations{

    
    public static void main (String[] args){
        long n = Long.parseLong(args[0]);
        long k = Long.parseLong(args[1]);
        System.err.println("n : " +n + ", k : " + k);

        long bottom = n -k;
        long[] bottomArray= generateBottomArray(n,k);
	long[] topArray = generateTopArray(n,k);
        
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

        System.err.println(Arrays.toString(bottomArray));
        System.err.println(Arrays.toString(topArray));
	//End of reduction

        System.out.println("Answer : " + multiplyToAnswer(topArray,bottomArray));
    }
    
    /**
     * Carries out multiplication of the numerator after initial reduction.
     * Carries out multiplication one by one checking for long overflow.
     * Checks for non trivial terms in the denominator and divides the
     * product when there is a factor in the denominator.
     * @param topArray - long array of numbers in the numerator.
     * @param bottomArray - long array of numbers in the denominator.
     * @return evaluation of the multiplication of numerator and division of denominator.
     */
    public static long multiplyToAnswer(long[] topArray,long[] bottomArray){
	try{
            //long bottomProduct = bottomArray[0];
           
            //System.err.println("Passed Bottom Product :" + bottomProduct);
	    long output = topArray[0];
            System.err.println(Arrays.toString(topArray));
	    for( int i = 1; i <topArray.length;i++){
		output = Math.multiplyExact(output,topArray[i]);
                for( int j = 0; j <bottomArray.length;j++){
                    if (bottomArray[j] != 1){
                        if (output%bottomArray[j] ==0){
                            output /= bottomArray[j];
                            bottomArray[j] = 1L;
                        }
                    }
                }
	    }
	
	    
	    return output;
	}catch(ArithmeticException e){
	    System.err.println("Long Overflow");
	    System.err.println(e);
	    System.exit(0);
	    return 0;
	}
    }

    /**
     * Generates an long array of numbers from the numerator of the combination formula.
     * Reduction by denominator terms taken into account.
     * @param n - total number of options
     * @param k - number to choose
     * @return long array of reduced numbers in numerator
     */
    public static long[] generateTopArray(long n, long k){
        //Generate Arrays
        
        long[] topArray;
        if (k < n-k){
            // k less than n-k
            topArray = new long[(int)k];
            int topArrayCounter = 0;
            for (long i = n; i >n-k;i--){
                topArray[topArrayCounter] = i;
                topArrayCounter++;
                //System.err.print(i+ ", ");
        }
            System.err.println(Arrays.toString(topArray));
            //System.err.println();
                
        }else{
            // k larger than n-k
            
            topArray = new long[(int)(n-k)];
            int topArrayCounter = 0;
            for (long i = n; i >k;i--){
                topArray[topArrayCounter] = i;
                topArrayCounter++;
                //System.err.print(i+ ", ");
            }
            System.err.println(Arrays.toString(topArray));
            //System.err.println();
            
        }
    }

    /**
     * Generates an long array of numbers from the denominator of the combination formula.
     * Reduction of the numerator taken into account.
     * @param n - total number of options
     * @param k - number to choose
     * @return long array of reduced numbers in denominator.
     */
    public static long[] generateBottomArray(long n, long k){
        long[] bottomArray;
        int bottomArrayCounter = 0;
        if(k < n-k){
            bottomArray = new long[(int)k];//Possible problem with int cast, maybe try araylist.
            for( long i= k; i >0; i--){
                bottomArray[bottomArrayCounter] = i;
                bottomArrayCounter++;
                //System.err.print(i+ ", ");
            }
            System.err.println(Arrays.toString(bottomArray));
        }else{
            bottomArray = new long[(int)(n-k)];
            for( long i = (n-k); i>0;i--){
                bottomArray[bottomArrayCounter] = i;
                bottomArrayCounter++;
                //System.err.print(i+ ", ");
            }
            System.err.println(Arrays.toString(bottomArray));
            
        }
        
    }
    
}
