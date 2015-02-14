package org.usfirst.frc.team2517.robot;
import java.lang.Math;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public double x, y, corX, corY, mag, tarTheta;
	public double diffTheta, curTheta, offset, turnVel, prevTurnVel, moveTime;
	private boolean changeSign;
	public SwerveModule(int mTalID, int tJagID, int eID, double xCOR, double yCOR)
	{
		turnJag = new CANJaguar(tJagID);
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
		offset = 0; // Pass this in later
	}
	public void update()
	{
		curTheta = -(encoder.getVoltage() - offset ) / 5 * 2 * Math.PI; // Get current angle in radians
		
		diffTheta = tarTheta - curTheta;
		
		if (diffTheta > Math.PI){ // lines 537-557
			diffTheta -= 2 * Math.PI;
		} 
		else if (diffTheta < - Math.PI){
			diffTheta += 2 * Math.PI;
		}

		if (diffTheta > Math.PI / 2) {
			diffTheta -= Math.PI;
			mag = mag * -1;
		} 
		else if (diffTheta < -Math.PI / 2) {
			diffTheta += Math.PI;
			mag = mag * -1;
		}
		
		turnVel = diffTheta / (Math.PI/2); // Line 559
		
		if (0 < turnVel && turnVel < .25){
			turnVel = .25;
		} 
		if (0 > turnVel && turnVel > -.25){
			turnVel = -.25;
		}
		if (Math.abs(diffTheta) < Math.PI/9 ){
			turnVel = 0;
		}
		if (((turnVel > 0 && prevTurnVel < 0)
				|| (turnVel < 0&& prevTurnVel> 0)) 
				&& !changeSign){
			changeSign = true;
		}
		if (changeSign){
			turnVel = 0;
		}
		
		
		turnJag.set(turnVel);
		moveTal.set(mag);
		
		
		
	}
}
