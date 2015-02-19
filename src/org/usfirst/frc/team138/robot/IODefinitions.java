package org.usfirst.frc.team138.robot;

public class IODefinitions {

	public IODefinitions() {
		
	}
	//Global static finalants
	static final float SPROCKET_1_TEETH_COUNT = 18.0f;
	static final float SPROCKET_2_TEETH_COUNT = 45.0f;
	static final float WHEEL_DIAMETER = 6.0f; // in inches
	//static final float PULSE_RATIO = 1 / (SPROCKET_1_TEETH_COUNT / SPROCKET_2_TEETH_COUNT * 3.14159265 * WHEEL_DIAMETER);
	// TODO: fill out numbers above
	static final double PULSE_RATIO = 0.091;
		
		public enum Compressor{
			COMPRESSOR_RELAY_CHANNEL (1),   //Resolve with Tim
			PRESSURE_SWITCH_CHANNEL (8),
			PRESSURE_SWITCH_MODULE (1),
			COMPRESSOR_RELAY_MODULE (1);
			
			private int value;
			
			private Compressor(int value) {
				this.value = value;
			}
		};

		
		public enum Solenoid {
			ARM_RAIL (1),
			CRADLE_LEDS (2),
			ARM_ROTATION (3),
			KICKER_SHIFTER (5), 
			KICKER_TRIGGER (7);
			
			private int value;
			
			private Solenoid(int value) {
				this.value = value;
			}
		};
		
		public enum DigIO{
			KickerLatchedSense (10),
			ArmExtenderIn (12);
			
			private int value;
			
			private DigIO(int value) {
				this.value = value;
			}
		};
		
		public enum AnalogIO{
			RANGE_FINDER (4),
			BALL_POSESSION_DETECTOR (2),
			AUTONOMOUS_SCENARIO_STATE (6);
			
			private int value;
			
			private AnalogIO(int value) {
				this.value = value;
			}
		};
		
		public static final int MOTOR_DRIVE_LEFT_1 = 2;
		public static final int MOTOR_DRIVE_LEFT_2 = 3;
		public static final int MOTOR_DRIVE_RIGHT_1 = 4;
		public static final int MOTOR_DRIVE_RIGHT_2 = 5;
		
		public enum  CanBus {
			UNUSED_0 (0),
			UNUSED_1 (1),
			MOTOR_DRIVE_LEFT_1(2),
			MOTOR_DRIVE_LEFT_2(3),
			MOTOR_DRIVE_RIGHT_1(4),
			MOTOR_DRIVE_RIGHT_2(5),
			UNUSED_6(6),
			UNUSED_7(7);
			
			private int value;
			
			private CanBus(int value) {
				this.value = value;
			}
		};

		public enum PWM_MOTORS {
			PWM_UNUSED_0 (0),
			MOTOR_ACQUISITION_EXTENDER (1),
			PWM_UNUSED_2 (2),
			MOTOR_ACQUISITION_BELT (3),
			PWM_UNUSED_4 (4),
			KICKER_PULL (5),
			PWM_UNUSED_6 (6),
			PWM_CAMERA_CONTROL (7);
			
			private int value;
			
			private PWM_MOTORS(int value) {
				this.value = value;
			}
		};
		
		public static final int USB_PORT_1 = 1;
		public static final int USB_PORT_2 = 2;
		
	    public enum Physical_USB_Port{
	    	USB_PORT_1 (1),
	    	USB_PORT_2 (2);
	    	
	    	private int value;
		
	    	private Physical_USB_Port(int value) {
	    		this.value = value;
			}
		};
	    
	    
		public enum Game_Stick_IO{ 
	    	GAME_BUTTON_NOT_USED         (0),
	    	KICKER_TRIGGERKICK           (10),
	    	KICKER_PREPAREKICK           (9),
	    	GAME_BUTTON_CRADLE_UP        (3),
	    	GAME_BUTTON_CRADLE_DOWN      (2),
			GAME_BUTTON_ARM_ROLL_IN      (1),
			GAME_BUTTON_ARM_ROLL_OUT     (4),
			GAME_BUTTON_ARM_UP           (7),
	    	GAME_BUTTON_ARM_DOWN         (5),
	    	GAME_BUTTON_ARM_IN           (6),
	    	GAME_BUTTON_ARM_OUT          (8);
	    	
	    	private int value;
			
			private Game_Stick_IO(int value) {
				this.value = value;
			}
	    	
	    };
	    
	    public static int RANGE_FINDER = 0;
}
