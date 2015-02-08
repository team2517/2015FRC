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
	
	public SwerveController(int moveFL, int turnFL, int encFL, double centerFLX, double centerFLY,
							int moveFR, int turnFR, int encFR, double centerFRX, double centerFRY, 
							int moveBL, int turnBL, int encBL, double centerBLX, double centerBLY, 
							int moveBR, int turnBR, int encBR, double centerBRX, double centerBRY){
		swerveFL = new SwerveModule(moveFL, turnFL, encFL, centerFLX, centerFLY);
		swerveFR = new SwerveModule(moveFR, turnFR, encFR, centerFRX, centerFRY);
		swerveBL = new SwerveModule(moveBL, turnBL, encBL, centerBLX, centerBRY);
		swerveBR = new SwerveModule(moveBR, turnBR, encBR, centerBRX, centerBRY);
	}
	public void swerve(double xVector, double yVector, double phi){
		
		// Makes all the left stick vectors have a magnitude of 1, rather than 1.4 in the corners.
		
	}
}
