package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IdleState extends AutonomousState{
	
	  EntropyDrive entropyDrive;
	
	IdleState(EntropyDrive entDrive)
	{
		this.entropyDrive = entDrive;
	}
	
	void Init()
	{
		//
	}
	
	boolean Update()
	{
		// Print information to the SmartDashboard
		SmartDashboard.putString("State", "Idle State");
		
		// Continually command the robot not to move to keep the heartbeat going
		entropyDrive.driveRobot(0.0, 0.0);
		
		// Always return false, the robot can never exit IdleState once entered
		return false;		
	}

}
