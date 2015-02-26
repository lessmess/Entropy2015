package autonomous;

import org.usfirst.frc.team138.robot.subsystems.Lift;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftState extends AutonomousState {

	// If direction is set to true, the lift will go up
	// If direction is set to false, the lift will go down
	private boolean direction;	
	private Lift lift;	
	private int delayCounter;
	private int delayLimit;
	
	public LiftState(Lift lift, boolean Direction, int Duration)
	{
		this.direction = Direction;
		this.lift = lift;
		this.delayCounter = 0;
		this.delayLimit = Duration;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Lift State");
		
		if (delayCounter == 0)
		{
			if (direction)
			{
				lift.up();
			}
			else
			{
				lift.down();
			}
					
		}		
		
		// Keep running the Update function until an arbitrary amount of time has
		// passed to allow the wrist to fully flip up/flip down
		delayCounter++;
		
		if (delayCounter >= delayLimit)
		{
			lift.stop();
			return true;
		}
		else
		{
			return false;
		}
	}	
}
