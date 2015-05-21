package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotationState extends AutonomousState{

	//Pointer to our EntropyDrive
	EntropyDrive entropyDrive;
	
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	static final double wheelBase = 22.5;
	private static final double scaleFactor = 0.05;	
	
	//Number of degrees of rotation required before completion of this state (used in conjunction with targetPosition)
	double targetRotation;
	double speed;
	boolean leftOrRight;
	boolean wiggle;
	
	double leftEncoderDistance;
	double rightEncoderDistance;
	double error;
	double rightSpeed;
	double currentPosition;
	double counter;


	// targetRotation is an angle, leftOrRight is true to turn right, speed is the speed of the rotation
	public RotationState(double targetRotation, boolean leftOrRight, double speed, boolean wiggle, Encoder leftEncoder, Encoder rightEncoder, EntropyDrive entDrive)
	{
	  this.wiggle = wiggle;
	  this.entropyDrive = entDrive;
	  this.leftEncoder = leftEncoder;
	  this.rightEncoder = rightEncoder;
	  this.leftEncoderDistance = 0.0;
	  this.rightEncoderDistance = 0.0;
	  this.error = 0.0;
	  this.rightSpeed = 0.0;
	  this.targetRotation = (targetRotation / 360) * wheelBase * Math.PI;
	  
	  this.leftOrRight = leftOrRight;
	  this.speed = speed;
	  this.counter = 0;
	}

	public void Init()
	{
		this.currentPosition = 0.0;
	}

	public boolean Update()
	{
		if(counter < 5 && !wiggle)
		{
			counter++;
			return false;
		}
		else{
		/* PRINT INFORMATION TO DRIVER STATION */
		error = leftEncoder.getDistance() - rightEncoder.getDistance();
		
		SmartDashboard.putString("State", "Rotation State");
		SmartDashboard.putNumber("Right Encoder Distance", rightEncoder.getDistance());
		SmartDashboard.putNumber("Left Encoder Distance", leftEncoder.getDistance());
		SmartDashboard.putNumber("Error", error);
		
		rightSpeed = speed + error * scaleFactor;
		
		if (Math.abs(leftEncoder.getDistance()) >= targetRotation || Math.abs(rightEncoder.getDistance()) >= targetRotation)
		{
			entropyDrive.driveRobot(0, 0);
			leftEncoder.reset();
			rightEncoder.reset();
			return true;
		}
		else if (leftOrRight)
		{
			entropyDrive.wpiDrive.setLeftRightMotorOutputs(speed, -speed);
			return false;
		}
		else 
		{
			entropyDrive.wpiDrive.setLeftRightMotorOutputs(-speed, speed);
			return false;
		}
		}
	}
}