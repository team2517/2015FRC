package org.usfirst.frc.team2517.robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Talon;

public class SwerveModule {
	private CANJaguar turnJag;
	private Talon moveTal;
	public static AnalogInput encoder;
	public double x, y, corX, corY, mag, tarTheta, diffTheta, curTheta, turnSpeed, rawDiffTheta;
	public boolean reverseMag = false;
	private double offset;
	public static String status;
	public SwerveModule(int mTalID, int tJagID, int eID, double xCOR, double yCOR, double off)
	{
		status = "Clear";
		try{
			turnJag = new CANJaguar(tJagID);
		} 
		catch(edu.wpi.first.wpilibj.can.CANMessageNotFoundException e){
			status = e.toString();
		}
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
		offset = off; // Pass this in later
	}
	public void update()
	{
		
		tarTheta = Math.atan2(y , x); // Calculate the angles we want to be at with the joystick inputs
		
		/**
		 * Takes all of the variables modified and updates the motor controllers in the module
		 */
		curTheta = (encoder.getVoltage() - offset)/5*(2*Math.PI);
		
		rawDiffTheta = tarTheta - curTheta;
		diffTheta = rawDiffTheta;
		
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
			reverseMag = true;
		} 
		else if (diffTheta < -Math.PI / 2){
			diffTheta += Math.PI;
			mag = mag * -1;
			reverseMag = true;
		}
		else{
			reverseMag = false;
		}
		
		turnSpeed = diffTheta / (Math.PI / 2);
		
		if (0 < turnSpeed && turnSpeed < 0.15){
			turnSpeed = 0.15;
		}
		if (0 > turnSpeed && turnSpeed > -0.15){
			turnSpeed = -0.15;
		}
		
		if (Math.abs(diffTheta) < Math.PI / 10){
			turnSpeed = 0;
		}
		
		
		
		if (Robot.stickX == 0 && Robot.stickY == 0 && Robot.stickPhi == 0){ // Hold Position if joystick is not being pressed
			turnJag.set(0);
			moveTal.set(0);
		}
		else{
			turnJag.set(turnSpeed);
			moveTal.set(mag);
		}
	}
	public void updateMag(){
		mag = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
	}
}
