package telephone;

import java.util.*;

public class Telephone{

    private static String INVALID_NUMBER = "INV";
    
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()){
            String line = scan.nextLine();
	    /* TODO Preserve input here?*/
            line= line.toUpperCase();
            System.out.printf("%-20s : %s\n", line,checkNumber(line));
	    /* TODO Process letters here*/
	    /* TODO Process the numbers to the standard format*/
	    /*May want to add space in the checkNumber method*/
        }
        
    }

    public static String checkNumber(String input){
        /* Removes Acceptable characters from line*/
               input = input.replaceAll("[ ()-]","");
        /*Check for non allowed characters*/
        if(input.matches(".*\\W.*")){
            return INVALID_NUMBER;
        }
	int length = input.length();

	/* Process numbers starting with 0508*/
        if(input.matches("0508\\w{6,9}")){
	    if(input.matches(".{10}[A-Za-z]{0,3}")){
		//System.out.println("Check for extra numbers");
		return input;
	    }else{
		return INVALID_NUMBER;
	    }
	}
	
	/* Processes numbers starting with 0800*/
	if(input.matches("0800\\w{6,9}")){
	    //System.out.println("0800 Recognised");
	    if(input.matches(".{10,11}[A-Za-z]{0,3}")){
		return input;
	    }else{
		return INVALID_NUMBER;
	    }
	}

	/* Processes number starting with 0900*/
	if(input.matches("0900\\w{5,9}")){
	     if(input.matches(".{9}[A-Za-z]{0,4}")){
		//System.out.println("Check for extra numbers");
		return input;
	    }else{
		return INVALID_NUMBER;
	    }
	}
	/* Process numbers starting with 02 + 409 for Scott Base*/
	if(input.matches("02409\\d{4}")){
	    return input;
	}

	/* Checks Numbers starting with area codes*/
	if(input.matches("0[34679][2-9]\\d{6}")){
	    if(!(input.matches("..900.*|..911.*|..999.*"))){
		return input;
	    }
	}

	/* Checks mobile numbers starting in 021 */
	if(input.matches("021\\d{6,8}")){
	    return input;
	}

	/* Checks mobile numbers starting in 022 or 027 */
	if(input.matches("02[27]\\d{7}")){
	    return input;
	}

	/* Checks mobile numbers starting in 025 */
	if(input.matches("025\\d{6}")){
	    String numberSuffix = input.substring(3);
	    String outputNumber = "0274"+ numberSuffix;
	    return outputNumber;
	}

		 
	
	return INVALID_NUMBER;
    }
    
}
    

