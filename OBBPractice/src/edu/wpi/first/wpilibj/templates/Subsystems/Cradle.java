
package edu.wpi.first.wpilibj.templates.Subsystems;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Cradle {
    
    private final Victor aimCradle = new Victor(Constants.CRADLE_VICTOR);
    
    private final AnalogChannel cradlePot = new AnalogChannel(Constants.CRADLE_POT);
    
    private final Solenoid cradleUp = new Solenoid(Constants.CRADLE_SOL_A);
    private final Solenoid cradleDown = new Solenoid(Constants.CRADLE_SOL_B);
    
    public Cradle(){
        
    }
    
    public void cradleDown()
    {
        cradleDown.set(true);
        cradleUp.set(false);
    }
    
    public void cradleUp()
    {
        cradleDown.set(false);
        cradleUp.set(true);
    }
    
    public void cradleMotor(double cradleSpeed)
    {
        aimCradle.set(cradleSpeed);
    }
    
}
