
package org.usfirst.frc.team138.robot;

import java.util.Date;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//import org.usfirst.frc.team138.robot.commands.ExampleCommand;






import org.usfirst.frc.team138.robot.subsystems.*;

import autonomous.Autonomous;


public class Robot extends IterativeRobot
{

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	
	DriverStation EntropyDriverStation;			// driver station object
	int m_priorPacketNumber;					// keep track of the most recent packet number from the DS
	int m_dsPacketsReceivedInCurrentSecond;	// keep track of the ds packets received in the current second
	
	// Declare variables for the two joysticks being used
	Joystick DriveStick;			// EntropyJoystick used for robot driving
	Joystick GameStick;			// EntropyJoystick for all other functions		
	EntropyDrive RobotDrive;
	IODefinitions test;
	
	AnalogInput RangeFinder;
	
	Autonomous autonomous;
	
	public static Claw claw;
    public static Wrist wrist;
    public static Mantis mantis;
    public static Lift lift;
    public static ArmExtension armExtension;
	public static Commands commands;
	
    boolean delay;
    long epoch;
    long currentTime;
    
    boolean lift1;
    boolean lift2;
    boolean lift3;
    
	DriverStation station;
	float m_turnSpeed;
    int state;
    boolean pressed_flag;
    
	public static OI oi;

	boolean slow_mode;

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	
    	RobotMap.init();
		oi = new OI();
		claw = new Claw();
        wrist = new Wrist();
        mantis = new Mantis();
        lift = new Lift();
        armExtension = new ArmExtension();
        commands = new Commands();
        
        this.RangeFinder = new AnalogInput(IODefinitions.RANGE_FINDER);
		// instantiate the command used for the autonomous period
		this.DriveStick = new Joystick(IODefinitions.USB_PORT_1);
		this.GameStick = new Joystick(IODefinitions.USB_PORT_2);	
	    this.RobotDrive = new EntropyDrive();
	    autonomous = new Autonomous(RobotDrive, armExtension, claw, wrist, lift, RangeFinder);

	    lift1 = false;
	    lift2 = false;
	    lift3 = false;	    
    }
									
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() 
    {
       //  schedule the autonomous command (example)
        autonomous.Init();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
        autonomous.Update();
    }

    public void teleopInit() 
    {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
      RobotDrive.Initialize();
      claw = new Claw();
      wrist = new Wrist();
      armExtension = new ArmExtension();
      lift = new Lift();
      commands = new Commands();
      delay = false;
      
      lift1 = false;
      lift2 = false;
      lift3 = false;
      
      slow_mode = false;
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
        if(delay == false)
        {
        	//        station.println(DriverStation.Line.kUser1, 1, DriveStick.getY());
        	SmartDashboard.putNumber("throttle", DriveStick.getY());
        	SmartDashboard.putNumber("rotate", DriveStick.getRawAxis(0));
        	//gamepad
        	RobotDrive.driveRobot(DriveStick.getY(), DriveStick.getRawAxis(4));
        	//rc controller
        	//RobotDrive.driveRobot(DriveStick.getY(), DriveStick.getRawAxis(0), slow_mode);
        	SmartDashboard.putNumber("range", RangeFinder.getVoltage());
        	if(GameStick.getRawButton(1))
        	{
        		claw.open();
        	}
        	if(GameStick.getRawButton(2))
        	{
        		claw.close();
        	}
        	if(GameStick.getRawButton(4))
        	{
        		wrist.up();
        	}
        	if(GameStick.getRawButton(3))
        	{
        		wrist.down();
        	}
        	//5 and 6 left and right //3 and 2 left and right
        	if(DriveStick.getRawButton(5))
        	{
        		mantis.outRight();
        	}
        	
        	if(DriveStick.getRawButton(6))
        	{
        		mantis.outLeft();
        	}
        	
        	if(DriveStick.getRawButton(3))
        	{
        		mantis.inRight();
        	}
        	
        	if(DriveStick.getRawButton(2))
        	{
        		mantis.inLeft();
        	}    
        	
        	if(DriveStick.getRawButton(1))
        	{
        		mantis.inLeft();
        		mantis.inRight();
        	}
        	
        	if(GameStick.getRawButton(7))
        	{
        		armExtension.out();
        	}
        	
        	else if(GameStick.getRawButton(5))
        	{
        		armExtension.in();
        	}
        	else
        	{
        		armExtension.stop();
        	}
        		
        	if(GameStick.getRawButton(6))
        	{
        		lift.up();
        	}
        	
        	else if(GameStick.getRawButton(8))
        	{
        		lift.down();
        	}
        	
        	else
        	{
        		lift.stop();
        	}
        
        	/*if(GameStick.getRawButton(1) || lift1 == true)
        	{
        		lift1 = commands.liftPositionAquire(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(2) || lift2 == true)
        	{
        		lift2 = commands.liftPositionTransport(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(4) || lift3 == true)
        	{
        		lift3 = commands.liftPositionMax(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(9))
        	{
        		commands.armExtensionIn(lift, claw, armExtension, wrist, RangeFinder);
        	}  
        	
        	if(GameStick.getRawButton(10))
        	{
        		commands.armExtensionOut(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(5))
        	{
        		commands.acquireContainer(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(6))
        	{
        		commands.acquireTote(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	if(GameStick.getRawButton(7))
        	{
        		commands.placeContainer(lift, claw, armExtension, wrist, RangeFinder, 0);
        		delay = true;
        		Date date = new Date();
        		epoch = date.getTime();
        	}
        	
        	if(GameStick.getRawButton(8))
        	{
        		commands.placeTote(lift, claw, armExtension, wrist, RangeFinder);
        	}
        	
        	SmartDashboard.putNumber("raw axis 0", GameStick.getRawAxis(0));
        	if(GameStick.getRawAxis(1) > .15)
        	{
        		armExtension.out();
        	}
        	
        	if(GameStick.getRawAxis(1) < -.15)
        	{
        		armExtension.in();
        	}
        	
        	if(GameStick.getRawAxis(2) > .15)
        	{
        		lift.up();
        	}
        	
        	if(GameStick.getRawAxis(2) < -.15)
        	{
        		lift.down();
        	}
        	
        	if(GameStick.getRawAxis(2) >= -.15 && GameStick.getRawAxis(2) <= .15)
        	{
        //		lift.stop();
        	}
        }
        else
        {
        	Date date = new Date();
    		currentTime = date.getTime();
        	RobotDrive.driveRobot(0,0);
        	if(currentTime - epoch > 1000)
        	{
        		delay = false;
        		commands.placeContainer(lift, claw, armExtension, wrist, RangeFinder, 1);
        	} */    	
        }
    	
    	
    	
        //RobotDrive.driveRobot(0,0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
