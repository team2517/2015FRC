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

public class SwerveModule {
	public CANJaguar turnJag, moveJag;
	public AnalogInput encoder;
	private double turnSpeed;
	private static double minVoltage = 0.2;
	public double corX, corY;
	public SwerveModule(int tJagID, int mJagID, int eID, double xCOR, double yCOR)
	{
		turnJag = new CANJaguar(tJagID);
		moveJag = new CANJaguar(mJagID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
	}
	public void update(double targetAngle, double magnitude)
	{
		moveJag.set(magnitude);
		double currentAngle = (encoder.getValue() / 2.5 * Math.PI); //translates encoder voltage values to pi radians
		if (targetAngle < 0){
			targetAngle += 2 * Math.PI;
			}  //Change range from (-pi,pi) to (0, 2pi).  Ease calculations.
		double diffAngle = targetAngle - currentAngle;
		if (Utils.deadband(diffAngle, Math.PI / 60) == 0){
			turnJag.set(0);
		}  //applied deadband to test if target and current angle is close enough. Tolerance: 3 degrees (PI/60)

		else if (Math.abs(diffAngle) > Math.PI) {
			if (targetAngle > currentAngle){
				turnJag.set((diffAngle - 2 * Math.PI) / Math.PI * (1 - minVoltage) - minVoltage);
			}
			else {
				turnJag.set((2 * Math.PI-diffAngle) / Math.PI * (1 - minVoltage) + minVoltage);
			}
		}

		else if (Math.abs(diffAngle) < Math.PI){
			if (targetAngle > currentAngle){
				turnJag.set(diffAngle / Math.PI * (1 - minVoltage) + minVoltage);
			}
			else {
				turnJag.set(-diffAngle / Math.PI * (1 - minVoltage) - minVoltage);
			}
		}
		/*
		 * Passing in voltage into motor control. Turn direction calculated based on the position and distance of 
		 * target from current angle.  Turn voltage 0.2 minimum.  Other 0.8 voltage of power depends on distance of
		 * target from current angle.  Calculation: absolute of the the difference divided by pi times 0.8 plus
		 * 0.2.
       	 */
	}
}
