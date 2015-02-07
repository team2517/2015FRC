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
	
	public SwerveController(int moveFL, int turnFL, int encFL, 
							int moveFR, int turnFR, int encFR, 
							int moveBL, int turnBL, int encBL, 
							int moveBR, int turnBR, int encBR){
		swerveFL = new SwerveModule(moveFL, turnFL, encFL);
		swerveFR = new SwerveModule(moveFR, turnFR, encFR);
		swerveBL = new SwerveModule(moveBL, turnBL, encBL);
		swerveBR = new SwerveModule(moveBR, turnBR, encBR);
	}
	public void swerve(double axisX, double axisY, double phi){
		if(Math.abs(axisY) > .02){ // Deadband functions
			axisY = 0;
		}
		if(Math.abs(axisX) > .02){
			axisX = 0;
		}
		// Makes all the left stick vectors have a magnitude of 1, rather than 1.4 in the corners.
		
	}
}
