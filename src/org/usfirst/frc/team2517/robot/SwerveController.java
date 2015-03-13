package org.usfirst.frc.team2517.robot;
import org.usfirst.frc.team2517.robot.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.*;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

/**
 * 1 entry in the swerves array for each module. Order is:
 * swerves[0] = Front Left Swerve
 * swerves[1] = Front Right Swerve
 * swerves[2] = Back Left Swerve
 * swerves[3] = Back Right Swerve
 */
public class SwerveController {
	SwerveModule[] swerves = new SwerveModule[1]; 
	private double largestMag;
	private ArrayList<Double> mags = new ArrayList<Double>();
	public int calMode = 0;
	private final boolean debug = true;
	
	/**
	 * Constructor for a swerve helper class
	 * @param moveFL
	 *            ID to the Front Left motor controller for movement in the swerve
	 * @param turnFL
	 *            ID to the Front Left motor controller for turning in the swerve
	 * @param encFL
	 *            ID to the Front Left analog absolute encoder
	 *            
	 * @param moveFR
	 *            ID to the Front Right motor controller for movement in the swerve
	 * @param turnFR
	 *            ID to the Front Right motor controller for turning in the swerve
	 * @param encFR
	 *            ID to the Front Right analog absolute encoder
	 *            
	 * @param moveBL
	 *            ID to the Back Left motor controller for movement in the swerve
	 * @param turnBL
	 *            ID to the Back Left motor controller for turning in the swerve
	 * @param encBL
	 *            ID to the Back Left analog absolute encoder
	 *            
	 * @param moveBR
	 *            ID to the Back Right motor controller for movement in the swerve
	 * @param turnBR
	 *            ID to the Back Right motor controller for turning in the swerve
	 * @param encBR
	 *            ID to the Back Right analog absolute encoder
	 */
	public SwerveController(int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR,
							int moveBL, int turnBL, int encBL,
							int moveBR, int turnBR, int encBR){
		swerves[0] = new SwerveModule(moveFL, turnFL, encFL, 0.707, 0.707, 3.204);
//		swerves[1] = new SwerveModule(moveFR, turnFR, encFR, 0.707, -0.707, 4.9);
//		swerves[2] = new SwerveModule(moveBL, turnBL, encBL, -0.707, 0.707, 0.703); // Update back offsets
//		swerves[3] = new SwerveModule(moveBR, turnBR, encBR, -0.707, -0.707, 2.872);
		
//		swerves[0] = new SwerveModule(moveFL, turnFL, encFL, 0.707, 0.707, 2.517);
//		swerves[1] = new SwerveModule(moveFR, turnFR, encFR, 0.6703, -0.7421, 2.819);
//		swerves[2] = new SwerveModule(moveBL, turnBL, encBL, -0.6703, 0.7421, 1.687); // Update back offsets
//		swerves[3] = new SwerveModule(moveBR, turnBR, encBR, -0.7421, -0.6703, 2.620);

//		swerves[0] = new SwerveModule(moveFL, turnFL, encFL, 0.707, 0.707, prefs.getDouble("SwerveOffset1", 2.001));
//		swerves[1] = new SwerveModule(moveFR, turnFR, encFR, 0.707, -0.707, prefs.getDouble("SwerveOffset2", 4.254));
//		swerves[2] = new SwerveModule(moveBL, turnBL, encBL, -0.6703, 0.7421, prefs.getDouble("SwerveOffset3", 4.211)); // Update back offsets
//		swerves[3] = new SwerveModule(moveBR, turnBR, encBR, -0.7421, -0.6703, prefs.getDouble("SwerveOffset4", 4.941));
//		options.addObject("Motor Downscale", new downscale());
	}
	
	/**
	 * Recieves joystick inputs and gives results of swerve calculations to each module.
	 * @param xVector
	 * 						X coordinate in between -1 and 1 for input. Usually mapped to an X coordinate from a joystick axis.
	 * @param yVector
	 * 						Y coordinate in between -1 and 1 for input. Usually mapped to a Y coordinate from a joystick axis.
	 * @param phi
	 * 						Range between -1 and 1 which completely controls rotation on it's own. Usually mapped to an X coordinate of a joystick axis seperate from the xVector and yVector.
	 */
	public void swerve(double xVector, double yVector, double phi){
		for(int i=0; i<1; i++){
			swerves[i].x = (swerves[i].corX*phi)+xVector;
			swerves[i].y = (swerves[i].corY*phi)+yVector; 
			swerves[i].updateMag();
		}
		
		// Add all magnitudes to arraylist
		for(int i=0; i<1; i++){
			mags.add(swerves[i].mag);	
		}
		
		largestMag = Collections.max(mags);
		
		// If one mag is greater than 1 then scale the rest of the modules by the largest magnitude
		if(largestMag > 1){
			for(int i=0; i<1; i++){
				swerves[i].mag = swerves[i].mag / largestMag;
			}
		}
		
		for(int i=0; i< 1; i++){
			swerves[i].update(); // We need to run this to use all equations individual to the modules and to set values of motor controllers.
		}
		
		
		if(debug){ // Values into driverstation for debug purposes. Might be worth cleaning up sometime.
			SmartDashboard.putNumber("FLturnSpeed", swerves[0].turnSpeed); 
			SmartDashboard.putNumber("FLtarTheta", swerves[0].tarTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLcurTheta", swerves[0].curTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLdiffTheta", swerves[0].diffTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLmag", swerves[0].mag);
			
//			SmartDashboard.putNumber("FRturnSpeed", swerves[1].turnSpeed);
//			SmartDashboard.putNumber("FRtarTheta", swerves[1].tarTheta *(180/Math.PI));
//			SmartDashboard.putNumber("FRcurTheta", swerves[1].curTheta *(180/Math.PI));
//			SmartDashboard.putNumber("FRdiffTheta", swerves[1].diffTheta *(180/Math.PI));
//			SmartDashboard.putNumber("FRmag", swerves[1].mag);
//			
//			SmartDashboard.putNumber("BLturnSpeed", swerves[2].turnSpeed); 
//			SmartDashboard.putNumber("BLtarTheta", swerves[2].tarTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BLcurTheta", swerves[2].curTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BLdiffTheta", swerves[2].diffTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BLmag", swerves[2].mag);
//			
//			SmartDashboard.putNumber("BRturnSpeed", swerves[3].turnSpeed);
//			SmartDashboard.putNumber("BRtarTheta", swerves[3].tarTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BRcurTheta", swerves[3].curTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BRdiffTheta", swerves[3].diffTheta *(180/Math.PI));
//			SmartDashboard.putNumber("BRmag", swerves[3].mag);
			
			SmartDashboard.putNumber("StickX", xVector);
			SmartDashboard.putNumber("StickY", yVector);
			SmartDashboard.putNumber("StickPhi", phi);
	
			SmartDashboard.putNumber("FrontLeftRawEnc", swerves[0].encoder.getVoltage());
//			SmartDashboard.putNumber("FrontRightRawEnc", swerves[1].encoder.getVoltage());
//			SmartDashboard.putNumber("BackLeftRawEnc", swerves[2].encoder.getVoltage());
//			SmartDashboard.putNumber("BackRightRawEnc", swerves[3].encoder.getVoltage());
//			
			SmartDashboard.putNumber("FrontLeftCORX", swerves[0].corX);
//			SmartDashboard.putNumber("FrontLeftCORY", swerves[0].corY);
//			SmartDashboard.putNumber("FrontRightCORX", swerves[1].corX);
//			SmartDashboard.putNumber("FrontRightCORY", swerves[1].corY);
//			SmartDashboard.putNumber("BackLeftCORX", swerves[2].corX);
//			SmartDashboard.putNumber("BackLeftCORY", swerves[2].corY);
//			SmartDashboard.putNumber("BackRightCORX", swerves[3].corX);
//			SmartDashboard.putNumber("BackRightCORY", swerves[3].corY);
			
//			SmartDashboard.putString("FrontLeftErrorStatus", swerves[0].status);
//			SmartDashboard.putString("FrontRightErrorStatus", swerves[1].status);
//			SmartDashboard.putString("BackLeftErrorStatus", swerves[2].status);
//			SmartDashboard.putString("BackRightErrorStatus", swerves[3].status);
			

		}
		
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
		SmartDashboard.putNumber("FrontLeftRawEnc", swerves[0].encoder.getVoltage());
		SmartDashboard.putNumber("FrontRightRawEnc", swerves[1].encoder.getVoltage());
		SmartDashboard.putNumber("BackLeftRawEnc", swerves[2].encoder.getVoltage());
		SmartDashboard.putNumber("BackRightRawEnc", swerves[3].encoder.getVoltage());
	}
	public double getRawEncoderValue(int module){
		return swerves[module].encoder.getVoltage();
	}
	public void updateModule(int module, double tSpeed, double mSpeed){
		swerves[module].rawUpdate(true, tSpeed);
		swerves[module].rawUpdate(false, mSpeed);
	}
}
