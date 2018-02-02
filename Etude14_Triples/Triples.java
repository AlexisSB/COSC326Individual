package triples;


public class Triples{

    public static void main (String[] args){
        int x,z;
        int count = 1;
        
        x =1;
        //for (x = 1;x <1000; x++){
        while(count < 71){
            for(z =(int)Math.sqrt(x);z<x;z++){
                //for (y=x+1;y<Integer.MAX_VALUE;y++){
                long xSquared = (long)Math.pow(x,2);
                //long ySquared = (long)Math.pow(y,2);
                long zQuared = (long)Math.pow(z,4);
                double y = Math.sqrt(1+zQuared-xSquared);
                if (((y-(int)y) == 0) && (y > x) ){
                    //if (xSquared+ySquared-zQuared ==1){
                    if(checkCoPrime(x,(int)y,z)){
                        System.out.println(count + " " + x + " "+ (int)y + " " +z);
                        count++;
                    }
                }
                
            }
            x++;
        }

        ///part 2

        //  System.err.println("Part 2");
        int countZ = 1;
        z =1;
        while(countZ < 71){
            for(x =1; x< Math.pow(z,2);x++){
                long xSquared = (long)Math.pow(x,2);
                long zQuared = (long)Math.pow(z,4);
                double y = Math.sqrt(1+zQuared-xSquared);
                if (((y-(int)y) == 0) && (y > x) && (x>z)){
                    //if (xSquared+ySquared-zQuared ==1){
                    if(checkCoPrime(x,(int)y,z)){
                        System.out.println(countZ + " " + x + " " + (int)y+ " " +z);
                        countZ++;
                    }
                }

            }
            z++;
        }
           
    }


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
     *
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
