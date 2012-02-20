package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CANJaguar;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.Wheel;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;

/*
 * The DriveSystem class represents the robot's physical drive system. Each
 * wheel is comprised of two motors
 * 
 * TODO: Drive system needs get/set or functions for wheel manipulation
 * TODO: Wheel name simplification?
 * TODO: Variable Cleanup
 */
public class DriveSystem extends Subsystem {

    public Wheel frontleftWheel;
    public Wheel frontrightWheel;
    public Wheel backleftWheel;
    public Wheel backrightWheel;
    public double[] magnitude = new double[4];
    public double[] angle = new double[4];
    double turnSpeed;
    //public double[] lastAngle = new double[4];
    public AnalogChannel[] EncoderChannels = new AnalogChannel[4];
    //last current angle...
    // double lastfrDrive, lastflDrive, lastrlDrive,lastrrDrive;//last speed? need this??
    //represent the desired speed and direction of travel
    //[0]= frontLeft, [1]=frontright,[2]=Rearright,[3]=Rearleft
    public NetworkTable table;
    public CANJaguar frlet;

    public DriveSystem() {
        EncoderChannels[0] = new AnalogChannel(RobotMap.frPot);
        EncoderChannels[1] = new AnalogChannel(RobotMap.flPot);
        EncoderChannels[2] = new AnalogChannel(RobotMap.blPot);
        EncoderChannels[3] = new AnalogChannel(RobotMap.brPot);
        frontrightWheel = new Wheel(RobotMap.frontrightWheelTurn, RobotMap.frontrightWheelDrive);
        frontleftWheel = new Wheel(RobotMap.frontleftWheelTurn, RobotMap.frontleftWheelDrive);
        backleftWheel = new Wheel(RobotMap.backleftWheelTurn, RobotMap.backleftWheelDrive);
        backrightWheel = new Wheel(RobotMap.backrightWheelTurn, RobotMap.backrightWheelDrive);
    }

    public double getCurrentAngle(int analogChannel) {
        return ((int) (EncoderChannels[analogChannel].getVoltage() * 109 * 100)) / 100.0;
    }

    public void setWheel(double turnSpeed) {
        this.turnSpeed = turnSpeed;
        frontrightWheel.setWheel(swerveTurn(getCurrentAngle(0), angle[0]), magnitude[0]);
        frontleftWheel.setWheel(swerveTurn(getCurrentAngle(1), angle[1]), magnitude[1]);
        backleftWheel.setWheel(swerveTurn(getCurrentAngle(2), angle[2]), magnitude[2]);
        backrightWheel.setWheel(swerveTurn(getCurrentAngle(3), angle[3]), magnitude[3]);

    }

    public double swerveTurn(double currentAngle, double targetAngle) {
        if (targetAngle > (currentAngle + 90) && (targetAngle <= (currentAngle + 180))) {
            return -1 * turnSpeed;
        } else if (targetAngle > (currentAngle + 45) && (targetAngle <= (currentAngle + 90))) {
            return -.75 * turnSpeed;
        } else if (targetAngle > (currentAngle + 30) && (targetAngle <= (currentAngle + 45))) {
            return -.5 * turnSpeed;
        } else if (targetAngle > (currentAngle + 2) && (targetAngle <= (currentAngle + 30))) {
            return -.25 * turnSpeed;
        } else if (targetAngle < (currentAngle - 90) && (targetAngle >= (currentAngle - 180))) {
            return 1 * turnSpeed;
        } else if (targetAngle < (currentAngle - 45) && (targetAngle >= (currentAngle - 90))) {
            return .75 * turnSpeed;
        } else if (targetAngle < (currentAngle - 30) && (targetAngle >= (currentAngle - 45))) {
            return .5 * turnSpeed;
        } else if (targetAngle < (currentAngle - 2) && (targetAngle >= (currentAngle - 30))) {
            return .25 * turnSpeed;
        } else if ((targetAngle > (currentAngle + 2) || targetAngle > currentAngle - 2)) {
            return 0 * turnSpeed;
        } else {
            return 0;
        }
    }

    public NetworkTable getTable() {
        // make a table
        if (table == null) {
            table = super.getTable();
        }
        SmartDashboard.putData(frontleftWheel);
        SmartDashboard.putDouble("Front Left Encoder", getCurrentAngle(1));
        SmartDashboard.putData(frontrightWheel);
        SmartDashboard.putDouble("Front Right Encoder", getCurrentAngle(0));
        SmartDashboard.putData(backleftWheel);
        SmartDashboard.putDouble("Back Left Encoder", getCurrentAngle(2));
        SmartDashboard.putData(backrightWheel);
        SmartDashboard.putDouble("Back Right Encoder", getCurrentAngle(3));
        return table;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here. Something that sends current motor speeds to the dashboard?
        //setDefaultCommand(new MySpecialCommand());
    }
}
