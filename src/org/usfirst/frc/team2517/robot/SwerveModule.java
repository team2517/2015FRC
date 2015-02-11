package org.usfirst.frc.team2517.robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;

/* *
 * We want the swerve module class to
 * 1. Initialize 2 CANJaguars as parameters
 * 2. Directly receive direction and magnitude variables through a function and move the 
 * motorTurn jaguar until it has reached its goal
 * 3. Look nice and have comments
 * 
 * You will need to basically rip code from last year's math code and convert it to java
 * 
 * */
						// Test comment please ignore
public class SwerveModule {
	public CANJaguar turnJag, moveJag;
	public AnalogInput encoder;
	double turnSpeed;
	private static double minVoltage = 0.2;
	public SwerveModule(int tJagID, int mJagID, int eID)
	{
		turnJag = new CANJaguar(tJagID);
		moveJag = new CANJaguar(mJagID);
		encoder = new AnalogInput(eID);
	}
	public void update(double targetAngle, double magnitude)
	{
		moveJag.set(magnitude);
		double currentAngle = (encoder.getValue() / 2.5 * Math.PI); //translates encoder voltage values to pi radians
		if (Utils.deadband(targetAngle - currentAngle, Math.PI / 60) == 0){
			turnJag.set(0);
		}  //applied dead band to test if target and current angle is close enough. Tolerance: 3 degrees (PI/60)
		if (targetAngle < 0){
			targetAngle += 2 * Math.PI;
			}  //Change range from (-pi,pi) to (0, 2pi).  Ease calculations.
		else if (Math.abs(targetAngle - currentAngle) > Math.PI && targetAngle > currentAngle){
			turnJag.set(-(currentAngle + (2 * Math.PI - targetAngle)) / Math.PI * (1 - minVoltage) - minVoltage);
		}
		else if (Math.abs(targetAngle - currentAngle) > Math.PI && targetAngle < currentAngle){
			turnJag.set((currentAngle + (2 * Math.PI - targetAngle)) / Math.PI * (1 - minVoltage) + minVoltage);
		}
		else if (Math.abs(targetAngle - currentAngle) < Math.PI && targetAngle > currentAngle){
			turnJag.set((targetAngle - currentAngle) / Math.PI * (1 - minVoltage) + minVoltage);
		}
		else if (Math.abs(targetAngle-currentAngle) < Math.PI && targetAngle < currentAngle)
		{
			turnJag.set(-(targetAngle - currentAngle) / Math.PI * (1 - minVoltage) - minVoltage);
		}
		/*
		 * Passing in voltage into motor control. Turn direction calculated based on the position and distance of 
		 * target from current angle.  Turn voltage 0.2 minimum.  Other 0.8 voltage of power depends on distance of
		 * target from current angle.  Calculation: absolute of the the difference divided by pi times 0.8 plus
		 * 0.2.
       	 */
	}
}
