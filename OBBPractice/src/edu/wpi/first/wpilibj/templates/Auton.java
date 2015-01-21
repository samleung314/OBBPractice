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
    int benscount;

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
        benscount = 0;

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
        squareSequence(0.5);
        //BentonSquare(10, .6, benscount);
        //pid.ramp(data.rampTime, data.rampSpeed);
    }

    public void squareSequence(double speed) {
        if (!seg1) {
            seg1 = travelDistance(38, speed);
        }

        if ((seg1) && (!turn1)) {
            turn1 = PIDturnDegrees(90);
        }

        if ((turn1) && (!seg2)) {
            seg2 = travelDistance(36, speed);
        }

        if ((seg2) && (!turn2)) {
            turn2 = PIDturnDegrees(90);
        }

        if ((turn2) && (!seg3)) {
            seg3 = travelDistance(38, speed);
        }

        if ((seg3) && (!turn3)) {
            turn3 = PIDturnDegrees(90);
        }

        if ((turn3) && (!seg4)) {
            seg4 = travelDistance(36, speed);
        }

        if ((seg4) && (!turn4)) {
            turn4 = PIDturnDegrees(90);
        }
    }

    public void BentonSquare(int distinches, double speed, int count) {
        if (count == 0) {
            if (encode.avgEncDist() < distinches) {
                travelDistance(distinches, speed);
            } else {
                drive.sudoVics(0, 0);
                time.delay(2000);
                benscount = 1;
                encode.resetEnc();
            }
        }
        if (count == 1) {
            if (encode.avgEncDist() < distinches) {
                drive.sudoVics(speed, -speed);
            } else {
                drive.sudoVics(0, 0);
                benscount = 2;
            }
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
        } else {
            return true;
        }
    }

    public boolean PIDturnDegrees(double degrees) {
        if (gyro.getAngle() < degrees - 2 && gyro.getAngle() > degrees + 2) {
            pid.turnPID.setSetpoint(degrees);
            pid.turnPID.enable();
            drive.sudoVics(pid.turnOutput, pid.turnOutput);
            return false;
        }
        else if (gyro.getAngle() > degrees - 2 && gyro.getAngle() < degrees + 2){
            pid.turnPID.disable();
            drive.sudoVics(0, 0);
            gyro.resetGyro();
            return true;
        }
        else{
            return true;
        }
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
            drive.sudoVics(0, 0);
            gyro.resetGyro();
            return true;
        }
    }
}
