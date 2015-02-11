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
	
	public SwerveController(){
		swerveFL = new SwerveModule(0, 0, 0, 0.7421, 0.6703);
		swerveFR = new SwerveModule(0, 0, 0, 0.6703, -0.7421);
		swerveBL = new SwerveModule(0, 0, 0, -0.6703, 0.7421);
		swerveBR = new SwerveModule(0, 0, 0, -0.7421, -0.6703);
	}
	public void swerve(double xVector, double yVector, double phi){
		
		// Makes all the left stick vectors have a magnitude of 1, rather than 1.4 in the corners.
	}
}
