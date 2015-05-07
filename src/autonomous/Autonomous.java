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
	Queue<AutonomousState> WTFMantisFirst = new LinkedList<AutonomousState>();
	Queue<AutonomousState> WTF2MantisFirst = new LinkedList<AutonomousState>();
	Queue<AutonomousState> WTF3 = new LinkedList<AutonomousState>();
	Queue<AutonomousState> heh = new LinkedList<AutonomousState>();
	private AnalogInput range;
	public double voltage;
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
		
		
		WTF.add(new DriveState(20.5, false, 0.5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new MantisArmState(true, false));
		WTF.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(120, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(71, true, 0.8, false, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(29, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(12, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new MantisArmState(false, false));
		WTF.add(new RotationState(15, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(30, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(30, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(30, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(30, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(15, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		
		WTF.add(new RotationState(95, true, .4, false, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(183, true, 0.895, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(29, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(10, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new RotationState(168, false, .4, false, LeftEncoder, RightEncoder, EntDrive));
		/*WTF.add(new DriveState(34, false, 0.7, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.35, LeftEncoder, RightEncoder, EntDrive));*/
		WTF.add(new DriveState(37, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(36, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(27, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new MantisArmState(true, false));
		//WTF.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(4, true, 0.55, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new MantisArmState(false, false));
		WTF.add(new IdleState(EntDrive));
		
		WTF2.add(new DriveState(20.5, false, 0.5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new MantisArmState(true, false));
		WTF2.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(120, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new DriveState(80, true, 0.8, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new DriveState(21, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(12, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new MantisArmState(false, false));
		//WTF2.add(new WiggleState(EntDrive, true));
		WTF2.add(new RotationState(15, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(30, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(30, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(30, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(30, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(15, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		
		WTF2.add(new RotationState(95, false, .4, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new DriveState(187.5, true, 0.85, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new DriveState(29, true, 0.6, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new DriveState(9.5, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new RotationState(168, true, .4, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.7, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.35, LeftEncoder, RightEncoder, EntDrive));
		WTF2.add(new DriveState(61, false, 0.75, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new DriveState(16, false, 0.75, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new MantisArmState(true, false));
		//WTF2.add(new WiggleState(EntDrive, false));
		//WTF2.add(new DriveState(4, true, 0.55, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new MantisArmState(false, false));
		WTF2.add(new IdleState(EntDrive));
		
		WTF3.add(new DriveState(20.5, false, 0.5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF3.add(new MantisArmState(true, false));
		WTF3.add(new WiggleState(EntDrive, false));
		WTF3.add(new DriveState(71, true, 0.8, false, LeftEncoder, RightEncoder, EntDrive));
		WTF3.add(new DriveState(29, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF3.add(new MantisArmState(false, false));
		//WTF3.add(new WiggleState(EntDrive, true));
		//WTF3.add(new DriveState(120, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		//WTF3.add(new MantisArmState(false, false));
		//WTF3.add(new WiggleState(EntDrive, true));
		WTF3.add(new IdleState(EntDrive));
		
		
		//Create a queue of commands to grab the container, and push the tote into the auto zone
		BothGrabQueue.add(new RotationState(20, true, .5, false, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new LiftState(range, lift, .40));
		BothGrabQueue.add(new RotationState(80, true, .6, false, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new DriveState (160, true, 0.3, false, LeftEncoder, RightEncoder, EntDrive));
		BothGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue of commands for grabbing the container and driving into the auto zone
		//ContainerGrabQueue.add(new DriveState(160, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new LiftState(range, lift, .3));
		ContainerGrabQueue.add(new DriveState (108, false, 0.5, false, LeftEncoder, RightEncoder, EntDrive));
		ContainerGrabQueue.add(new IdleState(EntDrive));
		
		//Create a queue for of commands for grabbing the two containers on the step
		MantisArmQueue.add(new DriveState(15.5, false, 0.75, true, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(true, false));
		MantisArmQueue.add(new WiggleState(EntDrive, false));
		MantisArmQueue.add(new DriveState(60, true, 0.75, false, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new DriveState(60, true, 0.75, false, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new MantisArmState(false, false));
		MantisArmQueue.add(new WiggleState(EntDrive, true));
		MantisArmQueue.add(new DriveState(15, false, 0.6, false, LeftEncoder, RightEncoder, EntDrive));
		MantisArmQueue.add(new IdleState(EntDrive));
		
		
		WTFMantisFirst.add(new MantisArmState(true, true));
		WTFMantisFirst.add(new DriveState(15.5, false, 0.5, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(120, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new DriveState(71, true, 0.8, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new DriveState(29, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(12, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new MantisArmState(false, false));
		WTFMantisFirst.add(new RotationState(15, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(30, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(30, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(30, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(30, false, .7, true, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(15, true, .7, true, LeftEncoder, RightEncoder, EntDrive));
		
		WTFMantisFirst.add(new RotationState(95, true, .4, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new DriveState(183, true, 0.895, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(29, true, 0.5, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(10, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new RotationState(140, false, .4, false, LeftEncoder, RightEncoder, EntDrive));
		/*WTF.add(new DriveState(34, false, 0.7, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		WTF.add(new DriveState(34, false, 0.35, LeftEncoder, RightEncoder, EntDrive));*/
		WTFMantisFirst.add(new DriveState(37, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new DriveState(36, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new DriveState(27, false, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new MantisArmState(true, false));
		WTFMantisFirst.add(new WiggleState(EntDrive, false));
		WTFMantisFirst.add(new DriveState(4, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTFMantisFirst.add(new MantisArmState(false, false));
		WTFMantisFirst.add(new IdleState(EntDrive));
		
		WTF2MantisFirst.add(new MantisArmState(true, true));
		WTF2MantisFirst.add(new DriveState(18.5, false, 0.5, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new WiggleState(EntDrive, false));
		//WTF.add(new DriveState(120, true, 0.70, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new DriveState(80, true, 0.8, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new DriveState(21, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(12, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new MantisArmState(false, false));
		//WTF2.add(new WiggleState(EntDrive, true));
		WTF2MantisFirst.add(new RotationState(15, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(30, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(30, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(30, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(30, false, .5, true, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(15, true, .5, true, LeftEncoder, RightEncoder, EntDrive));
		
		WTF2MantisFirst.add(new RotationState(95, false, .4, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new DriveState(187.5, true, 0.85, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new DriveState(29, true, 0.6, LeftEncoder, RightEncoder, EntDrive));
		//WTF2.add(new DriveState(9.5, true, 0.45, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new RotationState(90, true, .4, false, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.7, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.5, LeftEncoder, RightEncoder, EntDrive));
		//WTF.add(new DriveState(34, false, 0.35, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new DriveState(57, false, 0.75, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new DriveState(16, false, 0.75, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new MantisArmState(true, false));
		WTF2MantisFirst.add(new WiggleState(EntDrive, false));
		WTF2MantisFirst.add(new DriveState(4, true, 0.55, false, LeftEncoder, RightEncoder, EntDrive));
		WTF2MantisFirst.add(new MantisArmState(false, false));
		WTF2MantisFirst.add(new IdleState(EntDrive));
		
		
		
		
		
		
		
		heh.add(new RotationState(90, true, .4, false, LeftEncoder, RightEncoder, EntDrive));
		heh.add(new IdleState(EntDrive));
		
		
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
		
		voltage = SelectorSwitch.getVoltage();
		SmartDashboard.putNumber("Voltage", voltage);
		//Select the autonomous queue to run based on the selector switch
		if (voltage >= 0 && voltage < .3)
	    { 
			SmartDashboard.putString("mode", "4CSP");
			// Standard autonomous procedure selected
			if (WTF2.peek().Update())
	    	{
				WTF2.remove();
			} 
	     }
		else if (voltage > .5 && voltage < 1.1)
	    { 
			SmartDashboard.putString("mode", "4CNSP");
			// Standard autonomous procedure selected
			if (WTF.peek().Update())
	    	{
				WTF.remove();
			} 
	     }
		else if (voltage > 1.3 && voltage < 1.8)
	    { 
			SmartDashboard.putString("mode", "2CSA");
			// Standard autonomous procedure selected
			if (WTF3.peek().Update())
	    	{
				WTF3.remove();
			} 
	     }
		else
		{
			SmartDashboard.putString("mode", "Idle");
			// Standard autonomous procedure selected
			if (IdleQueue.peek().Update())
	    	{
				IdleQueue.remove();
			} 
		}
		return false;
	
	}
}

