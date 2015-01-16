
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class Auton {
    Drive drive;
    DisplayData data;
    Encoders encode;
    Gyroscope gyro;
    PID pid; 
    
    boolean metPoint;
    boolean metTurn;
        
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
        //pid.time.reset();
        //pid.time.start();
        
        pid.straightPID.enable();
        pid.straightPID.setSetpoint(0);
        //pid.distancePID.enable();
    }
    
    public void AutonPeriodic(){
        data.AutoSmartDash();
        pid.displayPID();
        moveSequence();
        
        //pid.ramp(data.rampTime, data.rampSpeed);
    }
    
    public void moveSequence()
    {
        travelDistance(12, 0.5);
        if (metPoint)
        {
            turnDegrees(180, 0.6, 5);
            
            if (metTurn)
            {
                travelDistance(12, 0.5);
            }
        }
    }
    
    public void travelDistance(double distance, double speed)
    {
        //pid.distancePID.setSetpoint(distance);
        
        if (encode.avgEncDist() < distance)
        {
            metPoint = false;
            drive.sudoVics(speed, -speed);
        }
        else if (encode.avgEncDist() >= distance)
        {
            metPoint = true;
            drive.sudoVics(0, 0);
        }
    }
    
    public void turnDegrees(double degrees, double turnSpeed, double tolerance)
    {
        
        double turnError = Math.abs(degrees - gyro.getAngle());
        
        if (turnError > tolerance) //Sets tolerance for robot to stop
        {
            metTurn = false;
            
            if (degrees < 0) //Negative Degrees (CCW)
            {
                if (gyro.getAngle() > degrees)
                {
                    drive.sudoVics(-turnSpeed, -turnSpeed); //Turn CCW
                }
                else if (gyro.getAngle() < degrees)
                {
                    drive.sudoVics(turnSpeed, turnSpeed); //Turn CW
                }
            }
            else if (degrees > 0) //Positive Degrees (CW)
            {
                if (gyro.getAngle() > degrees)
                {
                    drive.sudoVics(-turnSpeed, -turnSpeed); //Turn CCW
                }
                else if (gyro.getAngle() < degrees)
                {
                     drive.sudoVics(turnSpeed, turnSpeed); //Turn CW
                }
            }
        }
        else if (turnError <= tolerance) // When within acceptable amount of error
        {
            metTurn = true;
            drive.sudoVics(0, 0);
        }
    }
}
