package numbers;

import java.util.*;

public class StandardDeviation{

    public static int[] data;
    
    public static void main (String[] args){
	Scanner scan = new Scanner(System.in);
	int population = scan.nextInt();
	float meanSinglePrecision;
	double meanDoublePrecision;
	data = new int[population];
	for (int i = 0; i<population;i++){
	    data[i] = scan.nextInt();
	}
	System.err.println(Arrays.toString(data));
	float meanFloat = calculateFloatMean();
	float stdDevFloat = calculateFloatStandardDeviation(meanFloat);
	System.err.println("Single Precision Mean Calculated : " + stdDevFloat);
	double meanDouble = calculateDoubleMean();
	double stdDevDouble = calculateDoubleStandardDeviation(meanDouble);
	System.err.println(" Double Precision Mean Calculated : " + stdDevDouble);
	
	
    }

    public static float calculateFloatMean(){
	float sum = 0;
	for(int i = 0; i <data.length;i++){
	    sum += data[i];
	}
	float mean = sum/data.length;
	System.err.println("Single Precision Mean : " + mean);
	return mean;
    }

    public static double calculateDoubleMean(){
	double sum = 0;
	for(int i = 0; i <data.length;i++){
	    sum += data[i];
	}
	double mean = sum/data.length;
	System.err.println("Double Precision Mean : " + mean);
	return mean;
    }

    public static float calculateFloatStandardDeviation(float mean){
	float sum = 0;
	for(int i=0;i<data.length;i++){
	    //Using times operator to avoid using Math libraries that use double precision.
	    float variance = (data[i]-mean)*(data[i]-mean);
	    sum+=variance; 
	    
	}

	float divideByPopulation = sum/data.length;
	float stdDeviation = (float)Math.sqrt(divideByPopulation);
	return stdDeviation;
	
    }

    public static double calculateDoubleStandardDeviation(double mean){
	double sum = 0;
	for(int i=0;i<data.length;i++){
	    //Using times operator to avoid using Math libraries that use double precision.
	    double variance = (data[i]-mean)*(data[i]-mean);
	    sum+=variance; 
	    
	}
	
	double divideByPopulation = sum/data.length;
	double stdDeviation = Math.sqrt(divideByPopulation);
	return stdDeviation;
    }
    
    public static float calculateFloatStandardDeviation(){
	return -1;
    }

    public static double calculateDoubleStandardDeviation(){
	return -1;
    }

}
