package numbers;

import java.util.*;

/**
 * COSC326 SS 2018 Etude 6
 * Checks and algebraic Identity for values x and y.
 * The identity can be expressed as
 * x = ((x/y) - (x*y))*y + x*y*y
 * Attempt to see if single of double precision affects the equality.
 * Equality assumed to be in a certain number of digits
 * @author Alexis Barltrop
 */
public class Identity{

    public static void main (String[] args){


        //double xD = 2.0;
        //double yD = 1.0*Math.pow(10,8);

        //double xD = 1.0;
        //double yD = 1.0*Math.pow(10,70);

        //double xD = Math.pow(10,100);
        //double yD = 3.0;

        //double xD = Math.pow(10,70);
        //double yD = 1.0;

        double xD = 1.0;
        double yD = Math.pow(10,10);
        System.out.println(xD/yD);
        System.out.println((xD/yD) - (xD*yD));
        System.out.println(((xD/yD) - (xD*yD))*yD);
        System.out.println((xD*yD*yD));
        //double xD = 1.0;
        //double yD = 100000000000000000006.0;

        //double xD = 1.0;
        //double yD = Math.pow(10,20) +6.0;

        float xF = (float) xD;
        float yF = (float) yD;


        float identityF = ((xF/yF) - (xF*yF))*yF + (xF*yF*yF);
        System.out.println(identityF);



        double identityD = ((xD/yD) - (xD*yD))*yD + (xD*yD*yD);
        System.out.println(identityD);

    }


}
