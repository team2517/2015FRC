package org.usfirst.frc.team2517.robot;
import org.usfirst.frc.team2517.robot.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.*;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;
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
	private final int SWERVEMODULES = 4;
	SwerveModule[] swerves = new SwerveModule[SWERVEMODULES]; 
	public final String CALFILE = "/home/lvuser/Offsets.txt";
	private double largestMag;
	private ArrayList<Double> mags = new ArrayList<Double>();
	private ArrayList<Double> offsets = new ArrayList<Double>();
	public int calMode = 0;
	private int stickYModifier;
	private final boolean debug = true;
	private Writer writer = null;
	private Random random = new Random();
	private double encoderTarget, encoderTargetDeviation;
	private int encoderTargetMultiplier;
	
	/**
	 * Constructor for a swerve helper class
	 * @param moveMotor
	 * 			  Type of motor controller used for the wheel movement motor on each swerve. ID's accepted are:
	 * 					0: CANTalon		1: TalonSRX		2: VictorSP		3: Talon	4: CANJaguar 	5: Jaguar	6: Victor
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
	public SwerveController(int moveMotor, int turnMotor, boolean invertedY,
							int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR,
							int moveBL, int turnBL, int encBL,
							int moveBR, int turnBR, int encBR){
		try{ 
			BufferedReader calIn = new BufferedReader(new FileReader(CALFILE));
			for(int i=0; i<swerves.length; i++){
				offsets.add(Double.parseDouble(calIn.readLine()));
			}
			calIn.close();
		}catch(IOException ex){ex.printStackTrace();}
		swerves[0] = new SwerveModule(moveMotor, turnMotor, moveFL, turnFL, encFL, 0.707, 0.707, offsets.get(0));
		swerves[1] = new SwerveModule(moveMotor, turnMotor, moveFR, turnFR, encFR, 0.707, 0.707, offsets.get(1));
		swerves[2] = new SwerveModule(moveMotor, turnMotor, moveBL, turnBL, encBL, 0.707, 0.707, offsets.get(2));
		swerves[3] = new SwerveModule(moveMotor, turnMotor, moveBR, turnBR, encBR, 0.707, 0.707, offsets.get(3));
		
		if(invertedY){
			stickYModifier = -1;
		}
		else{
			stickYModifier = 1;
		}
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
		for(int i=0; i<swerves.length; i++){
			swerves[i].x = (swerves[i].corX*phi)+xVector;
			swerves[i].y = ((swerves[i].corY*phi)+(yVector*stickYModifier)); 
			swerves[i].updateMag();
		}
		
		// Add all magnitudes to arraylist
		for(int i=0; i<swerves.length; i++){
			mags.add(swerves[i].mag);	
		}
		
		largestMag = Collections.max(mags);
		
		// If one mag is greater than 1 then scale the rest of the modules by the largest magnitude
		if(largestMag > 1){
			for(int i=0; i<swerves.length; i++){
				swerves[i].mag = swerves[i].mag / largestMag;
			}
		}
		
		for(int i=0; i<swerves.length; i++){
			swerves[i].update(); // We need to run this to use all equations individual to the modules and to set values of motor controllers.
		}
		
		
		if(debug){ // Values into driverstation for debug purposes. Might be worth cleaning up sometime.
			SmartDashboard.putNumber("FLturnSpeed", swerves[0].turnSpeed); 
			SmartDashboard.putNumber("FLtarTheta", swerves[0].tarTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLcurTheta", swerves[0].curTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLdiffTheta", swerves[0].diffTheta *(180/Math.PI));
			SmartDashboard.putNumber("FLmag", swerves[0].mag);
			SmartDashboard.putNumber("FrontLeftRawEnc", swerves[0].encoder.getVoltage());
			SmartDashboard.putNumber("FrontLeftCORX", swerves[0].corX);
			SmartDashboard.putNumber("FrontLeftCORY", swerves[0].corY);
			SmartDashboard.putString("FrontLeftErrorStatus", swerves[0].status);
			SmartDashboard.putNumber("FrontLeftOffset", swerves[0].offset);
			
			if(swerves.length >= 2){
				SmartDashboard.putNumber("FRturnSpeed", swerves[1].turnSpeed);
				SmartDashboard.putNumber("FRtarTheta", swerves[1].tarTheta *(180/Math.PI));
				SmartDashboard.putNumber("FRcurTheta", swerves[1].curTheta *(180/Math.PI));
				SmartDashboard.putNumber("FRdiffTheta", swerves[1].diffTheta *(180/Math.PI));
				SmartDashboard.putNumber("FRmag", swerves[1].mag);
				SmartDashboard.putNumber("FrontRightRawEnc", swerves[1].encoder.getVoltage());
				SmartDashboard.putNumber("FrontRightCORX", swerves[1].corX);
				SmartDashboard.putNumber("FrontRightCORY", swerves[1].corY);
				SmartDashboard.putString("FrontRightErrorStatus", swerves[1].status);
			}
			if(swerves.length >= 3){
				SmartDashboard.putNumber("BLturnSpeed", swerves[2].turnSpeed); 
				SmartDashboard.putNumber("BLtarTheta", swerves[2].tarTheta *(180/Math.PI));
				SmartDashboard.putNumber("BLcurTheta", swerves[2].curTheta *(180/Math.PI));
				SmartDashboard.putNumber("BLdiffTheta", swerves[2].diffTheta *(180/Math.PI));
				SmartDashboard.putNumber("BLmag", swerves[2].mag);
				SmartDashboard.putNumber("BackLeftRawEnc", swerves[2].encoder.getVoltage());
				SmartDashboard.putNumber("BackLeftCORX", swerves[2].corX);
				SmartDashboard.putNumber("BackLeftCORY", swerves[2].corY);
				SmartDashboard.putString("BackLeftErrorStatus", swerves[2].status);
			}
			if (swerves.length >= 4){
				SmartDashboard.putNumber("BRturnSpeed", swerves[3].turnSpeed);
				SmartDashboard.putNumber("BRtarTheta", swerves[3].tarTheta *(180/Math.PI));
				SmartDashboard.putNumber("BRcurTheta", swerves[3].curTheta *(180/Math.PI));
				SmartDashboard.putNumber("BRdiffTheta", swerves[3].diffTheta *(180/Math.PI));
				SmartDashboard.putNumber("BRmag", swerves[3].mag);
				SmartDashboard.putNumber("BackRightRawEnc", swerves[3].encoder.getVoltage());
				SmartDashboard.putNumber("BackRightCORX", swerves[3].corX);
				SmartDashboard.putNumber("BackRightCORY", swerves[3].corY);
				SmartDashboard.putString("BackRightErrorStatus", swerves[3].status);
			}
			
			SmartDashboard.putNumber("StickX", xVector);
			SmartDashboard.putNumber("StickY", yVector);
			SmartDashboard.putNumber("StickPhi", phi);
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
		for(int i=0; i < swerves.length; i++){
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
	/*
	 * MAGICALLY CHANGES CENTER OF ROTATION VALUES
	 */
	public void changeCOR(double flx, double fly, double frx, double fry, double blx, double bly, double brx, double bry){
		swerves[0].corX = flx;
		swerves[0].corY = fly;
		swerves[1].corX = frx;
		swerves[1].corY = fry;
		swerves[2].corX = blx;
		swerves[2].corY = bly;
		swerves[3].corX = brx;
		swerves[3].corY = bry;
	}
	public void updateOffsets(){
		for(int i=0; i<swerves.length; i++){
			try{ 
				BufferedReader calIn = new BufferedReader(new FileReader(CALFILE));
				for(int j=0; j<swerves.length; j++){
					offsets.add(Double.parseDouble(calIn.readLine()));
				}
				calIn.close();
			}catch(IOException ex){ex.printStackTrace();}
			for(int j=0; j<offsets.size(); j++){
				swerves[i].offset = offsets.get(i);
			}
		}
	}
	public void randomModulePosition(int module, double minDistance){
		if(random.nextBoolean()){
			encoderTargetMultiplier = 1;
		}
		else{
			encoderTargetMultiplier = -1;
		}
		encoderTargetDeviation = (random.nextDouble()*(5-(2*minDistance)))*(minDistance*encoderTargetMultiplier); // Generate random number between minDistance and 5-minDistance, and make positive or negative based on encoderTargetMultiplier
		encoderTarget = Utils.wrap(swerves[module].encoder.getVoltage() + encoderTargetDeviation, 0, 5);
	}
}
