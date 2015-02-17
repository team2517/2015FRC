package org.usfirst.frc.team2517.robot;

import org.usfirst.frc.team2517.robot.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

public class SwerveController {
	/* *
	 * 1 entry in the swerves array for each module. Order is:
	 * swerves[0] = Front Left Swerve
	 * swerves[1] = Front Right Swerve
	 * swerves[2] = Back Left Swerve
	 * swerves[3] = Back Right Swerve
	 * */
	SwerveModule[] swerves = new SwerveModule[4]; 
	private double largestMag;
	ArrayList<Double> mags = new ArrayList<Double>();
	int calMode = 0;
	
	public SwerveController(int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR ){
//							int moveBL, int turnBL, int encBL,
//							int moveBR, int turnBR, int encBR){
		swerves[0] = new SwerveModule(moveFL, turnFL, encFL, 0.707, 0.707, 3.962);
		swerves[1] = new SwerveModule(moveFR, turnFR, encFR, 0.707, -0.707, 4.868);
//		swerves[2] = new SwerveModule(moveBL, turnBL, encBL, -0.6703, 0.7421);
//		swerves[3] = new SwerveModule(moveBR, turnBR, encBR, -0.7421, -0.6703);
	}
	
	public void swerve(double xVector, double yVector, double phi){
		swerves[0].x = (swerves[0].corX*phi)+xVector;
		swerves[0].y = (swerves[0].corY*phi)+yVector; 
		swerves[1].x = (swerves[1].corX*phi)+xVector;
		swerves[1].y = (swerves[1].corY*phi)+yVector;
//		swerves[2].x = (swerves[2].corX*phi)+xVector;
//		swerves[2].y = (swerves[2].corY*phi)+yVector;
//		swerves[3].x = (swerves[3].corX*phi)+xVector;
//		swerves[3].y = (swerves[3].corY*phi)+yVector;
		
		// Find magnitudes through pythagorean therom
		swerves[0].mag = Math.sqrt(Math.pow(swerves[0].x,2) + Math.pow(swerves[0].y, 2));
		swerves[1].mag = Math.sqrt(Math.pow(swerves[1].x,2) + Math.pow(swerves[1].y, 2));
//		swerves[2].mag = Math.sqrt(Math.pow(swerves[2].x,2) + Math.pow(swerves[2].y, 2));
//		swerves[3].mag = Math.sqrt(Math.pow(swerves[3].x,2) + Math.pow(swerves[3].y, 2));
		
		// Add all magnitudes to arraylist
		mags.add(swerves[0].mag);
		mags.add(swerves[1].mag);
//		mags.add(swerves[2].mag);
//		mags.add(swerves[3].mag);
		
		largestMag = Collections.max(mags);
		
		if(largestMag > 1){ // If one mag is greater than 1 then scale the rest of the modules by the largest magnitude
			swerves[0].mag = swerves[0].mag / largestMag;
			swerves[1].mag = swerves[1].mag / largestMag;
//			swerves[2].mag = swerves[2].mag / largestMag;
//			swerves[3].mag = swerves[3].mag / largestMag;
		}
		
		swerves[0].tarTheta = Math.atan2(swerves[0].y , swerves[0].x); // Calculate the angles we want to be at with the joystick inputs
		swerves[1].tarTheta = Math.atan2(swerves[1].y , swerves[1].x);
//		swerves[2].tarTheta = Math.atan2(swerves[2].y, swerves[2].x);
//		swerves[3].tarTheta = Math.atan2(swerves[3].y, swerves[3].x);
//		if (swerves[0].x < 0){
//			swerves[0].tarTheta += Math.PI;
//		}
//		if (swerves[1].x < 0){
//			swerves[1].tarTheta += Math.PI;
//		}
		
		swerves[0].update(); // We need to run this to set the values of the motor controllers
		swerves[1].update();
//		swerves[2].update();
//		swerves[3].update();
		
		SmartDashboard.putNumber("FLturnSpeed", swerves[0].turnSpeed); // Values into driverstation for debug purposes
		SmartDashboard.putNumber("FLtarTheta", swerves[0].tarTheta *(180/Math.PI));
		SmartDashboard.putNumber("FLcurTheta", swerves[0].curTheta *(180/Math.PI));
		SmartDashboard.putNumber("FLdiffTheta", swerves[0].diffTheta *(180/Math.PI));
		SmartDashboard.putNumber("FLmag", swerves[0].mag);
		SmartDashboard.putNumber("FRturnSpeed", swerves[1].turnSpeed);
		SmartDashboard.putNumber("FRtarTheta", swerves[1].tarTheta *(180/Math.PI));
		SmartDashboard.putNumber("FRcurTheta", swerves[1].curTheta *(180/Math.PI));
		SmartDashboard.putNumber("FRdiffTheta", swerves[1].diffTheta *(180/Math.PI));
		SmartDashboard.putNumber("FLrawDiffTheta", swerves[1].rawDiffTheta *(180/Math.PI));
		SmartDashboard.putNumber("FRrawDiffTheta", swerves[1].rawDiffTheta *(180/Math.PI));
		SmartDashboard.putNumber("FRdiffTheta", swerves[1].diffTheta *(180/Math.PI));
		SmartDashboard.putNumber("FRmag", swerves[1].mag);
		SmartDashboard.putNumber("StickX", xVector);
		SmartDashboard.putNumber("StickY", yVector);
		SmartDashboard.putNumber("StickPhi", phi);

		SmartDashboard.putNumber("FrontLeftRawEnc", SwerveModule.encoder.getVoltage());
		SmartDashboard.putNumber("FrontRightRawEnc", SwerveModule.encoder.getVoltage());
		
		SmartDashboard.putNumber("FrontLeftCORX", swerves[0].corX);
		SmartDashboard.putNumber("FrontLeftCORY", swerves[0].corY);
		SmartDashboard.putNumber("FrontRightCORX", swerves[1].corX);
		SmartDashboard.putNumber("FrontRightCORY", swerves[1].corY);

		SmartDashboard.putNumber("FrontRightCORY*Phi", swerves[1].corY*phi);
		SmartDashboard.putNumber("FrontRightCORX*Phi", swerves[1].corX*phi);
		SmartDashboard.putNumber("FrontLeftCORY*Phi", swerves[0].corY*phi);
		SmartDashboard.putNumber("FrontLeftCORX*Phi", swerves[0].corX*phi);

		SmartDashboard.putBoolean("FrontLeftReversingMag", swerves[0].reverseMag);
		SmartDashboard.putBoolean("FrontRightReversingMag", swerves[1].reverseMag);
		
	}
	/**
	 * Turns all wheels at the rate of tSpeed and moves the wheels at the rate of tSpeed
	 * @param tSpeed
	 *            Speed to move all of the turning motor controllers
	 * @param mSpeed
	 *            Speed to move all of the moving motor controllers
	 */
	public void updateAll(double tSpeed, double mSpeed){
		for(int i=0; i < 4; i++){
			swerves[i].rawUpdate(true, tSpeed);
			swerves[i].rawUpdate(false, mSpeed);
		}
		SmartDashboard.putNumber("FrontLeftRawEnc", SwerveModule.encoder.getVoltage());
		SmartDashboard.putNumber("FrontRightRawEnc", SwerveModule.encoder.getVoltage());
	}
}
