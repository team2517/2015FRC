
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

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
	public static double stickX, rawStickX, stickY, rawStickY, stickPhi;
	public static final double deadBandThreshold = .08;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	swerveDrive = new SwerveController(2, 12, 0,  // TalonFL, JagFL, EncFL
    									   1, 4, 3,   // TalonFR, JagFR, EncFR
    									   3, 45, 1,  // TalonBL, JagBL, EncBL
    									   0, 30, 2); // TalonBR, JagBR, EncBR
    }
    /**
     * This function is called once before autonomous
     */
public void autonomousInit() {
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
    	rawStickX = Utils.deadband(stick.getRawAxis(0), deadBandThreshold); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
    	swerveDrive.updateAll(rawStickX/3, .3);
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
}
