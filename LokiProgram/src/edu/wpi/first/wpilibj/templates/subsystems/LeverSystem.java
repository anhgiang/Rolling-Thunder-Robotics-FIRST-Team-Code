package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkListener;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 */
public class LeverSystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Victor leverVictor;
    private NetworkTable table;

    public LeverSystem() {
        leverVictor = new Victor(RobotMap.leverSlot); 
    }
    public void moveLever(double speed){
        leverVictor.set(speed/2);//-1*speed);
    }
          


    /*    public NetworkTable getTable() {
    if (table == null) {
    table = super.getTable();
    table.putInt("PWM", (int) leverJaguar.get());
    table.addListener("PWM", new NetworkListener() {
    
    public void valueChanged(String key, Object value) {
    leverJaguar.set(((Integer) value).doubleValue());
    }
    
    public void valueConfirmed(String key, Object value) {
    }
    });
    new Thread() {
    
    public void run() {
    while (true) {
    Timer.delay(.2);
    table.putInt("PWM", (int) leverJaguar.get());
    }
    }
    }.start();
    }
    return table;
    }*/
    public void initDefaultCommand() {
        // Set the default command for a subsystem here. Sends current lever angle to smartdashboard?
        //setDefaultCommand(new MySpecialCommand());
    }
}
