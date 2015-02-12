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
	public double x, y, corX, corY, mag, tarTheta;
	private double diffTheta, curTheta;
	public SwerveModule(int mTalID, int tJagID, int eID, double xCOR, double yCOR)
	{
		turnJag = new CANJaguar(tJagID);
		moveTal = new Talon(mTalID);
		encoder = new AnalogInput(eID);
		corX = xCOR;
		corY = yCOR;
	}
	public void update()
	{
		
	}
}
