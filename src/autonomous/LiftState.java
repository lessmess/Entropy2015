package autonomous;

import org.usfirst.frc.team138.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftState extends AutonomousState {

	// If direction is set to true, the lift will go up
	// If direction is set to false, the lift will go down
	private Lift lift;	
	private int delayCounter;
	private int delayLimit;
	private double destination;
	private AnalogInput range;
	
	public LiftState(AnalogInput range, Lift lift, double Destination)
	{
		this.destination = Destination;
		this.lift = lift;
		this.delayCounter = 0;
		this.range = range;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Lift State");
		
		if (range.getVoltage() < destination)
		{
			lift.up();
			return false;
		}
		else 
		{
			lift.stop();
			return true;
		}
		
		
		
		/*if (delayCounter == 0)
		{
			
					
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
		}*/
	}	
}
