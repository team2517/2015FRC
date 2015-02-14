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
}
