     /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

//import edu.wpi.first.wpilibj.AnalogChannel;
//import edu.wpi.first.wpilibj.AnalogModule;
//import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.can.CANTimeoutException;
//import edu.wpi.first.wpilibj.Gyro;
import com.sun.squawk.util.MathUtils;
//import edu.wpi.first.wpilibj.templates.OI;

/**
 *
 * @author Giang
 */
public class DriverCommand extends CommandBase {
    //joystick and it's values...

    //rotation motor controller.
    //Victor frTurn = new Victor(1);
    //Victor flTurn = new Victor(2);
    //Victor rlTurn = new Victor(3);
    //Victor rrTurn = new Victor(4);
    //speed controller.
    //CANJaguar frAcc;
    //CANJaguar flAcc;
    //CANJaguar rlAcc;
    //CANJaguar rrAcc;
    //represent the desired speed and direction of travel
    //[0]= frontLeft, [1]=frontright,[2]=Rearright,[3]=Rearleft
    //double[] magnitude = new double[4];
    //double[] angle = new double[4];
    // double lastfrDrive, lastflDrive, lastrlDrive,lastrrDrive;//last speed? need this??
    //double[] lastAngle = new double[4];//last current angle...
    // frAngle,flAngle,rlAngle,rrAngle;//actual angles, not encoder values
    //constants...
    double L = 9.5;
    double W = 14.0;
    double R = Math.sqrt(MathUtils.pow(L, 2.0) + MathUtils.pow(W, 2.0));

    public DriverCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        swerveWithRotation(oi.getJoystick1().getX(), oi.getJoystick1().getY(), oi.getJoystick1().getTwist());
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

    protected void swerveWithRotation(double STR, double FWD, double RCW) {
        //convert to field-centric...
        double temp = FWD * Math.cos(gyroSubsystem.getDriveAngle()) + STR * Math.sin(gyroSubsystem.getDriveAngle());
        STR = -FWD * Math.sin(gyroSubsystem.getDriveAngle()) + STR * Math.cos(gyroSubsystem.getDriveAngle());
        FWD = temp;
        //Vector components...
        double A = STR - RCW * (L / R);
        double B = STR + RCW * (L / R);
        double C = FWD - RCW * (W / R);
        double D = FWD + RCW * (W / R);

        //temp speed for each wheel from -1 to 1
        driveSubsystem.magnitude[0] = Math.sqrt(MathUtils.pow(B, 2.0) + MathUtils.pow(C, 2.0));
        driveSubsystem.magnitude[1] = Math.sqrt(MathUtils.pow(B, 2.0) + MathUtils.pow(D, 2.0));
        driveSubsystem.magnitude[2] = Math.sqrt(MathUtils.pow(A, 2.0) + MathUtils.pow(D, 2.0));
        driveSubsystem.magnitude[3] = Math.sqrt(MathUtils.pow(A, 2.0) + MathUtils.pow(C, 2.0));
        // temp angle for each wheel from -180 to 180 clockwise
        driveSubsystem.angle[0] = MathUtils.atan2(B, C) * 180 / Math.PI;
        driveSubsystem.angle[1] = MathUtils.atan2(B, D) * 180 / Math.PI;
        driveSubsystem.angle[2] = MathUtils.atan2(A, D) * 180 / Math.PI;
        driveSubsystem.angle[3] = MathUtils.atan2(A, C) * 180 / Math.PI;

        //normalize the speed...
        double max = driveSubsystem.magnitude[0];
        if (driveSubsystem.magnitude[1] > max) {
            max = driveSubsystem.magnitude[1];
        }
        if (driveSubsystem.magnitude[2] > max) {
            max = driveSubsystem.magnitude[2];
        }
        if (driveSubsystem.magnitude[3] > max) {
            max = driveSubsystem.magnitude[3];
        }
        if (max > 1) {
            driveSubsystem.magnitude[0] /= max;
            driveSubsystem.magnitude[1] /= max;
            driveSubsystem.magnitude[2] /= max;
            driveSubsystem.magnitude[3] /= max;
        }
        adjustSpeedAndAngle();


        //set motors speed for each wheel...
        driveSubsystem.setWheel(oi.getJoystick1().getTwist());

    }
    //reverse speed rather than turn >180
    //Cannot turn more than 180 on map...

    protected void adjustSpeedAndAngle() {
        for (int i = 0; i <= 3; i++) {
            /* driveSubsystem.magnitude[i] = driveSubsystem.magnitude[i] * MathUtils.pow(-1, (int) (driveSubsystem.angle[i] / 180.0));
            driveSubsystem.angle[i] = (driveSubsystem.angle[i] % 180.0);*/
            //normalize the angle...from 0 to 360 clockwise
            for (int f = 0; f < 4; f++) {
                if (driveSubsystem.angle[f] < 0) {
                    driveSubsystem.angle[f] = driveSubsystem.angle[f] + 360;
                }
            }
            if (driveSubsystem.angle[i] > 180 && driveSubsystem.angle[i] < 360) {
                driveSubsystem.angle[i] -= 180;
                driveSubsystem.magnitude[i] *= -1;
            }
            /* else if (driveSubsystem.angle[i] > 180) {
            driveSubsystem.angle[i] = driveSubsystem.angle[i] - 180.0;
            driveSubsystem.magnitude[i] *= -1;
            }*/
        }

    }
}
