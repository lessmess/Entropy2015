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
	private double lastRange;
	private double thisRange;
	private double[] rangeArray = new double[10000];
	
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
		
		for (int i=0;i<10000;i++)
		{
			rangeArray[i] = range.getVoltage();
		}
		
		double minRange = 10;
		
		for (int i=0;i<10000;i++)
		{
			if (rangeArray[i] < minRange)
			{
				minRange = rangeArray[i];
			}
		}
		SmartDashboard.putDouble("range", minRange);
		if (minRange > destination && minRange < 10)
		{
			lift.stop();
			return true;
		}
		else 
		{
			lift.up();
			return false;
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
