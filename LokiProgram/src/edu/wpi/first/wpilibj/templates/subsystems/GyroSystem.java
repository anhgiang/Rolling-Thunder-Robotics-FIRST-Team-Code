package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableGyro;

/**
 * TODO: Flesh out system, add info get and set functions
 * TODO Investigate updateAngle
 */
public class GyroSystem extends Subsystem {

    double gyroAngle = 0.0;
    public SendableGyro sendableGyro;
    private NetworkTable table;

    public double getAngle() {
        return gyroAngle;
    }

    public void updateAngle(double angle) {
        gyroAngle += angle; // NOTE - Why not use reset?
    }

    public NetworkTable getTable() {
        if (table == null) {
        table = super.getTable();
        }
        //table.putSubTable("Gyro", sendableGyro.getTable());*/
        return table;
    }

    ;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here. This should probably be something that sends the gyro values to the dashboard
        //setDefaultCommand(new MySpecialCommand());
    }
}
