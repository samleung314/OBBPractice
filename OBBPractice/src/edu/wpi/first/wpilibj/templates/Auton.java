package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class Auton {

    Drive drive;
    DisplayData data;
    Encoders encode;
    Gyroscope gyro;
    PID pid;

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
        
        pid.straightPID.setPID(0.36, 0, 0);
        pid.straightPID.setSetpoint(0);

        seg1 = false;
        seg2 = false;
        seg3 = false;
        seg4 = false;

        turn1 = false;
        turn2 = false;
        turn3 = false;
    }

    public void AutonPeriodic() {
        data.AutoSmartDash();
        pid.displayPID();
        zigZag(data.travSpeed);

        //pid.ramp(data.rampTime, data.rampSpeed);
    }

    public void zigZag(double speed) {
        if (!seg1) { 
            seg1 = travelDistance(85, speed);
        }

        if ((seg1) && (!turn1)) {
            turn1 = turnDegrees(90, speed);
        }
        
        if ((turn1) && (!seg2)){
            seg2 = travelDistance(82, speed);
        }
        
        if ((seg2) && (!turn2)){
            turn2 = turnDegrees(90, speed);
        }
        
        if ((turn2) && (!seg3)){
            seg3 = travelDistance(85, speed);
        }
        
        if ((seg3) && (!turn3)){
            turn3 = turnDegrees(90, speed);
        }
        
        if ((turn3) && (!seg4)){
            seg4 = travelDistance(82, speed);
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
            pid.straightPID.disable();
            drive.sudoVics(0, 0);
            encode.resetEnc();
            return true;
        }
        else return true;
    }

    public boolean turnDegrees(double degrees, double turnSpeed) {

        turnSpeed = turnSpeed *.8;
        //double turnError = Math.abs(degrees - gyro.getAngle());
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
