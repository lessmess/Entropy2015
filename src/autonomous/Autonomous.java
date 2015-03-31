package autonomous;

import java.util.LinkedList;
import java.util.Queue;

import org.usfirst.frc.team138.robot.subsystems.Claw;
import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;
import org.usfirst.frc.team138.robot.subsystems.Lift;
import org.usfirst.frc.team138.robot.subsystems.Wrist;
import org.usfirst.frc.team138.robot.subsystems.ArmExtension;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	EntropyDrive EntDrive;
	Claw claw;
	Wrist wrist;
	Lift lift;
	ArmExtension arm;
	
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
	Queue<AutonomousState> WTF = new LinkedList<AutonomousState>();
	Queue<AutonomousState> WTF2 = new LinkedList<AutonomousState>();
	private AnalogInput range;
	private String mode;
	
	public Autonomous(EntropyDrive entDrive, ArmExtension arm, Claw autoClaw, Wrist autoWrist, Lift lift, AnalogInput RangeFinder)
	{
		this.EntDrive = entDrive;
		this.claw = autoClaw;
		this.wrist = autoWrist;
		this.lift = lift;
		this.arm = arm;
		this.range = RangeFinder;
		this.SelectorSwitch = new AnalogInput(selectorSwitchPort);
		
		RightEncoder = new Encoder(0, 1, false);
		LeftEncoder = new Encoder(3, 2, true);
		
		LeftEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
		RightEncoder.setDistancePerPulse(Encoder_Inches_Per_Pulse);
	}
	
	public void Init()
	{
		/*
		 rotation (from back):
		 false = left
		 true = right

		 driving (not from back):
		 false = backwards
		 true = forwards
		 */
		
		
		WTF.add(new DriveState(9.5, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new MantisArmState(true));
		WTF.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(120, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(71, true, 0.8, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(29, true, 0.55, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(12, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new MantisArmState(false));
		WTF.add(new WiggleState(EntDrive, true));
		WTF.add(new RotationState(107, true, .4, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(144, true, 0.95, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(29, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(10, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(120, false, .4, LeftEncoder, RightEncoder, EntDrive));
		/*WTF.add(new DriveState(34, false, 0.7, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.35, LeftEncoder, RightEncoder, EntDrive));*/
		WTF.add(new DriveState(37, false, 0.55, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(36, false, 0.55, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(27, false, 0.55, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new MantisArmState(true));
		WTF.add(new WiggleState(EntDrive, false));
		WTF.add(new IdleState(EntDrive));
		
		WTF2.add(new DriveState(9.5, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new MantisArmState(true));
		WTF2.add(new WiggleState(EntDrive, false));
		WTF2.add(new DriveState(71, true, 0.8, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new DriveState(29, true, 0.55, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new MantisArmState(false));
		WTF2.add(new WiggleState(EntDrive, true));
		WTF2.add(new DriveState(120, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new IdleState(EntDrive));
		
		
		//Create a queue of commands to grab the container, and push the tote into the auto zone
		BothGrabQueue.add(new RotationState(20, true, .3, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new LiftState(range, lift, .40));
		BothGrabQueue.add(new RotationState(80, true, .4, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new DriveState (160, true, 0.4, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue of commands for grabbing the container and driving into the auto zone
		//ContainerGrabQueue.add(new DriveState(160, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new LiftState(range, lift, .3));
		ContainerGrabQueue.add(new DriveState (108, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue for of commands for grabbing the two containers on the step
		MantisArmQueue.add(new DriveState(9.5, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(true));
		MantisArmQueue.add(new WiggleState(EntDrive, false));
		MantisArmQueue.add(new DriveState(60, true, 0.55, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new DriveState(60, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(false));
		MantisArmQueue.add(new WiggleState(EntDrive, true));
		MantisArmQueue.add(new DriveState(15, false, 0.6, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new IdleState(EntDrive));
		
		//Create a queue (of one) to not do anything
		IdleQueue.add(new IdleState(EntDrive));
		
		
		
		
		
	}


	public boolean Update()
	{		
		/*
		1: .004 	
		2: .399
		3: .796
		4: 1.194
		5: 1.588
		 */
		
		mode = SmartDashboard.getString("Auto");
		SmartDashboard.putString("mode", mode);
		//Select the autonomous queue to run based on the selector switch
		if (mode.equals("1"))
	    { 
			SmartDashboard.putString("test", "one");
			// Standard autonomous procedure selected
			if (ContainerGrabQueue.peek().Update())
	    	{
				ContainerGrabQueue.remove();
			} 
	     }
	    else if (mode.equals("2"))
	    { 
	    	SmartDashboard.putString("test", "two");
	    	//Container Grab & tote push autonomous procedure selected
	    	if (BothGrabQueue.peek().Update())
			{
				BothGrabQueue.remove();
			} 
	    }
	    else if (mode.equals("3"))
	    { 
	    	SmartDashboard.putString("test", "three");
	    	// Container Grab autonomous procedure selected
	    	if (MantisArmQueue.peek().Update())
			{
				MantisArmQueue.remove();
			} 
	    }
	    else if (mode.equals("4"))
	    { 
	    	SmartDashboard.putString("test", "four");
	    	// Container Grab autonomous procedure selected
	    	if (WTF.peek().Update())
			{
				WTF.remove();
			} 
	    }
	    else if (mode.equals("5"))
	    { 
	    	SmartDashboard.putString("test", "five");
	    	// Container Grab autonomous procedure selected
	    	if (WTF2.peek().Update())
			{
				WTF2.remove();
			} 
	    }
	    else
	    {
	    	SmartDashboard.putString("test", "sad");
	    	if (IdleQueue.peek().Update())
			{
				IdleQueue.remove();
			}
	    }
		return false;
	
	}
}

