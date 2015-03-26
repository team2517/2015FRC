
package org.usfirst.frc.team2517.robot;

public class Utils {
	public static double deadband(double input, double tolerance){ // Set number to 0 if in between tolerance and -tolerance
		if(Math.abs(input) < tolerance){
			return 0;
		}
		else{
			return input;
		}	
	}
	public static double wrap(double input, double min, double max){
		if(input < min) {
			input = max+input;
			if(input < min){
				input = min;
			}
			return input;
		}
		else if(input > max){
			input = input-max;
			if(input > max){
				input = max;
			}
			return input;
		}
		else{
			return input;
		}
	}
}
