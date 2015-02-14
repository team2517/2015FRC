
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public static int stagger;// Used to stagger the updates to the dashboard so we don't overwhelm the driver station. Set to 0 and used at end of robot code and in Utils.dashboardAdd()
	public static final int staggerMax = 5;
	
    public void robotInit() {
    	stick = new Joystick(0);
    	swerveDrive = new SwerveController(0, 4, 0, 1, 30, 1); // this is not okay
    	stagger = 0; 
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
		Utils.dashboardAdd("RawStickX", rawStickX);
		Utils.dashboardAdd("RawStickY", rawStickY);
		Utils.dashboardAdd("StickX", stickX);
		Utils.dashboardAdd("StickY", stickY);
    	swerveDrive.swerve(rawStickX, rawStickY, stickPhi);
    	
    	// Resetting stagger variable if over maximum or adding 1
    	if (stagger >= staggerMax){
    		stagger = 0;
    	}
    	else{
    		stagger++;
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
}
