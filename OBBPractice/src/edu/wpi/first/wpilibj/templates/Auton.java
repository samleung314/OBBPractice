package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.Subsystems.*;
import edu.wpi.first.wpilibj.templates.sensors.*;

public class Auton {

    Drive drive;
    DisplayData data;
    Encoders encode;
    Gyroscope gyro;
    PID pid;

    boolean seg1, seg2, seg3, turn1, turn2;

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
        //pid.time.reset();
        //pid.time.start();

        //pid.straightPID.enable();
        //pid.straightPID.setSetpoint(0);
        //pid.distancePID.enable();

        seg1 = false;
        seg2 = false;
        seg3 = false;

        turn1 = false;
        turn2 = false;
    }

    public void AutonPeriodic() {
        data.AutoSmartDash();
        //pid.displayPID();
        zigZag(data.travSpeed);

        //pid.ramp(data.rampTime, data.rampSpeed);
    }

    public void zigZag(double speed) {
        if (!seg1) {
            seg1 = travelDistance(86, speed);
        }

        if ((seg1) && (!turn1)) {
            turn1 = turnDegrees(135, speed);
        }
        
        if ((turn1) && (!seg2)){
            seg2 = travelDistance(121.6, speed);
        }
        
        if ((seg2) && (!turn2)){
            turn2 = turnDegrees(-135, speed);
        }
        
        if ((turn2) && (!seg3)){
            seg3 = travelDistance(86, speed);
        }
    }

    public boolean travelDistance(double distance, double speed) {
        //pid.distancePID.setSetpoint(distance);

        if (encode.avgEncDist() < distance) {
            drive.sudoVics(speed, -speed);
            return false;

        } else if (encode.avgEncDist() >= distance) {

            drive.sudoVics(0, 0);
            encode.resetEnc();
            return true;
        }
        else return true;
    }

    public boolean turnDegrees(double degrees, double turnSpeed) {

        //double turnError = Math.abs(degrees - gyro.getAngle());
        if (gyro.getAngle() < Math.abs(degrees) - 3 || gyro.getAngle() > Math.abs(degrees) + 3) {

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
