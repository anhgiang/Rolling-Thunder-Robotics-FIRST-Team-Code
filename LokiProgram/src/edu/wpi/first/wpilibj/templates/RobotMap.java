package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Joystick;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
   
    //Victors slots #...turn
    public static final int frontrightWheelTurn=1;
    public static final int frontleftWheelTurn=2;
    public static final int backleftWheelTurn=3;
    public static final int backrightWheelTurn=4;
    
    //Canjaguar slots #...drive
    public static final int frontrightWheelDrive=3;
    public static final int frontleftWheelDrive=2;
    public static final int backleftWheelDrive=5;
    public static final int backrightWheelDrive=4;
    
    //Encoder analog channels #...
    public static final int frPot=1;
    public static final int flPot=2;
    public static final int blPot=3;
    public static final int brPot=4;
    public static final Joystick stick1 = new Joystick(1);//joy stick for drivetrain
}
