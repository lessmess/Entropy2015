package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotationState extends AutonomousState{

	//Pointer to our EntropyDrive
	EntropyDrive entropyDrive;
	
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	//Number of degrees of rotation required before completion of this state (used in conjunction with targetPosition)
	double targetRotation;
	double rotation;
	// Distance traveled since the last frame
	double deltaLeftDistance;
	double deltaRightDistance;
	
	double currentAngle;

	public RotationState(EntropyDrive entDrive, Encoder leftEncoder, Encoder rightEncoder,  double targetRotation)
	{
	  this.entropyDrive = entDrive;
	  this.leftEncoder = leftEncoder;
	  this.rightEncoder = rightEncoder;
	  
	  this.targetRotation = targetRotation;
	  
	}

	public void Init()
	{
	  rotation = 0.0;
	  deltaLeftDistance = 0.0;
	  deltaRightDistance = 0.0;
	  currentAngle = 0.0;
	}
	//This needs to find current angle based on initial angle and the two encoder values
	private double angleFinder(double leftEncoderDifference, double rightEncoderDifference)
	{
		
		double angle = 0.0; 
		return angle;	}

	public boolean Update()
	{
		/* PRINT INFORMATION TO DRIVER STATION */
		SmartDashboard.putString("State", "Rotation State");
		
		deltaLeftDistance = deltaLeftDistance + leftEncoder.getDistance();
		deltaRightDistance = deltaRightDistance + rightEncoder.getDistance();
		
		currentAngle = angleFinder(deltaLeftDistance, deltaRightDistance);
		
		if (currentAngle >= targetRotation)
		{
			/* DRIVE THE ROBOT */
			//entropyDrive.DriveRobot(0,0);
			return true;
		}
		else 
		{
			/* DRIVE THE ROBOT */
			//entropyDrive.DriveRobot(0.3, 0.3);
			return false;
		}

	}
}