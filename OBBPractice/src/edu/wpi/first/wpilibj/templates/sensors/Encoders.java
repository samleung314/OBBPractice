
package edu.wpi.first.wpilibj.templates.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.templates.*;

public class Encoders implements PIDSource {
    
    public static Encoder leftEnc = new Encoder(Constants.LEFT_ENC_X, Constants.LEFT_ENC_Y);
    public static Encoder rightEnc = new Encoder(Constants.RIGHT_ENC_X, Constants.RIGHT_ENC_Y);
    
    public Encoders()
    {
        
    }

    public double leftEncDist()
    {
        return convertDist(leftEnc.getDistance());
    }
    
    public double pidGet() {
        return avgEncDist();
    }
    
    public double rightEncDist()
    {
        return convertDist(rightEnc.getDistance());
    }
    
    public double convertDist(double rawDist)
    {
        double revs =  (rawDist)/(25.5);
        double inch = revs*(Math.PI * 4);
        return inch;
    }
    
    public double avgEncDist()
    {
        double avgEncDist = (leftEncDist() + rightEncDist())/2;
        return avgEncDist;
    }
    
    public void startEnc()
    {
        leftEnc.start();
        rightEnc.start();
        
        leftEnc.setDistancePerPulse(Math.PI/256);
        rightEnc.setDistancePerPulse(Math.PI/256);
        
        //One encoder revoultion is 3.14 inches. One revolution of the encoder is 256 ticks. One revolution of the wheel is 4Pi inches
    }
    
    public void stopEnc()
    {
        leftEnc.stop();
        rightEnc.stop();
    }
    
    public void resetEnc()
    {
        leftEnc.reset();
        rightEnc.reset();
    }
}
