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
	public double x, y, corX, corY, mag, tarTheta, diffTheta, curTheta, turnSpeed;
	private double offset;
	public SwerveModule(int mTalID, int tJagID, int eID, double xCOR, double yCOR, double off)
	{
//		try{ We need to get the CANNotFoundException thing here and probably some for PID as well.
		turnJag = new CANJaguar(tJagID);
//		} catch()
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
		offset = off; // Pass this in later
	}
	public void update()
	{
		curTheta = -(encoder.getVoltage() - offset )/5*(2*Math.PI);
		
		diffTheta = tarTheta - curTheta;
		
		// If our angle is over PI, then subtract PI*2 to bring the theta to be a smaller negative number
		if (diffTheta > Math.PI) {
			diffTheta -= 2 * Math.PI;
		} // Converse for last statement for if the theta is less than PI
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
		
		turnSpeed = diffTheta / (Math.PI / 2);
		
		if (0 < turnSpeed && turnSpeed < 0.15){
			turnSpeed = 0.15;
		}
		if (0 > turnSpeed && turnSpeed > -0.15){
			turnSpeed = -0.15;
		}
		if (Math.abs(diffTheta) < Math.PI / 20){
			turnSpeed = 0;
		}
		
		
		
		if (Robot.stickX == 0 && Robot.stickY == 0 && Robot.stickPhi == 0){ // Hold Position if joystick is not being pressed
			turnJag.set(0);
			moveTal.set(0);
		}
//		else if (diffTheta < Math.PI / 36){ // Prevent the motor jittering to correct itself (5 degrees thereshold)
//			turnJag.set(0);
//			moveTal.set(0);
//		}
		else{
			turnJag.set(turnSpeed);
			moveTal.set(mag);
		}
	}
}
