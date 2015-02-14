
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team2517.robot.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;

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
	private double stickX, stickY, stickPhi; // Joystick values
	
    public void robotInit() {
    	stick = new Joystick(0);
    	swerveDrive = new SwerveController(30, 1, 0, 4, 0, 1);
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
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	stickPhi = Utils.deadband(stick.getRawAxis(2), 0.02);
    	stickX = Utils.deadband(stick.getRawAxis(0), 0.02) * Math.sqrt(1 - 0.5 * Math.pow(rawStickY, 2)); // Math equation to scale the joystick values so the difference (mag) of the vectors will be 1 instead of 1.414 (sqrt of 2)
    	stickY = Utils.deadband(stick.getRawAxis(1), 0.02) * Math.sqrt(1 - 0.5 * Math.pow(rawStickX, 2));
    	swerveDrive.swerve(stickX, stickY, stickPhi);
    }
    
}
