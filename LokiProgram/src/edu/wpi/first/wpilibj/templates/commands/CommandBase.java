package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.templates.subsystems.GyroSystem;
import edu.wpi.first.wpilibj.templates.subsystems.LeverSystem;
import edu.wpi.first.wpilibj.templates.subsystems.MassSystem;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static MassSystem massSubsystem = new MassSystem();
    public static LeverSystem leverSubsystem = new LeverSystem();
    public static GyroSystem gyroSubsystem = new GyroSystem();
    //public static DriveSystem driveSubsystem = new DriveSystem();
    protected static Thread networkThread;
    static private DriverStation driverStation;

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        driverStation = DriverStation.getInstance();
        // Show what command your subsystem is running on the SmartDashboard
        new Thread() {

            public void run() {
                while (true) {
                    Timer.delay(.2);
                    //SmartDashboard.putData(Scheduler.getInstance());
                    SmartDashboard.putData(massSubsystem);
                    SmartDashboard.putData(leverSubsystem);
                    SmartDashboard.putData(gyroSubsystem);
                   // SmartDashboard.putData(driveSubsystem);                   
                }
            }
        }.start();

    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
