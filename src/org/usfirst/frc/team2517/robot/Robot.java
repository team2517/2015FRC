
package org.usfirst.frc.team2517.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;

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
	public static final double deadBandThreshold = .08;
	private final double autoDur = 5; // Amount of seconds the robot moves forward in autonomous
	private final double autoSpeed = .3; // The speed (between -1 and 1) where the robot moves forward during autonomous
	private Timer autoTimer;
	private Solenoid lift, eject;
	private Talon pickUpLeft, pickUpRight;
	private ArrayList<Double> offsets = new ArrayList<Double>();
	private ArrayList<Double> tempOffsets = new ArrayList<Double>();
	private boolean calButtonPressed;
	private int calMode, calSubMode;
	private final boolean emergencymode = false; // Puts robot into tank drive if true
	
    public void robotInit() {
    	autoTimer = new Timer();
    	stick = new Joystick(0);
			swerveDrive = new SwerveController(3, 4, true, // WheelMotor, RotationMotor, InvertedY 
											   3, 12, 0,   // TalonFL, JagFL, EncFL {3, 12} {55, 14}
											   1, 30, 3,   // TalonFR, JagFR, EncFR
											   2, 45, 2,   // TalonBL, JagBL, EncBL
											   0, 4,  1);
//    	pickUpLeft = new Talon(4);
//    	pickUpRight = new Talon(5);
//    	lift = new Solenoid(0);
//    	eject = new Solenoid(1);
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
    	if (!emergencymode){
	    	rawStickX = Utils.deadband(stick.getRawAxis(0), deadBandThreshold); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
	    	rawStickY = Utils.deadband(stick.getRawAxis(1), deadBandThreshold);
	    	stickPhi = Utils.deadband(stick.getRawAxis(2), deadBandThreshold);
	    	stickX = rawStickX * Math.sqrt(1 - 0.5 * Math.pow(rawStickY, 2)); // Math equation to scale the joystick values so the difference (mag) of the vectors will be 1 instead of 1.414 (sqrt of 2)
	    	stickY = rawStickY * Math.sqrt(1 - 0.5 * Math.pow(rawStickX, 2));
	    	swerveDrive.swerve(stickX, stickY, stickPhi);
	    	if(Utility.getUserButton()){
	    		swerveDrive.updateOffsets();
	    	}
//	    	if (stick.getRawButton(7)){
//	    		pickUpLeft.set(0.42);
//	    		pickUpRight.set(0.42);
//	    		eject.set(false);
//	        }
//	    	else{
//	    		pickUpLeft.set(0);
//	    		pickUpRight.set(0);
//	    	}
//	    	if (stick.getRawButton(5)){
//	    		eject.set(true);
//	    		pickUpLeft.set(0);
//	    		pickUpRight.set(0);
//	    	}
//	    	if (stick.getRawButton(8)){
//	    		lift.set(false);
//	    	}
//	    	else if (stick.getRawButton(6)){
//	    		lift.set(true);
//	    	}
	    	
//	    	Stuff for changing center of rotation
//	    	if(stick.getRawButton(7)){ 
//	    		swerveDrive.changeCOR(flx, fly, frx, fry, blx, bly, brx, bry);
//	    	}
//	    	else if(stick.getRawButton(8)){
//	    		swerveDrive.changeCOR(flx, fly, frx, fry, blx, bly, brx, bry);
//	    	}
//	    	else{
//	    		swerveDrive.changeCOR(flx, fly, frx, fry, blx, bly, brx, bry);
//	    	}
    	}
    	else{
    		swerveDrive.updateModule(0, 0, Utils.deadband(stick.getRawAxis(1), deadBandThreshold));
    		swerveDrive.updateModule(1, 0, Utils.deadband(stick.getRawAxis(3), deadBandThreshold));
    		swerveDrive.updateModule(2, 0, Utils.deadband(stick.getRawAxis(1), deadBandThreshold));
    		swerveDrive.updateModule(3, 0, Utils.deadband(stick.getRawAxis(3), deadBandThreshold));
    	}
	}
    
    /**
     * This function is called once before test mode
     */
    public void testInit() {
    	calMode = 0;
    	calSubMode = 0;
    	calButtonPressed = false;
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() { // Dirty calibration code.		TODO: Clean up calibration code and add comments
    	rawStickX = Utils.deadband(stick.getRawAxis(0), deadBandThreshold); // Deadband to make sure if the value is low enough then it is 0 because when the joystick is not touched it is not always 0.
    	if(calMode < swerveDrive.swerves.length){
    		swerveDrive.updateModule(calMode, Utils.deadband(stick.getRawAxis(0), deadBandThreshold)/3, .3);
    		if(!calButtonPressed && stick.getRawButton(1)){
    			calButtonPressed = true;
    			if(calSubMode >= 3){
        			calMode++;
    				calSubMode = 0;
    				tempOffsets.clear();
    			}
    			else{
    				tempOffsets.add(swerveDrive.swerves[calMode].encoder.getVoltage());
    				calSubMode++;
    			}
    		}
    	}
    }
    
}
