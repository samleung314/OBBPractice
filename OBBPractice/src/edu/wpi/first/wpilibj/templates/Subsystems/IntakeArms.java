
package edu.wpi.first.wpilibj.templates.Subsystems;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class IntakeArms {
    
    private final Solenoid armsUp = new Solenoid(Constants.ARMSUP_SOL);
    private final Solenoid armDown = new Solenoid(Constants.ARMSDOWN_SOL);

    public IntakeArms()
    {
        
    }
    
    public void armsUp()
    {
        armDown.set(true);
        armsUp.set(false);
    }
    
    public void armsDown()
    {
        armDown.set(false);
        armsUp.set(true);
    }
}
