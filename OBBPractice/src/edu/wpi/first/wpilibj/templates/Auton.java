
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class Auton {
    Drive drive;
    DisplayData data;
    Encoders encode;
    Gyroscope gyro;
    PID pid; 
        
    public Auton(Drive drive, Encoders encode, Gyroscope gyro, DisplayData data, PID pid) {
        this.data = data;
        this.drive  = drive;
        this.encode = encode;
        this.gyro   = gyro;
        this.pid = pid;
    }
    
    public void AutonInit() //Seperate auton init method so Auton objects aren't created everytime autonomous code is ran
    {
        data.Prefer();
        encode.resetEnc();
        encode.startEnc();
        gyro.resetGyroCount();
        pid.time.reset();
        pid.time.start();
        
        pid.straightPID.enable();
        pid.straightPID.setSetpoint(0);
        pid.distancePID.enable();
    }
    
    public void AutonPeriodic(){
        data.AutoSmartDash();
        pid.displayPID();
        travelDistance(data.travDistance, data.travSpeed);
        //pid.ramp(data.rampTime, data.rampSpeed);
    }
    
    public void travelDistance(double distance, double speed)
    {
        pid.distancePID.setSetpoint(distance);
        
        drive.sudoVics(pid.pidOutput, -pid.pidOutput);
        
        /* USING RAMP PID
        if ((encode.leftEncDist() < distance) || (encode.rightEncDist() < distance))
        {
            drive.sudoVics(speed, -speed);
        }
        else if ((encode.leftEncDist() >= distance) || (encode.rightEncDist() >= distance))
        {
            drive.sudoVics(0, 0);
        }
        */
    }
    
    
    public void turnDegrees(double degrees, double turnSpeed)
    {
        if (degrees < 0) //Negative Degrees (CCW)
        {
            if (gyro.getAngle() > degrees)
            {
                
            }
            else if (gyro.getAngle() < degrees)
            {
                
            }
        }
        else if (degrees > 0) //Positive Degrees (CW)
        {
            if (gyro.getAngle() > degrees)
            {
                
            }
            else if (gyro.getAngle() < degrees)
            {
                
            }
        }
    }
}
