package numbers;

import java.util.*;

public class StandardDeviation{

    public static int[] inputData;
    
    public static void main (String[] args){
	Scanner scan = new Scanner(System.in);
	int population = 1000000;
	float meanSinglePrecision;
	double meanDoublePrecision;
	inputData = new int[population];
	for (int i = 0; i<population;i++){
	    inputData[i] = scan.nextInt();
            inputData[i] += 1;
	}
        
	//System.err.println(Arrays.toString(data));

        for (int popSize = 100; popSize <=population; popSize *=10){
            int[] instanceData = Arrays.copyOfRange(inputData, 0,popSize +1);
            float meanFloat = calculateFloatMean(instanceData);
            double meanDouble = calculateDoubleMean(instanceData);
            float stdDevFloat = calculateFloatStandardDeviation(meanFloat,instanceData);
            System.err.println("Single Precision StdDev with Mean : " + stdDevFloat);
            
            double stdDevDouble = calculateDoubleStandardDeviation(meanDouble, instanceData);
            System.err.println("Double Precision StdDev with Mean : " + stdDevDouble);
            
            float stdDevFloatNoMean = calculateFloatStandardDeviation(instanceData);
            System.err.println("Single Precision StdDev No Mean : " + stdDevFloatNoMean);
            double stdDevDoubleNoMean = calculateDoubleStandardDeviation(instanceData);
            System.err.println("Double Precision StdDev No Mean : " + stdDevDoubleNoMean);
            System.out.println(popSize + "," + stdDevFloat+","+ stdDevDouble
                               +","+ stdDevFloatNoMean + "," + stdDevDoubleNoMean);
	}
	
    }

    public static float calculateFloatMean(int[] data){
	float sum = 0;
	for(int i = 0; i <data.length;i++){
	    sum += data[i];
	}
	float mean = sum/data.length;
	System.err.println("Single Precision Mean : " + mean);
	return mean;
    }

    public static double calculateDoubleMean(int[] data){
	double sum = 0;
	for(int i = 0; i <data.length;i++){
	    sum += data[i];
	}
	double mean = sum/data.length;
	System.err.println("Double Precision Mean : " + mean);
	return mean;
    }

    public static float calculateFloatStandardDeviation(float mean, int[] data){
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

    public static double calculateDoubleStandardDeviation(double mean, int[] data){
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
    
    public static float calculateFloatStandardDeviation(int[] data){
        float sumOfASquared=0;
        float sumOfA = 0;

        for(int i =0;i<data.length;i++){
            sumOfASquared += (float)(data[i]*data[i]);
            sumOfA += data[i];
        }

        float topHalf = sumOfASquared - ((sumOfA*sumOfA)/data.length);
        float termInsideSqrt = topHalf/data.length;

        float stdDev = (float) Math.sqrt(termInsideSqrt);
        
	return stdDev;
    }

    public static double calculateDoubleStandardDeviation(int[]data){
        double sumOfASquared=0;
        double sumOfA = 0;

        for(int i =0;i<data.length;i++){
            sumOfASquared += (double)(data[i]*data[i]);
            sumOfA += data[i];
        }

        double topHalf = sumOfASquared - ((sumOfA*sumOfA)/data.length);
        double termInsideSqrt = topHalf/data.length;

        double stdDev = Math.sqrt(termInsideSqrt);
        
	return stdDev;

       
    }

}
