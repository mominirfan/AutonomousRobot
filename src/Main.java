import rxtxrobot.*;
import java.lang.Math; 

public class Main {
	//still need to add 160 points back to our budget
	private static RXTXRobot r; 
	public static final int PING_PIN  = 8; 
	public static final int SERVO_1_PIN = 9; 
	public static final int THERM_PIN = 1; 
	public static final int BUMP_PIN = 2;
	public static final int ANEM_PIN1 = 3; 
	public static final int ANEM_PIN2 = 4; 
	public static final int ENCODER_ONe = 10; 
	public static final int ENCODER_TWO = 11; 
	
	public static void main(String[] args) 
	{
		r = new ArduinoUno(); 
		r.setPort("COM3");
		r.setVerbose(true); // Turn on debugging messages 
		r.connect();
		
		r.refreshAnalogPins();
		r.refreshDigitalPins(); 
		
		
		System.out.println(r.getAvailableDigitalPins()); 
		//r.attachServo(RXTXRobot.SERVO1, SERVO_1_PIN);
		//r.moveServo(RXTXRobot.SERVO1, 160);
		//sprinkler();
		//System.out.println(getThermistorReading(THERM_PIN));
		//System.out.println(getAnemReading());
		//ping(); 
		//runUntillBump(); 
		//moveForSeconds(5);
		turn(2.0, -400, -250); 
		//ping(); 
		//obstacleRun();
		r.close();

	}

	/** Reads the voltage coming into analog pin 0 ten times
	takes the average, then returns the result.
	@return the average of the 10 results 
*/ 
	

public static void run()
{
	r.moveServo(RXTXRobot.SERVO1, 30);
	ping();
	System.out.println(getThermistorReading(THERM_PIN));
	moveForSeconds(4.80); 
	r.sleep(2000);
	runUntillBump();
	
}

public static void obstacleRun()
{
	r.refreshAnalogPins();
	boolean reachedTarget = false;
	int targetVal = 50;
	r.resetEncodedMotorPosition(RXTXRobot.MOTOR1);
	int currVal = r.getEncodedMotorPosition(RXTXRobot.MOTOR1);
	int deltaVal = 0;
	while(!reachedTarget)
	{
		r.refreshDigitalPins();
		if(ping() >= 25)
		{
			r.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -340, 0);
			System.out.println(r.getPing(PING_PIN));
			currVal = r.getEncodedMotorPosition(RXTXRobot.MOTOR1) - deltaVal;
			if (currVal == targetVal)
				reachedTarget = true; 
		}
		else
		{
			r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
			turn(2.0, -400, -250); 
			moveForSeconds(1.5);
			turn(2.0, 400, 250);
			moveForSeconds(1.5);
			turn(2.0, 400, 250);
			moveForSeconds(1.5);
			turn(2.0, 400, 250); 
			deltaVal = r.getEncodedMotorPosition(RXTXRobot.MOTOR1) - currVal;
			if (currVal == targetVal)
				reachedTarget = true; 
		}
	}
	r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
	System.out.println("You reached your target!");
	
}
public static void turn(double seconds, int speed1, int speed2)
{
	r.runMotor(RXTXRobot.MOTOR1, speed1, RXTXRobot.MOTOR2, speed2, 0);
	r.sleep((int)(seconds * 1000));
	r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
	
}

public static void sprinkler()
{
	int k = 0; 
	while(r.getAnalogPin(2).getValue() >= 800)
	{
		k += 10; 
		r.refreshAnalogPins(); 
		if (k == 180)
			k =0;
		r.moveServo(RXTXRobot.SERVO1,k);
	}
	
}
public static double getThermistorReading(int pin) //for the green thermistor
{
	double sum = 0;
	double readingCount = 10;
		    
	//Read the analog pin values ten times, adding to sum each time
	for (int i = 0; i < readingCount; i++) 
	{
		//Refresh the analog pins so we get new readings
		r.refreshAnalogPins();
		double reading = r.getAnalogPin(pin).getValue();
		sum += reading;
	}
	//y = 791.902 - 9.551x
	/*
	 * (y- 791.902 )/ - 9.551 = x; 
	 */
	//Return the average reading
	return ((sum / readingCount) - 796.902) / (-9.551);
}

public static double getThermistorReading2(int pin) //for the green thermistor
{
	double sum = 0;
	double readingCount = 10;
		    
	//Read the analog pin values ten times, adding to sum each time
	for (int i = 0; i < readingCount; i++) 
	{
		//Refresh the analog pins so we get new readings
		r.refreshAnalogPins();
		double reading = r.getAnalogPin(pin).getValue();
		sum += reading;
	}
	//y = 791.902 - 9.551x
	/*
	 * (y- 791.902 )/ - 9.551 = x; 
	 */
	//Return the average reading
	return ((sum / readingCount) - 750.902) / (-9.556);
}

public static double ping()
{
	return r.getPing(PING_PIN); 
}
public static void moveForSeconds(double seconds)
{
	r.runMotor(RXTXRobot.MOTOR1, -350, RXTXRobot.MOTOR2, 500, 0);
	r.sleep((int)(seconds * 1000)) ;
	r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
}

public static void servo(int servo, int degree)
{
	r.moveServo(servo, degree); 
	r.moveServo(servo, -1 * degree);
}

public static double getDistance()
{
	double sum = 0;
	double readingCount = 10;
		    
	//Read the analog pin values ten times, adding to sum each time
	for (int i = 0; i < readingCount; i++) 
	{
		//Refresh the analog pins so we get new readings
		r.refreshDigitalPins();
		double reading = r.getDigitalPin(PING_PIN).getValue();
		sum += reading;
		System.out.println(reading); 
	}
	
	double avg = sum / readingCount; 
	return avg;
	
}

public static void runUntillBump()
{
	r.refreshAnalogPins();
	while(r.getAnalogPin(2).getValue() >= 800)
	{
		r.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -250, 0);
		r.refreshAnalogPins();
		System.out.println(r.getAnalogPin(2).getValue()); // Run both motors forward indefinitely 
	}
	r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
	System.out.println("Bump Works");
}

public static double getAnemReading()
{
	double sheilded = getThermistorReading2(ANEM_PIN2); 
	double open = getThermistorReading2(THERM_PIN); 
	double diff = sheilded - open;
	double windSpeed = diff; //prototype value is the difference right now 
	System.out.println(open); 
	return Math.abs(windSpeed) / 3; 
}


}
