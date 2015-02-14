
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	private Joystick stick;
	private SwerveController swerveDrive;
	private double stickX, rawStickX, stickY, rawStickY, stickPhi; // Joystick values
	
    public void robotInit() {
    	stick = new Joystick(0);
    	swerveDrive = new SwerveController(0, 4, 0, 1, 30, 1); // this is not okay
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	rawStickX = Utils.deadband(stick.getRawAxis(0), 0.02); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
    	rawStickY = Utils.deadband(stick.getRawAxis(1), 0.02);
    	stickPhi = Utils.deadband(stick.getRawAxis(2), 0.02);
    	stickX = rawStickX * Math.sqrt(1 - 0.5 * Math.pow(rawStickY, 2)); // Math equation to scale the joystick values so the difference (mag) of the vectors will be 1 instead of 1.414 (sqrt of 2)
    	stickY = rawStickY * Math.sqrt(1 - 0.5 * Math.pow(rawStickX, 2));
    	
    	swerveDrive.swerve(rawStickX, rawStickY, stickPhi);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
}
