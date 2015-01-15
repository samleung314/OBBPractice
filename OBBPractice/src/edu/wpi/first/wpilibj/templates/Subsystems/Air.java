
package edu.wpi.first.wpilibj.templates.Subsystems;

import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.Compressor;

public class Air {
    
    
    public final Compressor compress = new Compressor(Constants.COMPRESSOR_SWITCH,Constants.COMPRESSOR_RELAY);
    
    public Air()
    {
        compress.enabled();
    }
    public void pressurize()
    {
            if (!compress.getPressureSwitchValue())
            {
                    compress.start(); 
            }
            else 
            {
                    compress.stop();
            }
    }
    
    public void stopPressurize()
    {
        compress.stop();
    }   
}
