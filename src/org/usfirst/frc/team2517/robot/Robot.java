
package org.usfirst.frc.team2517.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	private AnalogInput encoder;
	private CANJaguar motorMoveFL, motorTurnFL, motorMoveFR, motorTurnFR, 
	motorMoveBL, motorTurnBL, motorMoveBR, motorTurnBR;
	private AnalogInput posFL, posFR, posBL, posBR;
	
	double moveValue;
	double turnValue;
	
	
    public void robotInit() {
    	motorMoveFL = new CANJaguar(0);  // Add hard-coded Jaguar IDs
    	motorTurnFL = new CANJaguar(0);
    	motorMoveFR = new CANJaguar(0);
    	motorTurnFR = new CANJaguar(0);
    	motorMoveBL = new CANJaguar(0);
    	motorTurnBL = new CANJaguar(0);
    	motorMoveBR = new CANJaguar(0);
    	motorMoveBL = new CANJaguar(0);
    	
    	posFL = new AnalogInput(0);
    	posFR = new AnalogInput(0);
    	posBL = new AnalogInput(0);
    	posBR = new AnalogInput(0);
    	
    	    	
    	stick = new Joystick(0);
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
    	motorMoveBL.set(stick.getRawAxis(1));
    	motorTurnBL.set(stick.getRawAxis(0));
    	motorMoveBR.set(stick.getRawAxis(2));
    	motorTurnBR.set(stick.getRawAxis(3));
    
    }
    
}
