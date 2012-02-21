/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.DriverCommand;
import edu.wpi.first.wpilibj.templates.commands.MainAutoCommand;
import edu.wpi.first.wpilibj.templates.commands.MoveLever;
import edu.wpi.first.wpilibj.templates.commands.MoveMass;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class LokiRobot extends IterativeRobot {

    Joystick stick1 = new Joystick(1);//drive Joytstick...
    Command autonomousCommand;
    DriveSystem driveSubsystem;
    static Watchdog watchdog = Watchdog.getInstance();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = new MainAutoCommand();
        driveSubsystem = new DriveSystem();
        watchdog.setExpiration(1);
        watchdog.setEnabled(true);

    }

    public void autonomousInit() {

        // schedule the autonomous command (example)
        autonomousCommand.start();
        driveSubsystem.setWheel(0, 1, 0);
    }
//       

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //Scheduler.getInstance().run();
        watchdog.feed();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autonomousCommand.cancel();

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        watchdog.getInstance().feed();
        driveSubsystem.setWheel(stick1.getY(), stick1.getX(), stick1.getTwist());


    }
}
