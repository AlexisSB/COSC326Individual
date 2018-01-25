package numbers;

import java.util.*;

public class randomIntGen{


    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        int numberToGenerate = scan.nextInt();
        for(int i=0 ; i<numberToGenerate;i++){
            System.out.println(rand.nextDouble());
        }
        

    }


}
