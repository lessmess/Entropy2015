package autonomous;

import org.usfirst.frc.team138.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MantisArmState extends AutonomousState {

	// If targetArmState is set to true, the mantis arms will extend
	// If set to false, the mantis arms will retract
	private boolean targetArmState;
	
	private int delayCounter;
	private static final int delayLimit = 50;
	
	private Solenoid leftMantisArmSolenoid = RobotMap.leftMantis;
	private Solenoid rightMantisArmSolenoid = RobotMap.rightMantis;
	
	public MantisArmState(boolean Extended)
	{
		this.targetArmState = Extended;
		this.delayCounter = 0;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Mantis Arms State");
		
		if (delayCounter == 0)
		{
			// Extend or retract the mantis arms
			leftMantisArmSolenoid.set(targetArmState);
			rightMantisArmSolenoid.set(targetArmState);			
		}		
		
		// Keep running the Update function until an arbitrary amount of time has
		// passed to allow the mantis arms to fully extend/retract
		delayCounter++;
		
		if (delayCounter >= delayLimit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
}
