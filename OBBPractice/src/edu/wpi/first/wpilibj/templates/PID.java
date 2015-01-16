package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class PID implements PIDOutput {
    
    Drive drive;
    Encoders encode;
    Gyroscope gyro;
    Timer time = new Timer(); 
    
    public double pS, pD;
    public double iS, iD; 
    public double dS, dD;
    public double pidOutput;
    
    public PIDController straightPID;
    public PIDController distancePID;
    
    public PID(Drive drive, final Encoders encode, Gyroscope gyro) {
        this.drive  = drive;
        this.encode = encode;
        this.gyro   = gyro;

        straightPID = new PIDController(pS, iS, dS, gyro.gyro, drive.leftVics);
                // pass `this` because we are the pid output

        distancePID = new PIDController(pD, iD, dD, encode, this);
        // PIDSource source = () -> encode.avgEncDist(); for Java 8
    }
    
    public void pidWrite(double output) {
        this.pidOutput = output;
    }
        
    public void displayPID() //Created to avoid cross referencing between DisplayData and PID
    {
         SmartDashboard.putData("StraightPID", straightPID);
         //SmartDashboard.putData("DistancePID", distancePID);
         SmartDashboard.putNumber("PID Output", pidOutput);
    }
    
    public void ramp(double seconds, double speed)
    {
        double currTime = time.get();
        //double last = seconds * 1000; //convert to miliseconds
        
        System.out.println(currTime);
        
        if (currTime < seconds)
        {
            drive.sudoVics(currTime * (speed/seconds) , -currTime * (speed/seconds));
        }
        else if (currTime >= seconds)
        {
            drive.sudoVics(speed, -speed);
        }
        
    }
}
