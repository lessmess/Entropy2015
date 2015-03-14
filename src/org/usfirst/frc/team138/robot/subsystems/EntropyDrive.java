package org.usfirst.frc.team138.robot.subsystems;

import org.usfirst.frc.team138.robot.IODefinitions;
import edu.wpi.first.wpilibj.*;

public class EntropyDrive {

	
	
	int x_index;
	int y_index;
	
	//this is for gamepad driving
	double DEAD_ZONE_MAX = .15;
	//this is for the rc controller
	//double DEAD_ZONE_MAX = .10;
	

	double CompMoveValuePlus=0.99;
	double CompMoveValueMinus=0.60;
	double CompRotateValuePlus=0.99;
	double CompRotateValueMinus=0.99;
	
	CANJaguar MotorDriveLeft1;
	CANJaguar MotorDriveRight1;
	CANJaguar MotorDriveLeft2;
	CANJaguar MotorDriveRight2;
	public RobotDrive wpiDrive;

	double dampValue=0.05;
	public EntropyDrive(){
		MotorDriveLeft1 = new CANJaguar(IODefinitions.MOTOR_DRIVE_LEFT_1);
		MotorDriveLeft2 = new CANJaguar(IODefinitions.MOTOR_DRIVE_LEFT_2);
		MotorDriveRight1 = new CANJaguar(IODefinitions.MOTOR_DRIVE_RIGHT_1);
		MotorDriveRight2 = new CANJaguar(IODefinitions.MOTOR_DRIVE_RIGHT_2);

		wpiDrive = new RobotDrive( 	MotorDriveLeft1,
				MotorDriveLeft2,
				MotorDriveRight1,
				MotorDriveRight2 );
	}
	public enum DriveMode 
	{
		Rotate (0), 
		Radius (1);
		
		private int value;
			
			private DriveMode(int value) {
				this.value = value;
			}
	};

	public boolean Initialize () 
	{ 
		return true;
	}
			
		
	void cleanup ()
	{
	}
		
			
	public boolean driveRobot(double MoveValue, double RotateValue){

		double LeftMotors = 0; 
		double RightMotors = 0; 

		MoveValue = Limit(MoveValue);
		RotateValue = Limit(RotateValue);

		MoveValue = addDeadZone(MoveValue);
		//MoveValue = moveValueDampen(MoveValue);
		
		
		// TODO:Drive table is causing issues 2/4/2015
		LeftMotors = left_scale(RotateValue, MoveValue, DriveMode.Rotate);  
		RightMotors = right_scale(RotateValue, MoveValue, DriveMode.Rotate);
		
		//DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line5, "Drive L: %f", LeftMotors);
		//DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line6, "Drive R: %f", RightMotors);
		//DriverStationLCD::GetInstance()->UpdateLCD();
		
		//Command motors
		wpiDrive.setLeftRightMotorOutputs( LeftMotors, RightMotors );
		

		return true;
	}
		
			
		
	public boolean DriveRobotTrig(double MoveValue, double RotateValue){

		double LeftMotors = 0; 
		double RightMotors = 0; 
		double OutsideWheels = 0;
		double InsideWheels = 0;
		double Hypot = 0;
		double AbsMoveValue = 0;
		double AbsRotateValue = 0;



		//Normalize Joystick inputs
		if (MoveValue >= 0.0)
		{
			MoveValue = MoveValue/CompMoveValuePlus;
		}
		else
		{
			MoveValue = MoveValue/CompMoveValueMinus;
		}

		if (RotateValue >= 0.0)
		{
			RotateValue = RotateValue/CompRotateValuePlus;
		}
		else
		{
			RotateValue = RotateValue/CompRotateValueMinus;
		}


		MoveValue=Limit(MoveValue);
		RotateValue=Limit(RotateValue);
		AbsMoveValue=Math.abs(MoveValue);
		AbsRotateValue= Math.abs(RotateValue);


		//Theta = atanf(AbsMoveValue/(]]]AbsRotateValue+0.000001));
		//Theta = asinf(0.2);
		Hypot = Math.sqrt(AbsMoveValue*AbsMoveValue+AbsRotateValue*AbsRotateValue);


		OutsideWheels = AbsMoveValue*(AbsMoveValue/Hypot);   
		InsideWheels = AbsMoveValue*( 1- (AbsRotateValue/Hypot));

		//Scale Motor inputs

		if (RotateValue<=0.0)
		{
			LeftMotors = InsideWheels * MoveValue/AbsMoveValue;
			RightMotors = OutsideWheels * MoveValue/AbsMoveValue;		
		}
		else
		{
			LeftMotors = OutsideWheels *  MoveValue/AbsMoveValue;
			RightMotors = InsideWheels * MoveValue/AbsMoveValue;
		}	



		//Command motors
		wpiDrive.setLeftRightMotorOutputs( -1*LeftMotors,-1*RightMotors );

		return true;
	}


	/* search left drive table using binary search
	 Input:   x_value  (rotate)
	          y_value  (move -forward/backward)
	slow_mo  if true, scale output - not being done
	return: left scale_value */
	double left_scale(double rotateValue, double moveValue, DriveMode mode)
	{
		 x_index = 0;
		 y_index = 0;
		double temp_drive = 0;
		int x_idx = 0;
		double absRotate = rotateValue;

		if(mode == DriveMode.Radius)
		{
			absRotate = Math.abs(rotateValue);
		}	

		get_index(moveValue, absRotate, mode);

		if(mode == DriveMode.Rotate)
		{
			x_idx = x_index;
		}
		else
		{
			if(rotateValue < 0)
			{
				x_idx = 32-x_index;
			}
			else
			{
				x_idx = x_index;
			}

		}

		temp_drive = EntropyDriveTable.left_fast_njxy[y_index][x_idx];


		return temp_drive;
	}

    public static void main(String args[]) {
	     System.out.println("Constructor");
	     EntropyDrive ed = new EntropyDrive();
	     double ret = ed.left_scale(0, 0, DriveMode.Radius);
	     System.out.println("left value= " + ret);
	     ret = ed.right_scale(0, 0, DriveMode.Radius);
	     System.out.println("right value= " + ret);
	
	
}

	// Code is replicated from RobotDrive class
	double Limit(double num)
	{
		if (num > 1.0)
		{
			return 1.0;
		}
		if (num < -1.0)
		{
			return -1.0;
		}
		return num;
	}



	/* search right drive table using binary search if axis index tables */
	/* Input:   x_value  (rotate)*/
	/*          y_value  (move -forward/backward)*/
	/*          slow_mo  if true, scale output - not being done */
	/* return: left scale_value */
	double right_scale(double rotateValue, double moveValue, DriveMode mode)
	{
		x_index = 0;
		y_index = 0;
		double temp_drive = 0;
		int x_idx = 0;
		double absRotate = rotateValue;

		if(mode == DriveMode.Radius)
		{
			absRotate = Math.abs(rotateValue);
		}

		get_index(moveValue, absRotate, mode);

		temp_drive = EntropyDriveTable.left_fast_njxy[y_index][32-x_index];

		if(mode == DriveMode.Rotate)
		{
			x_idx = 32-x_index;
		}
		else
		{
			if(rotateValue < 0)
			{
				x_idx = x_index;
			}
			else
			{
				x_idx = 32-x_index;
			}
		}

		temp_drive = EntropyDriveTable.left_fast_njxy[y_index][x_idx];


		return temp_drive;
	}

	boolean range(double x, double y, double z) 
	{  
		return (((y <= x) && (x <= z)) || ((y >= x) && (x >= z)));
	}




	double drive_table_limit(double x, double max, double min)
	{
		if(x > max)
		{
			return max;
		}
		else if(x < min)
		{
			return min;
		}
		else
		{
			return x;
		}
	}


	void get_index(double moveValue, double rotateValue, DriveMode mode)
	{
		double rotate = 0;
		double move = 0;
		double minRotate = 0;
		double maxRotate = 0;
		final double[] arrayPtr;
        int arrayLength = 0;
		double diff1 = 0;
		double diff2 = 0;

		if(mode == DriveMode.Radius)
		{
			arrayPtr = EntropyDriveTable.left_lookup_radius;
			arrayLength = EntropyDriveTable.left_lookup_radius.length;
			minRotate = arrayPtr[16];
			maxRotate = arrayPtr[15];
		}
		else /*Rotate*/
		{
			arrayPtr = EntropyDriveTable.left_lookupx;
			arrayLength = EntropyDriveTable.left_lookupx.length;
			minRotate = arrayPtr[0];
			maxRotate = arrayPtr[arrayLength-1];
		}

		rotate = drive_table_limit(rotateValue, maxRotate, minRotate);

		for(int i = 0; i < arrayLength; i++) 
		{
			if(i+1 >= arrayLength || range(rotate, arrayPtr[i], arrayPtr[i+1]))
			{
				//Assume match found
				if((i + 1) >= arrayLength)
				{
					x_index = i;	
				}
				else
				{
					diff1 = Math.abs(rotate - arrayPtr[i]);
					diff2 = Math.abs(rotate - arrayPtr[i+1]);

					if(diff1 < diff2)
					{
						x_index = i;
					}
					else
					{
						x_index = i + 1;
					}
				}
				break;
			}
		}

		arrayLength = EntropyDriveTable.left_lookupy.length;
		move = drive_table_limit(moveValue, EntropyDriveTable.left_lookupy[32], EntropyDriveTable.left_lookupy[0]);

		for( int i = 0; i < arrayLength; i++) 
		{
			if(i+1 >= arrayLength || range(move, EntropyDriveTable.left_lookupy[i], EntropyDriveTable.left_lookupy[i+1]))
			{
				//Assume match found
				if((i + 1) >= arrayLength)
				{
					y_index = i;	
				}
				else
				{
					diff1 = Math.abs(move - EntropyDriveTable.left_lookupy[i]);
					diff2 = Math.abs(move - EntropyDriveTable.left_lookupy[i+1]);

					if(diff1 < diff2)
					{
						y_index = i;
					}
					else
					{
						y_index = i + 1;
					}
				}
				break;
			}
		}
	}

	double addDeadZone (double Value)
	{
		if (Value < DEAD_ZONE_MAX){
			if (Value > -DEAD_ZONE_MAX){
				Value = 0;
			}
		}	
		return Value;
	}


	double moveValueDampen (double moveValue)
	{

		double previousValue = 0;

		if (((moveValue - previousValue) > -0.1 && (moveValue - previousValue) < 0.1) && moveValue == 0){
			previousValue = 0;
			moveValue = 0;
		}	 
		else if(moveValue > previousValue){

			moveValue = previousValue + dampValue;
		}	 

		else if (moveValue < previousValue){
			moveValue = previousValue - dampValue;
		}


		previousValue = moveValue;
		return moveValue;	


	}
	
	/** %%%

	void DriveTrainTest( ) 
	{ 

		CANJaguar * Motors[4] = {MotorDriveLeft1, MotorDriveLeft2, MotorDriveRight1, MotorDriveRight2 };
		char * Motors_Names[4] = {"MotorLeft1", "MotorLeft2", "MotorRight1", "MotorRight2" };
		int Motors_Values[4] = { IODefinitions::MOTOR_DRIVE_LEFT_1,
				IODefinitions::MOTOR_DRIVE_LEFT_2,
				IODefinitions::MOTOR_DRIVE_RIGHT_1,
				IODefinitions::MOTOR_DRIVE_RIGHT_2
		};

		UINT8 syncGroup = 0x80; 
		for ( int i = 0; i < 4; i++ )
		{
			DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line1,"Motor: %s",
					 Motors_Names[i] );
			DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line2,"Jaguar: %d", Motors_Values[i]);
			DriverStationLCD::GetInstance()->UpdateLCD();
			Motors[i]->Set( 0.6 ,syncGroup);
			CANJaguar::UpdateSyncGroup(syncGroup);
			Wait( 5 );
			Motors[i]->Set( 0.0 , syncGroup);
			CANJaguar::UpdateSyncGroup(syncGroup);
			Wait( 2 );
			DisplayEncodersTestDSLine5Line6();
		}
	}

	void InitEncoderTest()
	{
		m_leftEncoderTest = new Encoder(1, 1, 1, 2, false, Encoder::k4X);
		m_rightEncoderTest = new Encoder(1, 3, 1, 4, true, Encoder::k4X);
			 
		m_leftEncoderTest->SetDistancePerPulse(PULSE_RATIO / 12.0);
		m_leftEncoderTest->SetPIDSourceParameter(Encoder::kRate);
		m_rightEncoderTest->SetDistancePerPulse(PULSE_RATIO / 12.0);
		m_rightEncoderTest->SetPIDSourceParameter(Encoder::kRate);
								 
		m_leftEncoderTest->Start();
		m_rightEncoderTest->Start();

	}
		
		
	void DisplayEncodersTestDSLine5Line6()
	{
		  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line5, "Left Dist: %f", m_leftEncoderTest->GetDistance());
		  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line6, "Right Dist: %f", m_rightEncoderTest->GetDistance());
		  DriverStationLCD::GetInstance()->UpdateLCD();
	}
*/
}
