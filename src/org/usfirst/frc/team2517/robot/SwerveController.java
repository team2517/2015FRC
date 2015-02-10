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
	private double swerveFLX, swerveFLY, swerveFRX, swerveFRY, swerveBLX, swerveBLY, swerveBRX, swerveBRY;
	
	public SwerveController(int moveFL, int turnFL, int encFL,
							int moveFR, int turnFR, int encFR, 
							int moveBL, int turnBL, int encBL,
							int moveBR, int turnBR, int encBR){
		swerveFL = new SwerveModule(moveFL, turnFL, encFL, 0.7421, 0.6703);
		swerveFR = new SwerveModule(moveFR, turnFR, encFR, 0.6703, -0.7421);
		swerveBL = new SwerveModule(moveBL, turnBL, encBL, -0.6703, 0.7421);
		swerveBR = new SwerveModule(moveBR, turnBR, encBR, -0.7421, -0.6703);
	}
	public void swerve(double xVector, double yVector, double phi){
		swerveFL.x = (swerveFL.corX*phi)+xVector;
		swerveFL.y = (swerveFL.corY*phi)+yVector; // Someday outsource these into a SwerveModule function and run 4 times instead of 8
		swerveFR.x = (swerveFR.corX*phi)+xVector;
		swerveFR.y = (swerveFR.corY*phi)+yVector;
		swerveBL.x = (swerveBL.corX*phi)+xVector;
		swerveBL.y = (swerveBL.corY*phi)+yVector;
		swerveBR.x = (swerveBR.corX*phi)+xVector;
		swerveBR.y = (swerveBR.corY*phi)+yVector;
		
		// Find magnitudes through pythagorean therom
//		sqrt(pow(wheel[i].x, 2) + pow(wheel[i].y, 2))
	}
}
