package autonomous;

import org.usfirst.frc.team138.robot.subsystems.EntropyDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WiggleState extends AutonomousState{

	private static final int x = 10;
	EntropyDrive EntDrive;
	boolean MegaWiggle;
	int counter;
	
	public WiggleState (EntropyDrive EntDrive, boolean MegaWiggle) {
		this.EntDrive = EntDrive;
		this.MegaWiggle = MegaWiggle;
		this.counter = 0;
	}
	
	public void Init() {
		
	}

	public boolean Update() {
		
		SmartDashboard.putString("State", "Wiggling...");
		if (MegaWiggle)
		{
			if (counter < x){
				EntDrive.driveRobot(0.0, 0.9, false);
				counter++;
				return false;
			}
			else if (counter < 3 * x) {
				EntDrive.driveRobot(0.0, -0.9, false);
				counter++;
				return false;
			}
			else if (counter < 5 * x) {
				EntDrive.driveRobot(0.0, 0.9, false);
				counter++;
				return false;
			}
			else if (counter < 7 * x)
			{
				EntDrive.driveRobot(0.0, -0.9, false);
				counter++;
				return false;
			}
			else if (counter < 9 * x)
			{
				EntDrive.driveRobot(0.0, 0.9, false);
				counter++;
				return false;
			}
			else if (counter < 10 * x)
			{
				EntDrive.driveRobot(0.0,  -0.9, false);
				counter++;
				return false;
			}
			else 
			{
				EntDrive.driveRobot(0.0, 0.0, false);
				counter++;
				return true;
			}
		}
		else
		{
			if (counter < x){
				EntDrive.driveRobot(0.0, 0.6, false);
				counter++;
				return false;
			}
			else if (counter < 3 * x) {
				EntDrive.driveRobot(0.0, -0.6, false);
				counter++;
				return false;
			}
			else if (counter < 4 * x) {
				EntDrive.driveRobot(0.0, 0.6, false);
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
}
