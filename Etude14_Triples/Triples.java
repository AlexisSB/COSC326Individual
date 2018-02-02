package triples;


/**
 * COSC326 Etude 14 Triples.
 * Calculates a set of positve coprime integers x,y,z where
 * x^2 + y^2 = 1 + z^4 and z<x<y
 * Part one makes the first 70 in order of x.
 * Part two makes the first 70 in order of z.
 * @author Alexis Barltrop 
 */
public class Triples{

    /**
     * Main method.
     * Calls part one and part two methods.
     */
    public static void main (String[] args){
	printPartOne();
	printPartTwo();
    }

    /**
     * Prints the first 70 solutions ordered by increasing x.
     * Secondary ordering by z value.
     * Loops through valid values of z for each value of x
     * Calculates y from the equation and checks for coprimeness
     */
    public static void printPartOne(){
	int x,z;
        int count = 1;
	x =1;
	while(count < 71){
	    int zStart =(int)Math.sqrt(x);
	    if (zStart%2 ==0) zStart++;
            for(z=zStart;z<x;z+=2){
		long xSquared = (long)Math.pow(x,2);
		long zQuared = (long)Math.pow(z,4);
		double y = Math.sqrt(1+zQuared-xSquared);
                if ((y>x) && ((y-(int)y) == 0) ){
		    if(checkCoPrime(x,(int)y,z)){
                        System.out.println(count + " " + x + " "+ (int)y + " " +z);
                        count++;
                    }
                }
                
            }
            x+=2;
        }
    }

     /**
     * Prints the first 70 solutions ordered by increasing z.
     * Secondary ordering by x value.
     * Loops through valid values of x for each value of z.
     * Calculates y from the equation and checks for coprimeness
     */
    public static void printPartTwo(){
	int x,z;
        int count = 1;
        z =1;
        while(count < 71){
            for(x =1; x< Math.pow(z,2);x+=2){
                long xSquared = (long)Math.pow(x,2);
                long zQuared = (long)Math.pow(z,4);
                double y = Math.sqrt(1+zQuared-xSquared);
                if ((y > x) && (x>z)&& ((y-(int)y) == 0)){
		    if(checkCoPrime(x,(int)y,z)){
                        System.out.println(count + " " + x + " " + (int)y+ " " +z);
                        count++;
                    }
                }

            }
            z+=2;
        }
           
    }

    /**
     * Checks to see if three numbers are coprime.
     * Uses the gcd method to check all pairings.
     * @param a,b,c - three integers to check if co prime.
     * @return true if three numbers are co prime, false if not.
     */
    public static boolean checkCoPrime(int a, int b, int c){
        if( gcd(a,b) == 1
            && gcd (a,c) ==1
            && gcd(b,c) == 1){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Finds greatest common divisor (gcd) of two numbers.
     * Uses the Euclidian Algortihm with modulus operator.
     * Checks which one is bigger and does appropriate operations. 
     * @param a,b - integers to find gcd for. 
     * @return the gcd of the two numbers.
     */
    public static int gcd(int a, int b){
        if(a > b){
            while (b != 0){
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }else{
            while (a != 0){
                int temp = a;
                a = b % a;
                b = temp;
            }
            return b;
	}
    }
}
