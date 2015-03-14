package autonomous;

import java.util.LinkedList;
import java.util.Queue;

import org.usfirst.frc.team138.robot.subsystems.Claw;
import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;
import org.usfirst.frc.team138.robot.subsystems.Lift;
import org.usfirst.frc.team138.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;

public class Autonomous {

	EntropyDrive EntDrive;
	Claw claw;
	Wrist wrist;
	Lift lift;
	
	AnalogInput SelectorSwitch;
	private static final int selectorSwitchPort = 3;
	
	Encoder LeftEncoder;
	Encoder RightEncoder;
	private static final double Encoder_Inches_Per_Pulse = 0.087;
	
	//Define the queues of commands that will be used in various autonomous scenarios
	Queue<AutonomousState> BothGrabQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> ContainerGrabQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> MantisArmQueue = new LinkedList<AutonomousState>();
	Queue<AutonomousState> IdleQueue = new LinkedList<AutonomousState>();
	private AnalogInput range;
	
	public Autonomous(EntropyDrive entDrive, Claw autoClaw, Wrist autoWrist, Lift lift, AnalogInput RangeFinder)
	{
		this.EntDrive = entDrive;
		this.claw = autoClaw;
		this.wrist = autoWrist;
		this.lift = lift;
		this.range = RangeFinder;
		this.SelectorSwitch = new AnalogInput(selectorSwitchPort);
		
		RightEncoder = new Encoder(0, 1, false);
		LeftEncoder = new Encoder(3, 2, true);
		
		LeftEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
		RightEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
	}
	
	public void Init()
	{
		//Create a queue of commands to grab the container, and push the tote into the auto zone
		BothGrabQueue.add(new RotationState(30, true, .5, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new WristState(wrist, claw, true));
		BothGrabQueue.add(new DriveState (2, true, 0.4, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new LiftState(range, lift, .2));
		BothGrabQueue.add(new RotationState(60, true, .5, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new DriveState (144, true, 0.4, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue of commands for grabbing the container and driving into the auto zone
		//ContainerGrabQueue.add(new DriveState(160, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new WristState(wrist, claw, true));
		ContainerGrabQueue.add(new RotationState(45, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new LiftState(range, lift, .15));
		ContainerGrabQueue.add(new DriveState (112, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue for of commands for grabbing the two containers on the step
		MantisArmQueue.add(new DriveState(9.5, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(true));
		MantisArmQueue.add(new WiggleState(EntDrive, false));
		MantisArmQueue.add(new DriveState(190, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(false));
		MantisArmQueue.add(new WiggleState(EntDrive, true));
		MantisArmQueue.add(new DriveState(90, false, 0.6, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new IdleState(EntDrive));
		
		//Create a queue (of one) to not do anything
		IdleQueue.add(new WiggleState(EntDrive, false));
		IdleQueue.add(new IdleState(EntDrive));
	}

	public boolean Update()
	{		
		//Select the autonomous queue to run based on the selector switch
		/*if (SelectorSwitch.getVoltage() < 0.4)
	    { 
			// Standard autonomous procedure selected
			if (ContainerGrabQueue.peek().Update())
	    	{
				ContainerGrabQueue.remove();
			} 
	     }
	    else if (SelectorSwitch.getVoltage() >= 0.4 && SelectorSwitch.getVoltage() < 0.8)
	    { */
	    	// Container Grab & tote push autonomous procedure selected
	    	if (BothGrabQueue.peek().Update())
			{
				BothGrabQueue.remove();
			} /*
	    else if (SelectorSwitch.getVoltage() >= 0.8 && SelectorSwitch.getVoltage() < 1.2)
	    { 
	    	// Container Grab autonomous procedure selected
	    	if (MantisArmQueue.peek().Update())
			{
				MantisArmQueue.remove();
			} 
	    }
	    else
	    {
	    	if (IdleQueue.peek().Update())
			{
				IdleQueue.remove();
			}
	    }
		*/
		return false;
	}	
}
