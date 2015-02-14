package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Utils {
	public static double deadband(double input, double tolerance){ // Set number to 0 if in between tolerance and -tolerance
		if(Math.abs(input) < tolerance){
			return 0;
		}
		else{
			return input;
		}
	}
	public static void dashboardAdd(String key, double value){
		if (Robot.stagger >= Robot.staggerMax){
			SmartDashboard.putNumber(key, value);
		}
	}
}