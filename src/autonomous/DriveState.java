package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveState extends AutonomousState {
	
	/* Local Class Variables */
	private EntropyDrive entropyDrive;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	// Target position to reach before completion of this state (used in conjunction with degreesToRotate)
	private double targetPosition;
	
	// Current position of robot relative to the position in which it began the state)
	
	// Current forward speed
	private double forwardSpeed;
	
	// The amount to adjust the robots heading by to keep it driving straight
	//private double rotationCorrection;
	
	private double error;
	
	private double leftEncoderDistance;
	private double rightEncoderDistance;
	
	private double leftSpeed;
	private double rightSpeed;

	private double counter;
	private boolean stopcounter;
	
	// 0.05 is a good scale factor for 16.5 inches
	private static final double scaleFactor = 0.9;
	private static final double Dead_Zone = 0.5;
	
	// targetPosition is in inches
	//For direction: input true for forwards, input false for backwards
	public DriveState(double targetPosition, boolean direction, double speed, boolean stopcounter, Encoder leftEncoder, Encoder rightEncoder, EntropyDrive entDrive)
	{
		this.entropyDrive = entDrive;		
		this.targetPosition = targetPosition;		
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;		
		if (direction)
		{
			this.forwardSpeed = speed;
		}
		else
		{
			this.forwardSpeed = -speed;
		}
		this.error = 0.0;
		this.leftEncoderDistance = 0.0;
		this.rightEncoderDistance = 0.0;
		this.rightSpeed = forwardSpeed;
		this.leftSpeed = forwardSpeed;
		this.counter = 0;
		this.stopcounter = stopcounter;
	}
	
	public void Init()
	{
		//
	}
	
	public boolean Update()
	{
		counter++;
		if(counter > 60 && stopcounter)
		{
			return true;
		}
		// Retrieve the latest distance measurements from the encoders
		leftEncoderDistance = leftEncoder.getDistance();
		rightEncoderDistance = rightEncoder.getDistance();

		if(Math.abs(leftEncoderDistance) > targetPosition ||
				Math.abs(rightEncoderDistance) > targetPosition)
		{
			//Robot is close enough to target position that it is now OK to stop
			entropyDrive.driveRobot(0.0, 0.0);
			
			leftEncoder.reset();
			rightEncoder.reset();
			
			return true;
		}
		else
		{
			//if error is positive, then the left wheel is farther than right, and vice versa
			error = leftEncoderDistance - rightEncoderDistance;
			
			rightSpeed = forwardSpeed + error * scaleFactor;
			leftSpeed = forwardSpeed - error * 0.1;
			//Robot is not at target position yet, keep driving
				//entropyDrive.wpiDrive.setLeftRightMotorOutputs(0,0);

				entropyDrive.wpiDrive.setLeftRightMotorOutputs(leftSpeed, rightSpeed);

			/* PRINT INFORMATION TO DRIVER STATION */
			SmartDashboard.putString("State", "Driving...");
			SmartDashboard.putNumber("Right Encoder Distance", rightEncoder.getDistance());
			SmartDashboard.putNumber("Left Encoder Distance", leftEncoder.getDistance());
			SmartDashboard.putNumber("Error", error);
			
			//lastLeftDistance = leftEncoderDistance;
			//lastRightDistance = rightEncoderDistance;
			
			return false;
		}
	}
}
