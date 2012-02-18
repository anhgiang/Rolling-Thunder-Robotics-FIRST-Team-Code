package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.Wheel;
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
    public double[] lastAngle = new double[4];//last current angle...
    // double lastfrDrive, lastflDrive, lastrlDrive,lastrrDrive;//last speed? need this??
    //represent the desired speed and direction of travel
    //[0]= frontLeft, [1]=frontright,[2]=Rearright,[3]=Rearleft
    public NetworkTable table;
    public CANJaguar frlet;

    public DriveSystem() {

        frontrightWheel = new Wheel(RobotMap.frontrightWheelTurn, RobotMap.frontrightWheelDrive);
        frontleftWheel = new Wheel(RobotMap.frontleftWheelTurn, RobotMap.frontleftWheelDrive);
        backleftWheel = new Wheel(RobotMap.backleftWheelTurn, RobotMap.backleftWheelDrive);
        backrightWheel = new Wheel(RobotMap.backrightWheelTurn, RobotMap.backrightWheelDrive);
        /* frlet = new CANJaguar(2);
        } catch (CANTimeoutException ex) {
        int foo=2;
        }*/
        for (int i = 0; i <= 3; i++) {
            lastAngle[i] = 0;
        }
    }

    public void setWheel() {

        frontrightWheel.setWheel(angle[0], magnitude[0]);
        frontleftWheel.setWheel(1, magnitude[1]);
        backleftWheel.setWheel(angle[2], magnitude[2]);
        backrightWheel.setWheel(angle[3], magnitude[3]);
        //  frlet.setX(.5);

    }

    public NetworkTable getTable() {
        // make a table
        if (table == null) {
            table = super.getTable();
        }
        table.putSubTable("Front Left", frontleftWheel.getTable());
        table.putSubTable("Front Right", frontrightWheel.getTable());
        table.putSubTable("Back Left", backleftWheel.getTable());
        table.putSubTable("Back Right", backrightWheel.getTable());
        return table;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here. Something that sends current motor speeds to the dashboard?
        //setDefaultCommand(new MySpecialCommand());
    }
}
