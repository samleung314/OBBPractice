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
    
    public double pS, pD, pT;
    public double iS, iD, iT; 
    public double dS, dD, dT;
    public double turnOutput;
    
    public PIDController straightPID;
    public PIDController turnPID;
    public PIDController distancePID;
    
    public PID(Drive drive, final Encoders encode, Gyroscope gyro) {
        this.drive  = drive;
        this.encode = encode;
        this.gyro   = gyro;

        straightPID = new PIDController(pS, iS, dS, gyro.gyro, drive.leftVics);
                // pass `this` because we are the pid output
        
        turnPID = new PIDController(pT, iT, dT, gyro.gyro, this);

        //distancePID = new PIDController(pD, iD, dD, encode, this);
        // PIDSource source = () -> encode.avgEncDist(); for Java 8
    }
    
    public void pidWrite(double output) {
        this.turnOutput = output;
    }
        
    public void displayPID() //Created to avoid cross referencing between DisplayData and PID
    {
         SmartDashboard.putData("StraightPID", straightPID);
         SmartDashboard.putData("TurnPID", turnPID);
         //SmartDashboard.putData("DistancePID", distancePID);
         SmartDashboard.putNumber("PID Turn Output", turnOutput);
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
