package telephone;

import java.util.*;

public class Telephone{

    private static String[] INVALID_NUMBER = {"INV"};
    private static String INVALID_OUTPUT = "INV";
    static final int PREFIX = 0;
    static final int SUFFIX =1;
    
    
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Hashtable<Integer,String> phoneBook = new Hashtable<Integer,String>(100);

        while (scan.hasNextLine()){
            String line = scan.nextLine();
	    /* TODO Preserve input here?*/
            String[] numberInProgress;
            //numberInProgress = line.toUpperCase();
            //System.out.printf("%-20s : %s\n", numberInProgress,checkNumber(numberInProgress));
            
            numberInProgress = checkNumber(line);
            
	    if(numberInProgress != INVALID_NUMBER){
                //System.out.println(line);
                numberInProgress[SUFFIX] = replaceLettersWithNumbers(numberInProgress[SUFFIX]);
                //System.out.println("After Process: " + convertToStandardFormat(numberInProgress));

                String standardFormatOutput = convertToStandardFormat(numberInProgress);
                Integer outputHashCode = standardFormatOutput.hashCode();
                if (phoneBook.containsKey(outputHashCode)){
                    System.out.println(standardFormatOutput + " DUP");
                }else{
                    phoneBook.put(outputHashCode,standardFormatOutput);
                    System.out.println(standardFormatOutput);
                }
                                
            }else{
                System.out.println(line + " " +INVALID_OUTPUT);
            }
            /*May want to add space in the checkNumber method*/
            /*TODO check for duplicates*/
            /*TODO (02) 123455 case fix, what about (024) 091234 ?*/

        }
                        
    }
    
    /* Takes in a partially processed number
     * Input must only be numbers
     */
    public static String convertToStandardFormat(String[] number){
        String output = "";
        //System.out.println("Before process: " + Arrays.toString(number));

        switch (number[SUFFIX].length()){

            case 5:
                output = number[PREFIX] + " " + number[SUFFIX];
                break;
            case 6:
            case 7:
                output = number[PREFIX] + " "
                    + number[SUFFIX].substring(0,3) + " "
                    + number[SUFFIX].substring(3);
                break;
            case 8:
            case 9:
                output = number[PREFIX]
                    + " " + number[SUFFIX].substring(0,4)
                    + " " + number[SUFFIX].substring(4);
                break;
            
                
            default:
                output = "INV from case";
                break;
        }
        return output;
    }
    
    /*Phone number should be with uppercase letters*/
    public static String replaceLettersWithNumbers(String phoneNumber){
        String output = phoneNumber;
        String[][] letterToNumberArray = {{"[ABC]","2"},{"[DEF]","3"},
                                          {"[GHI]","4"},{"[JKL]","5"},
                                          {"[MNO]","6"},{"[PQRS]", "7"},
                                          {"[TUV]","8"},{"[WXYZ]","9"}};
        //System.out.println(letterToNumberArray.length);
        for( int i =0; i < letterToNumberArray.length; i++){
            output = output.replaceAll(letterToNumberArray[i][0],letterToNumberArray[i][1]);
            
        }
        //System.out.println(output);
        return output;
    }
    

    public static String[] checkNumber(String input){
	
        String[] output = {"prefix", "suffix"};

	/* Removes Acceptable characters from line*/
	//input = input.replaceAll("[ ()-]","");

	/*Check for non allowed characters*/
	if(input.matches("[^A-Z0-9() \\-]")){
	    System.out.println("Reject point 1");
            return INVALID_NUMBER;
        }
          
        int length = input.length();
	
	/*Remove Spaces*/
	//input = input.replaceAll("[ ]","");
       	
        /* Process numbers starting with 0508*/
        if(input.matches("\\(?0508\\)? ?\\w{3} ?\\-?\\w{3}[A-Z]{0,3}")){
	    input = input.replaceAll("[() ]","");
            //if(input.matches(".{10}[A-Z]{0,3}")){
                //System.out.println("Check for extra numbers");
                output[0] = "0508";
                output[1] = input.substring(4,10);
                return output;
		//}else{
		//return INVALID_NUMBER;
		//}
        }
        
        input = input.replaceAll("[ ]","");  
        /* Processes numbers starting with 0800*/
        if(input.matches("\\(?0800\\)? ?\\w{3} ?\\-?\\w{3,4}")){
	    input = input.replaceAll("[()]","");
            //System.out.println("0800 Recognised");
            if(input.matches(".{10,11}[A-Z]{0,3}")){
                output[0] = "0800";
                output[1] = input.substring(4);
                return output;
            }else{
                return INVALID_NUMBER;
            }
        }

        
git
        /* Processes number starting with 0900*/
        if(input.matches("0900\\w{5,9}")){
	    input = input.replaceAll("[()]","");
            if(input.matches(".{9}[A-Z]{0,4}")){
                //System.out.println("Check for extra numbers");
                output[0] = "0900";
                output[1] = input.substring(4);
                return output;
            }else{
                return INVALID_NUMBER;
            }
        }
	
        /* Process numbers starting with 02 + 409 for Scott Base*/
        if(input.matches("\\(?02\\)?409\\-?\\d{4}")){
	    input = input.replaceAll("[()]","");
            output[0] = "02";
            output[1] = input.substring(2);
            return output;
        }

        /* Checks Numbers starting with area codes*/
        if(input.matches("\\(?0[34679]\\)?[2-9]\\d{2}\\-?\\d{4}")){
	    input = input.replaceAll("[()\\-]","");
            if(!(input.matches("..900.*|..911.*|..999.*"))){
                output[0] = input.substring(0,2);
                output[1] = input.substring(2);
                return output;
            }
        }
    
        /* Checks mobile numbers starting in 021 */
        if(input.matches("\\(?021\\)?\\d{3}\\-?\\d{3,4}")
	   ||input.matches("\\(?021\\)?\\d{4}\\-?\\d{4}")){
	    input = input.replaceAll("[()\\-]","");
            output[0] = "021";
            output[1] = input.substring(3);
            return output;
        }

        /* Checks mobile numbers starting in 022 or 027 */
        if(input.matches("\\(?02[27]\\)?\\d{3}\\-?\\d{4}")){
	    input = input.replaceAll("[()-]","");
            output[0] = input.substring(0,3);
            output[1] = input.substring(3);
            return output;
        }
    
        /* Checks mobile numbers starting in 025 */
        if(input.matches("\\(?025\\)?\\d{3}\\-?\\d{3}")){
	    input = input.replaceAll("[()-]","");
            output[PREFIX] = "027";
            output[SUFFIX] = "4" + input.substring(3);
            return output;
        }
        
        return INVALID_NUMBER;
    }
        
}
    

