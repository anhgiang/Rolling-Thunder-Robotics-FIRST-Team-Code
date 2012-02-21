package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 */
public class MassSystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Victor massVictor;

    public MassSystem() {
        massVictor = new Victor(RobotMap.massSlot);
    }
    
    public void moveMass(double speed) {
        massVictor.set(speed/2);//-1 * speed);
     
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here. Send Mass position?
        //setDefaultCommand(new MySpecialCommand());
    }
}
