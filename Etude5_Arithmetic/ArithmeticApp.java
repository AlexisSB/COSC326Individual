/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arithmetic;

import java.util.*;
/**
 *
 * @author alexis
 */
public class ArithmeticApp {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            String numberInput = scan.nextLine();
            long total = scan.nextLong();
            String resultsInput = scan.nextLine().trim();
            String[] numbersString = numberInput.split(" ");
            long[] numbersLong = new long[numbersString.length];

            for (int i = 0; i < numbersLong.length;i++){
                numbersLong[i] = Long.parseLong(numbersString[i]);
            }
            System.err.println(Arrays.toString(numbersLong));
            System.err.println(total + " " + resultsInput);

            Arithmetic solutionFinder = new Arithmetic(numbersLong,total);
            System.out.println(solutionFinder.depthFirstSearch(resultsInput));

            }
        }



    }
