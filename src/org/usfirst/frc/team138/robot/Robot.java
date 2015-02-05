
package org.usfirst.frc.team138.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//import org.usfirst.frc.team138.robot.commands.ExampleCommand;
import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;
import org.usfirst.frc.team138.robot.subsystems.ExampleSubsystem;

public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	
	DriverStation EntropyDriverStation;			// driver station object
	int m_priorPacketNumber;					// keep track of the most recent packet number from the DS
	int m_dsPacketsReceivedInCurrentSecond;	// keep track of the ds packets received in the current second
	
	// Declare variables for the two joysticks being used
	Joystick DriveStick;			// EntropyJoystick used for robot driving
	Joystick GameStick;			// EntropyJoystick for all other functions		
	EntropyDrive RobotDrive;
	IODefinitions test;
	
	float m_turnSpeed;
    int state;
    boolean pressed_flag;
    
	public static OI oi;


	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		// instantiate the command used for the autonomous period
		this.DriveStick = new Joystick(IODefinitions.USB_PORT_1);
		this.GameStick = new Joystick(IODefinitions.USB_PORT_2);	
	    this.RobotDrive = new EntropyDrive();
    }
									
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
       //  schedule the autonomous command (example)
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
      RobotDrive.Initialize();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
       // RobotDrive.driveRobot(DriveStick.getY(), DriveStick.getRawAxis(4));
        RobotDrive.driveRobot(0,0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
