package combinations;
import java.util.*;

public class Combinations{


    public static void main (String[] args){
        long n = Long.parseLong(args[0]);
        long k = Long.parseLong(args[1]);
        System.err.println("n : " +n + ", k : " + k);

        long bottom = n -k;
        
       
        if (k < bottom){
            for (long i = n; i >bottom;i--){
                System.err.print(i+ ", ");
            }
            System.err.println();
            for( long i= k; i >0; i--){
                System.err.print(i+ ", ");
            }
            
        }else{
            for (long i = n; i >k;i--){
                System.err.print(i+ ", ");
            }
            System.err.println();
            for( long i = bottom; i>0;i--){
                System.err.print(i+ ", ");
            }
           
            
        }

    }

}
