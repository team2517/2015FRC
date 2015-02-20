
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
	private double rawStickX, rawStickY; // Joystick values
	public static double stickX, stickY, stickPhi;
	public static final double deadBandThereshold = .08;
	private final double autoDur = 5; // Amount of seconds the robot moves forward in autonomous
	private final double autoSpeed = .3; // The speed (between -1 and 1) where the robot moves forward during autonomous
	private Timer autoTimer;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	swerveDrive = new SwerveController(0, 4, 0,  // TalonFL, JagFL, EncFL
    									   1, 12, 1, // TalonFR, JagFR, EncFR
    									   2, 45, 2,  // TalonBL, JagBL, EncBL
    									   3, 30, 3); // TalonBR, JagBR, EncBR
    }
    /**
     * This function is called once before autonomous
     */
public void autonomousInit() {
    	autoTimer.start();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	// Move robot forward for a set amount of seconds
    	if(autoTimer.get() < autoDur){
    		swerveDrive.swerve(0, autoSpeed, 0);
    	}
    	else{
    		swerveDrive.swerve(0, 0, 0);
    	}
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	rawStickX = Utils.deadband(stick.getRawAxis(0), deadBandThereshold); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
    	rawStickY = Utils.deadband(stick.getRawAxis(1), deadBandThereshold);
    	stickPhi = Utils.deadband(stick.getRawAxis(2), deadBandThereshold);
    	stickX = rawStickX * Math.sqrt(1 - 0.5 * Math.pow(rawStickY, 2)); // Math equation to scale the joystick values so the difference (mag) of the vectors will be 1 instead of 1.414 (sqrt of 2)
    	stickY = rawStickY * Math.sqrt(1 - 0.5 * Math.pow(rawStickX, 2));
    	swerveDrive.swerve(stickX, stickY, stickPhi);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	rawStickX = Utils.deadband(stick.getRawAxis(0), deadBandThereshold); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
    	swerveDrive.updateAll(rawStickX, .3);
    }
    
}
