package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;
import edu.wpi.first.wpilibj.Timer;

public class Auton {

    Drive drive;
    DisplayData data;
    Encoders encode;
    Gyroscope gyro;
    PID pid;
    Timer time = new Timer();

    boolean seg1, seg2, seg3, seg4, turn1, turn2, turn3, turn4;

    public Auton(Drive drive, Encoders encode, Gyroscope gyro, DisplayData data, PID pid) {
        this.data = data;
        this.drive = drive;
        this.encode = encode;
        this.gyro = gyro;
        this.pid = pid;
    }

    public void AutonInit() //Seperate auton init method so Auton objects aren't created everytime autonomous code is ran
    {
        data.Prefer();
        encode.resetEnc();
        encode.startEnc();
        gyro.resetGyro();
        
        pid.straightPID.setSetpoint(0);
        pid.turnPID.setContinuous();

        seg1 = false;
        seg2 = false;
        seg3 = false;
        seg4 = false;

        turn1 = false;
        turn2 = false;
        turn3 = false;
        turn4 = false;
    }

    public void AutonPeriodic() {
        data.AutoSmartDash();
        pid.displayPID();
        //turnTest();

        //pid.ramp(data.rampTime, data.rampSpeed);
    }
    
    public void turnTest(){
       PIDturnDegrees(data.turnDegree);
       time.delay(0.010);
    }

    public void squareSequence(double speed) {
        if (!seg1) { 
            seg1 = travelDistance(75/2, speed);
        }

        if ((seg1) && (!turn1)) {
            turn1 = turnDegrees(90, speed);
        }
        
        if ((turn1) && (!seg2)){
            seg2 = travelDistance(72/2, speed);
        }
        
        if ((seg2) && (!turn2)){
            turn2 = turnDegrees(90, speed);
        }
        
        if ((turn2) && (!seg3)){
            seg3 = travelDistance(75/2, speed);
        }
        
        if ((seg3) && (!turn3)){
            turn3 = turnDegrees(90, speed);
        }
        
        if ((turn3) && (!seg4)){
            seg4 = travelDistance(72/2, speed);
        }
        
        if ((seg4) && (!turn4)){
            turn4 = turnDegrees(90, speed);
        }
    }

    public boolean travelDistance(double distance, double speed) {
        //pid.distancePID.setSetpoint(distance);

        if (encode.avgEncDist() < distance) {
            pid.straightPID.enable();
            drive.sudoVics(speed, -speed);
            return false;

        } else if (encode.avgEncDist() >= distance) {
            drive.sudoVics(0, 0);
            pid.straightPID.disable();
            encode.resetEnc();
            return true;
        }
        else return true;
    }
    
    public void PIDturnDegrees(double degrees) {
            pid.turnPID.setSetpoint(degrees);
            pid.turnPID.enable();
            drive.sudoVics(pid.turnOutput, pid.turnOutput);
            
            
    }

    public boolean turnDegrees(double degrees, double turnSpeed) {

        if (gyro.getAngle() < degrees - 2 || gyro.getAngle() > degrees + 2) {

            if (degrees < 0) //Negative Degrees (CCW)
            {
                if (gyro.getAngle() > degrees) {
                    drive.sudoVics(-turnSpeed, -turnSpeed); //Turn CCW
                } else if (gyro.getAngle() < degrees) {
                    drive.sudoVics(turnSpeed, turnSpeed); //Turn CW
                }
            } else if (degrees > 0) //Positive Degrees (CW)
            {
                if (gyro.getAngle() > degrees) {
                    drive.sudoVics(-turnSpeed, -turnSpeed); //Turn CCW
                } else if (gyro.getAngle() < degrees) {
                    drive.sudoVics(turnSpeed, turnSpeed); //Turn CW
                }
            }
            return false;
        } else {
            gyro.resetGyro();
            return true;
        }
    }
}
