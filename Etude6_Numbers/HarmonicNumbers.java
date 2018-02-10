package numbers;
import java.util.*;

/**
 * Calculates the series of harmonic numbers up to a given number.
 * Harmonic series is the series (1/1) + (1/2) + .... + (1/n)
 * COSC 326 SS 2018 Etude 6.
 * @author Alexis Barltrop
 */
public class HarmonicNumbers{

    private enum Precision {SINGLE, DOUBLE}

    public static void main (String[] args){

        int limit = Integer.parseInt(args[0]);

        printSinglePrecisionForward(limit);
        printDoublePrecisionForward(limit);

        printSinglePrecisionBackwards(limit);
        printDoublePrecisionBackwards(limit);

    }

    public static void printSinglePrecisionForward(int limit){
        System.out.println((float)harmonicForward(limit,Precision.SINGLE));
                
    }

    public static void printDoublePrecisionForward(int limit){
        System.out.println((double)harmonicForward(limit,Precision.DOUBLE));
                
    }

    public static double harmonicForward(int limit, Precision p){
        if (p == Precision.SINGLE){
            float total = 0;
            
            for(int i = 1; i <= limit; i++){
                total += (1/(float)i);
            }
            return total;
        }else{
            double total = 0;
            
            for(int i = 1; i <= limit; i++){
                total += (1/(double)i);
            }
            return total;
        }
    }

    public static void printSinglePrecisionBackwards(int limit){
        System.out.println((float)harmonicBackward(limit,Precision.SINGLE));
                
    }
    public static void printDoublePrecisionBackwards(int limit){
        System.out.println((double)harmonicBackward(limit,Precision.SINGLE));
                
    }

    public static double harmonicBackward(int limit, Precision p){
        if (p == Precision.SINGLE){
            float total = 0;
            
            for(int i = limit; i >0 ; i--){
                total += (1/(float)i);
            }
            return total;
        }else{
            double total = 0;
            
            for(int i = limit; i >0; i--){
                total += (1/(double)i);
            }
            return total;
        }
    }
}
           
           
        
    
