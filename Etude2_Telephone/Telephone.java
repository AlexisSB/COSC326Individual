package telephone;

import java.util.*;

/**
 * COSC326 SS 2018 Etude 2 Telephone
 * Class processes telephone numbers from stdin.
 * Outputs numbers in standard format or INV for invalid numbers
 * Etude brief at http://www.cs.otago.ac.nz/cosc326/etudes2018ss/02.pdf
 * @author Alexis Barltrop 2018
 */
public class Telephone{

    private static String[] INVALID_NUMBER = {"INV"};
    private static String INVALID_OUTPUT = "INV";
    private static final int PREFIX = 0;
    private static final int SUFFIX =1;
    
    /**
     * Main method takes input from stdin.
     * Sends to checkNumber for validation.
     * If number is valid it will replace any letters with numbers.
     * Then format the number to the standard format.
     * And then prints to std out.
     * Checks for duplicates using a hashtable to store the numbers.
     * @param args - cmd line arguements not used here.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Hashtable<Integer,String> phoneBook = new Hashtable<Integer,String>(100);

        while (scan.hasNextLine()){
            String line = scan.nextLine();
            String[] numberInProgress;
            
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
        }
    }
    
   
    /**
     * Takes a phone number in any format and checks whether
     * it is a valid phone number format.
     * Valid formats covered in the brief document.
     * The number is split up into an initial code -> the prefix
     * and a string of digits -> the suffix.
     * This aides later processing.
     * If invalid number will return the INVALID_NUMBER array.
     * @param input - a potential phone number to check.
     * @return If number is valid returns a length 2 String array with
     * first entry the prefix and second entry the suffix
     * else INVALID_NUMBER array.
     */
    public static String[] checkNumber(String input){
	
        String[] output = {"prefix", "suffix"};

	/*Check for non allowed characters*/
	if(input.matches(".*[^A-Z0-9() \\-]+.*")){
	    //System.err.println("Reject point 1"+ input);
            return INVALID_NUMBER;
        }
          
        /* Process numbers starting with 0508*/
        if(input.matches("\\(?0508\\)? ?\\d{3}[ \\-]?\\d{3}")
           ||input.matches ("\\(?0508\\)? ?\\w{3} ?\\w{3}[A-Z]{0,3}")){
	    input = removeAcceptablePunctuation(input);
            //if(input.matches(".{10}[A-Z]{0,3}")){
                //System.out.println("Check for extra numbers");
                output[PREFIX] = "0508";
                output[SUFFIX] = input.substring(4,10);
                return output;
		//}else{
		//return INVALID_NUMBER;
		//}
        }
        
        /* Processes numbers starting with 0800*/
        if(input.matches("\\(?0800\\)? ?\\d{3}[ \\-]?\\d{3,4}")
           || input.matches("\\(?0800\\)? ?\\w{3} ?\\w{3}[A-Z]{0,3}" )
           || input.matches("\\(?0800\\)? ?\\w{3} ?\\w{4}[A-Z]{0,2}" )){
	    input = removeAcceptablePunctuation(input);
            //System.err.println("0800 check: " + input);
            //System.out.println("0800 Recognised");
            if(input.matches(".{10,11}[A-Z]{0,3}")){
                output[PREFIX] = "0800";
                if(input.length()==10){
                    output[SUFFIX] = input.substring(4,10);
                }else{
                    output[SUFFIX] = input.substring(4,11);
                }
                return output;
            }else{
                return INVALID_NUMBER;
            }
        }
        
       
       
        /* Processes number starting with 0900*/
        if(input.matches("\\(?0900\\)? ?\\w{5}[A-Z]{0,4}")){
	    input = removeAcceptablePunctuation(input);
            //System.out.println("Check for extra numbers");
            output[PREFIX] = "0900";
            output[SUFFIX] = input.substring(4,9);
            return output;
        }
        
	
        /* Process numbers starting with 02 + 409 for Scott Base*/
        if(input.matches("\\(?02\\)? ?409[\\- ]?\\d{4}")){
            input = removeAcceptablePunctuation(input);
            //System.err.println("02 check: " + input);
            output[PREFIX] = "02";
            output[SUFFIX] = input.substring(2);
            return output;
        }
        
        /* Processes numbers starting with area codes 03,04,06,07,09*/
        if(input.matches("\\(?0[34679]\\)? ?[2-9]\\d{2}[ \\-]?\\d{4}")){
	    input = removeAcceptablePunctuation(input);
            //System.err.println("03 check: " + input);
            if(!(input.matches("..900.*|..911.*|..999.*"))){
                output[PREFIX] = input.substring(0,2);
                output[SUFFIX] = input.substring(2);
                return output;
            }
        }
    
        /* Processes  mobile numbers starting in 021 */
        if(input.matches("\\(?021\\)? ?\\d{3}[ \\-]?\\d{3,4}")
	   ||input.matches("\\(?021\\)? ?\\d{4}[\\- ]?\\d{4}")){
	    input = removeAcceptablePunctuation(input);
            output[PREFIX] = "021";
            output[SUFFIX] = input.substring(3);
            return output;
        }

        /* Processes mobile numbers starting in 022 or 027 */
        if(input.matches("\\(?02[27]\\)? ?\\d{3}[\\- ]?\\d{4}")){
            input = removeAcceptablePunctuation(input);
            output[PREFIX] = input.substring(0,3);
            output[SUFFIX] = input.substring(3);
            return output;
        }
    
        /* Processes mobile numbers starting in 025 */
        if(input.matches("\\(?025\\)? ?\\d{3}[\\- ]?\\d{3}")){
	    input = removeAcceptablePunctuation(input);
            output[PREFIX] = "027";
            output[SUFFIX] = "4" + input.substring(3);
            return output;
        }
        
        return INVALID_NUMBER;
    }

    /**
     * Removes spaces, brackets and dashes/hyphens from the phone number.
     * @param input - Phone number string being processed.
     * @return phone number string without punctuation characters.
     */
    public static String removeAcceptablePunctuation(String input){
        String output = input.replaceAll("[()\\- ]","");
        return output;
    }

    /**
     * Converts phone number to standard format.
     * Input must contain only digits.
     * @param phone number as string array with prefix and suffix entries.
     * @return phone number in standard format as string.
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
    
    /**
     * Replaces any letters in a phone number with the corresponding digit.
     * Letter to digit key can be found in etude brief document.
     * Phone number only have  uppercase letters.
     * @param phone number containing mix of digits and letters
     * @return phone number with only digits.
     */
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
    
        
}
    

