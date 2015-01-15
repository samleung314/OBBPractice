package edu.wpi.first.wpilibj.templates.Subsystems;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Drive {
    
    private final Victor leftVictorA = new Victor(Constants.LEFT_VICTOR_A);
    private final Victor leftVictorB = new Victor(Constants.LEFT_VICTOR_B);
    private final  Victor leftVictorC = new Victor(Constants.LEFT_VICTOR_C);
    
    private final Victor rightVictorA = new Victor(Constants.RIGHT_VICTOR_A);
    private final Victor rightVictorB = new Victor(Constants.RIGHT_VICTOR_B);
    private final Victor rightVictorC = new Victor(Constants.RIGHT_VICTOR_C);
    
    public final sudoVictor leftVics = new sudoVictor(leftVictorA, leftVictorB, leftVictorC);
    private final sudoVictor rightVics = new sudoVictor(rightVictorA, rightVictorB, rightVictorC);
    
    private final Solenoid highGear = new Solenoid(Constants.SHIFT_SOL_A);
    private final Solenoid lowGear = new Solenoid(Constants.SHIFT_SOL_B);  
    
    public Drive ()
    {
        
    } 
    
    public void manualVics(double leftSpeed, double rightSpeed) //Without using sudoVictor
    {
        leftVictorA.set(leftSpeed);
        leftVictorB.set(leftSpeed);
        leftVictorC.set(leftSpeed);
        
        rightVictorA.set(rightSpeed);
        rightVictorB.set(rightSpeed);
        rightVictorC.set(rightSpeed);
    }
    
    public void sudoVics(double leftSpeed, double rightSpeed)
    {
        leftVics.set(leftSpeed);
        rightVics.set(rightSpeed);
    }
     
     public void highGear()
    {
        highGear.set(false);
        lowGear.set(true);
    }
    
    public void lowGear()
    {
        highGear.set(true);
        lowGear.set(false);
    } 
}

