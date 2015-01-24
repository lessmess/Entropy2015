/**
 * 
 */
package org.usfirst.frc.team138.robot.subsystems;

import edu.wpi.first.wpilibj.hal.PWMJNI;
import edu.wpi.first.wpilibj.hal.SolenoidJNI;
/**
 * @author User
 *
 */
public class AcquisitionArms implements EntropySubsystemTemplate {
	
	SolenoidJNI upperSolenoid = new SolenoidJNI ();
	SolenoidJNI lowerSolenoid = new SolenoidJNI ();
	
	//EntropyInfraredSensor InfraredSensor = new EntropyInfraredSensor ();
	PWMJNI MotorBelt = new PWMJNI ();
	PWMJNI  MotorExtender = new PWMJNI ();
	SolenoidJNI  CradleLEDs = new SolenoidJNI ();
	
	boolean ArmUp,CradleUp;

	public AcquisitionArms() {
		
	}

	@Override
	public boolean initialize() {
		return true;
	}

	@Override
	public void cleanup() {
		
	}
	
	public void upperVerticalPos(boolean Arm_Up, boolean Arm_Down) {
		
	}
	
	public void lowerVerticalPos(boolean Cradle_Up, boolean Cradle_Down) {
		
	}
	
	public void extend(boolean Arm_Out, boolean Arm_In, boolean Arm_Down) {
		
	}

	public void beltEnable(boolean Roll_In, boolean Roll_Out) {
		
	}
	@Override
	public String getFeedback() {	
		return null;
	}

}
