package org.usfirst.frc.team2517.robot;

import org.usfirst.frc.team2517.robot.SwerveModule;

public class SwerveController {
	SwerveModule swerveFL = new SwerveModule(0, 0, 0); // Initialize one object for each module. Format is turnJag, moveJag, encID
	SwerveModule swerveFR = new SwerveModule(0, 0, 0);
	SwerveModule swerveBL = new SwerveModule(0, 0, 0);
	SwerveModule swerveBR = new SwerveModule(0, 0, 0);
	
	public void swerve(float axisX, float axisY, float phi)
	{
		
	}
}
