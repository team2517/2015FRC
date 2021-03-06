package org.usfirst.frc.team2517.robot;
import java.lang.Math;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The SwerveModule class is intended to hold all variables, objects, and calculations
 * that are required for each module.
 * @param mMotor
 * 						ID for the location of the Talon PWM slot in the roboRIO.
 * @param tMotor
 * 						ID for the Jaguar's ID wired through CAN.
 * @param eID
 * 						ID for the absolute encoder slot in Analog Input.
 * @param xCor
 * 						X Value for center of rotation
 * @param yCor
 * 						Y Value for center of rotation
 * @param off
 * 						Offset of the position of the absolute encoder's zero and where you want your zero to be.
 */
public class SwerveModule {
	private SpeedController turnMotor;
	private SpeedController moveMotor;
	public AnalogInput encoder;
	public double x, y, corX, corY, mag, tarTheta, diffTheta, curTheta, turnSpeed, offset;
	public String status;
	
	
	public SwerveModule(int mMotorType, int tMotorType, int mMotor, int tMotor, int eID, double xCOR, double yCOR, double off)
	{
		status = "Clear";
		try{
			switch(mMotorType){
				case 0:	moveMotor = new CANTalon(mMotor);
						break;
				case 1:	moveMotor = new TalonSRX(mMotor);
						break;
				case 2:	moveMotor = new VictorSP(mMotor);
						break;
				case 3:	moveMotor = new Talon(mMotor);
						break;
				case 4:	moveMotor = new CANJaguar(mMotor);
						break;
				case 5:	moveMotor = new Jaguar(mMotor);
						break;
				case 6:	moveMotor = new Victor(mMotor);
						break;
			}
			switch(tMotorType){
				case 0:	turnMotor = new CANTalon(mMotor);
						break;
				case 1:	turnMotor = new TalonSRX(tMotor);
						break;
				case 2:	turnMotor = new VictorSP(tMotor);
						break;
				case 3:	turnMotor = new Talon(tMotor);
						break;
				case 4:	turnMotor = new CANJaguar(tMotor);
						break;
				case 5:	turnMotor = new Jaguar(tMotor);
						break;
				case 6:	turnMotor = new Victor(tMotor);
						break;
			}
		} 
		catch(edu.wpi.first.wpilibj.can.CANMessageNotFoundException e){ // TODO: Log this into a file instead of driverstation
			status = e.toString();
		}
		
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = -yCOR;
		offset = Utils.wrap(off - 1.25, 0, 5);
	}
	
	/**
	 * Runs all calculations independant to the swerve module and updates motor controllers.
	 */
	public void update()
	{
		tarTheta = Math.atan2(y, x); // Calculate the angles we want to be at with the joystick inputs
		
		/**
		 * Takes all of the variables modified and updates the motor controllers in the module
		 */
		curTheta = (encoder.getVoltage() - offset)/5*(2*Math.PI);
		diffTheta = tarTheta - curTheta;
		
		// If our angle is over PI, then subtract PI*2 to bring the theta to be a smaller negative number
		if (diffTheta > Math.PI) {
			diffTheta -= 2 * Math.PI;
		} // Converse for last statement for if the theta is less than PI
		else if (diffTheta < - Math.PI) {
			diffTheta += 2 * Math.PI;
		}
		
		// If the diffTheta is greater than PI/2 (90 Degrees) we can make our movements more efficient
		// by moving the module to have the wheel effectively be facing the same direction but have the wheel reversed.
		if (diffTheta > Math.PI / 2) {
			diffTheta -= Math.PI;
			mag = mag * -1;
		} 
		else if (diffTheta < -Math.PI / 2){
			diffTheta += Math.PI;
			mag = mag * -1;
		}
		
		turnSpeed = diffTheta / (Math.PI / 2); // Our way of ramping down the turnSpeed based on the angle
		
		// Making sure turning motor never goes below 15% because friction in the motor will cause the swerve to have a hard time turning with any less
		if (0 < turnSpeed && turnSpeed < 0.15){
			turnSpeed = 0.15;
		}
		if (0 > turnSpeed && turnSpeed > -0.15){
			turnSpeed = -0.15;
		}
		
		// Our 'deadband' for the swerve module for an acceptable thereshold (4 degrees)
		if (Math.abs(diffTheta) < (Math.PI / 45)){
			turnSpeed = 0;
		}

		
//		 Hold Position if joystick is not being pressed to save power if we are continuing with a similar movement
		if (x == 0 && y == 0){ 
			turnMotor.set(0);
			moveMotor.set(0);
		}
		else{ // Update motor controllers
			turnMotor.set(turnSpeed);
			moveMotor.set(mag);
		}
	}
	/**
	 * Set a raw speed input into the swerve module without applying any extra mathematics
	 * @param turning
	 *            The motor to turn during the function.
	 *            If true, update the motor controller for turning the wheel.
	 *            If false, update the turning of the wheel itself.
	 * @param speed
	 * 			  The speed to set the motor controller to.
	 * 			  Accept a double in between -1 and 1
	 */
	public void rawUpdate(boolean turning, double speed){
		if(turning){
			turnMotor.set(speed/3);
		}
		else{
			moveMotor.set(speed/3);
		}
	}
	
	/**
	 * Takes the public x and y variables in the swerveModule and updates the magnitude alone.
	 */
	public void updateMag(){
		mag = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
	}
}
