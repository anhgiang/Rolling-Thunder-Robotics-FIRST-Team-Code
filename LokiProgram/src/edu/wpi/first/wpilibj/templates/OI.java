
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    public static final int JOYSTICK_PORT1 = 1;
    public static final int JOYSTICK_PORT2 = 2;
    public static final int JOYSTICK_PORT3 = 3;
    public static final int upLeverPort = 6;
    public static final int downLeverPort = 7;
    
    private Joystick stick1;//drive joystick
    private Joystick stick2;//mass joystick
    private Joystick stick3;//lever joystick
    
    private JoystickButton upLever;
    private JoystickButton downLever;
            

    public OI() {
        stick1 = new Joystick (JOYSTICK_PORT1);
        stick2 = new Joystick (JOYSTICK_PORT2);
        stick3 = new Joystick (JOYSTICK_PORT3);
    }   
    public Joystick getJoystick1() {
        return stick1;
    }
    
    public Joystick getJoystick2() {
        return stick2;
    }
   public Joystick getJoystick3() {
       return stick3;
   }
    
    public JoystickButton getUpLever() {
        return upLever;
    }
    
    public JoystickButton getDownLever() {
        return downLever;
    }
    
}

