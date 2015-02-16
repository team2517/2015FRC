package org.usfirst.frc.team2517.robot;

import org.usfirst.frc.team2517.robot.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

public class SwerveController {
	ArrayList<Double> mags = new ArrayList<Double>();
	SwerveModule swerveFL; // Initialize one object for each module. Format is turnJag, moveJag, encID
	SwerveModule swerveFR;
//	SwerveModule swerveBL;
//	SwerveModule swerveBR;
	private double largestMag;
	
	public SwerveController(int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR ){
//							int moveBL, int turnBL, int encBL,
//							int moveBR, int turnBR, int encBR){
		swerveFL = new SwerveModule(moveFL, turnFL, encFL, 0.707, 0.707, 1.343);
		swerveFR = new SwerveModule(moveFR, turnFR, encFR, 0.707, -0.707, 4.930);
//		swerveBL = new SwerveModule(moveBL, turnBL, encBL, -0.6703, 0.7421);
//		swerveBR = new SwerveModule(moveBR, turnBR, encBR, -0.7421, -0.6703);
	}
	
	public void swerve(double xVector, double yVector, double phi){
		swerveFL.x = (swerveFL.corX*phi)+xVector;
		swerveFL.y = (swerveFL.corY*phi)+yVector; // Someday outsource these into a SwerveModule function and run 4 times instead of 8
		swerveFR.x = (swerveFR.corX*phi)+xVector;
		swerveFR.y = (swerveFR.corY*phi)+yVector;
//		swerveBL.x = (swerveBL.corX*phi)+xVector;
//		swerveBL.y = (swerveBL.corY*phi)+yVector;
//		swerveBR.x = (swerveBR.corX*phi)+xVector;
//		swerveBR.y = (swerveBR.corY*phi)+yVector;
		
		// Find magnitudes through pythagorean therom
		swerveFL.mag = Math.sqrt(Math.pow(swerveFL.x,2) + Math.pow(swerveFL.y, 2));
		swerveFR.mag = Math.sqrt(Math.pow(swerveFR.x,2) + Math.pow(swerveFR.y, 2));
//		swerveBL.mag = Math.sqrt(Math.pow(swerveBL.x,2) + Math.pow(swerveBL.y, 2));
//		swerveBR.mag = Math.sqrt(Math.pow(swerveBR.x,2) + Math.pow(swerveBR.y, 2));
		
		// Add all magnitudes to arraylist
		mags.add(swerveFL.mag);
		mags.add(swerveFR.mag);
//		mags.add(swerveBL.mag);
//		mags.add(swerveBR.mag);
		
		largestMag = Collections.max(mags);
		
		if(largestMag > 1){ // If one mag is greater than 1 then scale the rest of the modules by the largest magnitude
			swerveFL.mag = swerveFL.mag / largestMag;
			swerveFR.mag = swerveFR.mag / largestMag;
//			swerveBL.mag = swerveBL.mag / largestMag;
//			swerveBR.mag = swerveBR.mag / largestMag;
		}
		
		swerveFL.tarTheta = Math.atan(swerveFL.y / swerveFL.x); // Calculate the angles we want to be at with the joystick inputs
		swerveFR.tarTheta = Math.atan(swerveFR.y / swerveFR.x);
//		swerveBL.tarTheta = Math.atan2(swerveBL.y, swerveBL.x);
//		swerveBR.tarTheta = Math.atan2(swerveBR.y, swerveBR.x);
		if (swerveFL.x < 0){
			swerveFL.tarTheta += Math.PI;
		}
		if (swerveFR.x < 0){
			swerveFR.tarTheta += Math.PI;
		}
		
		swerveFL.update(); // We need to run this to set the values of the motor controllers
		swerveFR.update();
//		swerveBL.update();
//		swerveBR.update();
		SmartDashboard.putNumber("FLturnSpeed", swerveFL.turnSpeed);
		SmartDashboard.putNumber("FLtarTheta", swerveFL.tarTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FLcurTheta", swerveFL.curTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FLdiffTheta", swerveFL.diffTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FLmag", swerveFL.mag);
		SmartDashboard.putNumber("FRturnSpeed", swerveFR.turnSpeed);
		SmartDashboard.putNumber("FRtarTheta", swerveFR.tarTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FRcurTheta", swerveFR.curTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FRdiffTheta", swerveFR.diffTheta/Math.PI *57.2957795);
		SmartDashboard.putNumber("FRmag", swerveFR.mag);
		SmartDashboard.putNumber("StickX", Robot.stickX);
		SmartDashboard.putNumber("StickY", Robot.stickY);
		SmartDashboard.putNumber("StickPhi", Robot.stickPhi);
		
		SmartDashboard.putNumber("FrontLeftCORX", swerveFR.corX);
		SmartDashboard.putNumber("FrontLeftCORY", swerveFR.corY);
		SmartDashboard.putNumber("FrontRightCORX", swerveFR.corX);
		SmartDashboard.putNumber("FrontRightCORY", swerveFR.corY);
	}
}
