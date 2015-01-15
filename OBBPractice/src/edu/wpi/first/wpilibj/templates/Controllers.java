
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.Joystick;

public class Controllers {
    
    private final Joystick xBox = new Joystick(Constants.XBOX);
    private final Joystick leftJoy = new Joystick(Constants.LEFT_JOYSTICK);
    private final Joystick rightJoy = new Joystick(Constants.RIGHT_JOYSTICK);
    
    Air air;
    Cradle cradle;
    Drive drive;
    IntakeArms arms;
    
    public Controllers(Air air,IntakeArms arms, Cradle cradle, Drive drive) {
        this.air = air;
        this.arms = arms;
        this.cradle = cradle; 
        this.drive = drive;
    }

    public void xBoxDrive()
    {
        drive.sudoVics(-xBox.getRawAxis(2), xBox.getRawAxis(5));
        
        xBoxShift();
        xBoxArms();
        xBoxCradle();
        xBoxchargeAir();
    }
    
    public void joystickDrive()
    {
            drive.sudoVics(-leftJoy.getY(), rightJoy.getY());

            xBoxShift();
            xBoxArms();
            xBoxCradle();
            xBoxchargeAir();
    }
    
    public void xBoxShift()
     {
         if(xBox.getRawButton(5))//Left Bumper
         {
             drive.highGear();
         }
         else if(xBox.getRawButton(6))//Right Bumper
         {
             drive.lowGear();
         }
     }
     
     public void xBoxArms()
     {
         if(xBox.getRawButton(4))//Y Button
         {
              arms.armsUp();
         }
         else if(xBox.getRawButton(3))//X Button
         {
             arms.armsDown();
         }
     }
     
     public void xBoxCradle()
     {
         if(xBox.getRawButton(1))//A Button
         {
              cradle.cradleDown();
         }
         else if(xBox.getRawButton(2))//B Button
         {
             cradle.cradleUp();
         }
     }
     
     public void xBoxchargeAir()
     {
         if(xBox.getRawButton(8))//Start Button
         {
              air.pressurize();
         }
         else if(xBox.getRawButton(7))//Back Button
         {
             air.stopPressurize();
         }
     }
}

/* XBOX KEY MAPPINGS

1: A
2: B
3: X
4: Y
5: Left Bumper
6: Right Bumper
7: Back
8: Start
9: Left Joystick
10: Right Joystick

The axis on the controller follow this mapping
(all output is between -1 and 1)

    1: Left Stick X Axis
    -Left:Negative ; Right: Positive

    2: Left Stick Y Axis
    -Up: Negative ; Down: Positive

    3: Triggers
    -Left: Positive ; Right: Negative

    4: Right Stick X Axis
    -Left: Negative ; Right: Positive

    5: Right Stick Y Axis
    -Up: Negative ; Down: Positive

    6: Directional Pad (Not recommended, buggy)
*/
