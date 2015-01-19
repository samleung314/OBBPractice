package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class RobotTemplate extends IterativeRobot {
   
    Air air;
    Auton autonomous;
    Cradle cradle;
    Drive drive;
    DisplayData data;
    Controllers control;
    Encoders encode;
    Gyroscope gyro;
    IntakeArms arms;
    PID pid;
    
    public void robotInit() {
        air    = new Air();
        cradle = new Cradle();
        drive  = new Drive();
        arms   = new IntakeArms();
        encode = new Encoders();
        gyro   = new Gyroscope();
        control = new Controllers(air, arms, cradle, drive);
        pid    = new PID(drive, encode, gyro);
        data   = new DisplayData(drive, encode, gyro);  
        autonomous = new Auton(drive, encode, gyro, data, pid);
        
        pid.displayPID();
    }
    
    public void autonomousInit(){ 
        autonomous.AutonInit();
    }

    public void autonomousPeriodic() {
        autonomous.AutonPeriodic();  
    }

    public void teleopInit(){
        pid.turnPID.disable();
        pid.straightPID.disable();
    }
    
    public void teleopPeriodic() {
        control.joystickDrive();
        data.TeleopSmartDash();
    }
    
    public void disabledInit()
    {
        pid.turnPID.disable();
        pid.straightPID.disable();
    }
    
    public void testPeriodic() {
    
    }
    
}
