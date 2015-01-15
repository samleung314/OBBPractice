package edu.wpi.first.wpilibj.templates.Subsystems;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

public class sudoVictor implements SpeedController, IDeviceController
{
    private Victor vic1; 
    private Victor vic2;
    private Victor vic3;
    private double value; 
    
    public sudoVictor (Victor v1, Victor v2, Victor v3)
    {
        if (v1 == null || v2 == null || v3 == null)
            throw new NullPointerException("Hey, you didn't put three victors, bud");
        vic1 = v1; 
        vic2 = v2; 
        vic3 = v3; 
    }
    public void set(double a, byte b)
    {
        //nothing to say
    }
    public double get()
    {
        return value;
    }
    public void set(double val)
    { 
        value = val;
        vic1.set(value);
        vic2.set(value);
        vic3.set(value);
    }
    public void pidWrite(double output) {
        set(output);
    }
    public void disable()
    {
        vic1.set(0);
        vic2.set(0);
        vic3.set(0);
    }
}
