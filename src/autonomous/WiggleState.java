package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WiggleState extends AutonomousState{

	EntropyDrive EntDrive;
	int counter;
	
	public WiggleState(EntropyDrive EntDrive) {
		this.EntDrive = EntDrive;
		this.counter = 0;
	}
	
	public void Init() {
		
	}

	public boolean Update() {
		
		SmartDashboard.putString("State", "Wiggling...");
		
		if (counter < 7){
			EntDrive.driveRobot(0.0, 0.25, false);
			counter++;
			return false;
		}
		else if (counter < 21) {
			EntDrive.driveRobot(0.0, -0.25, false);
			counter++;
			return false;
		}
		else if (counter < 30) {
			EntDrive.driveRobot(0.0, 0.25, false);
			counter++;
			return false;
		}
		else {
			EntDrive.driveRobot(0.0, 0.0, false);
			counter++;
			return true;
		}
	}

}
