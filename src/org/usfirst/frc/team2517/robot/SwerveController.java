package org.usfirst.frc.team2517.robot;

import org.usfirst.frc.team2517.robot.SwerveModule;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import java.lang.Math;

public class SwerveController {
	SwerveModule swerveFL; // Initialize one object for each module. Format is turnJag, moveJag, encID
	SwerveModule swerveFR;
	SwerveModule swerveBL;
	SwerveModule swerveBR;
	private double centerFLX, centerFLY, centerFRX, centerFRY, centerBLX, centerBLY, centerBRX, centerBRY;
	
	public SwerveController(int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR, 
							int moveBL, int turnBL, int encBL,
							int moveBR, int turnBR, int encBR){
		swerveFL = new SwerveModule(moveFL, turnFL, encFL);
		swerveFR = new SwerveModule(moveFR, turnFR, encFR);
		swerveBL = new SwerveModule(moveBL, turnBL, encBL);
		swerveBR = new SwerveModule(moveBR, turnBR, encBR);
		centerFLX = 0.7421;
		centerFLY = 0.6703;
		centerFRX = 0.6703;
		centerFRY = -0.7421;
		centerBLX = -0.6703;
		centerBLY = 0.7421;
		centerBRX = -0.7421;
		centerBRY = -0.6703;
	}
	public void swerve(double xVector, double yVector, double phi){
		
		// Makes all the left stick vectors have a magnitude of 1, rather than 1.4 in the corners.
	}
}
