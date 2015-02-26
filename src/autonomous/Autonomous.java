package autonomous;

import java.util.LinkedList;
import java.util.Queue;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;

public class Autonomous {

	EntropyDrive EntDrive;
	
	AnalogInput SelectorSwitch;
	private static final int selectorSwitchPort = 3;
	
	Encoder LeftEncoder;
	Encoder RightEncoder;
	private static final double Encoder_Inches_Per_Pulse = 0.087;
	
	//Define the queues of commands that will be used in various autonomous scenarios
	Queue<AutonomousState> DriveForwardQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> MantisArmQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> ContainerGrabQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> IdleQueue = new LinkedList<AutonomousState>();
	
	public Autonomous(EntropyDrive entDrive)
	{
		this.EntDrive = entDrive;
		this.SelectorSwitch = new AnalogInput(selectorSwitchPort);
		
		RightEncoder = new Encoder(0, 1, false);
		LeftEncoder = new Encoder(3, 2, true);
		
		LeftEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
		RightEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
	}
	
	public void Init()
	{
		//Create a queue of commands for pushing the totes/containers into the auto zone
		//DriveForwardQueue.add(new WiggleState(EntDrive));
		DriveForwardQueue.add(new DriveState(160, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		DriveForwardQueue.add(new IdleState(EntDrive));
		
		//Create a queue for of commands for grabbing the two containers on the step
		MantisArmQueue.add(new DriveState(9.5, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(true));
		//MantisArmQueue.add(new WiggleState(EntDrive));
		MantisArmQueue.add(new DriveState(150, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new DriveState(30,true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		//MantisArmQueue.add(new DriveState(5, false, 0.45, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(false));
		//MantisArmQueue.add(new DriveState(50, false, 0.45, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new IdleState(EntDrive));
		
		// Create a queue for when there is a container directly in front of the robot
		// Flip wrist, close claw, raise lift, and drive forward.
		//
		
		//Create a queue (of one) to not do anything
		IdleQueue.add(new IdleState(EntDrive));
	}

	public boolean Update()
	{		
		//Select the autonomous queue to run based on the selector switch
		if (SelectorSwitch.getVoltage() < 0.4)
	    {
			// Standard autonomous procedure selected
			if (DriveForwardQueue.peek().Update())
	    	{
	    		DriveForwardQueue.remove();
			}
	    }
	    else if (SelectorSwitch.getVoltage() >= 0.4 && SelectorSwitch.getVoltage() < 0.8)
	    {	
	    	// Container Grab autonomous procedure selected
	    	if (MantisArmQueue.peek().Update())
			{
				MantisArmQueue.remove();
			}
	    }
	    else
	    {
	    	IdleQueue.peek().Update();
	    }
		
		return false;
	}	
}
