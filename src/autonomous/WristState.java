package autonomous;

import org.usfirst.frc.team138.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WristState extends AutonomousState {

	// If targetState is set to true, the wrist will flip up
	// If targetState is set to false, the wrist will flip down
	private boolean targetState;	
	private Wrist wrist;	
	private int delayCounter;
	private static final int delayLimit = 100;
	
	public WristState(Wrist wrist, boolean Extended)
	{
		this.targetState = Extended;
		this.wrist = wrist;
		this.delayCounter = 0;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Wrist State");
		
		if (delayCounter == 0)
		{
			if (targetState)
			{
				wrist.up();
			}
			else
			{
				wrist.down();
			}
					
		}		
		
		// Keep running the Update function until an arbitrary amount of time has
		// passed to allow the wrist to fully flip up/flip down
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
