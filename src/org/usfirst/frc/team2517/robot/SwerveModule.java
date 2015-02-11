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
	private double turnSpeed;
	private static double minVoltage = 0.2;
	public double x, y, corX, corY, mag, tarAngle;
	private double diffAngle, curAngle;
	public SwerveModule(int tJagID, int mTalID, int eID, double xCOR, double yCOR)
	{
		turnJag = new CANJaguar(tJagID);
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
	}
	public void update()
	{
		moveTal.set(mag);
		curAngle = (encoder.getValue() / 2.5 * Math.PI); //translates encoder voltage values to pi radians
		if (tarAngle < 0){
			tarAngle += 2 * Math.PI;
			}  //Change range from (-pi,pi) to (0, 2pi).  Ease calculations.
		diffAngle = tarAngle - curAngle;
		if (Utils.deadband(diffAngle, Math.PI / 60) == 0){
			turnJag.set(0);
		}  //applied deadband to test if target and current angle is close enough. Tolerance: 3 degrees (PI/60)

		else if (Math.abs(diffAngle) > Math.PI) {
			if (tarAngle > curAngle){
				turnJag.set((diffAngle - 2 * Math.PI) / Math.PI * (1 - minVoltage) - minVoltage);
			}
			else {
				turnJag.set((2 * Math.PI-diffAngle) / Math.PI * (1 - minVoltage) + minVoltage);
			}
		}

		else if (Math.abs(diffAngle) < Math.PI){
			if (tarAngle > curAngle){
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
