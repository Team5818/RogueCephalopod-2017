package org.usfirst.frc.team5818.robot.utils;

/*Like a Command but FAST*/
public interface FastLoop{
    

    public abstract boolean isFinished();


    public abstract void update();


    public abstract void done();


    public abstract void start();
    
}
