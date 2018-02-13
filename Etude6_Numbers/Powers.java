package numbers;
import java.util.*;

/**
 * Calculates the series of harmonic numbers up to a given number.
 * Harmonic series is the series (1/1) + (1/2) + .... + (1/n)
 * COSC 326 SS 2018 Etude 6.
 * @author Alexis Barltrop
 */
public class Powers{

    private enum Precision {SINGLE, DOUBLE}

    public static void main (String[] args){

        long limit = Long.parseLong(args[0]);

        printSinglePrecisionForward(limit);
        printDoublePrecisionForward(limit);

        printSinglePrecisionBackwards(limit);
        printDoublePrecisionBackwards(limit);

    }

    public static void printSinglePrecisionForward(long limit){
        System.out.println("Single Forward: " +(float)harmonicForward(limit,Precision.SINGLE));

    }

    public static void printDoublePrecisionForward(long limit){
        System.out.println("Double Forward: " + (double)harmonicForward(limit,Precision.DOUBLE));

    }

    public static double harmonicForward(long limit, Precision p){
        if (p == Precision.SINGLE){
            float total = 0;

            for(long i = 1; i <= limit; i++){
                total += (1/(float)((i*(i+1))/2));
            }
            return total;
        }else{
            double total = 0;

            for(long i = 1; i <= limit; i++){
                total += (1/(double)((i*(i+1))/2));
            }
            return total;
        }
    }

    public static void printSinglePrecisionBackwards(long limit){
        System.out.println("Single Backward: " + (float)harmonicBackward(limit,Precision.SINGLE));

    }
    public static void printDoublePrecisionBackwards(long limit){
        System.out.println("Double Backward: " + (double)harmonicBackward(limit,Precision.DOUBLE));

    }

    public static double harmonicBackward(long limit, Precision p){
        if (p == Precision.SINGLE){
            float total = 0;

            for(long i = limit; i >0 ; i--){
                total += (1/(float)((i*(i+1))/2));
            }
            return total;
        }else{
            double total = 0;

            for(long i = limit; i >0; i--){
                total += (1/(double)((i*(i+1))/2));
            }
            return total;
        }
    }
}
