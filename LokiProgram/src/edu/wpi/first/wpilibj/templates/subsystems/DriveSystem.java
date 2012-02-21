package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CANJaguar;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.Wheel;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SendableGyro;

/*
 * The DriveSystem class represents the robot's physical drive system. Each
 * wheel is comprised of two motors
 * 
 * TODO: Drive system needs get/set or functions for wheel manipulation
 * TODO: Wheel name simplification?
 * TODO: Variable Cleanup
 */
public class DriveSystem extends Subsystem {

    //Gyro driveGyro;
    final Victor frTurn = new Victor(2);
    final Victor flTurn = new Victor(1);
    final Victor blTurn = new Victor(4);
    final Victor brTurn = new Victor(3);
    CANJaguar frCan;
    CANJaguar flCan;
    CANJaguar blCan;
    CANJaguar brCan;
    final AnalogChannel frPot = new AnalogChannel(2);
    final AnalogChannel flPot = new AnalogChannel(1);
    final AnalogChannel blPot = new AnalogChannel(4);
    final AnalogChannel brPot = new AnalogChannel(3);
    public double[] magnitude = new double[4];
    public double[] angle = new double[4];
    public double[] currentAngle = new double[4];
    double L = 19;
    double W = 28.0;
    double R = Math.sqrt(MathUtils.pow(L, 2.0) + MathUtils.pow(W, 2.0));
    public NetworkTable table;

    public DriveSystem() {

        try {
                flCan = new CANJaguar(2, CANJaguar.ControlMode.kPercentVbus);
            frCan = new CANJaguar(3, CANJaguar.ControlMode.kPercentVbus);
            brCan = new CANJaguar(4, CANJaguar.ControlMode.kPercentVbus);
            blCan = new CANJaguar(5, CANJaguar.ControlMode.kPercentVbus);

            flCan.enableControl();
            frCan.enableControl();
            brCan.enableControl();
            blCan.enableControl();
        } catch (CANTimeoutException a) {
           // driveGyro = new Gyro(7);
        }
    }

    public void setWheel(double FWD, double STR, double RCW) {
        if (STR > -0.1 && STR < 0.1) {
            STR = 0.0;
        }
        if (FWD > -0.1 && FWD < 0.1) {
            FWD = 0.0;
        }
        if (RCW > -0.2 && RCW < 0.2) {
            RCW = 0.0;
        }
        // convert to field-centric...
//       double temp = FWD * Math.cos(driveGyro.getAngle()) + STR * Math.sin(driveGyro.getAngle());
//       STR = -FWD * Math.sin(driveGyro.getAngle()) + STR * Math.cos(driveGyro.getAngle());
//       FWD = temp;
        double A = STR - RCW * (L / R);
        double B = STR + RCW * (L / R);
        double C = FWD - RCW * (W / R);
        double D = FWD + RCW * (W / R);
        //temp speed for each wheel from -1 to 1
        magnitude[0] = Math.sqrt(MathUtils.pow(B, 2.0) + MathUtils.pow(C, 2.0));
        magnitude[1] = Math.sqrt(MathUtils.pow(B, 2.0) + MathUtils.pow(D, 2.0));
        magnitude[2] = Math.sqrt(MathUtils.pow(A, 2.0) + MathUtils.pow(D, 2.0));
        magnitude[3] = Math.sqrt(MathUtils.pow(A, 2.0) + MathUtils.pow(C, 2.0));
        // temp angle for each wheel from -180 to 180 clockwise
        angle[0] = MathUtils.atan2(B, C) * 180 / Math.PI;
        angle[1] = MathUtils.atan2(B, D) * 180 / Math.PI;
        angle[2] = MathUtils.atan2(A, D) * 180 / Math.PI;
        angle[3] = MathUtils.atan2(A, C) * 180 / Math.PI;

        for (int f = 0; f < 4; f++) {
            if (angle[f] < 0) {
                angle[f] = angle[f] + 360;
            }
        }
        //normalize the speed...
        double max = magnitude[0];
        if (magnitude[1] > max) {
            max = magnitude[1];
        }
        if (magnitude[2] > max) {
            max = magnitude[2];
        }
        if (magnitude[3] > max) {
            max = magnitude[3];
        }
        if (max > 1) {
            magnitude[0] /= max;
            magnitude[1] /= max;
            magnitude[2] /= max;
            magnitude[3] /= max;
        }
        double rightLimit = 100;
        double leftLimit = 260;
        for (int i = 0; i < 4; i++) {
            if (angle[i] > 90 && angle[i] < 180) {
                angle[i] = angle[i] + 180;
                magnitude[i] = magnitude[i] * -1;

            } else if (angle[i] >= 180 && angle[i] < 270) {
                angle[i] = angle[i] - 180;
                magnitude[i] = magnitude[i] * -1;
            }

        }
        currentAngle[0] = (int) (frPot.getAverageVoltage() * 72);
        currentAngle[1] = (int) (flPot.getAverageVoltage() * 72);
        currentAngle[2] = (int) (blPot.getAverageVoltage() * 72);
        currentAngle[3] = (int) (brPot.getAverageVoltage() * 72);

        frTurn.set(swerveTurn(currentAngle[0], angle[0]));
        flTurn.set(swerveTurn(currentAngle[1], angle[1]));
        blTurn.set(swerveTurn(currentAngle[2], angle[2]));
        brTurn.set(swerveTurn(currentAngle[3], angle[3]));
        try {

            frCan.setX(magnitude[0]);
            flCan.setX(magnitude[1]);
            blCan.setX(magnitude[2]);
            brCan.setX(magnitude[3]);

        } catch (CANTimeoutException ex) {
            int b = 3;
        }

    }

    public double swerveTurn(double currentAngle, double targetAngle) {
        double angleDifference = targetAngle - currentAngle;
        int reverseSpeed = 1;
        if (angleDifference > 180 || angleDifference < -180) {
            reverseSpeed = -1;
        }

        if (angleDifference > 0) {
            if (angleDifference > 30) {
                return 0.25 * reverseSpeed;
            } else if ((angleDifference < 30) && (angleDifference > 20)) {
                return 0.1 * reverseSpeed;
            } else if ((angleDifference < 20) && (angleDifference > 10)) {
                return 0.05 * reverseSpeed;
            } else {
                return 0;
            }
        } else {
            angleDifference = angleDifference * -1;
            if (angleDifference > 30) {
                return -0.25 * reverseSpeed;
            } else if ((angleDifference < 30) && (angleDifference > 20)) {
                return -0.1 * reverseSpeed;
            } else if ((angleDifference < 20) && (angleDifference > 10)) {
                return -0.05 * reverseSpeed;
            } else {
                return 0;
            }
        }
//        if (targetAngle > (currentAngle + 90) && (targetAngle <= (currentAngle + 180))) {
//            return 1 * turnSpeed;
//        } else if (targetAngle > (currentAngle + 45) && (targetAngle <= (currentAngle + 90))) {
//            return .75 * turnSpeed;
//        } else if (targetAngle > (currentAngle + 30) && (targetAngle <= (currentAngle + 45))) {
//            return .5 * turnSpeed;
//        } else if (targetAngle > (currentAngle + 2) && (targetAngle <= (currentAngle + 30))) {
//            return .25 * turnSpeed;
//        } else if (targetAngle < (currentAngle - 90) && (targetAngle >= (currentAngle - 180))) {
//            return -1 * turnSpeed;
//        } else if (targetAngle < (currentAngle - 45) && (targetAngle >= (currentAngle - 90))) {
//            return -.75 * turnSpeed;
//        } else if (targetAngle < (currentAngle - 30) && (targetAngle >= (currentAngle - 45))) {
//            return -.5 * turnSpeed;
//        } else if (targetAngle < (currentAngle - 2) && (targetAngle >= (currentAngle - 30))) {
//            return -.25 * turnSpeed;
//        } else if ((targetAngle > (currentAngle + 2) || targetAngle > currentAngle - 2)) {
//            return 0 * turnSpeed;
//        } else {
//            return 0;
//        }
    }

    public NetworkTable getTable() {
        // make a table
        if (table == null) {
            table = super.getTable();
        }
//        SmartDashboard.putData(frontleftWheel);
//        SmartDashboard.putDouble("Front Left Encoder", getCurrentAngle(1));
//        SmartDashboard.putData(frontrightWheel);
//        SmartDashboard.putDouble("Front Right Encoder", getCurrentAngle(0));
//        SmartDashboard.putData(backleftWheel);
//        SmartDashboard.putDouble("Back Left Encoder", getCurrentAngle(2));
//        SmartDashboard.putData(backrightWheel);
//        SmartDashboard.putDouble("Back Right Encoder", getCurrentAngle(3));
//        
//        
//        SmartDashboard.putData(frontleftWheel);
//        SmartDashboard.putDouble("Front Left Angle", (int)(swerveTurn(getCurrentAngle(1), angle[1])*100)/100);
//        SmartDashboard.putData(frontrightWheel);
//        SmartDashboard.putDouble("Front Right angle", (int)(swerveTurn(getCurrentAngle(0), angle[0])*100)/100);
//        SmartDashboard.putData(backleftWheel);
//        SmartDashboard.putDouble("Back Left angle", (int)(swerveTurn(getCurrentAngle(2), angle[2])*100)/100);
//        SmartDashboard.putData(backrightWheel);
//        SmartDashboard.putDouble("Back Right angle",(int)(swerveTurn(getCurrentAngle(3), angle[3])*100)/100);
        return table;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here. Something that sends current motor speeds to the dashboard?
        //setDefaultCommand(new MySpecialCommand());
    }
}
