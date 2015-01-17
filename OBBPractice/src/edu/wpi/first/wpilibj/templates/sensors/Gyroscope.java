
package edu.wpi.first.wpilibj.templates.sensors;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.Gyro;

public class Gyroscope {
    
    public final Gyro gyro = new Gyro(Constants.GYRO);
     
    public Gyroscope()
    {
        
    }
    
    public double getAngle()
    {
        return gyro.getAngle();
    }
    
    public void resetGyro()
    {
        gyro.reset();
    }
    
}
