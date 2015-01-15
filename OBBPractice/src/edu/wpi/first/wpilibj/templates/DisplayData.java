package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;
//import edu.wpi.first.wpilibj.Ultrasonic;


public class DisplayData {
    
    Drive drive;
    Encoders encode;
    Gyroscope gyro;
    //Ultrasonic ultraA, ultraB;
    
    private final Preferences pref = Preferences.getInstance();
    public double rampSpeed, rampTime, travDistance, travSpeed;
    
    public DisplayData(Drive drive, Encoders encode, Gyroscope gyro) {
        this.drive = drive;
        this.gyro  = gyro;
        this.encode = encode;
        //ultraA = new Ultrasonic(14,2);
        //ultraA.setAutomaticMode(true);
        //ultraB = new Ultrasonic(8,9);
        //ultraB.setAutomaticMode(true);
    }
    
    public void AutoSmartDash()
    {
        double leftEncDist = encode.leftEncDist();
        double rightEncDist = encode.rightEncDist();
        double avgEncDist = encode.avgEncDist();

        SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
        
        SmartDashboard.putNumber("Left Encoder Inches", leftEncDist);
        SmartDashboard.putNumber("Right Encoder Inches", rightEncDist);
        SmartDashboard.putNumber("Avg Encoder Inches", avgEncDist);
        
        SmartDashboard.putNumber("Inches to travel", travDistance);
        
//        if(pid != null) {
//            if(pid.straightPID != null) {
//                SmartDashboard.putData("PID Controller", pid.straightPID); //Used for PID Tuning
//            }
//        }
    }
    
    public void TeleopSmartDash()
    {
       //SmartDashboard.putNumber("Sonic 1", ultraA.getRangeInches());
       //SmartDashboard.putNumber("Sonic 2", ultraB.getRangeInches());   
    }
    
    public void Prefer()
    {
        travDistance = pref.getDouble("Distance", 0);
        travSpeed = pref.getDouble("Speed", 0);
        
        rampTime = pref.getDouble("RampTime", 0);
        rampSpeed = pref.getDouble("RampSpeed", 0);
        
        pref.save();
        //SmartDashboard > View > Add > Robot Preferences 
    }
}
