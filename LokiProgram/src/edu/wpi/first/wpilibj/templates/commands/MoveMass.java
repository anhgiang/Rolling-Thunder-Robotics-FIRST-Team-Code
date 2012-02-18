 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
import com.sun.squawk.util.MathUtils;

/**
 *
 * @author Giang
 */
public class MoveMass extends CommandBase {
    
    public MoveMass() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
      requires(massSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
     
     // massSubsystem.moveMass(getMassSpeed(massSubsystem.accelerometer.getAcceleration()));
    }

    protected double getMassSpeed(double acc){
       return MathUtils.asin(acc/9.8)*(180/Math.PI)*-.25;
        
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
