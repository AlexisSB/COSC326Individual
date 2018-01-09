package telephone;

import java.util.*;

public class Telephone{


    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()){
            String line = scan.nextLine();
            line= line.toUpperCase();
            System.out.println(checkNumber(line));
        }
        
    }

    public static String checkNumber(String input){
        /* Removes Acceptable characters from line*/
        input = input.replaceAll("[ ()-]","");
        /*Check for non allowed characters*/
        if(input.matches(".*\\W.*")){
            return "INV";
        }

        if(input.matches("0508")){
            if(input.matches("[A-Z]+"
            int length = input.length();
            if(length > 9)
            return input;
        }

        if(input.matches("0800")){
            return input;
        }

        if(input.matches("0900")){
            return input;
        }
        
            return input;
        }

    }
    
}
