/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData;
import edu.wpi.first.wpilibj.Jaguar;


/**
 *
 * @author Giang
 */
public class Wheel implements SmartDashboardData {

    private CANJaguar driveJaguar;
    //public Jaguar driveJaguar;
    private Victor turnVictor;
    private boolean alive;
    public NetworkTable table;
public static int numberOfretry=10;
    public Wheel(int turnchannel, int drivechannel) {
        for(int a=0;a<numberOfretry;a++)
        {try {
            driveJaguar = new CANJaguar(drivechannel, CANJaguar.ControlMode.kPercentVbus);
            driveJaguar.enableControl();
            break;
       //driveJaguar.setExpiration(2500000.0);
            
        } catch (CANTimeoutException ex) {
            if(a==numberOfretry)
                driveJaguar=null;
        }
        }
        
            turnVictor = new Victor(turnchannel);
        
    }

    public void setWheel(double turnRate, double driveRate) {
        /*turnVictor.Feed();
        alive=turnVictor.isAlive();
        turnVictor.set(.25);
        double foo=turnVictor.get();
        for(int a=0;a<numberOfretry;a++)
        { try {
            if(driveJaguar!=null){
                driveJaguar.setX(driveRate);
                break;}
        } catch (CANTimeoutException ex) {
            int b=3;
        }}*/
        

    }

    public NetworkTable getTable() {
        if (table == null) {
            table = new NetworkTable();
        }
       /* try {
            table.putInt("Jaguar Faults", driveJaguar.getFaults());
        } catch (Exception exception) {
            table.putString("FaultException", exception.toString());
        }*/
        /*try {
            table.putBoolean("Jaguar Forward Limit", driveJaguar.getForwardLimitOK());
        } catch (Exception exception) {
            table.putString("ForwardException", exception.toString());
        }*/
        
       /* try {
            table.putBoolean("Jaguar Reverse Limit", driveJaguar.getReverseLimitOK());
        } catch (Exception exception) {
            table.putString("ReverseException", exception.toString());
        }*/
        try {
            table.putDouble("Jaguar Speed", driveJaguar.getSpeed());
        } catch (Exception exception) {
            table.putString("SpeedException", exception.toString());
        }
        // Victor Values
        try {
            table.putDouble("Victor Speed", turnVictor.getSpeed());
        } catch (Exception exception) {
            table.putString("VSpeed Exception", exception.toString());
        }
        try {
            table.putInt("Victor Channel", turnVictor.getChannel());
        } catch (Exception exception) {
            table.putString("VChannel Exception", exception.toString());
        }
        return table;
    }

    public String getType() {
        return "Wheel";
    }
}
