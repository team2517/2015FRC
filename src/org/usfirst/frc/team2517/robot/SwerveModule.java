package org.usfirst.frc.team2517.robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Talon;

/* *
 * We want the swerve module class to
 * 1. Initialize 2 CANJaguars as parameters
 * 2. Directly receive direction and mag variables through a function and move the 
 * motorTurn jaguar until it has reached its goal
 * 3. Look nice and have comments
 * 
 * You will need to basically rip code from last year's math code and convert it to java
 * 
 * */

public class SwerveModule {
	private CANJaguar turnJag;
	private Talon moveTal;
	public AnalogInput encoder;
	//private static double minVoltage = 0.2;
	public double x, y, corX, corY, mag, tarTheta, turnSpeed, diffTheta, curTheta;
	private double offset;
	public SwerveModule(int mTalID, int tJagID, int eID, double xCOR, double yCOR)
	{
//		try{ We need to get the CANNotFoundException thing here and probably some for PID as well.
		turnJag = new CANJaguar(tJagID);
//		} catch()
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
		offset = 0; // Pass this in later
	}
	public void update()
	{
		curTheta = -(encoder.getVoltage() - offset ) / 5 * 2 * Math.PI;
		
		diffTheta = tarTheta - curTheta;
		
		if (diffTheta > Math.PI) {
			diffTheta -= 2 * Math.PI;
		} 
		else if (diffTheta < - Math.PI) {
			diffTheta += 2 * Math.PI;
		}

		if (diffTheta > Math.PI / 2) {
			diffTheta -= Math.PI;
			mag = mag * -1;
		} 
		else if (diffTheta < -Math.PI / 2){
			diffTheta += Math.PI;
			mag = mag * -1;
		}
		turnSpeed = diffTheta / Math.PI / 2;
		if (0 < turnSpeed && turnSpeed < 0.25){
			turnSpeed = 0.25;
		}
		if (0 > turnSpeed && turnSpeed > -0.25){
			turnSpeed = -0.25;
		}
		if (Math.abs(diffTheta) < Math.PI / 45){
			turnSpeed = 0;
		}
	}
}
