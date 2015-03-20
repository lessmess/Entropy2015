package autonomous;

import org.usfirst.frc.team138.robot.subsystems.ArmExtension;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ExtendState extends AutonomousState {

	// If targetState is set to true, the wrist will flip up
	// If targetState is set to false, the wrist will flip down
	private boolean targetState;
	private ArmExtension arm;
	private double time;
	
	private double time_passed;
	private double last_time;
	private double current_time;
	
	public ExtendState(ArmExtension arm, double time)
	{
		this.arm = arm;
		this.time = time;
		
		this.last_time = 0;
	}

	public void Init()
	{
		//
	}
	
	public boolean Update()
	{		
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Grab State");
		
		current_time = System.currentTimeMillis();
		if (last_time != 0 && time_passed < time)
		{
			time_passed += current_time - last_time;
			arm.out();
			return false;
		}
		else
		{
			arm.stop();
			return true;
		}
		
	}	
}
