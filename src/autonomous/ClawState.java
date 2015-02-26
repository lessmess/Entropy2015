package autonomous;

import org.usfirst.frc.team138.robot.subsystems.Claw;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawState extends AutonomousState {

	// If targetState is set to true, the claw will open
	// If targetState is set to false, the claw will close
	private boolean targetState;	
	private Claw claw;	
	private int delayCounter;
	private static final int delayLimit = 100;
	
	public ClawState(Claw claw, boolean Extended)
	{
		this.targetState = Extended;
		this.claw = claw;
		this.delayCounter = 0;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Claw State");
		
		if (delayCounter == 0)
		{
			if (targetState)
			{
				claw.open();
			}
			else
			{
				claw.close();
			}
					
		}		
		
		// Keep running the Update function until an arbitrary amount of time has
		// passed to allow the claw to fully open/close
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
